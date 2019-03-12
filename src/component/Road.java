package component;

public class Road extends Tile {
	
	public Road() {
		super();
		this.type = "road";
	}
	
	public Road(String type) {
		if(type.compareToIgnoreCase("road-verticle") == 0) {
			this.type = "road-verticle";
		} else if(type.compareToIgnoreCase("road-horizontal") == 0) {
			this.type  =  "road-horizontal";
		} else {
			this.type = "road";
		}
	}
	 
	public String getType() {
		return this.type;
	}

}
