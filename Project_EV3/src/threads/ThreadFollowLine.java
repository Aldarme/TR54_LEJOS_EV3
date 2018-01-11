package threads;

import robotmodel.Robot;

/**
 * Forgot the idea of thread
 * it's just a class who manage robot intelligence
 * @author promet
 *
 */
public class ThreadFollowLine implements Runnable{
	
	private Robot myThreadRobot;
	private int[] threadFollowLineData;
	
	/**
	 * Builder by default
	 * @param pRobot
	 */
	public ThreadFollowLine(Robot pRobot, int[] pData) {
		myThreadRobot = pRobot;
		threadFollowLineData = pData;
	}
	
	/**
	 * run function
	 * manage robot intelligence
	 */
	@Override
	public void run() 
	{
		for(;;)
		{
			threadFollowLineData[0] = myThreadRobot.moveModeController.followLine();
		}		
	}

}
