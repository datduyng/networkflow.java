package com.networkflow.component;

import java.util.LinkedList;

import com.almasb.fxgl.entity.Entity;

/**
 * Models a Stop Sign object extending from Intersection.
 * Uses FIFO data structure to allow cars to pass through.  
 * 
 * @author Reid Stagemeyer
 * @version 3.0 
 * @date March 25th, 2019
 *
 */

public class TrafficLight extends Intersection {

	private String state;
	private String color;
	private int passingInc;
	
	private LinkedList<Car> nsTraffic;
	private LinkedList<Car> ewTraffic;
	
	Entity trafficLightEntity;
	
	/**
	 * Default constructor
	 */
	public TrafficLight() {
		super("traffic-light");
		this.state = "empty";
		this.increment = 0;
		this.color = "<>"; //default starting direction - allowing E/W traffic
		this.nsTraffic = new LinkedList<Car>();
		this.ewTraffic = new LinkedList<Car>();
		this.passingInc = 0;
	}

	/**
	 * Constructor w/ type and index
	 * @param type 'traffic-light
	 * @param mapIndex position on map
	 */
	public TrafficLight(Point mapIndex) {
		super("traffic-light");
		this.mapIndex = mapIndex;
		this.state = "empty";
		this.increment = 0;
		this.color = "<>"; //default starting direction - allowing E/W traffic
		this.nsTraffic = new LinkedList<Car>();
		this.ewTraffic = new LinkedList<Car>();
		this.passingInc = 0;

	}
	
	/**
	 * Constructor w/ type, index, and directions
	 * @param type 'traffic-light'
	 * @param mapIndex position on map
	 * @param builtDirections built-in allowed travel directions
	 */
	public TrafficLight(Point mapIndex, String builtDirections) {
		super("traffic-light");
		this.mapIndex = mapIndex;
		this.builtDirections = builtDirections;
		this.state = "empty";
		this.increment = 0;
		this.color = "<>"; //default starting direction - allowing E/W traffic
		this.nsTraffic = new LinkedList<Car>();
		this.ewTraffic = new LinkedList<Car>();
		this.passingInc = 0;

	}
	
	/**
	 * @override overrides the  update Increment of Intersection
	 * Adds 1 to increment and dequeues applicable cars based on traffic light color.
	 * Switches traffic-light color every 2 minutes.
	 */
	public void updateIncrement() {
		//System.out.println("size: " + this.nsTraffic.size());
		this.increment++;
		
		//allow 5 seconds for car to pass through light
		if(this.state.equals("passing")) {
			this.passingInc++;
			if(this.passingInc > 0 && this.passingInc % 10 == 0) {
				this.state = "empty";
				this.passingInc = 0;
			}
		}
		//System.out.println("Traffic Light Inc: " + this.increment);
		if(this.state.equals("empty")) {
			this.deQueue();
		}
		//240 - 1/2 seconds = 120 seconds (2 min)
		if(this.increment > 0 && this.increment % 240 == 0) {
			this.increment = 0;
			this.switchColor();
			this.rotateTrafficLightEntity();

		}
	}
	
	/**
	 * Switches traffic-light color
	 */
	public void switchColor() {
		if(this.color.equals("<>")) {
			this.color =  "^v";
		} else if(this.color.equals("^v")) {
			this.color = "<>";
		}
	}
	
	/**
	 * Adds car to appropriate queue based on direction.
	 * @param car car to be added
	 */
	public void enQueue(Car car) {
		switch (car.getDirection()) 
		{
			case "^" :
				this.nsTraffic.add(car);
				break;
			case "v" :
				this.nsTraffic.add(car);
				break;
			case "<" :
				this.ewTraffic.add(car);
				break;
			case ">" :
				this.ewTraffic.add(car);
				break;
			default :
				
				break;
			
		}
	}
	
	/**
	 * Removes car (if any) from appropriate queue based on current 
	 * traffic-light direction, resets its state to 'stopped' 
	 */
	public void deQueue() {
		//System.out.println("deQueue() (traffic light): ");
		Car car = null;
		if (this.color.equals("^v")) {
			car = this.nsTraffic.peek();
			if(car != null) {
				this.nsTraffic.removeFirst();
				//System.out.println("size: " + this.nsTraffic.size());
				car.setState("passing");
				this.state = "passing";
				//System.out.println("dequeud car state: " + car.getState());
				car.setIncrement(0);
			}
		} else if (this.color.equals("<>")) {
			car = this.ewTraffic.peek();
			if(car != null) {
				this.ewTraffic.removeFirst();
				car.setState("passing");
				this.state = "passing";
				car.setIncrement(0);
			}
		} else {
			//default
		}
	}
	
	public void rotateTrafficLightEntity() {
		double oldRotAng = this.trafficLightEntity.getRotation();
		this.trafficLightEntity.setRotation(oldRotAng + 90);
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
	
	public String getColor() {
		return this.color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public Entity getTrafficLightEntity() {
		return trafficLightEntity;
	}


	public void setTrafficLightEntity(Entity trafficLightEntity) {
		this.trafficLightEntity = trafficLightEntity;
	}
 	
	public String toString() {
		return this.classType;
	}
}
	
	 

