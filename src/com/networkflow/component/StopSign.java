package com.networkflow.component;

import java.util.PriorityQueue;

/**
 * Models a Stop Sign object extending from Intersection.
 * Uses FIFO data structure to allow cars to pass through.  
 * 
 * @author Reid Stagemeyer
 * @version 3.0 
 * @date March 25th, 2019
 *
 */

public class StopSign extends Intersection {
	
	private PriorityQueue<Car> carEnter;
	private String state;
	
	/**
	 * Default constructor
	 */
	public StopSign() {
		this.type = "stop-sign";
		this.state = "empty";
		this.increment = 0;
		this.mapIndex = new Point(0,0);
		this.carEnter = new PriorityQueue<Car>();
	}
	
	/**
	 * Constructor with type
	 * @param type 'stop-sign'
	 */
	public StopSign(String type) {
		this.type = type;
		this.state = "empty";
		this.increment = 0;
		this.mapIndex = new Point(0,0);
		this.carEnter = new PriorityQueue<Car>();
	
	}
	
	/**
	 * Constructor w/ type and index
	 * @param type
	 * @param mapIndex
	 */
	public StopSign(String type, Point mapIndex) {
		this.mapIndex = mapIndex;
		this.type = type;
		this.state = "empty";
		this.increment = 0;
		this.carEnter = new PriorityQueue<Car>();
	}
	
	/**
	 * Constructor w/ type, index, and directions
	 * @param type
	 * @param mapIndex
	 * @param builtDirections
	 */
	public StopSign(String type, Point mapIndex, String builtDirections) {
		this.mapIndex = mapIndex;
		this.builtDirections = builtDirections;
		this.type = type;
		this.state = "empty";
		this.increment = 0;
		this.carEnter = new PriorityQueue<Car>();
	}
	
	/**
	 * Add car to Priority Queue 
	 * @param car to be added
	 * @return boolean true if car is successfully added
	 */
	public boolean addCarToQueue(Car car) {
		boolean carAdded = this.carEnter.add(car);
		if(this.carEnter.isEmpty() == false) {
			this.setState("passing");
		}
		return carAdded;
	}

	
	/**
	 * Get Car that is at the head of the priority queue 
	 * @return Car
	 */
	public void deQueue() {
		Car car = this.carEnter.peek();
		if(car != null) {
			car.setCurrentPosition(car.moveTile());
			car.setIncrement(0);
			car.setState("stopped");
			this.carEnter.remove();
		}
		if(this.carEnter.isEmpty()) {
			this.setState("empty");
		}
	}

	/**
	 * @override overrides updateIncrement() from Intersection class
	 * Done at each increment 'tick'.  Dequeues valid cars waiting at stop sign
	 * to pass through.
	 */
	public void updateIncrement() {
		this.deQueue();	
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
	
	public String toString() {
		return this.state;
	}

}
