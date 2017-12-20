package network;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import network.BroadcastReceiver;
import robotmodel.Position;
import robotmodel.RobotData;

public class ReceiveServer {

	static BroadcastReceiver r;
	static Listener l = new Listener();
	
	static String data;
	static int rId, rLocation, rSpeed, rRotation;
	static int deltaTime = 5;
	static double rTime;
	static boolean found = false;
	
	static ArrayList<RobotData> requestSequence = new ArrayList<RobotData>();
	static ArrayList<RobotData> passageSequence = new ArrayList<RobotData>();
	
	public void mainServer() throws IOException
	{
		r = BroadcastReceiver.getInstance();
		r.addListener(l);
		
		while(true) {
			LCD.drawString("Waiting for data", 0, 3);
			Delay.msDelay(100);
			
			// Get the data from the robot
			data = l.getData();
			
			// If there are some new data
			if (data != null){
				LCD.drawString(String.format("Data : ", data), 0, 5);
				processData();
			}			
		}
	}
	
	private static void updateServerAcquitment()
	{
		
	}
	
	private static void processData() throws SocketException, IOException
	{
		String delim = "\r\n";
		
		//Get the values from the data received
		String[] values = data.split(delim);
		
		rId = Integer.parseInt(values[0]);
		rLocation = Integer.parseInt(values[1]);
		rSpeed = Integer.parseInt(values[2]);
		//Register system time when data are processed
		rTime = System.nanoTime()*Math.pow(10,-9);
		rRotation = Integer.parseInt(values[3]);
		
		updateRequestSequence();
	}
	
	private static void updateRequestSequence() throws SocketException, IOException
	{			
		
		// Go through the sequence list
		for (RobotData r : requestSequence) {
			// If the Robot is on the sequence list
			if (r.getId() == rId) {
				// Refresh it's location and speed
				r.setLocation(rLocation);
				r.setSpeed(rSpeed);
				r.setRotation(rRotation);
			}
			//Set the found flag
			found = true;
		}
		
		// If the Robot is not on the sequence list
		if(!found) {
			// Add the Robot to the sequence
			requestSequence.add(new RobotData(rId, rLocation, rSpeed, rTime, rRotation));
		}
		
		// Reset the flag
		found = false;
		
		updatePassageSequence();
		
	}
	
	private static void updatePassageSequence() throws SocketException, IOException
	{
		int i = 0;
		
		if(requestSequence.size() == 1 && passageSequence.isEmpty())
		{
			passageSequence.add(requestSequence.get(0));
		}else {
			for (RobotData r : requestSequence) {
				i++;
				for(int o=0;o<passageSequence.size();o++)
				{
					// If new data from robot already in passageSequence -> Update
					if(r.getId() == passageSequence.get(o).getId())
					{
						if(Position.SORTIE.equals(r.getLocation()))
						{
							passageSequence.remove(o);
							requestSequence.remove(i);
						}else {
							passageSequence.get(o).setLocation(r.getLocation());
							passageSequence.get(o).setSpeed(r.getSpeed());
							passageSequence.get(o).setRotation(r.getRotation());
						}
						found = true;
					}
				}
				if(!found)
				{					
					// If the Robot from the request list is from the same path as the last one from sequence list, add the Robot
					if(passageSequence.get(passageSequence.size()-1).getRotation() == r.getRotation())
					{
						passageSequence.add(r);
					}else {
						if(r.getTime()-passageSequence.get(passageSequence.size()-1).getTime() >= deltaTime)
						{
							passageSequence.add(r);
						}
					}
				}
			}
			found = false;
		}
		
		sendPassageSequence();
	}
	
	private static void sendPassageSequence() throws SocketException, IOException
	{
		String data = "";
		for (RobotData r : passageSequence) {
			data += Integer.toString(r.getId()) + "\r\n" + Integer.toString(r.getLocation()) + "\r\n" + Integer.toString(r.getSpeed()) + "\r\n";
		}
		
		CentralizedSync.sendPos(data);
	}
}
