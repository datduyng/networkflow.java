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
		
		System.out.println("tileWidth: " + tileWidth);
		System.out.println("tileHeight: " + tileHeight);
		
		//create simulation factory and add to game world
		SimulationFactory factory = new SimulationFactory();
		
		//set width and height of each constructed tile entity
		factory.setTileWidth(tileWidth);
		factory.setTileHeight(tileHeight);
		
		//add factory to game world
		getGameWorld().addEntityFactory(factory);

		//Iterate through JSON array of tiles and spawn tile entities on map
		int y = 0, x = 0;
		JSONArray tiles = JSONProcessor.getTiles("simulation-data/map08.json");
		Iterator<JSONArray> rowIterator = tiles.iterator();
		while(rowIterator.hasNext() && y < JSONProcessor.getHeight("simulation-data/map08.json")) {
			JSONArray row = rowIterator.next();
			Iterator<JSONObject> colIterator = row.iterator();
			x = 0;
			while(colIterator.hasNext() && x < JSONProcessor.getWidth("simulation-data/map08.json")) {
				int spawnX = x * tileWidth;
				int spawnY = y * tileHeight;
				
				JSONObject nextObj = colIterator.next();
				String generalType = nextObj.get("generalType").toString();
				String classType = nextObj.get("classType").toString();
				
				//System.out.println("spawnX: " + spawnX);
				//System.out.println("spawnY: " + spawnY);
				
				getGameWorld().spawn("ground", spawnX, spawnY);
				
				/*
				
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
					
					case "horizontal-road":
						getGameWorld().spawn("horizontal-road", spawnX, spawnY); 
						break;
						
					case "verticle-road":
						getGameWorld().spawn("verticle-road", spawnX, spawnY);
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
				
				*/
				
				x+=1;
			}
			y+=1;
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
