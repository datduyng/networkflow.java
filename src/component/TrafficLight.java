package component;
//TODO: add To string method
public class TrafficLight extends Intersection {

	private String state;
	private int increment;
	
	public TrafficLight() {
		this.state = "empty";
		this.increment = 0;
	}
	
	public TrafficLight(String type) {
		this.type = type;
		this.state = "empty";
		this.increment = 0;
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
	
	public String toString() {
		return this.type;
	}
}
	
	 

