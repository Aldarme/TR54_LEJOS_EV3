package network;

import java.io.IOException;

/**
 * 
 * @author promet
 *
 */

public class SendServer {
	
	public SendServer() {
		// TODO Auto-generated constructor stub
	}
	
	public void dataToSend(int pID, int pPosition, int pSpeed)
	{
		try {
			CentralizedSync.sendPos( Integer.toString(pID) + "\r\n" + Integer.toString(pPosition) + "\r\n" +  Integer.toString(pSpeed) + "\r\n" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
