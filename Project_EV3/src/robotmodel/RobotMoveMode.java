package robotmodel;

import exporter.CsvExporter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

import lejos.hardware.lcd.LCD;


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

	RobotMotorController robotController = new RobotMotorController();
	RobotSensorController mySensor = new RobotSensorController();
	
	/*
	 * Robotleader
	 * set speed at 40% of maxSpeed
	 */
	void leader(int pTime, float pW)
	{
		pSpeedG = robotController.getMaxSpeedG() * pW;  
		pSpeedD = robotController.
		
		robotController.setSpeedLeft(Math.round(pSpeedG));
		robotController.setSpeedRight(Math.round(pSpeedD));
		robotController.forward();
		try {
			TimeUnit.MILLISECONDS.sleep(pTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		robotController.stop();
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
		pSpeedG = robotController.getMaxSpeedG() * pW;
		pSpeedD = robotController.getMaxSpeedD() * pW;
		
		if(mySensor.getDist(5) <= 0.15) //getDist() retourne une distance en mètre
		{
			pSpeedG = 0f;
			pSpeedD = 0f;
		}			
		LCD.clear();
		LCD.drawString(Float.toString(mySensor.getDist(5)), 0, 2);
		CsvExporter.Writefile(mySensor.getDist());					//write on CSV file
		robotController.setSpeedLeft(Math.round(pSpeedG));
		robotController.setSpeedRight(Math.round(pSpeedD));
		robotController.forward();
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
		CsvExporter.Writefile(mySensor.getDist());		//write on CSV file
		
		currentSpeed = Math.max( Math.min(50, a * (mySensor.getDist(5) - D) ), 0);
		
		robotController.setSpeedLeft(Math.round(currentSpeed));
		robotController.setSpeedRight(Math.round(currentSpeed));
		robotController.forward();
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
		CsvExporter.Writefile(mySensor.getDist());		//write on CSV file
		
		previousSpeed = currentSpeed;
		currentSpeed = Math.min(
								Math.max(
										 2.5f * (previousSpeed - 20f) , Math.min(
												 								Math.max(
												 											a * (mySensor.getDist(5) - D) , 0
												 										), mySensor.getDist(5) 
												 	 						   ) 
										) , 50 
								);
		
		
		robotController.setSpeedLeft(Math.round(currentSpeed));
		robotController.setSpeedRight(Math.round(currentSpeed));
		robotController.forward();
	}
	
	/*
	 * Communication sans-fil
	 */
	/**********************************************************************************
	 * COMMUNICATION SANS FIL
	 *********************************************************************************/
	void wifiRcv()
	{		
		String Speed ="";
		try {
			Speed = Wifi.ServeurWifi();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}
		if(Speed == "ERROR")
		{
			allOrNothing(50);
			return;
		}
		
		WifiSpeedrcv = Float.valueOf(Speed);
		robotController.setSpeedLeft(Math.round(WifiSpeedrcv));
		robotController.setSpeedRight(Math.round(WifiSpeedrcv));
		
	}
	
	void wifiSend() throws IOException
	{
		WifiSpeedsend = robotController.getMaxSpeedG();
		Wifi.clientWifi( Float.toString(WifiSpeedsend) );
	}
		
	
}