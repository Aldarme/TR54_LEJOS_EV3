package network;

import java.io.IOException;

/**
 * 
 * @author promet
 *
 */

public class SendServer {
	
//	public static SendServer() {
//		// TODO Auto-generated constructor stub
//	}
	
	public static void dataToSend(int pID, int pSpeed, int pPosition)
	{
		try {
			CentralizedSync.sendPos( Integer.toString(pID) + "\r\n" 
									+ Integer.toString(pPosition) + "\r\n" 
									+ Integer.toString(pSpeed) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
