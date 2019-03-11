package component;

public abstract class Tile {
	
	protected int numCarPass;
	
	Point mapIndex;
	Point position;
	String type;
	
	public Tile() {
		this.numCarPass = 0;
	}
	
	public Tile(Point mapIndex, Point position) {
		this.numCarPass = 0;
		this.mapIndex = mapIndex;
		this.position = position;
	}
	
	/**
	 * This function take a String indicate tile type
	 * then return corresponding object that implement
	 * Tile
	 * //////////////////TODO:///////////////
	 * @param <T>
	 * @param type
	 * @return
	 */
	public static Tile initTileType(String type) {
		
		if(type.equalsIgnoreCase("ground")) {
			return new Ground();
		} else if (type.equalsIgnoreCase("road")) {
			return new Road();
		} else if (type.equalsIgnoreCase("traffic light")) {
			return new TrafficLight();
		} else if (type.equalsIgnoreCase("stop sign")) {
			return new StopSign();
		} else {
			return null;
		}
	}


	/**
	 * Gets attribute of the specific tile
	 * @return the number of cars that have passed through this tile
	 */
	public int getNumCarPass() {
		return this.numCarPass;
	}
	
}
