package robotmodel;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

/**
 * 
 * @author promet
 *
 */

public class RobotSensorController
{
	private float[] rgbSensor = new float[3];
	private float[] distSample = new float[1];
	private float averageDist = 0;
	private int avrgCount = 0;
	
	//Color sensor
	EV3ColorSensor  myColorSensor;
	SampleProvider rgbSampler;

	//Distance sensor
	EV3UltrasonicSensor myDistSensor;
	SampleProvider distSampler;
	
	public RobotSensorController() {
		myColorSensor = new EV3ColorSensor(SensorPort.S3);
		myDistSensor = new EV3UltrasonicSensor(SensorPort.S2);
		rgbSampler = myColorSensor.getRGBMode();
		distSampler = myDistSensor.getDistanceMode();
	}
	
	/*
	 *	CAPTEUR DE COULEURS
	 *	Créez une fonction qui fait avancer votre robot jusqu’à ce que le capteur couleur rencontre un marqueur au sol
	 *	de couleur définie.
	 *	Vous réutiliserez le code produit dans la partie « Pilotage des moteurs » pour gérer le déplacement du robot.
	 */	
	
	/*
	 * RGB Sensor
	 */
	public void getColor()
	{		
		rgbSampler.fetchSample(rgbSensor, 0);		
	}
	
	//return rgb value return by the color sensor
	public float[] getRgbSampler()
	{
		return rgbSensor;
	}
	
	/*	
	 * Distance Sensor
	 */
	/*
	 *Créez la fonction float distance() qui retourne la distance renvoyée par le capteur de distance (voir EV3UltrasonicSensor) dans l’API.
	 *Ensuite,	appelez cette fonction, 
	 *			affichez le résultat des mesures sur l’écran LCD 
	 *			et enregistrez-les dans un fichier CSV.
	 */		
	public float getDist()
	{
		distSampler.fetchSample(distSample, 0);
		return distSample[0];		 
	}
	
	/*	
	 *	Créez la fonction float distance(int n) qui retourne la distance renvoyée par le capteur de distance 
	 *	après,		application d’un filtre moyenneur de n échantillons. Comparez les résultats avec la méthode distance() 
	 * 	pour n = 2, n = 5 et n = 10.
	 */
	
	public float getDist(int pNbrOfVal)
	{
		float temp=0;
		for(int i=0; i<pNbrOfVal; i++)
		{
			distSampler.fetchSample(distSample, 0);
			temp += distSample[0];
		}			
		return temp/pNbrOfVal;
	}
	
}