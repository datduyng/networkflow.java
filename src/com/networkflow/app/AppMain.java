package com.networkflow.app;


import java.util.Iterator;

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
import com.networkflow.component.Car;
import com.networkflow.component.JSONProcessor;
import com.networkflow.component.Point;
import com.networkflow.component.SimulationMap;
import com.networkflow.component.Tile;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;


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
	
	@Override
	public void initGame() {
		simulationMap = new SimulationMap("src/assets/json/map10-dat.json");
		
		//create simulation factory and add to game world
		SimulationFactory factory = new SimulationFactory();
		
		//set width and height of each constructed tile entity
		factory.setTileWidthHeight(simulationMap.getPixelSize(), simulationMap.getPixelSize());
		
		//add factory to game world
		getGameWorld().addEntityFactory(factory);
		
		initTiles();
		initCars();
//		initTiles("simulation-data/map09.json", simulationMap.getPixelSize(), simulationMap.getPixelSize());
//		initCars("simulation-data/map09.json", simulationMap.getPixelSize(), simulationMap.getPixelSize());

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
				getGameWorld().spawn(classType, spawnX, spawnY);
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
			getGameWorld().spawn("car-east", spawnX, spawnY);
			
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
			
			//System.out.println("spawnX: " + spawnX);
			//System.out.println("spawnY: " + spawnY);
			
		}					
	}	
	
	@Override
	public void initInput() {
		
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
