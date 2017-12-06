package network;

import java.net.SocketException;
import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import network.BroadcastReceiver;
import robotmodel.RobotData;

public class ReceiveServer {

	static BroadcastReceiver r;
	static Listener l = new Listener();
	
	static String data;
	static int rId, rLocation, rSpeed;
	static double rTime;
	static boolean found = false;
	
	static ArrayList<RobotData> requestSequence = new ArrayList<RobotData>();
	
	public static void main(String[] args) throws SocketException {
		r = BroadcastReceiver.getInstance();
		r.addListener(l);
		
		while(true) {
			LCD.drawString("Waiting for data", 0, 1);
			Delay.msDelay(100);
			
			// Get the data from the robot
			data = l.getData();
			
			// If there are some data
			if (data != null){
				LCD.drawString(String.format("Data : ", data), 0, 3);
				processData();
			}
		}
	}
	
	private static void processData()
	{
		String delim = "\r\n";
		
		//Get the values from the data received
		String[] values = data.split(delim);
		
		rId = Integer.parseInt(values[0]);
		rLocation = Integer.parseInt(values[1]);
		rSpeed = Integer.parseInt(values[2]);
		//Register system time when data are processed
		rTime = System.nanoTime();
		
		updateSequence();
	}
	
	private static void updateSequence()
	{			
		//TODO
		// Finir : Enlever les robots qui sortent de la zone de conflit de la séquence de passage
		
		int i = 0;
		
		// Go through the sequence list
		for (RobotData r : requestSequence) {
			i++;
			// If the Robot is on the sequence list
			if (r.getId() == rId) {
				/*if(rLocation == "ZONE DE SORTIE") {
					 requestSequence.remove(i);
				}else */
					 // Refresh it's location and speed
					 r.setLocation(rLocation);
					 r.setSpeed(rSpeed);
			 	//}
				 
				//Set the found flag
				found = true;
			}
		}
		
		// If the Robot is not on the sequence list
		if(!found) {
			// Add the Robot to the sequence
			requestSequence.add(new RobotData(rId, rLocation, rSpeed, rTime));
		}
		
		// Reset the flag
		found = false;
		
	}
}
