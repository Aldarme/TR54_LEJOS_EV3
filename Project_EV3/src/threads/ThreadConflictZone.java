package threads;

import lejos.hardware.lcd.LCD;
import robotmodel.Position;
import robotmodel.Robot;

/**
 * Forgot the idea of thread
 * it's just a class who manage when a robot enter in conflict area
 * @author promet
 *
 */
public class ThreadConflictZone implements Runnable {
	
	private Robot myThreadRobot;
	int dist = 2;
	
	/**
	 * Builder by default
	 * @param pRobot
	 */
	public ThreadConflictZone(Robot pRobot) {
		myThreadRobot = pRobot;
	}
	
	/**
	 * run function
	 * send request to server
	 */
	public void run()
	{
		//set current position of the robot
		myThreadRobot.setPosition(Position.CONFLICT);
		
		//send current position to the server
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition(),
										ThreadEntreeZone.curveDefined
									 );
		
		myThreadRobot.motorController.tachyReset();
		
		while(myThreadRobot.motorController.getTachy() < 900)
		{
			myThreadRobot.motorController.forward();	
		}
	}

}
