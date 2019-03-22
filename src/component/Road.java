package component;

public class Road extends Tile {
	private String type;
	
	public Road() {
		super();
		this.type = "Road";
	}
	
	public Road(String type) {
		this.type = type;
	}
	 
	public String getType() {
		return this.type;
	}
	//To string method
	public String toString() {
		return this.type;
	}
}
