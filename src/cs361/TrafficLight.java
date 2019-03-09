package cs361;

public class TrafficLight extends Tile {

	private String state;
	private int increment;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getIncrement() {
		return increment;
	}
	public void updateIncrement() {
		this.increment++;
	}
}
	
	 

