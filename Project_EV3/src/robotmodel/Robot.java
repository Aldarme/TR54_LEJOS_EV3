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
	private Position robotPos;
	private boolean ValidServer;
	
	private RobotMotorController motorController;
	private RobotSensorController sensorController;
	public RobotMoveMode moveModeController;
	
	/*
	 * Default Constructor
	 */
	public Robot(int pID)
	{
		this.ID = pID;
		this.robotPos = Position.SORTIE;
		this.ValidServer = false;
	}
	
	/*
	 * Getter ValidServer
	 */
	public boolean getValidServer()
	{
		return this.ValidServer;
	}
	
	/*
	 * Setter ValidServer
	 */
	public void getValidServer(boolean pBoolean)
	{
		this.ValidServer = pBoolean;
	}	
		
	/*
	 * Getter ID
	 */
	public int getId() {
		return this.ID;
	}
	
	/*
	 * Getter current Position
	 */
	public int getPosition()
	{
		return this.robotPos.getID();
	}

	/*
	 * Getter current Speed
	 */
	public int getSpeed() {
		return motorController.getCurrentSpeed();
	}
	
	/*
	 * Get forward Motor
	 */
	public void forward()
	{
		motorController.forward();
	}
	
	/*
	 * Get Stop Motor
	 */
	public void StopMotor()
	{
		motorController.stop();
	}
	
	/*
	 * Set rotate Nbr
	 */
	public void nbrRotateWheel(float pNbr)
	{
		motorController.rotate(pNbr);
	}
	
	/*
	 * Getter ColorSensor
	 */
	public float[] getColorSensor() {
		return sensorController.getRgbSampler();
	}

	/*
	 * Getter DistanceSensor
	 */
	public float getDistanceSensor() {
		return sensorController.getDist();
	}
	
	/*
	 * setter current Position
	 */
	public void setPosition(Position pPosition)
	{
		this.robotPos = pPosition;
	}
	
	

}
