package robotmodel;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

/**
 * 
 * @author promet
 *
 */
public class RobotMotorController
{
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	
	public RobotMotorController()
	{
		this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);	//Define left motor on Port B
		this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);	//Define right motor on Port C
	}
	
	/*
	 * Move forward
	 */
	void forward()
	{
		this.leftMotor.forward();;
		this.rightMotor.forward();
	}
	
	/*
	 * Robot rotate on itself of specify angle
	 * BEWARE: rotate function delete all brevious order of movement
	 */
	void rotate(float angle)
	{
		float rotation = angle * 2.33f;
		this.leftMotor.rotate((int)angle, true);
		this.rightMotor.rotate((int) -angle);
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
	
	/*
	 * Stop robot's motors
	 */
	void stop()
	{
		this.leftMotor.stop(true);
		this.rightMotor.stop();
	}
}
