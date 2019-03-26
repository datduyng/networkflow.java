package com.networkflow.component;

/**
 * Models a Road object that has a type of 'vertical-road' 
 * or 'horizontal-road' on which cars can travel.
 * 
 * @author Reid Stagemeyer
 * @version 3.0 
 * @date March 25th, 2019
 *
 */

public class Road extends Tile {
	
	private String type;
	
	/**
	 * Default constructor
	 */
	public Road() {
		super();
		this.type = "Road";
	}
	
	/**
	 * Road constructor w/ type
	 * @param type 'vertical-road' or 'horizontal-road'
	 */
	public Road(String type) {
		this.type = type;
	}
	 
	/**
	 * 
	 * @return type of road
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Returns String of the type of road: 'vertical-road', or 'horizontal-road'
	 */
	public String toString() {
		return this.type;
	}
}
