package com.networkflow.app;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.parser.tiled.TiledMap;
import com.almasb.fxgl.physics.box2d.collision.shapes.Shape;
import com.almasb.fxgl.settings.GameSettings;

import com.networkflow.app.ui.AgentInfoView;
import com.networkflow.app.ui.UserView;
import com.networkflow.apputils.AppException;
import com.networkflow.component.Car;
import com.networkflow.component.Intersection;
import com.networkflow.component.JSONProcessor;
import com.networkflow.component.Point;
import com.networkflow.component.SimulationMap;
import com.networkflow.component.StopSign;
import com.networkflow.component.Tile;
import com.networkflow.component.TrafficLight;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
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
		settings.setTitle("FXGL");
		settings.setWidth(gameWidth);
		settings.setHeight(gameHeight);
		settings.setVersion("0.1");
	}

	private SimulationMap simulationMap;
	
	public void initAssets() {
		try {
			simulationMap = new SimulationMap("simulation-data/multTurns_test.json");
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
	}
	
	
	
	@Override
	public void initGame() {
//		this.simulationTimer.
		initAssets();
		
		carList = simulationMap.getCarList();
		trafficCompList = simulationMap.getTrafficComponents();
		
		this.setTimeUnit(0.03);//default values
	}

	public SimulationMap getSimulationMap() {
		return simulationMap;
	}

	public void setSimulationMap(SimulationMap simulationMap) {
		this.simulationMap = simulationMap;
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
	
	public void initCars(String filePath, int tileWidth, int tileHeight) {
		//Iterate through JSON array of tiles and spawn tile entities on map
		int y = 0, x = 0;
		JSONArray cars = JSONProcessor.getCars(filePath);
		Iterator<JSONObject> iter = cars.iterator();
		while(iter.hasNext()) {
			JSONObject nextObj = iter.next();
			
			int xIndex = Integer.parseInt(nextObj.get("xIndex").toString());
			int yIndex = Integer.parseInt(nextObj.get("yIndex").toString());
			
			int adjX =  Math.toIntExact(Math.round((tileWidth) / 4.00));
			int adjY =  Math.toIntExact(Math.round((tileHeight) / 4.00));
			
			String direction = nextObj.get("direction").toString();
			int spawnX;
			int spawnY;
			
			switch (direction)
			{
				case ">":
					//spawn east car
					spawnX = (xIndex * tileWidth) + adjX;
					spawnY = (yIndex * tileHeight) + adjY;
					getGameWorld().spawn("car-east", spawnX, spawnY);
					break;
			
				case "^":
					//spawn north car
					spawnX = (xIndex * tileWidth) + adjX;
					spawnY = (yIndex * tileHeight) + adjY;
					getGameWorld().spawn("car-north", spawnX, spawnY);
					break;
				
				case "<":
					//spawn west car
					spawnX = (xIndex * tileWidth) + adjX;
					spawnY = (yIndex * tileHeight) - adjY;
					getGameWorld().spawn("car-west", spawnX, spawnY);
					break;
				
				case "v":
					//spawn south car
					spawnX = (xIndex * tileWidth) - adjX;
					spawnY = (yIndex * tileHeight) + adjY;
					getGameWorld().spawn("car-south", spawnX, spawnY);
					break;
				
				default:
					//default
					break;
				
			}
			
		}					
	}	
	
	@Override
	public void initInput() {
		
	}
	
	
	
	 @Override
	 protected void initGameVars(Map<String, Object> vars) {
		 /*
		 for(int i = 1; i <= simulationMap.getCarList().size(); i++) {
			 
			 String carnX = "car" + i + "x";
			 String carnY = "car" + i + "y";
			 
			 int initPixValX = simulationMap.getPixelSize() * simulationMap.getCarList().get(i-1).getCurrentIndex().getX();
			 int initPixValY = simulationMap.getPixelSize() * simulationMap.getCarList().get(i-1).getCurrentIndex().getY();

			 vars.put(carnX, initPixValX);
			 vars.put(carnY, initPixValY);

			 
			 //carPosX.textProperty().bind(simulationMap.getCarList().get(i).getCurrentIndex().getX());
		 }
		 */
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
