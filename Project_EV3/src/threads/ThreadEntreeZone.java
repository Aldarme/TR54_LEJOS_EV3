package threads;

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
		
		//send to the server, the current position
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition(),
										myThreadRobot.getCurCurve()
									  );
		
		myThreadRobot.motorController.tachyReset();
		while(myThreadRobot.motorController.getTachy() < 360)
		{
			//On boucle			
		}
	}
}
