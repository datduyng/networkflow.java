package component;
import java.util.ArrayList;
import java.lang.StringBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//class Point<X, Y> { 
//	  public final X x; 
//	  public final Y y; 
//	  public Point(X x, Y y) { 
//	    this.x = x; 
//	    this.y = y; 
//	  } 
//} 


public class SimulationMap {
	public ArrayList<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();
	private int height=30; 
	private int width=30;
	private int pixelSize=10;
	private ArrayList<Car> carList = new ArrayList<Car>();
	private ArrayList<Intersection> intersectionList = new ArrayList<Intersection>();
	
	public SimulationMap(int height, int width) {
		//init and then load map. 
	}
	
	/**
	 * This function load map components take input as filePath to 
	 * a JSON file. the format of json file will include
	 * the map format as csv file. delimit end of each row as a ';'. 
	 * seperate each tile as a ','.
	 * @param filePath
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public void loadComponents(String filePath) {
		JSONParser parser = new JSONParser();
	
		try {
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			//test
			System.out.println(jsonObject);
			
			//Process map tiles
			JSONArray tiles = (JSONArray) jsonObject.get("tiles");
			JSONArray components = (JSONArray) jsonObject.get("cars");
		}catch(FileNotFoundException e) {
			
		}catch(IOException e) {
			
		}catch(ParseException e) {
			
		}
	}
	
	public void _loadCars(JSONArray JSONcars) {
		
	}
	
	public void _loadTiles(JSONArray JSONcomponents) {
		
	}
	
	public String intersectionToString() {
		StringBuilder result = new StringBuilder();
		for(Intersection intersection: intersectionList) {
			result.append(intersection.toString());
		}
		return result.toString(); 
	}
	public String carListToString() {
		StringBuilder result = new StringBuilder();
		for(Car car: carList) {
			result.append(car.toString());
		}
		return result.toString();
	}
	public String mapToString() {
		StringBuilder result = new StringBuilder();
		for(ArrayList<Tile> row : this.map ){
			result.append(row.toString()); 
		}
		return result.toString();
	}
	
	
	//////////////////GETTER and SETTER/////////////////
	public int getHeight() {
		return height;
	}


	public int getWidth() {
		return width;
	}

	public void setSize(int height, int width) {
		this.width = width;
		this.height = height;
	}

	public int getPixelSize() {
		return pixelSize;
	}

	public void setPixelSize(int pixelSize) {
		this.pixelSize = pixelSize;
	}

	public ArrayList<Car> getCarList() {
		return carList;
	}

	public void setCarList(ArrayList<Car> carList) {
		this.carList = carList;
	}

	public ArrayList<Intersection> getIntersectionList() {
		return intersectionList;
	}

	public void setIntersectionList(ArrayList<Intersection> intersectionList) {
		this.intersectionList = intersectionList;
	}
	
}
