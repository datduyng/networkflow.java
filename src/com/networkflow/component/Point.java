package com.networkflow.component;
import javafx.beans.property.IntegerProperty;
/**
 * Models a Point object that has integer x and y coordinates.
 * 
 * @author Reid Stagemeyer
 * @version 3.0 
 * @date March 25th, 2019
 *
 */

public class Point {
	
	private int x;
	private int y;
	
	/**
	 * Constructor w/ x and y coordinates
	 * @param x-axis coordinate
	 * @param y-axis coordinate
	 */
	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * 
	 * @return x-axis coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * 
	 * @param x-axis coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * 
	 * @return y-axis coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @param y-axis coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Increments the x-axis coordinate by 1
	 */
	public void increaseX() {
		this.x++;
	}
	
	/**
	 * Increments the y-axis coordinate by 1
	 */
	public void increaseY() {
		this.y+=1;
	}
	
	/**
	 * Decrements the x-axis coordinate by 1
	 */
	public void decreaseX() {
		this.x-=1;
	}
	
	/**
	 * Decrements the y-axis coordinate by 1
	 */
	public void decreaseY() {
		this.y--;
	}
	
	/**
	 * Returns String object of the x and y coordinates of the Point object.
	 */
	public String toString() {
		return "x:" + this.x + "y:" + this.y + " ";
	}
	
}

