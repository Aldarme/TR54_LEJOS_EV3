package network;

import java.nio.ByteBuffer;

import robotmodel.Robot;

public class RobotRcvListner implements BroadcastListener{

	private Robot myRobot;
	
	public RobotRcvListner(Robot pMyRobot) {
		this.myRobot = pMyRobot;
	}
	
	@Override
	public void onBroadcastReceived(byte[] message)
	{
		//get string from wifi
		String lstnerValidServer = ByteBuffer.wrap(message).toString();
		
		//convert String to Boolean
		Boolean temp = Boolean.valueOf(lstnerValidServer);
		
		this.myRobot.SetValidServer(temp);
	}
}
