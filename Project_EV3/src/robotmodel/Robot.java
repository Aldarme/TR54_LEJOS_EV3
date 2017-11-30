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
	private int Speed;
	
	private RobotMotorController mottorController;
	private RobotSensorController sensorController;
	
	/*
	 * Default Constructor
	 */
	public Robot(int pID)
	{
		this.ID = pID;
		this.Speed = 0;
	}
		
	/*
	 * Getter ID
	 */
	public int getId() {
		return this.ID;
	}

	/*
	 * Sette ID
	 */
	public void setId(int pID) {
		this.ID = pID;
	}

	/*
	 * Getter Speed
	 */
	public int getSpeed() {
		return this.Speed;
	}	

	/*
	 * 
	 */
	public EV3ColorSensor getColorSensor() {
		//return 
	}

	/*
	 * 
	 */
	public EV3UltrasonicSensor getDistanceSensor() {
		//return 
	}
	
	

}
