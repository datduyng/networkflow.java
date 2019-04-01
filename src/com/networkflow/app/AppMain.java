package com.networkflow.app;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.physics.box2d.collision.shapes.Shape;

import com.networkflow.app.ui.AgentInfoView;
import com.networkflow.app.ui.UserView;
import com.networkflow.component.Car;
import com.networkflow.component.Intersection;
import com.networkflow.component.JSONProcessor;
import com.networkflow.component.Point;
import com.networkflow.component.SimulationMap;
import com.networkflow.component.StopSign;
import com.networkflow.component.Tile;

import javafx.util.Duration;


public class AppMain extends GameApplication {
	
	protected final int gameWidth = 800;
	protected final int gameHeight = 600;

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setTitle("FXGL");
		settings.setWidth(gameWidth);
		settings.setHeight(gameHeight);
		settings.setVersion("0.1");
	}
	
	private Entity player;
	private SimulationMap simulationMap;
	
	public void initAssets() {
		simulationMap = new SimulationMap("simulation-data/map12.json");
		
		//create simulation factory and add to game world
		SimulationFactory factory = new SimulationFactory();
		
		//set width and height of each constructed tile entity
		factory.setTileWidthHeight(simulationMap.getPixelSize(), simulationMap.getPixelSize());
		
		//add factory to game world
		FXGL.getGameWorld().addEntityFactory((EntityFactory) factory);
		
		initTiles();
		initCars();
	}
	
	@Override
	public void initGame() {

		
		initAssets();
		
		ArrayList<Car> carList = simulationMap.getCarList();
		ArrayList<Intersection> trafficCompList = simulationMap.getTrafficCompList();
		
		FXGL.getMasterTimer().runAtInterval(() ->{
			
			//update cars
			for(int i = 0; i < carList.size(); i++) {
				carList.get(i).move(simulationMap.getLayout());
				System.out.println("Current X Pos: " + carList.get(i).getCurrentIndex().getX());
				System.out.println("Current Y Pos: " + carList.get(i).getCurrentIndex().getY());
				System.out.println("Current State: " + carList.get(i).getState());
			}
			
			System.out.println();
			//update components 
			//TODO: 
			for(int i  = 0; i < trafficCompList.size(); i++) {
				((Intersection) SimulationMap.getTileAtIndex(trafficCompList.get(i).getMapIndex())).deQueue();
			}
			System.out.println();
			
			stop();
			
		}, Duration.seconds(.4));//0.4 seconds

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
				FXGL.getGameWorld().spawn(classType, spawnX, spawnY);
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
			int spawnX = (xIndex * simulationMap.getPixelSize());
			int spawnY = (yIndex * simulationMap.getPixelSize());
			FXGL.getGameWorld().spawn("car-east", spawnX, spawnY);
			
		}
	}
	
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
					FXGL.getGameWorld().spawn("car-east", spawnX, spawnY);
					break;
			
				case "^":
					//spawn north car
					spawnX = (xIndex * tileWidth) + adjX;
					spawnY = (yIndex * tileHeight) + adjY;
					FXGL.getGameWorld().spawn("car-north", spawnX, spawnY);
					break;
				
				case "<":
					//spawn west car
					spawnX = (xIndex * tileWidth) + adjX;
					spawnY = (yIndex * tileHeight) - adjY;
					FXGL.getGameWorld().spawn("car-west", spawnX, spawnY);
					break;
				
				case "v":
					//spawn south car
					spawnX = (xIndex * tileWidth) - adjX;
					spawnY = (yIndex * tileHeight) + adjY;
					FXGL.getGameWorld().spawn("car-south", spawnX, spawnY);
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
	public void onUpdate(double tpf) {
		System.out.println("tpf "+tpf);
		
	}

	
	@Override
	public void initPhysics() {
		
	}
	
	@Override 
	public void initUI() {
		FXGL.getGameScene().setUIMouseTransparent(false);
		FXGL.getGameScene().addUINodes(
//				new AgentInfoView("test"),
//				new UserView(100, 100)
		);
	}
	
	public int getTileWidth(Tile[][] layout) {
		return (int) Math.floor(gameWidth / layout[0].length);
	}
	
	public int getTileHeight(Tile[][] layout) {
		return gameHeight / layout.length;
	}
	
	
	public void initLayout(Tile[][] layout) {
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
