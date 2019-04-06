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


public class AppMain extends GameApplication {
	
	protected final int gameWidth = 960;
	protected final int gameHeight = 720;

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
			simulationMap = new SimulationMap("simulation-data/map09.json");
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

		
		initAssets();
		
		ArrayList<Car> carList = simulationMap.getCarList();
		ArrayList<Intersection> trafficCompList = simulationMap.getTrafficComponents();
		
		getMasterTimer().runAtInterval(() ->{
			
			//update cars
			for(int i = 0; i < carList.size(); i++) {
				carList.get(i).move(simulationMap.getLayout());
			}
			
			//update components 
			//TODO: 
			for(int i  = 0; i < trafficCompList.size(); i++) {
				((Intersection) SimulationMap.getTileAtIndex(trafficCompList.get(i).getMapIndex())).updateIncrement();
			}
			
			
		}, Duration.seconds(.1));//0.4 seconds
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
	 * Initialize cars from json file format
	 * @param filePath json path
	 * @param tileWidth tile pixel width
	 * @param tileHeight tile pixel height
	 */
	public void initCars(String filePath, int tileWidth, int tileHeight) {
		//Iterate through JSON array of tiles and spawn tile entities on map
		int y = 0, x = 0;
		JSONArray cars = JSONProcessor.getCars(filePath);
		//System.out.println("cars.size(): " + cars.size());
		Iterator<JSONObject> iter = cars.iterator();
		while(iter.hasNext()) {
			JSONObject nextObj = iter.next();
			
			int xIndex = Integer.parseInt(nextObj.get("xIndex").toString());
			int yIndex = Integer.parseInt(nextObj.get("yIndex").toString());
			
			int adjX =  Math.toIntExact(Math.round((tileWidth) / 4.00));
			int adjY =  Math.toIntExact(Math.round((tileHeight) / 4.00));
			
			//System.out.println("adjX: " + adjX);
			//System.out.println("adjY: " + adjY);
				
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

	 }
	

	@Override
	public void onUpdate(double tpf) {

	}
		
	
	@Override
	public void initPhysics() {
		
	}
	
	@Override 
	public void initUI() {
		getGameScene().setUIMouseTransparent(false);
		getGameScene().addUINodes(
				new AgentInfoView("test"),
				new UserView(100, 100)
		);
	}

	public void initLayout(Tile[][] layout) {
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
