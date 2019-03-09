package cs361;

public class Intersection extends Tile {
	protected int id;
	protected int increment;
	
	public Intersection() {
		super();
		this.id = 0;
		this.increment = 0;
	}
	
	public Intersection(int id, int increment) {
		super();
		this.id = id;
		this.increment = increment;
	}

	public int getID() {
		return this.id;
	}
	
	public void updateIncrement() {
		this.increment++;
	}
}
