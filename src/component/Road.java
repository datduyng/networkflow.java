package component;

public class Road extends Tile {
	private String type;
	
	public Road() {
		super();
		this.type = "road";
	}
	 
	public String getType() {
		return this.type;
	}
	
	public String toString() {
		return this.type;
	}

}
