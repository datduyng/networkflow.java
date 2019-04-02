package com.networkflow.component;

import java.util.LinkedList;

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

public class Car extends Entity {
	public Car(String id, String direction, Point startIndex) {
		super();
		this.id = id;
		this.direction = direction;
		this.startIndex = startIndex;
	}

	private static final int stopDelay = 1;
	
	private String id;
	private String direction;
	private int currentSpeed;
	private int increment; /* increments in one tile */
	private String state;
	Point startIndex;
	Point stopIndex;
	Point currentIndex; /* same as the tile position */
	Entity carEntity;

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

	/**
	 * Empty Car Constructor, initializes a Car object with an initial increment
	 * of 0 and an initial state of 'stopped'.
	 */
	public Car(){
		this.increment = 0;
		this.state = "idel";
	}


	/**
	 * The move method is called on car objects at every increment 'tick'.
	 * Updates the current state/speed of the car and its tile position/increment.
	 * 
	 * @param layout current map layout
	 */
	public void move(Tile[][] layout){
		System.out.println("increment: " + this.increment);
		
		//stop car if it has reached its destination 
		//this.checkCurrentIndex(); TODO: Implement end point
		
		this.updateState(layout);
		this.enQueue(layout);
		this.updateIncrement();
	}
	
	public void updateState(Tile[][] layout) {
		Point nextTile = null;

		switch (this.state) {
		case "stopped":
			
			
			break;
			
		case "waiting":
			
			break;
			
		case "idel":
			//one-half second has passed, begin moving
			if((this.increment % stopDelay) == 0) {
				this.increment = 0;
				this.state = "accel"; // change state to accel
			}
			break;
			
		case "passing":
			//get current intersection 
			Point intersectionTile = this.getNextTile(this.currentIndex);
			System.out.println("x: " + intersectionTile.getX());
			System.out.println("x: " + intersectionTile.getY());
			//get possible turning directions
			String builtDirections = ((Intersection) SimulationMap.getTileAtIndex(intersectionTile)).getBuiltDirections();
			System.out.println("builtDirections: " + builtDirections);
			//5 seconds, through intersection, turn, move tile
			if(this.increment > 0 && (this.increment % 10) == 0) {
				this.direction = this.turn(builtDirections); // turn (change dir)
				nextTile = this.getNextTile(intersectionTile);
				//update position if next tile is valid
				if(this.checkTile(nextTile, layout) == true) {
					this.currentIndex = nextTile;
					this.state = "accel";
					this.increment = 0;
				} else {
					this.state = "stopped"; // road ends
				}
			} 
			break;
			
		case "accel":
			//30 seconds, move tile
			if(this.increment > 0 && (this.increment % 60) == 0) {
				nextTile = this.getNextTile(this.currentIndex);
				//update position if next tile is valid
				if(this.checkTile(nextTile, layout) == true) {
					this.currentIndex = nextTile;
					this.state = "moving"; //increase speed
					this.increment = 0; // reset increment
				} else {
					//next tile isn't valid and not waiting/passing at intersection
					if(this.state.equals("accel")) {
						this.state = "stopped"; // road ends
					}
				}
			}
			break;
			
		case "moving":
			//15 seconds, move tile
			if(this.increment > 0 && (this.increment % 30) == 0) {
				nextTile = this.getNextTile(this.currentIndex);
				//update position if next tile is valid
				if(this.checkTile(nextTile, layout) == true) {
					this.currentIndex = nextTile;
					this.increment = 0; // reset increment
				} else {
					//next tile isn't valid and not waiting/passing at intersection
					if(this.state.equals("moving")) {
						this.state = "stopped"; // road ends
					}
				}
			}
			break;
		
		default:
			//default
			break;
		}
		
		
	}
	
	/**
	 * Adds car to appropriate queue if currently waiting at intersection
	 * @param layout current map layout
	 */
	public void enQueue(Tile[][] layout) {
		if(this.state.equals("waiting")) {
			Point nextPoint = this.getNextTile(this.currentIndex);
			int nextX = nextPoint.getX();
			int nextY = nextPoint.getY();
			String className = layout[nextY][nextX].getClass().getSimpleName();
			
			if(className.equals("StopSign")) {
				((StopSign) layout[nextY][nextX]).addCarToQueue(this);
					
			} else if (className.equals("TrafficLight")) {
				((TrafficLight) layout[nextY][nextX]).enQueue(this);
				
			} else {
				//default
			}
		}
				
	}
	
	/**
	 * Gets the next Tile Point position on the map based on the car's 
	 * current traveling direction.
	 * 
	 * @return the next tile point
	 */
	public Point getNextTile(Point currentTile) {
		int currentXIndex = currentTile.getX();
		int currentYindex = currentTile.getY();
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
	 * returns a boolean of 'true' if the tile is a valid one to move to.
	 * Updates the car's position if necessary
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
			this.state = "waiting"; //have car wait
			return false;
		
		} else if(tile.getClass().getSimpleName().equals("TrafficLight")) {
			if(((TrafficLight) tile).getColor().contains(this.getDirection()) && ((TrafficLight) tile).getState().equals("empty")) {
				this.state = "passing";
				//light is green in traveling direction and no cross traffic
				//car enters intersection 
				return false;
			} else if(((TrafficLight) tile).getColor().contains(this.getDirection()) && ((TrafficLight) tile).getState().equals("passing")) {
				//TODO: Light is green but has cross traffic, stop and wait
				this.state = "waiting"; //wait car
				return false;
			} else if(((TrafficLight) tile).getColor().contains(this.getDirection()) == false) {
				//light is red
				this.state = "waiting"; //wait car
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
		String oldDir = this.getDirection();
		
		//TODO: allow U-Turns if built directions is size 2
		//currently keeps looping until it finds a valid non uturn
		//infinite loop occurs if uturn is only possible turn
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
		
		this.rotateCarEntity(oldDir, newDirection);
		this.updateCarEntityTilePos(oldDir, newDirection);
		System.out.println("New Direction: " + newDirection);
		return newDirection;
	}
	
	/**
	 * rotates car entity's direction after it has turned
	 * @param oldDir
	 * @param newDir
	 */
	public void rotateCarEntity(String oldDir, String newDir) {
		double currRotAng = this.getCarEntity().getRotation();
		double rotAngDeg = RotationMapper.getRotationMapping().get(oldDir + newDir).doubleValue();
		System.out.println("rotAngDeg: " + rotAngDeg);
		this.getCarEntity().setRotation(currRotAng + rotAngDeg);
	}
	
	/**
	 * Checks current car index and stops car if it 
	 * is at its destination point.
	 */
	public void checkCurrentIndex() {
		int currX = this.getCurrentIndex().getX();
		int currY = this.getCurrentIndex().getY();
		
		int stopX = this.getStopIndex().getX();
		int stopY = this.getStopIndex().getY();
		
		if(currX == stopX && currY == stopY) {
			this.state = "stopped";
		}
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
	
	public void updateIncrement() {
		// only update increment if car is not waiting at intersection or at a deadend
		if(this.state.equals("waiting") == false && this.state.equals("stopped") == false) {
			this.increment++;
			this.updateCarEntityPos();
		}
	}
	
	/**
	 * updates the position of the car objects entity (gui) representation based on dir
	 */
	public void updateCarEntityPos() {
		double carEntX = this.getCarEntity().getX();
		double carEntY = this.getCarEntity().getY();
		
		int addVal = 0;
		if(this.getState().equals("accel") || this.getState().equals("passing") ) {
			addVal = 1;
		} else if(this.state.equals("moving")) {
			addVal = 2;
		}
			
		if(this.state.equals("passing") == false) {
			switch(this.getDirection()) 
			{
			case ">":
				this.getCarEntity().setX(carEntX + addVal);
				break;
			case "^":
				this.getCarEntity().setY(carEntY - addVal);
				break;
			case "<":
				this.getCarEntity().setX(carEntX - addVal);
				break;
			case "v":
				this.getCarEntity().setY(carEntY + addVal);
				break;
				
			default:
				//default
				break;
			}
		
		}
	}
	
	/**
	 * updates car entity position after car performs a turn
	 */
	public void updateCarEntityTilePos(String oldDir, String newDir) {
		
		double carEntX = this.getCarEntity().getX();
		double carEntY = this.getCarEntity().getY();
		
		double shiftX = ShiftMapperX.getShiftMappingX().get(oldDir + newDir);
		double shiftY = ShiftMapperY.getShiftMappingY().get(oldDir + newDir);
	
		this.getCarEntity().setX(carEntX + shiftX);
		this.getCarEntity().setY(carEntY + shiftY);
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
	public void setStopIndex(Point stopIndex) {
		this.stopIndex = stopIndex;
	}
	
	/**
	 * 
	 * @return current position (Point) of car
	 */
	public Point getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(Point currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public Entity getCarEntity() {
		return carEntity;
	}

	public void setCarEntity(Entity carEntity) {
		this.carEntity = carEntity;
	}
	
	/**
	 * Overrides the default toString() method
	 * Returns a string showing the direction and current position of the car.
	 */
	public String toString(){ 
		return "direction is " + direction + ", current position is " + "<" + this.getCurrentIndex().getX() +"," + this.getCurrentIndex().getY() + ">"; 
	}
	
}

