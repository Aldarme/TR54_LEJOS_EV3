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

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException
	{

		//Init my robot with an ID
		Robot myRobot = new Robot(0);
		ReceiveServer myServer = new ReceiveServer();
		
		int curve = 0;
		
		float orangeTab[] = new float[] {0.11f,0.2f,0.04f,0.08f,0.002f,0.08f};
		
		//Thread
		Thread threadEntree = new Thread(new ThreadEntreeZone(myRobot));
		Thread threadStock = new Thread(new ThreadStockZone(myRobot));
		Thread threadConflict = new Thread(new ThreadConflictZone(myRobot));
		Thread threadSortie = new Thread(new ThreadConflictZone(myRobot));

		LCD.drawString("Hello my friend!!!", 0, 1);
		LCD.drawString("Commands:", 0, 2);
		LCD.drawString("Right : server", 0, 3);
		LCD.drawString("Left : pilote", 0, 4);
		
		final int button = Button.waitForAnyPress();
		
		if(button == Button.ID_RIGHT) {
			LCD.clear();
			LCD.drawString("Server mode", 0, 1);
			myServer.mainServer();
		}
		else if(button == Button.ID_LEFT) {
			LCD.clear();
			myRobot.motorController.init();
			network.CentralizedSync.addRobotRcvListner(myRobot);
			
			for(;;)
			{
				/*
				 * Lancer la politique de suivis de ligne
				 */
				int debug = 0;
				debug = myRobot.moveModeController.followLine(curve);
				
				/*
				 * Orange mark detection
				 */
				if ((myRobot.getColorSensor()[0] > orangeTab[0] && 	myRobot.getColorSensor()[0] <  orangeTab[1]	)	//get real value
					&& (myRobot.getColorSensor()[1] > orangeTab[2] && myRobot.getColorSensor()[1] <  orangeTab[3]	)	//of ORANGE RGB
					&& (myRobot.getColorSensor()[2] > orangeTab[4] && myRobot.getColorSensor()[2] <  orangeTab[5]	) 
					)
				{
					//Send current Curve
					if( debug != 0)
					{
						if(curve < 0)
						{
							LCD.drawString("Right", 0, 4, false);
							myRobot.setCurCurve(curve);
						}
						else if(curve > 0)
						{
							LCD.drawString("Left ", 0, 4, false);
							myRobot.setCurCurve(curve);
						}					
					}					
					
					//Start the Thread for Entree Zone
					threadEntree.start();
					threadEntree.join();
					
					//Start the Thread for Stock Zone
					threadStock.start();
					threadStock.join();
					
					if(myRobot.getValidServer() == false)
					{
						myRobot.StopMotor();						
						
						//Wait that "ValidServer" was update from false to true
						while(myRobot.getValidServer() == false){}
						
						//restart engins
						myRobot.motorController.init();
						
					}
					
					//Start the Thread for Conflict Zone
					threadConflict.start();				
					threadConflict.join();
					
					//Start the Thread for Sortie Zone
					threadSortie.start();	
					threadSortie.join();
				}
				
				//Thread.sleep(100);
			}
		}
	}
}
