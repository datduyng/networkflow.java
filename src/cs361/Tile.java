package cs361;

public abstract class Tile {
	
	protected int numCarPass;
	
	/**
	 * Gets attribute of the specific tile
	 * @return the number of cars that have passed through this tile
	 */
	public int getAttr() {
		return this.numCarPass;
	}
	
	
}
