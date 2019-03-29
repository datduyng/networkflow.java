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
import com.networkflow.component.JSONProcessor;
import com.networkflow.component.Point;
import com.networkflow.component.Tile;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;


public class AppMain extends GameApplication {
	
	protected final int gameWidth = 600;
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

	
	@Override
	public void initGame() {
	
		int tileWidth = Math.floorDiv(gameWidth, JSONProcessor.getWidth("simulation-data/map08.json"));
		int tileHeight = Math.floorDiv(gameHeight, JSONProcessor.getHeight("simulation-data/map08.json"));
		
		//System.out.println("tileWidth: " + tileWidth);
		//System.out.println("tileHeight: " + tileHeight);
		
		//create simulation factory and add to game world
		SimulationFactory factory = new SimulationFactory();
		
		//set width and height of each constructed tile entity
		factory.setTileWidth(tileWidth);
		factory.setTileHeight(tileHeight);
		
		//add factory to game world
		getGameWorld().addEntityFactory(factory);
		
		initTiles("simulation-data/map09.json", tileWidth, tileHeight);
		initCars("simulation-data/map09.json", tileWidth, tileHeight);

	}
	
	public void initTiles(String filePath, int tileWidth, int tileHeight) {
		//Iterate through JSON array of tiles and spawn tile entities on map
		int y = 0, x = 0;
		JSONArray tiles = JSONProcessor.getTiles(filePath);
		Iterator<JSONArray> rowIterator = tiles.iterator();
		while(rowIterator.hasNext() && y < JSONProcessor.getHeight(filePath)) {
			JSONArray row = rowIterator.next();
			Iterator<JSONObject> colIterator = row.iterator();
			x = 0;
			while(colIterator.hasNext() && x < JSONProcessor.getWidth(filePath)) {
				int spawnX = x * tileWidth;
				int spawnY = y * tileHeight;
				
				JSONObject nextObj = colIterator.next();
				String generalType = nextObj.get("generalType").toString();
				String classType = nextObj.get("classType").toString();
				
				//System.out.println("spawnX: " + spawnX);
				//System.out.println("spawnY: " + spawnY);
		
				//spawn correct type of tile sized entity (w/ pic)
				switch(classType) 
				{
					case "construction-man":
						getGameWorld().spawn("construction-man", spawnX, spawnY);
						break;
						
					case "construction-barrier":
						getGameWorld().spawn("construction-barrier", spawnX, spawnY);
						break;
						
					case "grass":
						getGameWorld().spawn("grass", spawnX, spawnY);
						break;
					
					case "ground":
						getGameWorld().spawn("ground", spawnX, spawnY);
						break;
					
					case "road-horizontal":
						getGameWorld().spawn("road-horizontal", spawnX, spawnY); 
						break;
						
					case "road-verticle":
						getGameWorld().spawn("road-verticle", spawnX, spawnY);
						break;
						
					case "stop-sign":
						getGameWorld().spawn("stop-sign", spawnX, spawnY);
						break;
						
					case "traffic-light":
						getGameWorld().spawn("traffic-light", spawnX, spawnY);
						break;
					
					default:
						//default
						break;
						
				}				
				x+=1;
			}
			y+=1;
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
