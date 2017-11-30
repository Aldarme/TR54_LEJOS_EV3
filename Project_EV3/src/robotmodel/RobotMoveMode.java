package robotmodel;

import exporter.CsvExporter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

import lejos.hardware.lcd.LCD;

/**
 * 
 * @author promet
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

	RobotMotorController motorController = new RobotMotorController();
	RobotSensorController sensorController = new RobotSensorController();
	
	/*
	 * Robotleader
	 * set speed at 40% of maxSpeed
	 */
	void leader(int pTime, float pW)
	{
		pSpeedG = motorController.getMaxSpeedG() * pW;				
		pSpeedD = motorController.getMaxSpeedD() * pW;	
		
		motorController.setLeftSpeed(Math.round(pSpeedG));
		motorController.setRightSpeed(Math.round(pSpeedD));
		
		motorController.forward();
		try {
			TimeUnit.MILLISECONDS.sleep(pTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		motorController.stop();
		try {
			TimeUnit.MILLISECONDS.sleep(pTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/*
	 * Tout ou rien
	 * set speed at 50%, if distance < 0.15m
	 */
	void allOrNothing(float pW)
	{		
		pSpeedG = motorController.getMaxSpeedG() * pW;
		pSpeedD = motorController.getMaxSpeedD() * pW;
		
		if(sensorController.getDist(5) <= 0.15) //getDist() retourne une distance en mètre
		{
			pSpeedG = 0f;
			pSpeedD = 0f;
		}			
		LCD.clear();
		LCD.drawString(Float.toString(sensorController.getDist(5)), 0, 2);
		CsvExporter.Writefile(sensorController.getDist());					//write on CSV file
		motorController.setLeftSpeed(Math.round(pSpeedG));
		motorController.setRightSpeed(Math.round(pSpeedD));
		motorController.forward();
	}
	
	/*
	 * %(t) indique la vitesse appliquée le cycle précédent (en pourcentage de la vitesse maximale)
	 * Ts est le temps de cycle
	 * d(t) désigne la distance renvoyée par le capteur		  
	 */
	
	
	/*
	 * "A un point" -- %(t + T s ) = max(min(50, a × (d(t) − D)) , 0)
	 * a -> multiplicateur pour transformer la distance en vitesse
	 * D -> offset de distance
	 * "d(t) ou mySensor.getDist(5)" désigne la distance renvoyée par le capteur
	 */
	void aUnPoint(int a, float D)
	{
		CsvExporter.Writefile(sensorController.getDist());		//write on CSV file
		
		currentSpeed = Math.max( Math.min(50, a * (sensorController.getDist(5) - D) ), 0);
		
		motorController.setLeftSpeed(Math.round(currentSpeed));
		motorController.setRightSpeed(Math.round(currentSpeed));
		
		motorController.forward();
	}
	
	
	/* 
	 * "A deux points" -- %(t + T s ) = min(max(2.5 × (d(t) − 20), min(max(a × (d(t) − D), 0) , %(t))) , 50)
	 * a -> multiplicateur pour transformer la distance en vitesse
	 * D -> offset de distance
	 * "d(t) ou mySensor.getDist(5)" désigne la distance renvoyée par le capteur
	 * "%(t) ou previousSpeed" indique la vitesse appliquée le cycle précédent (en pourcentage de la vitesse maximale)
	 */
	void aDeuxPoint(int a, float D)
	{
		CsvExporter.Writefile(sensorController.getDist());		//write on CSV file
		
		previousSpeed = currentSpeed;
		currentSpeed = Math.min(
								Math.max(
										 2.5f * (previousSpeed - 20f) , Math.min(
												 								Math.max(
												 											a * (sensorController.getDist(5) - D) , 0
												 										), sensorController.getDist(5) 
												 	 						   ) 
										) , 50 
								);
		
		
		motorController.setLeftSpeed(Math.round(currentSpeed));
		motorController.setRightSpeed(Math.round(currentSpeed));
		motorController.forward();
	}	
}