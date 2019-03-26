package com.networkflow.component;

/**
 * Intersection is the Abstract Class that both StopSign and 
 * TrafficLight extend from.  Has members including: type, increment, 
 * and built-in allowed travel directions.
 * 
 * @author Reid Stagemeyer
 * @version 3.0 
 * @date March 25th, 2019
 *
 */

public abstract class Intersection extends Tile {
	
	protected String type;
	protected int increment;
	public String builtDirections;
	
	/**
	 * 
	 * @return type of Intersection: 'stop-sign', 'traffic-light'
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Increase increment by 1
	 */
	public void updateIncrement() {
		this.increment++;
	}
	
	/**
	 * 
	 * @return built in allowed travel directions of intersection, ex: '<>^v'
	 */
	public String getBuiltDirections() {
		return this.builtDirections;
	}
	
	/**
	 * Sets the built-in directions of the intersection
	 * @param str ex: '<>^v'
	 */
	public void setBuiltDirections(String str) {
		this.builtDirections = str;
	}

	/**
	 * Determine type of Intersection and initialize TrafficLight/Stop Sign object
	 * w/ its index, built-in directions, etc. 
	 * 
	 * @param generalType 'traffic-light' 'stop-sign'
	 * @param classType 
	 * @param mapIndex map position of intersection object created
	 * @param builtDirections allowed travel directions
	 * @return
	 */
	public static Intersection initIntersectionType(String generalType, String classType, Point mapIndex, String builtDirections) {
		if(generalType.equalsIgnoreCase("stop-sign")) {
			return new StopSign(classType, mapIndex, builtDirections);
		} else if(generalType.equalsIgnoreCase("traffic-light")) {
			return new TrafficLight(classType, mapIndex, builtDirections);
		}
		
		System.out.println("Invalid Intesection type");
		return null;
	}
	
	/**
	 * Return String w/ type map index and built-in directions
	 */
	public String toString() {
		return this.type + "@"  + this.mapIndex.toString() + this.builtDirections;
	}
	
	
}
