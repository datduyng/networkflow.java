package com.networkflow.app;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.settings.GameSettings;

import com.networkflow.app.ui.AgentInfoView;
import com.networkflow.app.ui.UserView;
import com.networkflow.apputils.AppException;
import com.networkflow.component.Car;
import com.networkflow.component.Boat;
import com.networkflow.component.Intersection;
import com.networkflow.component.JSONProcessor;
import com.networkflow.component.SimulationMap;
import com.networkflow.component.Tile;
import com.networkflow.component.TrafficLight;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class AppMain extends GameApplication {
	
	protected final int gameWidth = 960;
	protected final int gameHeight = 720;
	ArrayList<Intersection> trafficCompList;
	ArrayList<Car> carList;
	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setTitle("Traffic Simulator");
		settings.setWidth(gameWidth);
		settings.setHeight(gameHeight);
		settings.setVersion("1.0");
	}

	private static SimulationMap simulationMap;
	private static String mapPath = "src/resources/tiledmaps/map_full_test.json"; 
	
	public void initAssets() {
		try {
			simulationMap = new SimulationMap(mapPath);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create simulation factory and add to game world
		SimulationFactory factory = new SimulationFactory();
		
		//set width and height of each constructed tile entity
		factory.setTileWidthHeight(simulationMap.getPixelSize(), simulationMap.getPixelSize());
		
		//add factory to game world
		getGameWorld().addEntityFactory((EntityFactory) factory);
		
		initTiles();
		initCars();
		initBoats();
	}
	
	
	
	@Override
	public void initGame() {
//		this.simulationTimer.
		initAssets();
		
		carList = simulationMap.getCarList();
		trafficCompList = simulationMap.getTrafficComponents();
		
		this.setTimeUnit(0.05);//default values
	}

	public static SimulationMap getSimulationMap() {
		return simulationMap;
	}

	public static void setSimulationMap(SimulationMap newMap) {
		simulationMap = newMap;
	}
	
	public static String getMapPath() {
		return mapPath;
	}

	public static void setMapPath(String newMapPath) {
		AppMain.mapPath = newMapPath;
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	/**
	 * Init Tiles visualization no params
	 * @return
	 */
	public void initTiles() {
		for(int y=0;y<simulationMap.getLayout().length;y++) {
			for(int x=0;x<simulationMap.getLayout()[0].length;x++) {
				int spawnX = x * simulationMap.getPixelSize();
				int spawnY = y * simulationMap.getPixelSize();
				String classType = simulationMap.getLayout()[y][x].getClassType();
				Entity tileEntity = getGameWorld().spawn(classType, spawnX, spawnY);
				//set traffic-light entity for visually updating traffic directions
				if(classType.equals("traffic-light")) {
					((TrafficLight) simulationMap.getLayout()[y][x]).setTrafficLightEntity(tileEntity);
				}

			}
		}
	}
	
	/**
	 * Init car visualization no params
	 */
	public void initCars() {
		for(Car car : this.simulationMap.getCarList()) {
			int xIndex = car.getCurrentIndex().getX();
			int yIndex = car.getCurrentIndex().getY();
			double spawnX = (xIndex * simulationMap.getPixelSize());
			double spawnY = (yIndex * simulationMap.getPixelSize());
			
			//Entity newCar = getNewCarWFill("assets/textures/car-east.png", direction, spawnX, spawnY);
			Entity newCar = null;
			int pixSize = simulationMap.getPixelSize();	
			String direction = car.getDirection();
			
			switch (direction)
			{
				
				case ">":
					//spawn east car
					spawnX = ((xIndex - 0.5) * pixSize);
					spawnY = (yIndex * pixSize) + (pixSize/2);
					newCar = getGameWorld().spawn("car-east", spawnX, spawnY);
					break;
			
				case "^":
					//spawn north car
					spawnX = (xIndex * pixSize) + (pixSize/2);
					spawnY = ((yIndex + 1) * pixSize);
					newCar = getGameWorld().spawn("car-north", spawnX, spawnY);
					break;
				
				case "<":
					//spawn west car
					spawnX = ((xIndex + 1) * pixSize);
					spawnY = (yIndex * pixSize); 
					newCar = getGameWorld().spawn("car-west", spawnX, spawnY);
					break;
				
				case "v":
					//spawn south car
					spawnX = (xIndex * pixSize);
					spawnY = ((yIndex - 0.5) * pixSize);
					newCar = getGameWorld().spawn("car-south", spawnX, spawnY);
					break;
				
				default:
					//default
					break;
				
			}
			
			car.setCarEntity(newCar);
			
		}
	}
	
	/**
	 * Init boat visualization no params
	 */
	public void initBoats() {
		for(Boat boat : this.simulationMap.getBoatList()) {
			
			int xIndex = boat.getCurrentIndex().getX();
			int yIndex = boat.getCurrentIndex().getY();
			double spawnX = (xIndex * simulationMap.getPixelSize());
			double spawnY = (yIndex * simulationMap.getPixelSize());
			
			//Entity newCar = getNewCarWFill("assets/textures/car-east.png", direction, spawnX, spawnY);
			Entity newBoat = null;
			int pixSize = simulationMap.getPixelSize();	
			newBoat = getGameWorld().spawn("boat", spawnX, spawnY);
			
		}
		
	}
	
	@Override
	public void initInput() {
		
	}
	
	 @Override
	 protected void initGameVars(Map<String, Object> vars) {
		 
	 }
	 
	private static BooleanProperty simulationRunning = new SimpleBooleanProperty(false);
	public static DoubleProperty TIME_UNIT = new SimpleDoubleProperty();
	private double tick = 0.0;//keep track of total tpf per ticks
	
	
	public static void setSimulationRunning(boolean running) {
		AppMain.simulationRunning.set(running);
	}
	
	public static boolean getSimulationRunnning() {
		return AppMain.simulationRunning.get();
	}
	
	public static void setTimeUnit(double timeUnit) {
		AppMain.TIME_UNIT.set(timeUnit);
	}
	
	public static double getTimeUnit() {
		return AppMain.TIME_UNIT.get();
	}

	
	
	private void updateSimulation() {
		//update cars
		for(int i = 0; i < carList.size(); i++) {
			carList.get(i).move(simulationMap.getLayout());
		}
		
		//update components 
		//TODO: 
		for(int i  = 0; i < trafficCompList.size(); i++) {
			((Intersection) SimulationMap.getTileAtIndex(trafficCompList.get(i).getMapIndex())).updateIncrement();
		}
		
//		System.out.println("Running timer");
	}
	
	@Override
	public void onUpdate(double tpf) {
		
		if(this.getSimulationRunnning()) {
			this.tick += tpf;
			if(tick > AppMain.getTimeUnit()) {
				this.tick = 0.0;
				this.updateSimulation();
			}
		}
	}
		
	
	@Override
	public void initPhysics() {
		
	}
	
	@Override 
	public void initUI() {
		getGameScene().setUIMouseTransparent(false);
		getGameScene().addUINodes(
				new AgentInfoView("test"),
				new UserView(this.gameWidth, this.gameHeight)
		);
	}

	public void initLayout(Tile[][] layout) {
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
