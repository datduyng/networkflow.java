package com.networkflow.app;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entities.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.TextEntityFactory;
import com.almasb.fxgl.texture.Texture;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class SimulationFactory implements TextEntityFactory {
	
	/*
	 * You can add a factory to the game world as follows: 
	 * getGameWorld().addEntityFactory(new MyBlockFactory());. 
	 * Then you can spawn an entity, using getGameWorld().spawn("block");, 
	 * which in turn calls your factory method annotated with @Spawns("block").
	 */
	
	  private int tileWidth = 0;
	  private int tileHeight = 0;
	
	  /*
	   @Spawns("ground")
	   public Entity newGround(SpawnData data) {
		   return Entities.builder()
				   .from(data)
				   .type(EntityType.GROUND)
				   .viewFromTexture("ground.jpg")
				   .build();
	    }
	   */
	  
	   @Spawns("construction-man")
	   public Entity newConstructionMan(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/construction-man.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.CONSTRUCTIONMAN)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("construction-barrier")
	   public Entity newConstructionBarrier(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/construction-barrier.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.CONSTRUCTIONBARRIER)
				   .viewFromNode(rec)
				   .build();
	    }
	  
	   @Spawns("grass")
	   public Entity newGrass(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/grass.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.GRASS)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("ground")
	   public Entity newGround(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/ground.jpg");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.GROUND)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("horizontal-road")
	   public Entity newHorizontalRoad(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/horizontal-road.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.HORIZRD)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("verticle-road")
	   public Entity newVerticleRoad(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/verticle-road.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.VERTRD)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("stop-sign")
	   public Entity newStopSign(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/stop-sign.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.STOPSIGN)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("traffic-light")
	   public Entity newTrafficLight(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/traffic-light.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.TRAFFICLIGHT)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("car")
	   public Entity newCar(SpawnData data) {
		   Rectangle rec = getNewRecWFill("assets/textures/car.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.CAR)
				   .viewFromNode(rec)
				   .build();
	    }
	     
	   //"assets/textures/grass.png"
	   public Rectangle getNewRecWFill(String imagePath) {
		   Image im = new Image(imagePath);
		   ImagePattern imP = new ImagePattern(im, 0, 0, 1, 1, true);
		   //Texture text = FXGL.getAssetLoader().loadTexture("grass.png");
		   Rectangle rec = new Rectangle(this.tileWidth, this.tileHeight);
		   rec.setFill(imP);
		   return rec;
	   }
	   
	   /*
	   
	   @Spawns("block")
	   public Entity newBlock(SpawnData data) {
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.GROUND)
				   .viewFromTexture("ground.jpg")
				   .build();
	    }
	   
	   @Spawns("grass")
	   public Entity newGrass(SpawnData data) {
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.GRASS)
				   .viewFromTexture("grass.png")
				   .build();
	    }
	    
	    */
		

	@Override
	public int blockHeight() {
		// TODO Auto-generated method stub
		return 50;
	}

	@Override
	public int blockWidth() {
		// TODO Auto-generated method stub
		return 50;
	}

	@Override
	public char emptyChar() {
		// TODO Auto-generated method stub
		return ' ';
	}
	
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}
	
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}


}
