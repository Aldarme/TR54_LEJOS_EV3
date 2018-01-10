package mainProg;

import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import network.ReceiveServer;
import robotmodel.*;
import threads.*;

/**
 * 
 * @author promet
 * modify by ncarrion
 */

public class HappinessTherapy {

	public static void main(String[] args) throws InterruptedException, IOException
	{

		//Init my robot with an ID
		Robot myRobot = new Robot(0);
		ReceiveServer myServer = new ReceiveServer();
		
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
//			LCD.drawString(Float.toString( myRobot.getColorSensor()[0] ) ,0, 0);
//			LCD.drawString(Float.toString( myRobot.getColorSensor()[1] ) ,0, 1);
//			LCD.drawString(Float.toString( myRobot.getColorSensor()[2] ) ,0, 2);
			/*
			 * Orange mark detection
			 */
			if ((myRobot.getColorSensor()[0] > orangeTab[0] && 	myRobot.getColorSensor()[0] <  orangeTab[1]	)		//get real value of ORANGE RGB
				&& (myRobot.getColorSensor()[1] > orangeTab[2] && myRobot.getColorSensor()[1] <  orangeTab[3]	)
				&& (myRobot.getColorSensor()[2] > orangeTab[4] && myRobot.getColorSensor()[2] <  orangeTab[5]	) 
				)
			{
				//Send current Curve					
				if(followLineData[0] < 0)
				{
					//LCD.drawString("Right", 0, 4, false);
					myRobot.setCurCurve(followLineData[1]);
					LCD.drawString(Integer.toString(followLineData[1]), 1, 1);
				}
				else if(followLineData[0] > 0)
				{
					//LCD.drawString("Left ", 0, 4, false);
					myRobot.setCurCurve(followLineData[1]);
					LCD.drawString(Integer.toString(followLineData[1]), 1, 1);
				}
				
				//Start the Thread for Entree Zone
				threadEntree.run();
				
				//Start the Thread for Stock Zone
				threadStock.run();
				
				if(myRobot.getValidServer() == false)
				{
					myRobot.StopMotor();
					//LCD.drawString("motor stopped", 0, 1);
					
					//Wait that "ValidServer" was update from false to true
					while(myRobot.getValidServer() == false){}
					
					//restart engins
					myRobot.motorController.init();
					
				}
				
				//Start the Thread for Conflict Zone
				threadConflict.run();
				
				//Start the Thread for Sortie Zone
				threadSortie.run();
			}
		}
	}
}