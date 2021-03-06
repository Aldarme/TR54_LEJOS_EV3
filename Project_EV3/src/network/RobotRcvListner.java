package network;

import java.nio.ByteBuffer; 
import java.util.Vector;

import lejos.hardware.lcd.LCD;
import robotmodel.Robot;

/**
 * 
 * @author promet
 *
 */

public class RobotRcvListner implements BroadcastListener{

	private Robot myRobot;
	private String Delimiter = "\n";
	
	public RobotRcvListner(Robot pMyRobot) {
		this.myRobot = pMyRobot;
	}
	
	/**
	 * Function trigger when a message is detecte
	 * @param message
	 */
	@Override
	public void onBroadcastReceived(byte[] message)
	{
		//get string from wifi
		String lstnerValidServer = new String(message);
		
		System.out.println("Data: " + lstnerValidServer);
		
		try
		{
			//get Server
			String subString[] = lstnerValidServer.split(Delimiter);	
			
			int size = (subString.length-2)/3;
	
			//int rang = -1;
			
			if(subString[0].equals("-1"))
			{				
				for(int i=0; i < size; i++)
				{
					if(subString[(3*i)+1].equals(Integer.toString(this.myRobot.getId())))
					{
						//Robot could move across conflict zone
						this.myRobot.SetValidServer(true);
						
						//get robot's position into the scheduler to define led color
						switch(i)
						{
						case 0:
							this.myRobot.setLedMode(1);
							break;
						
						case 1:
							this.myRobot.setLedMode(3);
							break;
						
						case 3:
							this.myRobot.setLedMode(2);
							break;
						}
					}
				}
			}		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
