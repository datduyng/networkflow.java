package component;

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

public class TrafficLight extends Intersection {

	private String state;
	private String color;
	
	private PriorityQueue<Car> nsTraffic;
	private PriorityQueue<Car> ewTraffic;
	
	/**
	 * Default constructor
	 */
	public TrafficLight() {
		this.state = "empty";
		this.increment = 0;
		this.color = "<>"; //default starting direction - allowing E/W traffic
		this.nsTraffic = new PriorityQueue<Car>();
		this.ewTraffic = new PriorityQueue<Car>();
	}

	/**
	 * Constructor w/ type
	 * @param type 'traffic-light'
	 */
	public TrafficLight(String type) {
		this.type = type;
		this.state = "empty";
		this.increment = 0;
		this.color = "<>"; //default starting direction - allowing E/W traffic
		this.nsTraffic = new PriorityQueue<Car>();
		this.ewTraffic = new PriorityQueue<Car>();
	}
	
	/**
	 * Constructor w/ type and index
	 * @param type 'traffic-light
	 * @param mapIndex position on map
	 */
	public TrafficLight(String type, Point mapIndex) {
		this.mapIndex = mapIndex;
		this.type = type;
		this.state = "empty";
		this.increment = 0;
		this.color = "<>"; //default starting direction - allowing E/W traffic
		this.nsTraffic = new PriorityQueue<Car>();
		this.ewTraffic = new PriorityQueue<Car>();
	}
	
	/**
	 * Constructor w/ type, index, and directions
	 * @param type 'traffic-light'
	 * @param mapIndex position on map
	 * @param builtDirections built-in allowed travel directions
	 */
	public TrafficLight(String type, Point mapIndex, String builtDirections) {
		this.mapIndex = mapIndex;
		this.builtDirections = builtDirections;
		this.type = type;
		this.state = "empty";
		this.increment = 0;
		this.color = "<>"; //default starting direction - allowing E/W traffic
		this.nsTraffic = new PriorityQueue<Car>();
		this.ewTraffic = new PriorityQueue<Car>();
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
	
	/**
	 * @override overrides the  update Increment of Intersection
	 * Adds 1 to increment and dequeues applicable cars based on traffic light color.
	 * Switches traffic-light color every 2 minutes.
	 */
	public void updateIncrement() {
		this.increment++;
		System.out.println("Traffic Light Inc: " + this.increment);
		this.deQueue();
		//240 - 1/2 seconds = 120 seconds (2 min)
		if(this.increment % 240 == 0) {
			this.increment = 0;
			this.switchColor();
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
		Car car = null;
		if (this.color.equals("^v")) {
			car = this.nsTraffic.peek();
			if(car != null) {
				car.setCurrentPosition(car.moveTile());
				car.setIncrement(0);
				car.setState("stopped");
				this.nsTraffic.remove(car);
			}
		} else if (this.color.equals("<>")) {
			car = this.ewTraffic.peek();
			if(car != null) {
				car.setCurrentPosition(car.moveTile());
				car.setIncrement(0);
				car.setState("stopped");
				this.ewTraffic.remove(car);
			}
		} else {
			//default
		}
	}
	
	public String getColor() {
		return this.color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
 	
	public String toString() {
		return this.type;
	}
}
	
	 

