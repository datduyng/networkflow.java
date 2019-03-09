package cs361;

public class Tile {
	
	protected int numCarPass;
	
	
	public Tile() {
		this.numCarPass = 0;
	}
	
	public Tile(int numCarPass) {
		this.numCarPass = numCarPass;
	}

	/**
	 * Gets attribute of the specific tile
	 * @return the number of cars that have passed through this tile
	 */
	public int getNumCarPass() {
		return this.numCarPass;
	}
	
}
