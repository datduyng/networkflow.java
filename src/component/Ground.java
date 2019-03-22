package component;

public class Ground extends Tile{
	private String type;
	
	public Ground() {
		super();
		this.type = "Ground";
	}
	
	public Ground(String type) {
		this.type = type;
	}
 	
	public String getType() {
		return this.type;
	}
	
	public String toString() {
		return this.type;
	}
}
