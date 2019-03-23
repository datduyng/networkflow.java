package component;

public abstract class Tile {
	
	protected int numCarPass;
	
	Point mapIndex;
	Point position;
	String type; // added @dat
	
	public Tile() {
		this.numCarPass = 0;
	}
	
	//reid: 
	public Tile(Point mapIndex, Point position) {
//		this.numCarPass = numCarPass; del by @dat
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
	public static Tile initTileType(String generalType, String classType, Point mapIndex) {
		if(generalType.equalsIgnoreCase("ground")) {
			return new Ground(classType);
		} else if(generalType.equalsIgnoreCase("road")) {
			return new Road(classType);
		} else if(generalType.equalsIgnoreCase("stop-sign")) {
			return new StopSign(classType, mapIndex);
		} else if(generalType.equalsIgnoreCase("traffic-light")) {
			return new TrafficLight(classType, mapIndex);
		}
		return null;
	}

	/**
	 * Gets attribute of the specific tile
	 * @return the number of cars that have passed through this tile
	 */
	public int getNumCarPass() {
		return this.numCarPass;
	}
	
}
