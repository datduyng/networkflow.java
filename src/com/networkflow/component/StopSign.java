package com.networkflow.component;

import java.util.LinkedList;

/**
 * Models a Stop Sign object extending from Intersection.
 * Uses FIFO data structure to allow cars to pass through.  
 * 
 * @author Reid Stagemeyer
 * @version 3.0 
 * @date March 25th, 2019
 *
 */

public class StopSign extends Intersection{
	
	private LinkedList<Car> carEnter;
	private String state;
	
	/**
	 * Default constructor
	 */
	public StopSign() {
		super("stop-sign");
		this.generalType = "stop-sign";
		this.state = "empty";
		this.increment = 0;
		this.mapIndex = new Point(0,0);
		this.carEnter = new LinkedList<Car>();
		this.numPassed = 0;
	}
	
	/**
	 * Constructor w/ index
	 * @param type
	 * @param mapIndex
	 */
	public StopSign(Point mapIndex) {
		super("stop-sign");
		this.generalType = "stop-sign";
		this.mapIndex = mapIndex;
		this.state = "empty";
		this.increment = 0;
		this.carEnter = new LinkedList<Car>();
		this.numPassed = 0;
	}
	
	/**
	 * Constructor w/index, and directions
	 * @param type
	 * @param mapIndex
	 * @param builtDirections
	 */
	public StopSign(Point mapIndex, String builtDirections) {
		super("stop-sign");
		this.generalType = "stop-sign";
		this.mapIndex = mapIndex;
		this.builtDirections = builtDirections;
		this.state = "empty";
		this.increment = 0;
		this.carEnter = new LinkedList<Car>();
		this.numPassed = 0;
	}
	
	/**
	 * Add car to Priority Queue 
	 * @param car to be added
	 * @return boolean true if car is successfully added
	 */
	public boolean addCarToQueue(Car car) {
		boolean carAdded = this.carEnter.add(car);
		//System.out.println("carAdded: " + carAdded);
		//System.out.println("Size of queue: " + this.carEnter.size());
		return carAdded;
	}

	
	/**
	 * Get Car that is at the head of the priority queue 
	 * @return Car
	 */
	public void deQueue() {
		if(this.state.equals("empty")) {
			Car car = this.carEnter.peek();
			//System.out.println(this.getCarEnter().size());
			if(car != null) {
				car.setState("passing");
				this.state = "passing";
				car.setIncrement(0);
				this.carEnter.removeFirst();
				this.numPassed++;
			}
		}
	}

	/**
	 * @override overrides updateIncrement() from Intersection class
	 * Done at each increment 'tick'.  Dequeues valid cars waiting at stop sign
	 * to pass through.
	 */
	public void updateIncrement() {
		
		this.deQueue(); // dequeue car 
		
		//Let car pass for 5 seconds before getting the next one 
		if(this.state.equals("passing")) {
			this.increment++;
			
			if(this.increment > 0 && this.increment % 10 == 0) {
				this.increment = 0;
				this.state = "empty";
			}
		}
	}
	
	public LinkedList<Car> getCarEnter() {
		return this.carEnter;
	}
	
	public void setCarEnter(LinkedList<Car> carEnter) {
		this.carEnter = carEnter;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String toString() {
		return this.classType;
	}

}
