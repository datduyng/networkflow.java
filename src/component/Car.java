package component;

import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;
import component.Point;

public class Car {
	private String id;
	private String direction;
	//private int initialSpeed;
	private int currentSpeed;
	private int increment; /* increments in one tile */
	private String state;
	Point start;
	Point stop;
	Point currentPosition; /* same as the tile position */


	//constructors
	public Car(String id,String direction, int increment, String state, Point start, Point stop,
			Point currentPosition) {
		super();
		this.id = id;
		this.direction = direction;
		//this.initialSpeed = 15;
		//this.speed = 30;
		this.increment = increment;
		this.state = state;
		this.start = start;
		this.stop = stop;
		this.currentPosition = currentPosition;
	}

	//get component
	public Car(){}

	//toString
	public String toString(){ 
		return "direction is " + direction + ", current position is " + "<" + this.getCurrentPosition().getX() +"," + this.getCurrentPosition().getY() + ">"; 
	} 

	//move 
	public void move(){
		this.increment++;
		System.out.println("increment " + this.increment);
		if(this.state.equals("stopped")) {
			final int stopDelay = 1;
			//one-half second has passed
			if((this.increment % stopDelay) == 0) {
				this.setState("moving"); // change state to  moving
				this.increment = 0; // reset increment
				this.currentSpeed = 15;
			}
		} else if(this.state.equals("moving")) {
			if(this.currentSpeed == 15) {
				//30 seconds, move tile
				if((this.increment % 60) == 0) {
					this.setCurrentPosition(this.moveTile());
					this.currentSpeed = 30; //increase speed
					this.increment = 0; // reset increment
				}
			} else if(this.currentSpeed == 30) {
				//15 seconds, move tile
				if((this.increment % 30) == 0) {
					this.setCurrentPosition(this.moveTile());
					this.increment = 0; // reset increment
				}
			}
		}
	}
	
	//stop function, change state to stop
	//reset currentSpeed to 0 mph 
	public void stop() {
		this.state =  "stopped";
		this.currentSpeed = 0;
	}
	
	public Point moveTile() {
		//Point currPoint = this.getCurrentPosition();
		int currentX = this.getCurrentPosition().getX();
		int currentY = this.getCurrentPosition().getY();
		switch (this.direction) {
		case ">":
			//go right
			return new Point(currentX, (currentY + 1));
		case "<":
			//go left
			return new Point(currentX, (currentY - 1));
		case "^":
			//go up
			return new Point((currentX - 1), currentY);
		case "v":
			//go down
			return new Point((currentX + 1), currentY);
		default :
			return null;
		}
		
	}
	
	//getter and setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	/*
	public int getInitialSpeed() {
		return initialSpeed;
	}
	public void setInitialSpeed(int initialSpeed) {
		this.initialSpeed = initialSpeed;
	}
	*/
	public void setCurrentSpeed(int speed) {
		this.currentSpeed = speed;
	}
	
	public int getCurrentSpeed() {
		return this.currentSpeed;
	}
	public int getIncrement() {
		return increment;
	}
	public void setIncrement(int increment) {
		this.increment = increment;
	}
	public void updateIncrement() {
		this.increment++;
	}
	public void resetIncrement() {
		this.increment = 0;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Point getStart() {
		return start;
	}
	public void setStart(Point start) {
		this.start = start;
	}
	public Point getStop() {
		return stop;
	}
	public void setStop(Point stop) {
		this.stop = stop;
	}
	public Point getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(Point currentPosition) {
		this.currentPosition = currentPosition;
	}

}

