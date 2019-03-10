package component;

import java.util.PriorityQueue;


//TODO: add To string method
public class StopSign extends Intersection {
	
	private PriorityQueue carEnter;
	private String state;
	
	public StopSign() {
		this.carEnter = null;
		this.state = null;
		this.increment = 0;
	}
	
	public StopSign(PriorityQueue carEnter, String state, int increment) {
		super();
		this.carEnter = carEnter;
		this.state = state;
		this.increment = increment;
	}

	public PriorityQueue getCarEnter() {
		return this.carEnter;
	}
	public void setCarEnter(PriorityQueue carEnter) {
		this.carEnter = carEnter;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
