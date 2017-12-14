package mainProg;

import robotmodel.*;
import threads.*;

/**
 * 
 * @author promet
 *
 */

public class Main {

	public static void main(String[] args) throws InterruptedException
	{
		//Init my robot with an ID
		Robot myRobot = new Robot(0);
		
		//int array to test organe rgb value
		int orangeTab[] = new int[] {1,2,3};
		
		//Thread
		Thread threadEntree = new Thread(new ThreadEntreeZone(myRobot));
		Thread threadStock = new Thread(new ThreadStockZone(myRobot));
		Thread threadConflict = new Thread(new ThreadConflictZone(myRobot));
		Thread threadSortie = new Thread(new ThreadConflictZone(myRobot));
		   
		
		
		for(;;)
		{
			/*
			 * Lancer la politique de suivis de ligne
			 */
			
			
			/*
			 * Orange mark detection
			 */
			if (myRobot.getColorSensor()[0] == orangeTab[0]		//get real value
				&& myRobot.getColorSensor()[1] == orangeTab[1]	//of ORANGE RGB
				&&myRobot.getColorSensor()[2] == orangeTab[2]
				)
			{
				//Start the Thread for Entree Zone
				threadEntree.start();
				threadEntree.join();
				
				if(myRobot.getValidServer() == false)
				{
					myRobot.StopMotor();
					
					//Start the Thread for Stock Zone
					threadStock.start();
					threadStock.join();
					
					//Wait that "ValidServer" was update from false to true
					while(myRobot.getValidServer() == false){}					
				}
				
				//Start the Thread for Conflict Zone
				threadConflict.start();				
				threadConflict.join();
				
				//Start the Thread for Sortie Zone
				threadSortie.start();	
				threadSortie.join();
			}
		}
	}
}
