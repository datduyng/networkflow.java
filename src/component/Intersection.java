package component;

public class Intersection extends Tile {
	protected String type;
	protected int increment;
	public String builtDirections;
	
	public Intersection() {
		super();
		this.type = "Intersection";
		this.increment = 0;
	}
	
	public Intersection(int increment) {
		super();
		this.type = "Intersection";
		this.increment = increment;
	}

	public String getType() {
		return this.type;
	}
	
	public void updateIncrement() {
		this.increment++;
	}

	public static Intersection initIntersectionType(String generalType, String classType, Point mapIndex, String builtDirections) {
		if(generalType.equalsIgnoreCase("stop-sign")) {
			return new StopSign(classType, mapIndex, builtDirections);
		} else if(generalType.equalsIgnoreCase("traffic-light")) {
			return new TrafficLight(classType, mapIndex, builtDirections);
		}
		
		System.out.println("Invalid Intesection type");
		return null;
	}
	public String toString() {
		return this.type + "@"  + this.mapIndex.toString() + this.builtDirections;
	}
	
	
}
