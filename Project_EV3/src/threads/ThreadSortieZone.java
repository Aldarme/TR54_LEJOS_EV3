package threads;

import lejos.hardware.lcd.LCD;
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
										myThreadRobot.getPosition(),
										myThreadRobot.getCurCurve()
									);

		//.myThreadRobot.motorController.tachyReset();
		
		while(myThreadRobot.motorController.getTachy() < 360)
		{
			LCD.drawString(Integer.toString(myThreadRobot.motorController.getTachy()), 0, 1);
			myThreadRobot.motorController.forward();
		}
		
	}
}
