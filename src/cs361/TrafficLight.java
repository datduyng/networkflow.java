package cs361;

public class TrafficLight extends Intersection {

	private String state;
	private int increment;
	
	public TrafficLight() {
		this.state = null;
		this.increment = 0;
	}
	
	public TrafficLight(String state, int increment) {
		this.state = state;
		this.increment = increment;
	}
	
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
	
	 

