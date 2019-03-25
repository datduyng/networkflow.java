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


	/**
	 * Parameterized Car object Constructor
	 * @param id car identification number
	 * @param direction current direction of travel '<,>,^
	 * @param increment
	 * @param state
	 * @param start
	 * @param stop
	 * @param currentPosition
	 */
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
	public void move(Tile[][] layout){
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
			Point nextTile = null;
			if(this.currentSpeed == 15) {
				//30 seconds, move tile
				if((this.increment % 60) == 0) {
					nextTile = this.moveTile();
					//update position if next tile is valid
					if(this.checkTile(nextTile, layout) == true) {
						this.setCurrentPosition(this.moveTile());
						this.currentSpeed = 30; //increase speed
						this.increment = 0; // reset increment
					}
				}
			} else if(this.currentSpeed == 30) {
				//15 seconds, move tile
				if((this.increment % 30) == 0) {
					nextTile = this.moveTile();
					//update position if next tile is valid
					if(this.checkTile(nextTile, layout) == true) {
						this.setCurrentPosition(this.moveTile());
						this.increment = 0; // reset increment
					}
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
			return new Point((currentX + 1), currentY);
		case "<":
			//go left
			return new Point((currentX - 1), currentY);
		case "^":
			//go up
			return new Point(currentX, (currentY - 1));
		case "v":
			//go down
			return new Point(currentX, (currentY + 1));
		default :
			return null;
		}
		
	}
	
	public boolean checkTile(Point nextTile, Tile[][] layout) {
		Tile tile = layout[nextTile.getY()][nextTile.getX()];
		System.out.println(tile.getClass().getSimpleName());
		if(tile.getClass().getSimpleName().equals("Road")) {
			Road road = (Road) tile;
			if((this.getDirection().equals(">") || this.getDirection().equals(">")) && road.getType().equals("road-horizontal")) {
				return true;
			} else if((this.getDirection().equals("^") || this.getDirection().equals("v")) && road.getType().equals("road-verticle")) {
				return true;
			} else {
				return false;
			}
		} else if(layout[nextTile.getY()][nextTile.getX()].getClass().getSimpleName().equals("Ground")) {
			return false;
		} else if(layout[nextTile.getY()][nextTile.getX()].getClass().getSimpleName().equals("StopSign")) {
			return false;
		} else if(layout[nextTile.getY()][nextTile.getX()].getClass().getSimpleName().equals("TrafficLight")) {
			return false;
		} else {
			return false;
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

