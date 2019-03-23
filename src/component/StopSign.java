package component;

import java.util.PriorityQueue;


//TODO: add To string method
public class StopSign extends Intersection {
	
	private PriorityQueue<Car> carEnter;
	private String state;
	
	public StopSign() {
		this.carEnter = new PriorityQueue<Car>();
		this.state = "empty";
		this.increment = 0;
		this.mapIndex = new Point(0,0);
	}
	
	public StopSign(String type) {
		this.type = type;
		this.state = "empty";
		this.carEnter = new PriorityQueue<Car>();
		this.mapIndex = new Point(0,0);
	}
	public StopSign(String type, Point mapIndex) {
		this.mapIndex = mapIndex;
		this.type = type;
		this.state = "empty";
		this.increment = 0;
	}
	public StopSign(String type, Point mapIndex, String builtDirections) {
		this.mapIndex = mapIndex;
		this.builtDirections = builtDirections;
		this.type = type;
		this.state = "empty";
		this.increment = 0;
	}

	public PriorityQueue<Car> getCarEnter() {
		return this.carEnter;
	}
	public void setCarEnter(PriorityQueue<Car> carEnter) {
		this.carEnter = carEnter;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
