package com.networkflow.component;

import com.almasb.fxgl.entity.Entity;

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
	
	public Intersection(Entity mapEntity, int numCarPass, Point mapIndex, Point position, String type) {
		super(mapEntity, numCarPass, mapIndex, position, type);
		// TODO Auto-generated constructor stub
	}
	
	public Intersection(String classType) {
		super("intersection");
		this.classType = classType;
	}

	protected String generalType;
	protected int increment;
	protected String builtDirections;
	
	/**
	 * 
	 * @return type of Intersection: 'stop-sign', 'traffic-light'
	 */
	public String getGeneralType() {
		return this.generalType;
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
	 * @param generalType 'intersection'
	 * @param classType 'traffic-light' or 'stop-sign'
	 * @param mapIndex map position of intersection object created
	 * @param builtDirections allowed travel directions
	 * @return
	 */
	public static Intersection initIntersectionType(String classType, Point mapIndex, String builtDirections) {
		if(classType.equalsIgnoreCase("stop-sign")) {
			((Intersection) SimulationMap.getTileAtIndex(mapIndex)).setBuiltDirections(builtDirections);
			return new StopSign(mapIndex, builtDirections);
		} else if(classType.equalsIgnoreCase("traffic-light")) {
			((Intersection) SimulationMap.getTileAtIndex(mapIndex)).setBuiltDirections(builtDirections);
			return new TrafficLight(mapIndex, builtDirections);
		}
		
		System.out.println("Invalid Intesection type");
		return null;
	}
	
	public void deQueue() {
		
	}
	
	/**
	 * Return String w/ type map index and built-in directions
	 */
	public String toString() {
		return this.classType + "@"  + this.mapIndex.toString() + this.builtDirections;
	}
	
	
}
