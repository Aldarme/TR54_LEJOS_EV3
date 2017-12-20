package threads;

import robotmodel.Robot;

/**
 * 
 * @author promet
 *
 */

public class ThreadFollowLine implements Runnable{
	
	private Robot myThreadRobot;
	private int[] threadFollowLineData;

	public ThreadFollowLine(Robot pRobot, int[] pData) {
		myThreadRobot = pRobot;
		threadFollowLineData = pData;
	}
	
	@Override
	public void run() 
	{
		for(;;)
		{
			threadFollowLineData[0] = myThreadRobot.moveModeController.followLine(threadFollowLineData[1]);
		}		
	}

}
