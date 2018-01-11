package threads;

import lejos.hardware.lcd.LCD;
import robotmodel.Position;
import robotmodel.Robot;

/**
 * 
 * @author promet
 *
 */

public class ThreadConflictZone implements Runnable {
	
	private Robot myThreadRobot;
	int dist = 2;
	
	public ThreadConflictZone(Robot pRobot) {
		myThreadRobot = pRobot;
	}
	
	public void run()
	{
		//set current position of the robot
		myThreadRobot.setPosition(Position.CONFLICT);
		
		//send current position to the server
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition(),
										ThreadEntreeZone.curveDefined
										//myThreadRobot.getCurCurve()
									 );
		
		myThreadRobot.motorController.tachyReset();
		
		while(myThreadRobot.motorController.getTachy() < 900)
		{
			//LCD.drawString(Integer.toString(myThreadRobot.motorController.getTachy()), 0, 1);
			myThreadRobot.motorController.forward();	
		}
	}

}
