package com.networkflow.component;

import com.almasb.fxgl.entity.Entity;
import com.networkflow.component.Point;

/**
 * Models a Car object and its members/methods for traveling 
 * on roads from a starting to a stopping destination. 
 * 
 * @author Reid Stagemeyer
 * @version 3.0 
 * @date March 25th, 2019
 *
 */

public class Car extends Entity{
	public Car(String id, String direction, Point startIndex) {
		super();
		this.id = id;
		this.direction = direction;
		this.startIndex = startIndex;
	}

	private String id;
	private String direction;
	private int currentSpeed;
	private int increment; /* increments in one tile */
	private String state;
	Point startIndex;
	Point stopIndex;
	Point currentIndex; /* same as the tile position */
	Point Position;

	/**
	 * Parameterized Car object Constructor
	 * @param id car identification number
	 * @param direction current direction of travel '<,>,^
	 * @param increment keeps track of the cars position within a tile
	 * @param state current car's state: 'stopped', 'waiting', 'moving', 'passing', etc.
	 * @param start the car's starting position on the map
	 * @param stop the car's stopping position on the map
	 * @param currentPosition the car's current position on the map
	 */
	public Car(String id,String direction, int increment, String state, 
			Point startIndex, Point stopIndex, Point currentIndex) {
		super();
		this.id = id;
		this.direction = direction;
		this.currentSpeed = 0;
		this.increment = increment;
		this.state = state;
		this.startIndex = startIndex;
		this.stopIndex = stopIndex;
		this.currentIndex = currentIndex;
	}


	public void setStopIndex(Point stopIndex) {
		this.stopIndex = stopIndex;
	}

	public void setCurrentIndex(Point currentIndex) {
		this.currentIndex = currentIndex;
	}

	/**
	 * Empty Car Constructor, initializes a Car object with an initial increment
	 * of 0 and an initial state of 'stopped'.
	 */
	public Car(){
		this.increment = 0;
		this.state = "stopped";
	}

	/**
	 * Overrides the default toString() method
	 * Returns a string showing the direction and current position of the car.
	 */
	public String toString(){ 
		return "direction is " + direction + ", current position is " + "<" + this.getCurrentIndex().getX() +"," + this.getCurrentIndex().getY() + ">"; 
	} 


	/**
	 * The move method is called on car objects at every increment 'tick'.
	 * Updates the current state/speed of the car and its tile position/increment.
	 * 
	 * @param layout current map layout
	 */
	public void move(Tile[][] layout){
		System.out.println("increment: " + this.increment);
		
		
		//First, check current state of car
		//car is stopped, delay 1/2 second before moving
		if(this.state.equals("stopped")) {
			final int stopDelay = 1;
			//one-half second has passed, begin moving
			if((this.increment % stopDelay) == 0) {
				String currPosClassName = layout[this.getCurrentIndex().getY()][this.getCurrentIndex().getX()].getClass().getSimpleName();
				//car is currently in intersection, 'passing'
				if(currPosClassName.equals("TrafficLight") || currPosClassName.equals("StopSign")) {
					this.setState("passing");
				//car is moving on road
				} else {
					this.setState("moving"); // change state to  moving
				}
				this.increment = 0; // reset increment
				this.currentSpeed = 15;
			}
			
		//passing through intersection
		} else if(this.state.equals("passing")) {
			//get current tile position
			int currX = this.getCurrentIndex().getX();
			int currY = this.getCurrentIndex().getY();
			Tile currTile = layout[currY][currX];
			String currTileClassName = currTile.getClass().getSimpleName();
			//get possible turning directions
			String builtDirections = ((Intersection) currTile).getBuiltDirections();
			Point nextTile = null; // next tile to check based on current direction
			/*
			 * TODO: Change so that car waits at beginning of intersection for 
			 * full length of intersection passage, then turns and updates positions.
			 * 
			 */
			if(this.currentSpeed == 15) {
				//5 seconds, through intersection, turn, move tile
				if((this.increment % 10) == 0) {
					this.setDirection(this.turn(builtDirections)); // turn (change dir)
					nextTile = this.moveTile();
					//update position if next tile is valid
					if(this.checkTile(nextTile, layout) == true) {
						this.setCurrentPosition(this.moveTile());
						this.setState("moving");
						this.setIncrement(0);
					} else {
						this.setState("deadend"); // road ends
					}
				}
			} else if(this.currentSpeed == 30) {
				//5 seconds, through intersection, turn, move tile
				if((this.increment % 5) == 0) {
					this.setDirection(this.turn(builtDirections)); // turn (change dir)
					nextTile = this.moveTile();
					//update position if next tile is valid
					if(this.checkTile(nextTile, layout) == true) {
						this.setCurrentPosition(this.moveTile());
						this.setState("moving");
						this.setIncrement(0);
					} else {
						this.setState("deadend");
					}
				}
			}
			
		//car is moving on road
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
					} else {
						this.setState("deadend"); //road ends
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
					} else {
						this.setState("deadend");
					}
				}
			}
		}
		
		// only update increment if car is not waiting at intersection or at a deadend
		if(this.state.equals("waiting") == false && this.state.equals("deadend") == false) {
			this.increment++;
		}
	}
	
	/**
	 * Changes car's current state to 'stopped' which is used to indicate
	 * to delay for 1/2 second before beginning to move.
	 * 
	 * Also sets current speed to 0.
	 */
	public void stop() {
		this.state =  "stopped";
		this.currentSpeed = 0;
	}
	
	/**
	 * Changes current car's state to 'waiting' for waiting at an intersection. 
	 * 
	 * Also sets current speed to 0.
	 */
	public void waiting() {
		this.state =  "waiting";
		this.currentSpeed = 0;
	}
	
	/**
	 * Gets the next Tile Point position on the map based on the car's 
	 * current traveling direction.
	 * 
	 * @return the next tile point
	 */
	public Point moveTile() {
		int currentXIndex = this.getCurrentIndex().getX();
		int currentYindex = this.getCurrentIndex().getY();
		switch (this.direction) {
		case ">":
			//go right
			return new Point((currentXIndex + 1), currentYindex);
		case "<":
			//go left
			return new Point((currentXIndex - 1), currentYindex);
		case "^":
			//go up
			return new Point(currentXIndex, (currentYindex - 1));
		case "v":
			//go down
			return new Point(currentXIndex, (currentYindex + 1));
		default :
			return null;
		}
		
	}

	/**
	 * Checks the next Tile Point position on the current map layout and
	 * returns a boolean of 'true' if the tile is a valid one to move too.
	 * 
	 * @param nextTile the car's next proposed position to check
	 * @param layout the current map Tile layout
	 * @return 'true' if valid, otherwise 'false'
	 */
	public boolean checkTile(Point nextTile, Tile[][] layout) {
		Tile tile = layout[nextTile.getY()][nextTile.getX()];
		System.out.println(tile.getClass().getSimpleName());
		
		/*
		 * Make sure road direction allows car's current travel direction
		 */
		if(tile.getClass().getSimpleName().equals("Road")) {
			Road road = (Road) tile;
			if((this.getDirection().equals(">") || this.getDirection().equals("<")) && road.getClassType().equals("road-horizontal")) {
				//car is traveling E/W and next tile is E/W road, allow to move
				return true;
			} else if((this.getDirection().equals("^") || this.getDirection().equals("v")) && road.getClassType().equals("road-verticle")) {
				//car is traveling N/S and next tile is N/S road, allow to move
				return true;
			} else {
				//search for directions to turn shouldn't happen
				return false;
			}
		/*
		 * Next position is ground, search for valid direction to turn
		 */
		} else if(tile.getClass().getSimpleName().equals("Ground")) {
			Point currentIndex = this.getCurrentIndex();
			int currentXIndex  = currentIndex.getX();
			int currentYIndex = currentIndex.getY(); 
			if(this.getDirection().equals("<") || this.getDirection().equals(">")) {
				//can either turn north or south
				//check north tile
				if(layout[currentYIndex - 1][currentXIndex].getClass().getSimpleName().equals("Road")) {
					this.setDirection("^");
					return true;
				//check south tile
				} else  if (layout[currentYIndex + 1][currentXIndex].getClass().getSimpleName().equals("Road")) {
					this.setDirection("v");
					return true;
				} else {
					//default
					return false;
				}
			} else if(this.getDirection().equals("^") || this.getDirection().equals("v")) {
				//can either turn east or west 
				//check east tile
				if(layout[currentYIndex][currentXIndex + 1].getClass().getSimpleName().equals("Road")) {
					this.setDirection(">");
					return true;
				//check west tile
				} else  if (layout[currentYIndex][currentXIndex - 1].getClass().getSimpleName().equals("Road")) {
					this.setDirection("<");
					return true;
				} else {
					//default
					return false;
				}
			} else {
				//default
				return false;
			}
		
		/*
		 * Next position is a Stop Sign, add car to queue and have it wait
		 */
		} else if(tile.getClass().getSimpleName().equals("StopSign")) {
			StopSign stopSign = (StopSign) tile;
			this.waiting(); //have car wait
			stopSign.addCarToQueue(this);
			return false;
		/*
		 * TODO: Have car pass through for full time, then turn and continue on
		 * Next position is a Traffic Light, check color and have car pass or wait 
		 * at sign.
		 */
		
		} else if(tile.getClass().getSimpleName().equals("TrafficLight")) {
			TrafficLight trafficLight = (TrafficLight) tile;
			if(trafficLight.getColor().contains(this.getDirection()) && trafficLight.getState().equals("empty")) {
				this.setState("passing");
				//light is green in traveling direction and no cross traffic
				//car enters intersection 
				return true;
			} else if(trafficLight.getColor().contains(this.getDirection()) && trafficLight.getState().equals("passing")) {
				//TODO: Light is green but has cross traffic, stop and wait
				this.waiting(); //wait car
				trafficLight.enQueue(this);
				return false;
			} else if(trafficLight.getColor().contains(this.getDirection()) == false) {
				//light is red
				this.waiting(); //wait car
				trafficLight.enQueue(this);
				return false;
			} else {
				//default 
				return false;
			}
		} else {
			//default
			return false;
		}
	}
	
	/**
	 * Takes the built-in allowed traveling directions 
	 * from the current intersection and chooses a random valid 
	 * direction that is not a U-Turn.
	 * 
	 * @param builtDirections the allowed travel directions from the intersection
	 * @return the car's new traveling direction, chosen randomly
	 */
	public String turn(String builtDirections) {
		String oppDir;
		if(this.getDirection().equals("<")) {
			oppDir = ">";
		} else if(this.getDirection().equals(">")) {
			oppDir = "<";
		} else if(this.getDirection().equals("^")) {
			oppDir = "v";
		} else if(this.getDirection().equals("v")) {
			oppDir = "^";
		} else {
			oppDir = null;
		}
		
		int randInt = (int) (Math.random() * builtDirections.length());
		String newDirection = Character.toString(builtDirections.charAt(randInt));
		while(newDirection.equals(oppDir)) {
			randInt = (int) (Math.random() * builtDirections.length());
			newDirection = Character.toString(builtDirections.charAt(randInt));
		}
		System.out.println("New Direction: " + newDirection);
		return newDirection;
	}
	
	/**
	 * 
	 * @return this car's id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id the id of the car
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return current traveling direction of this car
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @param set car's current traveling direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	/**
	 * 
	 * @param speed current speed of car to set
	 */
	public void setCurrentSpeed(int speed) {
		this.currentSpeed = speed;
	}
	
	/**
	 * 
	 * @return car's current speed
	 */
	public int getCurrentSpeed() {
		return this.currentSpeed;
	}
	
	/**
	 * 
	 * @return position increment of car between two tiles
	 */
	public int getIncrement() {
		return increment;
	}
	
	/**
	 * 
	 * @param increment set current increment of car
	 */
	public void setIncrement(int increment) {
		this.increment = increment;
	}
	
	/**
	 * 
	 * @return current state of car: 'stopped', 'waiting', 'passing', etc.
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * 
	 * @param state set current car state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * 
	 * @return starting position (Point) of Car
	 */
	public Point getStartIndex() {
		return startIndex;
	}
	
	/**
	 * 
	 * @param start set start position (Point) of Car
	 */
	public void setStartIndex(Point startIndex) {
		this.startIndex = startIndex;
	}
	
	/**
	 * 
	 * @return get car's stopping position (Point)
	 */
	public Point getStopIndex() {
		return stopIndex;
	}
	
	/**
	 * 
	 * @param stop set car's stopping position (Point)
	 */
	public void setStop(Point stopIndex) {
		this.stopIndex = stopIndex;
	}
	
	/**
	 * 
	 * @return current position (Point) of car
	 */
	public Point getCurrentIndex() {
		return currentIndex;
	}
	
	/**
	 * 
	 * @param currentIndex sets current position (Point) of car
	 */
	public void setCurrentPosition(Point currentIndex) {
		this.currentIndex = currentIndex;
	}

}

