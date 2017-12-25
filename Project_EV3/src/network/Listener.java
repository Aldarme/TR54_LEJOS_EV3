package network;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class Listener implements BroadcastListener {

	private ReceiveServer myServerListener;
	
	public Listener(ReceiveServer myServer)	{
		this.myServerListener = myServer;
	}
	
	@Override	
	public void onBroadcastReceived(byte[] message) throws SocketException, IOException {
		String data = ByteBuffer.wrap(message).toString();
		//data = new String(message);
//		LCD.clear();
//		LCD.drawString("Data : " + data, 0, 2);
		System.out.println("Data : " + data);
		
		myServerListener.processData(data);
		
	}
}
