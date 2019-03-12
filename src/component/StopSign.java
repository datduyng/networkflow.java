package component;

import java.util.PriorityQueue;

public class StopSign extends Intersection {
	
	private PriorityQueue<Car> carEnter;
	private String state;
	
	public StopSign() {
		super();
		this.carEnter = new PriorityQueue<Car>();
		this.state = "empty";
		this.increment = 0;
		this.type = "stop sign";
	}
	
	public StopSign(String state) {
		super();
		this.carEnter = new PriorityQueue<Car>();
		this.state = state;
		this.increment = 0;
		this.type = "stop sign";
		
	}
	
	public StopSign(PriorityQueue<Car> carEnter, String state, int increment) {
		super();
		this.carEnter = carEnter;
		this.state = state;
		this.increment = increment;
		this.type = "stop sign";
	}

	/**
	 * 
	 * @return the Queue of Cars at this stop sign
	 */
	public PriorityQueue<Car> getCarEnter() {
		return carEnter;
	}
	
	/**
	 * Add car to Priority Queue 
	 * @param car to be added
	 * @return boolean true if car is successfully added
	 */
	public boolean addCarToQueue(Car car) {
		boolean carAdded = this.carEnter.add(car);
		if(this.carEnter.isEmpty() == false) {
			this.setState("passing");
		}
		return carAdded;
	}
	
	/**
	 * Get Car that is at the head of the priority queue 
	 * @return Car
	 */
	public Car deQueue() {
		Car car = this.carEnter.peek();
		if(car != null) {
			this.carEnter.remove();
		}
		
		if(this.carEnter.isEmpty()) {
			this.setState("empty");
		}
		return car;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		if(this.state.compareToIgnoreCase("empty") == 0) {
			this.state =  "empty";
		} else if(this.state.compareToIgnoreCase("passing") == 0)  {
			this.state = "passing";
		} else   {
			this.state = null;
		}
	}
}
