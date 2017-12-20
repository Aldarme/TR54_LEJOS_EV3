package network;

import java.nio.ByteBuffer;
import java.util.Vector;

import robotmodel.Robot;

public class RobotRcvListner implements BroadcastListener{

	private Robot myRobot;
	private String Delimiter = "\r\n";	
	
	public RobotRcvListner(Robot pMyRobot) {
		this.myRobot = pMyRobot;
	}
	
	@Override
	public void onBroadcastReceived(byte[] message)
	{
		//get string from wifi
		String lstnerValidServer = ByteBuffer.wrap(message).toString();	
		
		//get Server
		String subString[] = lstnerValidServer.split(Delimiter);
		
		int size = (subString.length-2)/3;
		
		if(subString[0] == "server")
		{
			for(int i=0; i < size; i++)
			{
				if(subString[(3*i)+1] == Integer.toString(this.myRobot.getId()))
				{
					//Robot could move across conflict zone
					this.myRobot.SetValidServer(true);
				}
			}
		}
		
		
		
		
		
		
		
	}
}
