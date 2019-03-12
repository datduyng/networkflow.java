package component;

import java.util.PriorityQueue;

public class TrafficLight extends Intersection {

	private String state;
	private int increment;
	
	private PriorityQueue<Car> northSouthTraffic;
	private PriorityQueue<Car> eastWestTraffic;
	
	public TrafficLight() {
		super();
		this.state = null;
		this.northSouthTraffic = new PriorityQueue<Car>();
		this.eastWestTraffic = new PriorityQueue<Car>();
		this.increment = 0;
		this.type = "traffic light";
	}
	
	public TrafficLight(String state, int increment) {
		super();
		this.setInitialState(state);
		this.northSouthTraffic = new PriorityQueue<Car>();
		this.eastWestTraffic = new PriorityQueue<Car>();
		this.increment = increment;
		this.type = "traffic light";
	}
	
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the initial state of traffic flow for this TrafficLight
	 * @param state
	 */
	public void setInitialState(String state) {
		if(state.compareToIgnoreCase("ns") == 0) {
			this.state = "ns";
		} else if (state.compareToIgnoreCase("we") == 0) {
			this.state = "we";
		} else {
			this.state = null;
		}
	}
	
	/**
	 * alternates the direction of traffic flow for this TrafficLight
	 */
	public void switchLightDir() {
		if(state.compareToIgnoreCase("ns") == 0) {
			this.state = "we";
		} else if (state.compareToIgnoreCase("we") == 0) {
			this.state = "ns";
		} else {
			this.state = null;
		}
	}
	
	public int getIncrement() {
		return increment;
	}
	public void updateIncrement() {
		this.increment++;
	}
	
	/**
	 * Add car traveling in N/S direction to stopped traffic light
	 * @param car to be enqueued
	 * @return boolean true on successful add to queue
	 */
	public boolean enQueueNS(Car car) {
		return this.northSouthTraffic.add(car);
	}
	
	
	/**
	 * Add car traveling in E/W direction to stopped traffic light
	 * @param car to be enqueued
	 * @return boolean true on successful add to queue
	 */
	public boolean enQueueEW(Car car) {
		return this.eastWestTraffic.add(car);
	}
	
	
	/**
	 * Dequeue cars based on current state of traffic light
	 * @return the Car dequeued from the priority queue
	 */
	public Car deQueue() {
		Car c;
		if(this.getState().compareTo("ns") == 0) {
			c = this.northSouthTraffic.peek();
			if(c != null) {
				this.northSouthTraffic.remove();
			}
			
		} else if (this.getState().compareTo("ew") == 0) {
			c = this.eastWestTraffic.peek();
			if(c != null) {
				this.eastWestTraffic.remove();
			}
		//default
		} else {
			return null;
		}
		return c;
	}

}
	
	 

