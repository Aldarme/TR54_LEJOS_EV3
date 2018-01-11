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
	
	/**
	 * RGB Sensor
	 */
	public void getColor()
	{		
		rgbSampler.fetchSample(rgbSensor, 0);		
	}
	
	/**
	 * return rgb value return by the color sensor
	 * @return
	 */
	public float[] getRgbSampler()
	{
		return rgbSensor;
	}
	
	/**
	 * Distance Sensor
	 * @return
	 */
	public float getDist()
	{
		distSampler.fetchSample(distSample, 0);
		return distSample[0];		 
	}
	
	/**
	 * 
	 * Return average distance, from distancec sensor
	 * 	Use a average filter of n elements
	 * @param pNbrOfVal
	 * @return
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