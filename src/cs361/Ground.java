package cs361;

public class Ground extends Tile{
	private int id;
	
	public Ground() {
		super();
		this.id = 0;
	}
 	
	public Ground(int id) {
		super();
		this.id = id;
	}

	public int getID() {
		return this.id;
	}
}
