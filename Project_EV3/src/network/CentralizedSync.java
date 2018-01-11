package network;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;

import robotmodel.Robot;

/**
 * 
 * @author promet
 *
 */

public class CentralizedSync {	

	/**
	 * Listner to get acquitment from server for ValidServer
	 * @param myRobot
	 * @throws IOException
	 */
	public static void addRobotRcvListner(Robot myRobot) throws IOException
	{
		RobotRcvListner myRcvListner = new RobotRcvListner(myRobot);
		BroadcastReceiver.getInstance().addListener(myRcvListner);
	}

	/**
	 * Send robots information as a String
	 * @param DataToSend
	 * @throws SocketException
	 * @throws IOException
	 */
	public static void sendPos(String DataToSend) throws SocketException, IOException
	{
		byte[] tmp = DataToSend.getBytes();
		
		BroadcastManager.getInstance().broadcast(tmp);
	}


	
}
