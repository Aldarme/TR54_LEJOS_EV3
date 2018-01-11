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
	private float vitesseDeBase =250.0f;
	
	public RobotMotorController()
	{
		this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);//Define left motor on Port B
		this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);	//Define right motor on Port C
	}
	
	/**
	 * Move forward
	 */
	public void forward()
	{
		this.leftMotor.forward();
		this.rightMotor.forward();
	}
	
	/**
	 *  Initialization of speed and state of robot (move forward)
	 */
	public void init(){
		
		this.leftMotor.setSpeed(vitesseDeBase);
		this.rightMotor.setSpeed(vitesseDeBase);
		//forward();
	}
	
	/**
	 * Robot rotate on itself of specify angle
	 * BEWARE: rotate function delete all brevious order of movement
	 * @param angle
	 */
	public void rotate(float angle)
	{
		float rotation = angle * 2.33f;
		this.leftMotor.rotate((int)angle, true);
		this.rightMotor.rotate((int) angle);
	}
	
	/**
	 * Rotate left
	 * @param angle
	 */
	public void rotateLeft(float angle)
	{
		float rotation = angle * 2.33f;
		this.rightMotor.rotate((int) angle, true);
	}
	
	/**
	 * Rotate right
	 * @param angle
	 */
	public void rotateRight(float angle)
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
		//forward();
	}
	
	/**
	 *  This function ask to robot to turn left while move forward
	 * @param ratio this parameter decline the speed of the left motor in relation to the speed of the right motor
	 */
	public void rotateLeftProgressive(float ratio) {
		this.rightMotor.setSpeed(vitesseDeBase);
		this.leftMotor.setSpeed(vitesseDeBase*ratio);
		//forward();
	}
	
	/**
	 *  This function ask to robot to rotate forward
	 * @param ratio this parameter between [0 ; 1]
	 */
	public void rotateBoth(float ratio) {
		this.rightMotor.setSpeed(vitesseDeBase*ratio);
		this.leftMotor.setSpeed(vitesseDeBase*ratio);
		forward();
	}
	
	/**
	 * set speed on left && right motor by multiplying a ceof on ( LeftMotorMaxSpeed && RightMotorMaxSpeed)
	 * @param pSpeed
	 */
	public void setSpeed(float pSpeed) {
		this.leftMotor.setSpeed( (int) (pSpeed / 100f * this.leftMotor.getMaxSpeed()) );
		this.rightMotor.setSpeed( (int) (pSpeed / 100f * this.rightMotor.getMaxSpeed()) );
	}
	
	/**
	 * Set motor speed, by multiplying a ceof * LeftMotorMaxSpeed
	 * @param pSpeed
	 */
	public void setLeftSpeed(float pSpeed) {
		this.leftMotor.setSpeed( (int) (pSpeed / 100f * this.leftMotor.getMaxSpeed()) );
	}
	
	/**
	 * Set motor speed, by multiplying a ceof * RightMotorMaxSpeed
	 * @param pSpeed
	 */
	public void setRightSpeed(float pSpeed) {
		this.rightMotor.setSpeed( (int) (pSpeed / 100f * this.rightMotor.getMaxSpeed()) );
	}
	
	public float getMaxSpeedG()
	{
		return this.leftMotor.getMaxSpeed();		 
	}
	
	/**
	 * get max speed
	 * @return
	 */
	public float getMaxSpeedD()
	{
		return this.rightMotor.getMaxSpeed();		 
	}
	
	/**
	 * get current speed
	 * @return
	 */
	public int getCurrentSpeed()
	
	{
		 int avrgSpeed = (this.leftMotor.getSpeed() +  this.rightMotor.getSpeed()) / 2; 
		 return avrgSpeed;
	}
	
	/**
	 * get tachy value from wheel
	 * @return
	 */
	public int getTachy()
	{
		return this.leftMotor.getTachoCount();
	}
	
	/**
	 * get reset tachy value
	 */
	public void tachyReset()
	{
		this.leftMotor.resetTachoCount();
	}
	
	/**
	 * Stop robot's motors
	 */
	public void stop()
	{
		this.leftMotor.stop();
		this.rightMotor.stop();
	}
}
