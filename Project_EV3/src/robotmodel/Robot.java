package robotmodel;

import lejos.hardware.BrickFinder;
import lejos.hardware.LED;
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
	private int currentCurv;
	private LED led;
	
	public RobotMotorController motorController;
	public RobotSensorController sensorController;
	public RobotMoveMode moveModeController;
	
	/**
	 * Default Constructor
	 * @param pID
	 */
	public Robot(int pID)
	{
		this.ID = pID;
		this.robotPos = Position.SORTIE;
		this.ValidServer = false;
		sensorController = new RobotSensorController();
		motorController = new RobotMotorController();
		moveModeController = new RobotMoveMode(sensorController,motorController);
		this.led = BrickFinder.getDefault().getLED();
	}
	
	/**
	 * Getter ValidServer
	 * @return
	 */
	public boolean getValidServer()
	{
		return this.ValidServer;
	}
	
	/**
	 * Setter ValidServer
	 * @param pBoolean
	 */
	public void SetValidServer(boolean pBoolean)
	{
		this.ValidServer = pBoolean;
	}	
		
	/**
	 * Getter ID
	 * @return
	 */
	public int getId() {
		return this.ID;
	}
	
	/**
	 * set current curve
	 * @param pCurve
	 */
	public void setCurCurve(int pCurve)
	{
		this.currentCurv = pCurve;
	}
	
	/**
	 * get current curve
	 * @return
	 */
	public int getCurCurve()
	{
		return this.currentCurv;
	}
	
	/**
	 * Getter current Position
	 * @return
	 */
	public int getPosition()
	{
		return this.robotPos.getID();
	}

	/**
	 * Getter current Speed
	 * @return
	 */
	public int getSpeed() {
		return motorController.getCurrentSpeed();
	}
	
	/**
	 * Get forward Motor
	 */
	public void forward()
	{
		motorController.forward();
	}
	
	/**
	 * Get Stop Motor
	 */
	public void StopMotor()
	{
		motorController.stop();
	}
	
	/**
	 * Set rotate Nbr
	 * @param pNbr
	 */
	public void nbrRotateWheel(float pNbr)
	{
		motorController.rotate(pNbr);
	}
	
	/**
	 * Getter ColorSensor
	 * @return
	 */
	public float[] getColorSensor() {
		return sensorController.getRgbSampler();
	}

	/**
	 * Getter DistanceSensor
	 * @return
	 */
	public float getDistanceSensor() {
		return sensorController.getDist();
	}
	
	/**
	 * setter current Position
	 * @param pPosition
	 */
	public void setPosition(Position pPosition)
	{
		this.robotPos = pPosition;
	}

	/**
	 * setter LED color
	 * mode = 1: green, 2: red, 3: orange, 5: blinking red  0: nothing !!!!
	 * @param mode
	 */
	public void setLedMode(int mode)
	{
		led.setPattern(mode);
	}
	

}
