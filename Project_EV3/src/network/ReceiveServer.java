package network;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import java.util.Iterator;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import network.BroadcastReceiver;
import robotmodel.Position;
import robotmodel.RobotData;

public class ReceiveServer {

	/*static BroadcastReceiver r;
	static Listener l = new Listener();*/
	
	
	static int rId, rLocation, rSpeed, rRotation = 0;
	static int deltaTime = 5;
	static double rTime;
	static boolean found = false;
	
	static ArrayList<RobotData> requestSequence = new ArrayList<RobotData>();
	static ArrayList<RobotData> passageSequence = new ArrayList<RobotData>();
	
	public void mainServer() throws IOException
	{
		/*r = BroadcastReceiver.getInstance();
		r.addListener(l);*/
		
		
		while(true) {
			/*LCD.drawString("Waiting for data", 0, 3);
			Delay.msDelay(100);
			
			// Get the data from the robot
			data = l.getData();
			
			// If there are some new data
			if (data != null){
				LCD.drawString("Data : " + data, 0, 5);
				Delay.msDelay(4000);
				processData();
			}		*/	
		}
	}
	
	/*private static void updateServerAcquitment()
	{
		
	}*/
	
	public void processData(String Data) throws SocketException, IOException
	{
		String delim = "\r\n";
		
		if(Data != null)
		{
			System.out.println("Ca passe1");
			//Get the values from the data received
			String[] values = Data.split(delim);
			
			if(values[0] != "server")
			{
				System.out.println("Ca passe 2");
				rId = Integer.parseInt(values[0]);
				rLocation = Integer.parseInt(values[1]);
				rSpeed = Integer.parseInt(values[2]);
				//Register system time when data are processed
				rTime = System.nanoTime()*Math.pow(10,-9);
				//rRotation = Integer.parseInt(values[3]);
				
				updateRequestSequence();
			}
		}
	}
	
	private static void updateRequestSequence() throws SocketException, IOException
	{			
		
		// Go through the sequence list
		for (RobotData r : requestSequence) 
		{
			// If the Robot is on the sequence list
			if (r.getId() == rId) {
				// Refresh it's location and speed
				r.setLocation(rLocation);
				r.setSpeed(rSpeed);
				r.setRotation(rRotation);
				
				//Set the found flag
				found = true;
			}			
		}
		
		// If the Robot is not on the sequence list
		if(!found)
		{
			// Add the Robot to the sequence
			requestSequence.add(new RobotData(rId, rLocation, rSpeed, rTime, rRotation));
		}
		
		// Reset the flag
		found = false;
		
		LCD.drawString("Je passe", 0,6);
		
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
				
				//TODO
				//replace by itarator
				Iterator toto = (Iterator) passageSequence.iterator();
				
				while(toto.hasNext())
				{
					RobotData robotNext = (RobotData)toto.next();
					
					//If new data from robot already in passageSequence -> Update
					if(r.getId() == robotNext.getId())
					{
						if(Position.SORTIE.getID() == r.getLocation())
						{
							toto.remove();
							requestSequence.remove(i);
						}else {
							robotNext.setLocation(r.getLocation());
							robotNext.setSpeed(r.getSpeed());
							robotNext.setRotation(r.getRotation());
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
		String data = "server\r\n";
		
		for (RobotData r : passageSequence) {
			data += Integer.toString(r.getId()) + "\r\n" 
					+ Integer.toString(r.getLocation()) + "\r\n" 
					+ Integer.toString(r.getSpeed()) + "\r\n";
		}
		
		data += "end";
		
		CentralizedSync.sendPos(data);
	}
}
