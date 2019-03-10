package cs361;

public class Tile {
	
	protected int numCarPass;
	
	Point mapIndex;
	Point position;
	
	public Tile() {
		this.numCarPass = 0;
	}
	
	public Tile(int numCarPass, Point mapIndex, Point position) {
		this.numCarPass = numCarPass;
		this.mapIndex = mapIndex;
		this.position = position;
	}

	/**
	 * Gets attribute of the specific tile
	 * @return the number of cars that have passed through this tile
	 */
	public int getNumCarPass() {
		return this.numCarPass;
	}
	
}
