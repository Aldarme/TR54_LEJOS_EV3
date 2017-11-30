package network;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;

/**
 * 
 * @author promet
 *
 */

public class CentralizedSync {	

	public static void sendPos(float pPosition) throws SocketException, IOException
	{
		byte[] tmp = ByteBuffer.allocate(4).putFloat(pPosition).array();
		
		BroadcastManager.getInstance().broadcast(tmp);
	}

	public static void sendPos(String DataToSend) throws SocketException, IOException
	{
		byte[] tmp = DataToSend.getBytes();
		
		BroadcastManager.getInstance().broadcast(tmp);
	}


	
}
