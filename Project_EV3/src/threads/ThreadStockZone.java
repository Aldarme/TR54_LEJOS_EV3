package threads;

import lejos.hardware.lcd.LCD;
import robotmodel.Position;
import robotmodel.Robot;

/**
 * Forgot the idea of thread
 * it's just a class who manage when robot enter in stock area
 * @author promet
 *
 */
public class ThreadStockZone implements Runnable {
	
	private Robot myThreadRobot;
	
	/**
	 * Builder by default
	 * @param pRobot
	 */
	public ThreadStockZone(Robot pRobot) {
		myThreadRobot = pRobot;
	}
	
	/**
	 * run function
	 * send request to server
	 */
	public void run()
	{
		//set current position of the robot
		myThreadRobot.setPosition(Position.STOCKAGE);
		
		//send current position to the server
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition(),
										ThreadEntreeZone.curveDefined
										
										//myThreadRobot.getCurCurve()
									);

		myThreadRobot.motorController.tachyReset();
		
		while(myThreadRobot.motorController.getTachy() < 180)
		{
			//LCD.drawString(Integer.toString(myThreadRobot.motorController.getTachy()), 0, 1);
			myThreadRobot.motorController.forward();
		}
}

}
