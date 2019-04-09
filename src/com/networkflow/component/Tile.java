package com.networkflow.component;

import com.almasb.fxgl.entity.Entity;

public abstract class Tile{
	
	private Entity mapEntity;
	protected int numCarPass;
	
	Point mapIndex;
	Point position;
	String generalType;
	String classType;
	


	
	public Tile(String generalType) {
		this.generalType = generalType;
	}
	
	public Tile(Point mapIndex, Point position) {
		this.mapIndex = mapIndex;
		this.position = position;
	}
	
	public Tile(Entity mapEntity, int numCarPass, Point mapIndex, Point position, String type) {
		super();
		this.mapEntity = mapEntity;
		this.numCarPass = numCarPass;
		this.mapIndex = mapIndex;
		this.position = position;
		this.generalType = generalType;
	}
	/**
	 * This function take a String indicate tile type
	 * then return corresponding object that implement
	 * Tile
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public static Tile initTileType(String classType, Point mapIndex) {
		switch(classType) {
		case "construction-man":
			return new Ground(classType);
			
		case "construction-barrier":
			return new Ground(classType);
			
		case "grass":
			return new Ground(classType);
		
		case "ground":
			return new Ground(classType);
			
		case "water":
			return new Ground(classType);
		
		case "road-horizontal":
			return new Road(classType);
			
		case "road-verticle":
			return new Road(classType);
			
		case "stop-sign":
			return new StopSign(mapIndex);
			
		case "traffic-light":
			return new TrafficLight(mapIndex);
		
		default:
			//default
			System.out.println("Tile Type does not Exist!!!!!!!!!!");
			return null;
			
		}	
	}
	
	
	/************getter and setters********/
	public Entity getMapEntity() {
		return mapEntity;
	}

	public void setMapEntity(Entity mapEntity) {
		this.mapEntity = mapEntity;
	}

	public Point getMapIndex() {
		return mapIndex;
	}

	public void setMapIndex(Point mapIndex) {
		this.mapIndex = mapIndex;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public String getGeneralType() {
		return generalType;
	}

	public void setGeneralType(String generalType) {
		this.generalType = generalType;
	}

	public void setNumCarPass(int numCarPass) {
		this.numCarPass = numCarPass;
	}

	/**
	 * Gets attribute of the specific tile
	 * @return the number of cars that have passed through this tile
	 */
	public int getNumCarPass() {
		return this.numCarPass;
	}
	
	public String getClassType() {
		return classType;
	}
	
}
