package threads;

import lejos.hardware.lcd.LCD;
import robotmodel.*;

/**
 * 
 * @author promet
 *
 */

public class ThreadEntreeZone implements Runnable {
	
	private Robot myThreadRobot;
	
	static int curveDefined = 0;
	
	public ThreadEntreeZone(Robot pRobot) {
		myThreadRobot = pRobot;
	}
	
	public void run()
	{
		//set current position of the robot
		myThreadRobot.setPosition(Position.ENTREE);
		curveDefined = myThreadRobot.getCurCurve();
		LCD.drawString("Virage", 0, 1);
		LCD.drawInt(curveDefined, 0, 2);
		//send current position to the server
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition(),
										curveDefined
									);

		myThreadRobot.motorController.tachyReset();
		
		while(myThreadRobot.motorController.getTachy() < 720)
		{
			//LCD.drawString(Integer.toString(myThreadRobot.motorController.getTachy()), 0, 1);
			myThreadRobot.motorController.forward();
		}
	}
}
