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
	
	public static void dataToSend(int pID, int pSpeed, int pPosition, int pCurve)
	{
		try {
			CentralizedSync.sendPos( Integer.toString(pID) + "\n" 
									+ Integer.toString(pPosition) + "\n" 
									+ Integer.toString(pSpeed)+ "\n" 
									+ Integer.toString(pCurve)+ "\n-1"
									);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
