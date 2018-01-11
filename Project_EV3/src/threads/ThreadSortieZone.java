package threads;

import lejos.hardware.lcd.LCD;
import robotmodel.Position;
import robotmodel.Robot;

/**
 * Forgot the idea of thread
 * it's just a class who manage when robot leave conflict area
 * @author promet
 *
 */
public class ThreadSortieZone implements Runnable {
	
	private Robot myThreadRobot;
	
	/**
	 * Builder by default
	 * @param pRobot
	 */	
	public ThreadSortieZone(Robot pRobot) {
		myThreadRobot = pRobot;
	}
	/**
	 * run function
	 * send request to server
	 */
	public void run()
	{
		//set current position of the robot
		myThreadRobot.setPosition(Position.SORTIE);
		myThreadRobot.setLedMode(0);
		
		//send current position to the server
		network.SendServer.dataToSend(	myThreadRobot.getId(), 
										myThreadRobot.getSpeed(), 
										myThreadRobot.getPosition(),
										ThreadEntreeZone.curveDefined
										//myThreadRobot.getCurCurve()
									);

		//.myThreadRobot.motorController.tachyReset();
		
		while(myThreadRobot.motorController.getTachy() < 180)
		{
			//LCD.drawString(Integer.toString(myThreadRobot.motorController.getTachy()), 0, 1);
			myThreadRobot.motorController.forward();
		}
		myThreadRobot.SetValidServer(false);

		myThreadRobot.setLedMode(0);
	}
}
