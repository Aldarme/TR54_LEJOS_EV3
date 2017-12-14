package robotmodel;

/**
 * 
 * @author promet
 *
 */

public class Main {

	public static void main(String[] args)
	{
		//Init my robot with an ID
		Robot myRobot = new Robot(0);
		
		//int array to test organe rgb value
		int orangeTab[] = new int[] {1,2,3};
		
		
		for(;;)
		{
			
			/*
			 * Orange mark detection
			 */
			if (myRobot.getColorSensor()[0] == orangeTab[0]
				&& myRobot.getColorSensor()[1] == orangeTab[1]
				&&myRobot.getColorSensor()[2] == orangeTab[2]
				)
			{
				//set current position of the robot
				myRobot.setPosition(Position.ENTREE);	
				
				//send to the server, the current position
				network.SendServer.dataToSend(	myRobot.getId(), 
											   	myRobot.getSpeed(), 
											   	myRobot.getPosition() );
				
				myRobot.nbrRotateWheel((float)(360*2));
				
				if(myRobot.getValidServer() == false)
				{
					myRobot.StopMotor();
					
					//set current position of the robot
					myRobot.setPosition(Position.STOCKAGE);
					
					//send current position to the server
					network.SendServer.dataToSend(	myRobot.getId(), 
											   		myRobot.getSpeed(), 
											   		myRobot.getPosition() );
					
					myRobot.nbrRotateWheel((float)(180));
					
					//Wait that "ValidServer" was update from false to true
					while(myRobot.getValidServer() == false){}					
				}
				
				myRobot.nbrRotateWheel((float)(360*4));
				
				//set current position of the robot
				myRobot.setPosition(Position.CONFLICT);
				
				//send current position to the server
				network.SendServer.dataToSend(	myRobot.getId(), 
										   		myRobot.getSpeed(), 
										   		myRobot.getPosition() );
				
				myRobot.nbrRotateWheel((float)(360*4));
				
				//set current position of the robot
				myRobot.setPosition(Position.SORTIE);
				
				//send current position to the server
				network.SendServer.dataToSend(	myRobot.getId(), 
										   		myRobot.getSpeed(), 
										   		myRobot.getPosition() );
				
				
				
			}			
		}
	}
}
