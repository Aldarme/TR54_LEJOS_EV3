package robotmodel;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

/**
 * 
 * @author promet
 * modify by ncarrion
 *
 */
public class RobotMotorController
{
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private float vitesseDeBase =330.0f;
	
	public RobotMotorController()
	{
		this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);//Define left motor on Port B
		this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);	//Define right motor on Port C
	}
	
	/*
	 * Move forward
	 */
	void forward()
	{
		this.leftMotor.forward();
		this.rightMotor.forward();
	}
	
	/**
	 * Initialization of speed and state of robot (move forward)
	 */
	public void init(){
		
		this.leftMotor.setSpeed(vitesseDeBase);
		this.rightMotor.setSpeed(vitesseDeBase);
		forward();
	}
	
	/*
	 * Robot rotate on itself of specify angle
	 * BEWARE: rotate function delete all brevious order of movement
	 */
	void rotate(float angle)
	{
		float rotation = angle * 2.33f;
		this.leftMotor.rotate((int)angle, true);
		this.rightMotor.rotate((int) angle);
	}
	
	void rotateLeft(float angle)
	{
		float rotation = angle * 2.33f;
		this.rightMotor.rotate((int) angle, true);
	}
	
	void rotateRight(float angle)
	{
		float rotation = angle * 2.33f;
		this.leftMotor.rotate((int)angle, true);
	}
	
	/**
	 * This function ask to robot to turn right while move forward
	 * @param ratio this parameter decline the speed of the right motor in relation to the speed of the left motor
	 */
	public void rotateRightProgressive(float ratio) {		
		this.rightMotor.setSpeed(vitesseDeBase*ratio);
		this.leftMotor.setSpeed(vitesseDeBase);
		forward();
	}
	
	/**
	 *  This function ask to robot to turn left while move forward
	 * @param ratio this parameter decline the speed of the left motor in relation to the speed of the right motor
	 */
	public void rotateLeftProgressive(float ratio) {
		this.rightMotor.setSpeed(vitesseDeBase);
		this.leftMotor.setSpeed(vitesseDeBase*ratio);
		forward();
	}
	
	
	/*
	 * set speed on left && right motor by multiplying a ceof on ( LeftMotorMaxSpeed && RightMotorMaxSpeed)
	 */
	public void setSpeed(float pSpeed) {
		this.leftMotor.setSpeed( (int) (pSpeed / 100f * this.leftMotor.getMaxSpeed()) );
		this.rightMotor.setSpeed( (int) (pSpeed / 100f * this.rightMotor.getMaxSpeed()) );
	}
	
	/*
	 * Set motor speed, by multiplying a ceof * LeftMotorMaxSpeed
	 */
	public void setLeftSpeed(float pSpeed) {
		this.leftMotor.setSpeed( (int) (pSpeed / 100f * this.leftMotor.getMaxSpeed()) );
	}
	
	/*
	 * Set motor speed, by multiplying a ceof * RightMotorMaxSpeed
	 */
	public void setRightSpeed(float pSpeed) {
		this.rightMotor.setSpeed( (int) (pSpeed / 100f * this.rightMotor.getMaxSpeed()) );
	}
	
	float getMaxSpeedG()
	{
		return this.leftMotor.getMaxSpeed();		 
	}
	
	float getMaxSpeedD()
	{
		return this.rightMotor.getMaxSpeed();		 
	}
	
	int getCurrentSpeed()
	
	{
		 int avrgSpeed = (this.leftMotor.getSpeed() +  this.rightMotor.getSpeed()) / 2; 
		 return avrgSpeed;
	}
	
	/*
	 * Stop robot's motors
	 */
	void stop()
	{
		this.leftMotor.stop(true);
		this.rightMotor.stop();
	}
}
