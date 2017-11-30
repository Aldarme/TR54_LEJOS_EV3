package robotmodel;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;

/**
 * 
 * @author Hessou PANASSIM
 * and modify by promet
 *
 */
public class Robot{
	private int ID;
	
	private RobotMotorController motorController;
	private RobotSensorController sensorController;
	
	/*
	 * Default Constructor
	 */
	public Robot(int pID)
	{
		this.ID = pID;
	}
		
	/*
	 * Getter ID
	 */
	public int getId() {
		return this.ID;
	}

	/*
	 * Getter current Speed
	 */
	public int getSpeed() {
		return motorController.getCurrentSpeed();
	}	

	/*
	 * Getter ColorSensor
	 */
	public float[] getColorSensor() {
		return sensorController.getSampler();
	}

	/*
	 * Getter DistanceSensor
	 */
	public float getDistanceSensor() {
		return sensorController.getDist();
	}
	
	

}
