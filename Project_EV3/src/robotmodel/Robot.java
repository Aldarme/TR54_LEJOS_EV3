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
	private int speed;
	private EV3LargeRegulatedMotor rightMotor;
	private EV3LargeRegulatedMotor leftMotor;
	private EV3ColorSensor colorSensor;
	private EV3UltrasonicSensor distSensor;
	
	public Robot(int pID)
	{
		this.ID = pID;
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		colorSensor = new EV3ColorSensor(SensorPort.S3);
		distSensor = new EV3UltrasonicSensor(SensorPort.S2);
	}
	
	public void forward() {
		this.getLeftMotor().synchronizeWith(new RegulatedMotor[] { this.getRightMotor() });
		this.getLeftMotor().startSynchronization();
		this.getRightMotor().forward();
		this.getLeftMotor().forward();
		this.getLeftMotor().endSynchronization();
	}

	public void rotate(float pLeftSpeed, float pRightSpeed) {
		this.setLeftSpeed(pLeftSpeed);
		this.setRightSpeed(pRightSpeed);
		this.forward();
	}

	public void setLeftSpeed(float pSpeed) {
		this.getLeftMotor().setSpeed( (int) (pSpeed / 100f * this.getLeftMotor().getMaxSpeed()) );
	}

	public void setRightSpeed(float pSpeed) {
		this.getLeftMotor().setSpeed( (int) (pSpeed / 100f * this.getLeftMotor().getMaxSpeed()) );
	}

	public void setSpeed(float pSpeed) {
		this.getLeftMotor().setSpeed( (int) (pSpeed / 100f * this.getLeftMotor().getMaxSpeed()) );
		this.getRightMotor().setSpeed( (int) (pSpeed / 100f * this.getRightMotor().getMaxSpeed()) );
	}

	public void stop() {
		this.getLeftMotor().stop(true);
		this.getRightMotor().stop();
	}

	public int getId() {
		return this.ID;
	}

	public void setId(int pID) {
		this.ID = pID;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int pSpeed) {
		this.speed = pSpeed;
	}

	public EV3LargeRegulatedMotor getRightMotor() {
		return this.rightMotor;
	}

	public void setRightMotor(EV3LargeRegulatedMotor pRightMotor) {
		this.rightMotor = pRightMotor;
	}

	public EV3LargeRegulatedMotor getLeftMotor() {
		return this.leftMotor;
	}

	public void setLeftMotor(EV3LargeRegulatedMotor pLeftMoto) {
		this.leftMotor = pLeftMoto;
	}

	public EV3ColorSensor getColorSensor() {
		return this.colorSensor;
	}

	public void setColorSensor(EV3ColorSensor pColorSensor) {
		this.colorSensor = pColorSensor;
	}

	public EV3UltrasonicSensor getDistanceSensor() {
		return this.distSensor;
	}

	public void setDistanceSensor(EV3UltrasonicSensor pDistanceSensor) {
		this.distSensor = pDistanceSensor;
	}
	
	

}
