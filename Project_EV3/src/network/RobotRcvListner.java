package network;

import java.nio.ByteBuffer;

public class RobotRcvListner implements BroadcastListener{

	private String lstnerValidServer;
	
	public RobotRcvListner() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onBroadcastReceived(byte[] message)
	{
		lstnerValidServer = ByteBuffer.wrap(message).toString();
	}
	
	public boolean getBool()
	{
		Boolean temp = Boolean.valueOf(lstnerValidServer);
		return temp;
	}
}
