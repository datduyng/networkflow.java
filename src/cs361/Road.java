package cs361;

public class Road extends Tile {
	private int id;
	
	public Road() {
		super();
		this.id = 0;
	}
	
	public Road(int id) {
		super();
		this.id = id;
	}
	 
	public int getID() {
		return this.id;
	}

}
