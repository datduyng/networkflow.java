package component;

public class TrafficLight extends Intersection {

	private String state;
	private int increment;
	
	public TrafficLight() {
		super();
		this.state = null;
		this.increment = 0;
		this.type = "traffic light";
	}
	
	public TrafficLight(String state, int increment) {
		super();
		this.state = state;
		this.increment = increment;
		this.type = "traffic light";
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
	
	 

