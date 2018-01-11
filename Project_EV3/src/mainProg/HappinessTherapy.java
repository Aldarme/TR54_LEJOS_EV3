package mainProg;

import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import robotmodel.*;
import threads.*;

/**
 * Main class
 * @author promet
 * modify by ncarrion
 */

public class HappinessTherapy {

	public static void main(String[] args) throws InterruptedException, IOException
	{
		

		int robotID = 0;
        int button = 0;
        
        while(button != Button.ID_ENTER)
        {
                LCD.clear();
                LCD.drawString("Choose Robot ID",0,0);
                LCD.drawString("ID : " + robotID,0,1);
                button = Button.waitForAnyPress();
                if(button == Button.ID_UP) {
                    if(robotID < 9)
                         robotID++;
                }
                else if(button == Button.ID_DOWN) {
                        if(robotID >0)
                                robotID--;
                }
        }
        LCD.clear();
        LCD.drawString("TR54 forever",0,0);
        LCD.drawString("Beast number "+ robotID,0,1);
        LCD.drawString("Activated !!!",0,2);
        
		//Init my robot with an ID
        Robot myRobot = new Robot(robotID);
		
		myRobot.setLedMode(0);
		
		float[] orangeTab = new float[] {0.11f,0.2f,0.04f,0.08f,0.002f,0.08f};
		int[] followLineData = new int[1]; //[0] = currentCurve
		
		//Thread
		Thread threadFollowLine = new Thread(new ThreadFollowLine(myRobot, followLineData));
		Thread threadEntree = new Thread(new ThreadEntreeZone(myRobot));
		Thread threadStock = new Thread(new ThreadStockZone(myRobot));
		Thread threadConflict = new Thread(new ThreadConflictZone(myRobot));
		Thread threadSortie = new Thread(new ThreadSortieZone(myRobot));

		LCD.clear();
		myRobot.motorController.init();			
		network.CentralizedSync.addRobotRcvListner(myRobot);
		
		//Activate followLine Thread
		threadFollowLine.start();
		
		for(;;)
		{
			myRobot.motorController.forward();
			/*
			 * Orange mark detection
			 */
			if ((myRobot.getColorSensor()[0] > orangeTab[0] && 	myRobot.getColorSensor()[0] <  orangeTab[1]	)		
				&& (myRobot.getColorSensor()[1] > orangeTab[2] && myRobot.getColorSensor()[1] <  orangeTab[3]	)
				&& (myRobot.getColorSensor()[2] > orangeTab[4] && myRobot.getColorSensor()[2] <  orangeTab[5]	) 
				)
			{
				//blinked red
				myRobot.setLedMode(5);
				//Send current Curve					
				if(followLineData[0] < 0)
				{
					myRobot.setCurCurve(followLineData[0]);
				}
				else if(followLineData[0] > 0)
				{
					myRobot.setCurCurve(followLineData[0]);
				}
				
				//Start the Thread for Entree Zone
				threadEntree.run();
				
				//Start the Thread for Stock Zone
				threadStock.run();

				if(myRobot.getValidServer() == false)
				{
					myRobot.StopMotor();
					
					//Wait that "ValidServer" was update from false to true
					while(myRobot.getValidServer() == false){}
					
					//restart engins
					myRobot.motorController.init();
					
				}
				
				//Start the Thread for Conflict Zone
				threadConflict.run();
				
				//Start the Thread for Sortie Zone
				threadSortie.run();

				//nothing
				myRobot.setLedMode(0);
			}
		}
	}
}