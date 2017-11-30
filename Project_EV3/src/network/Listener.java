package network;

import java.nio.ByteBuffer;

import lejos.hardware.lcd.LCD;
import network.BroadcastListener;

public class Listener implements BroadcastListener {

	private String data;
	
	Listener()	{
		
	}
	
	@Override
	
	public void onBroadcastReceived(byte[] message) {
		data = ByteBuffer.wrap(message).toString();
		LCD.clear();
		LCD.drawString(String.format("Data : ", data), 0, 2);
	} 
	
	public String getData() {
		return data;
	}

}
