package robotmodel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

import lejos.hardware.lcd.LCD;

/**
 * 
 * @author promet
 * modify by ncarrion
 *
 */
public class RobotMoveMode {
	
	/**********************************************************************************
	 * Robot  suiveur
	 **********************************************************************************/
	private float pSpeedG = 0;
	private float pSpeedD = 0;
	private	float previousSpeed = 0;
	private	float currentSpeed = 0;
	private float WifiSpeedrcv = 0;
	private float WifiSpeedsend = 0;
	private float distanceMin=0.15f; //meter
	private boolean rerunAfterStop = false;
	private int stateBeforeStop=0; // 1 left rotation, 2 right rotation, 3 right through 

	private int compteurBlanc =1;
	private int compteurNoir=1;
	private int compteurVirage=0;
	float rgb[]= new float[3];
	float ratioG = 0.95f;
	float ratioD = 0.96f;

	RobotMotorController motorController;
	RobotSensorController sensorController;
	
	public RobotMoveMode(RobotSensorController rSC,RobotMotorController rMC) {
		sensorController=rSC;
		motorController=rMC;
	}
	
	/**
	 * Function followLine 
	 * @return
	 */
	public int followLine() 
	{
		if(sensorController.getDist()<distanceMin)
		{
			rerunAfterStop=true;
			motorController.setSpeed(0);
		}
		else {
			//the robot knows in which state he is after a stop because link to a detection of a closer obstacle
			if(rerunAfterStop) {
				rerunAfterStop=false;
				if(stateBeforeStop==1)
					motorController.rotateLeftProgressive((float)(Math.pow(ratioG, compteurNoir)));	
				if(stateBeforeStop==2)
					motorController.rotateRightProgressive((float)(Math.pow(ratioG, compteurNoir)));
				if(stateBeforeStop==3)
					motorController.init();
			}else {
				sensorController.getColor();
				
				//Get value from capteur				
				rgb = sensorController.getRgbSampler();
				
				//Black	ou orange		
				if((rgb[0] > 0.11f && 	rgb[0] <  0.2f	)
						&& (rgb[1] > 0.04f && rgb[1] <  0.08f	)
						&& (rgb[2] > 0.002f && rgb[2] <  0.08f	) 
					)
				{
					stateBeforeStop=1;
					motorController.rotateLeftProgressive((float)(Math.pow(ratioG, compteurNoir)));	
				}
				else if((rgb[0]<= 0.07 && rgb[1]<= 0.07 && rgb[2]<=0.07))  {
					stateBeforeStop=1;
					compteurBlanc=1;
					motorController.rotateLeftProgressive((float)(Math.pow(ratioG, compteurNoir)));	
					compteurNoir++;
					compteurVirage++;
				}
				
				//White	//Save: rgb[0]>0.08 && rgb[1]>0.08 && rgb[2]>0.08
				else if(rgb[0]>0.08 && rgb[1]>0.08 && rgb[2]>0.08){
					stateBeforeStop=2;
					compteurNoir=1;
					motorController.rotateRightProgressive((float)Math.pow(ratioD, compteurBlanc));
					compteurBlanc++;
					compteurVirage--;
				}
				
				//Blue 
				//Else et non else if car apr�s s'�tre arr�t� le robot ne se retrouvait dans aucun des cas et restait donc arr�t�
				//else if ((rgb[0]>0.020 && rgb[0]<0.04 ) && (rgb[1]>0.05 && rgb[1]<0.2 ) && (rgb[2]>0.04 && rgb[2]<0.08 )) {
				else if ((rgb[0]>0.0264 && rgb[0]<0.123 ) && (rgb[1]>0.059 && rgb[1]<0.16 ) && (rgb[2]>0.105 && rgb[2]<151 ))
				{			
					stateBeforeStop=3;
					compteurNoir=1;
					compteurBlanc=1;
					compteurVirage=0;
					motorController.init();
				}
			}
		}
		return 	compteurVirage;
	}
}