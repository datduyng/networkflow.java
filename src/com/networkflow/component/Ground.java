package com.networkflow.component;

/**
 * Models a Ground object considered to be bare soil
 * or grass which Cars do not travel on.
 * 
 * @author Reid Stagemeyer
 * @version 3.0 
 * @date March 25th, 2019
 *
 */

public class Ground extends Tile{
	

	/**
	 * Default Constructor
	 */
	public Ground() {
		super("ground");
	}
	
	/**
	 * Constructor w/ type
	 * @param type type of ground: 'ground', 'grass', etc.
	 */
	public Ground(String classType) {
		super("ground");
		this.classType = classType;
	}
 	
	/**
	 * 
	 * @return type of ground
	 */
	public String getGeneralType() {
		return this.generalType;
	}
	
	/**
	 * returns String of the type of Ground
	 */
	public String toString() {
		return this.classType;
	}
}
