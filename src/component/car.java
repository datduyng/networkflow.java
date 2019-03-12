package component;

public class car {
	private String id;
	private String direction;
	private int initialSpeed;
	private int speed;
	private int increment;
	private String state;
	Point start;
	Point stop;
	Point currentPosition;

	//constructors
	public car(String id,String direction, int initialSpeed, int speed, int increment, String state, Point start, Point stop,
			Point currentPosition) {
		super();
		this.id = id;
		this.direction = direction;
		this.initialSpeed = initialSpeed;
		this.speed = speed;
		this.increment = increment;
		this.state = state;
		this.start = start;
		this.stop = stop;
		this.currentPosition = currentPosition;
	}

	//get component
	public car(){}

	//toString
	public String toString(){ 
		return "direction is " + direction + ", current position is " + currentPosition; 
	} 

	//move 
	public void move(){
		this.state = "move";
		int possibleDirection = 4;
		String direction = getDirection();
		int currentX = 0;
		int currentY = 0;
		
		switch (possibleDirection) {
		case 1:
			//go left 
			direction = ">";
			updateIncrement();
			currentX = (getStart().getX()) + (getSpeed() * getIncrement());
			currentPosition.setX(currentX);
		case 2:
			//go right
			direction = "<";
			updateIncrement();
			currentX = (getStart().getX()) - (getSpeed() * getIncrement());
			currentPosition.setX(currentX);
		case 3:
			//go up
			direction = "^";
			updateIncrement();
			currentY = (getStart().getY()) + (getSpeed() * getIncrement());
			currentPosition.setY(currentY);
		case 4:
			//go down
			direction = "v";
			updateIncrement();
			currentY = (getStart().getY()) - (getSpeed() * getIncrement());
			currentPosition.setY(currentY);		
		}
	}
	
	//update increment 
	public void updateIncrement(){
		setIncrement(0);
		this.increment++;
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
	public int getInitialSpeed() {
		return initialSpeed;
	}
	public void setInitialSpeed(int initialSpeed) {
		this.initialSpeed = initialSpeed;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
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

