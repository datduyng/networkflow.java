package cs361;

import java.util.Queue;

public class StopSign extends Intersection {
	
	private Queue carEnter;
	private String state;
	
	public StopSign() {
		this.carEnter = null;
		this.state = null;
		this.increment = 0;
	}
	
	public StopSign(Queue carEnter, String state, int increment) {
		super();
		this.carEnter = carEnter;
		this.state = state;
		this.increment = increment;
	}

	public Queue getCarEnter() {
		return carEnter;
	}
	public void setCarEnter(Queue carEnter) {
		carEnter = carEnter;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
