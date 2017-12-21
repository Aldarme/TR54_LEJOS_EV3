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
	
	public ThreadEntreeZone(Robot pRobot) {
		myThreadRobot = pRobot;
	}
	
	public void run()
	{
		//set current position of the robot
		myThreadRobot.setPosition(Position.ENTREE);
		
		//send current position to the server
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition(),
										myThreadRobot.getCurCurve()
									);

		myThreadRobot.motorController.tachyReset();
		
		while(myThreadRobot.motorController.getTachy() < 720)
		{
			LCD.drawString(Integer.toString(myThreadRobot.motorController.getTachy()), 0, 1);
			myThreadRobot.motorController.forward();
		}
	}
}
