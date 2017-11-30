package robotmodel;

public class RobotData {
	private int id;
	private int location;
	private int speed;
	
	public RobotData(int id, int location, int speed)
	{
		this.id = id;
		this.location = location;
		this.speed = speed;
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
	
}