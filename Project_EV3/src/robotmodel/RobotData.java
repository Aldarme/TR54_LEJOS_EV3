package robotmodel;

public class RobotData {
	private int id;
	private int location;
	private int speed;
	private double time;
	
	public RobotData(int id, int location, int speed, double time)
	{
		this.id = id;
		this.location = location;
		this.speed = speed;
		this.time = time;
	}
	
	public int getId() {
		return id;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public double getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}