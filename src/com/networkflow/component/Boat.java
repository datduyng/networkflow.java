package com.networkflow.component;

public class Boat {
	
	private Point currentIndex;
	private String direction;
	private String state;
	private int increment;
	
	public Boat() {
		this.state = "idle";
		this.increment = 0;
	}
	public Boat(Point currentIndex, String direction, String state, int increment) {
		this.currentIndex = currentIndex;
		this.direction = direction;
		this.state = state;
		this.increment = increment;
	}
	public Point getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(Point currentIndex) {
		this.currentIndex = currentIndex;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
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
	public void setIncrement(int increment) {
		this.increment = increment;
	}

}
