package cs361;

import java.util.List;

public class StopSign extends Tile {
	
	private List CarEnter;
	private String state;
	private int increment;
	
	public List getCarEnter() {
		return CarEnter;
	}
	public void setCarEnter(List carEnter) {
		CarEnter = carEnter;
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
