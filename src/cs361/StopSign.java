package cs361;

import java.util.PriorityQueue;

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
		return carEnter;
	}
	public void setCarEnter(PriorityQueue carEnter) {
		carEnter = carEnter;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
