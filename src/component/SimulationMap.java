package component;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.StringBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.*;
import org.json.simple.JSONObject;

import component.Point;



/**
 * Simulation class provide tiles 2d backend representation
 * of the simlation
 * @author datng
 *
 */
public class SimulationMap {
	public static Tile[][] layout;
	private int height=30; 
	private int width=30;
	private int numHeight; 
	private int numWidth;
	private int pixelSize=10;
	private ArrayList<Car> carList = new ArrayList<Car>();
	private ArrayList<Intersection> trafficComponents = new ArrayList<Intersection>();
	
	/**
	 * 
	 * @param height
	 * @param width
	 */
	public SimulationMap(int height, int width) {
		//init and then load map. 
	}
	
	
	/**
	 * This function load map components take input as filePath to 
	 * a JSON file. the format of json file will include
	 * the map format as csv file. delimit end of each row as a ';'. 
	 * seperate each tile as a ','.
	 * Tiles map can be creates on project website. netflot.github.io
	 * 
	 * @see https://netflow.github.io
	 * @see (last update:3/24/19) https://datduyng.github.io/cityboost/createMap.html
	 * @see https://www.mkyong.com/java/json-simple-example-read-and-write-json/
	 * @param filePath
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public void loadComponents(String filePath) {
		JSONParser parser = new JSONParser();
		System.out.println("File exist check" + new File(filePath).exists());
		try {
		    FileReader file= new FileReader(filePath);
			Object obj = parser.parse(file);
			JSONObject jsonObject = (JSONObject) obj;
			
			//Process map tiles
			JSONArray tiles = (JSONArray) jsonObject.get("tiles");
			JSONArray cars = (JSONArray) jsonObject.get("cars");
			JSONArray trafficComponents = (JSONArray) jsonObject.get("trafficComponents");
			
			System.out.println(jsonObject);
			this.numHeight = Integer.parseInt(jsonObject.get("numHeight").toString());
			this.numWidth = Integer.parseInt(jsonObject.get("numWidth").toString());
			
			//load tiles and cars given JSON objects
			this._loadTiles(tiles);
			this._loadCars(cars);
			this._loadTrafficComponents(trafficComponents);
			
		}catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}catch(IOException e) {
			System.out.println("IOException");
		}catch(ParseException e) {
			System.out.println("ParseException");
		}
	}
	
	
	/**
	 * Load traffic components List given JSONArray parsed from 
	 * JSON inputs.
	 * @param JSONTraffic
	 */
	public void _loadTrafficComponents(JSONArray JSONTraffic) {
		int i = 0;
		Iterator<JSONObject> iterator = JSONTraffic.iterator();
		while(iterator.hasNext()) {
			JSONObject componentObj = iterator.next();
			String generalType = componentObj.get("generalType").toString();
			String classType = componentObj.get("classType").toString();
			String builtDirections = componentObj.get("builtDirections").toString();
			int x = Integer.parseInt(componentObj.get("xIndex").toString()),
					y = Integer.parseInt(componentObj.get("yIndex").toString());
			Point mapIndex = new Point(x, y);
			Intersection intersection = Intersection.initIntersectionType(generalType, classType, mapIndex, builtDirections);
			trafficComponents.add(intersection);
		}
	}
	
	/**
	 * Load car list given JSONArray parsed from JSON inputs.
	 * @see (last update:3/24/19) https://netflow.github.io/createmap.html
	 * @param JSONCars
	 */
	public void _loadCars(JSONArray JSONCars) {
		int i = 0; 
		Iterator<JSONObject> iterator = JSONCars.iterator();
		while(iterator.hasNext()) {
			JSONObject carObj = iterator.next();	
			int x = Integer.parseInt(carObj.get("xIndex").toString()),
				y = Integer.parseInt(carObj.get("yIndex").toString());
			String direction = carObj.get("direction").toString();
			//TODO: init car object and add to list
			Car carComp = new Car();
			Point currentPosition = new Point(x,y);
			carComp.setCurrentPosition(currentPosition);
			carComp.setDirection(direction);
			carComp.setState("stopped");
			carComp.setCurrentSpeed(0);
			carList.add(carComp);
		}
	}
	
	/**
	 * Load tiles class given JSON array of tiles parsed from input JSON
	 * @see (last update:3/24/19) https://netflow.github.io/createmap.html 
	 * @param JSONTiles
	 */
	public void _loadTiles(JSONArray JSONTiles) {
		//init map layout size
		this.layout = new Tile[this.numHeight][this.numWidth];
		
		// create and build map 
		int y = 0, x = 0;
		Iterator<JSONArray> rowIterator = JSONTiles.iterator();
		while(rowIterator.hasNext() && y < this.numHeight) {
			JSONArray row = rowIterator.next();
			Iterator<JSONObject> colIterator = row.iterator();
			x = 0;
			while(colIterator.hasNext() && x < this.numWidth) {
				JSONObject nextObj = colIterator.next();
				String generalType = nextObj.get("generalType").toString();
				String classType = nextObj.get("classType").toString();
				this.layout[y][x] = Tile.initTileType(generalType, classType, new Point(x, y));
				x+=1;
			}
			y+=1;
		}
	}
	
	/**
	 * build and format string of components list
	 * @return String
	 */
	public String componentsToString() {
		StringBuilder result = new StringBuilder();
		for(Intersection intersection: trafficComponents) {
			result.append("Component: " + intersection.toString() + "\n");
		}
		return result.toString(); 
	}
	
	
	/**
	 * build and format string of car list
	 * @return String
	 */
	public String carListToString() {
		StringBuilder result = new StringBuilder();
		for(Car car: carList) {
			result.append("Car: "+ car.toString() + '\n');
			
		}
		return result.toString();
	}
	
	/**
	 * build and format string of tiles 2d array
	 * @return String
	 */
	public String mapToString() {
		StringBuilder result = new StringBuilder();
		
		for(int y=0;y<this.getNumHeight();y++) {
			for(int x=0;x<this.getNumWidth();x++) {
				result.append(this.layout[y][x].toString() + ",");
			}
			System.out.println();
			result.append("\n");
		}
		return result.toString();
	}
	
	
	//////////////////GETTER and SETTER/////////////////
	/**
	 * get height of 2d tiles map
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * get width of 2d tiles map
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * set size of tiles maps
	 * @param height
	 * @param width
	 */
	public void setSize(int height, int width) {
		this.width = width;
		this.height = height;
	}

	/**
	 * get size of pixel in simulation map
	 * @return
	 */
	public int getPixelSize() {
		return pixelSize;
	}

	/**
	 * set size of pixel in simulation map
	 * @param pixelSize
	 */
	public void setPixelSize(int pixelSize) {
		this.pixelSize = pixelSize;
	}

	/**
	 * get car list
	 * @return
	 */
	public ArrayList<Car> getCarList() {
		return carList;
	}

	/**
	 * set carList(ArrayList)
	 * @param carList
	 */
	public void setCarList(ArrayList<Car> carList) {
		this.carList = carList;
	}

	/**
	 * Set 2d tiles layouts(map)
	 * @return
	 */
	public Tile[][] getLayout() {
		return layout;
	}

	public void setLayout(Tile[][] layout) {
		this.layout = layout;
	}

	public int getNumHeight() {
		return numHeight;
	}

	public void setNumHeight(int numHeight) {
		this.numHeight = numHeight;
	}

	public int getNumWidth() {
		return numWidth;
	}

	public void setNumWidth(int numWidth) {
		this.numWidth = numWidth;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
