package threads;

import robotmodel.Position;
import robotmodel.Robot;

/**
 * 
 * @author promet
 *
 */

public class ThreadStockZone implements Runnable {
	
	private Robot myThreadRobot;
	
	public ThreadStockZone(Robot pRobot) {
		myThreadRobot = pRobot;
	}
	
	public void run()
	{
		myThreadRobot.setPosition(Position.STOCKAGE);
		
		//send current position to the server
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition(),
										myThreadRobot.getCurCurve()
									);
		
		myThreadRobot.motorController.tachyReset();
		
		while(myThreadRobot.motorController.getTachy() < 180)
		{
			//LCD.drawString(Integer.toString(myThreadRobot.motorController.getTachy()), 0, 1);		
		}
	}

}
