package component;

public abstract class Intersection extends Tile {
	protected long increment;
	
	public Intersection() {
		super();
		this.type = "Intersection";
		this.increment = 0;
	}
	
	public Intersection(int increment) {
		super();
		this.type = "Intersection";
		this.increment = increment;
	}

	public String getType() {
		return this.type;
	}
	
	public void updateIncrement() {
		this.increment++;
	}
}
