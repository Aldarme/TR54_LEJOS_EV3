package threads;

import robotmodel.Position;
import robotmodel.Robot;

/**
 * 
 * @author promet
 *
 */

public class ThreadSortieZone implements Runnable {
	
	private Robot myThreadRobot;
	
	public ThreadSortieZone(Robot pRobot) {
		myThreadRobot = pRobot;
	}
	
	public void run()
	{
		//set current position of the robot
		myThreadRobot.setPosition(Position.SORTIE);
		
		//send current position to the server
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition() );

		myThreadRobot.nbrRotateWheel((float)(360*2));		
	}
}
