package robotmodel;

public enum Position 
{
	ENTREE		(0),
	STOCKAGE	(1),
	CONFLICT	(2),
	SORTIE		(3);
	
	private final double ID;
	
	Position(int pID) 
	{
		this.ID = pID;
	}
	
	public double getID() { return this.ID; }
	
}
