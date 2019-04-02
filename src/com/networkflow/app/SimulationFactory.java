package com.networkflow.app;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entities.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.TextEntityFactory;
import com.almasb.fxgl.texture.Texture;
import com.networkflow.component.SimulationMap;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class SimulationFactory implements TextEntityFactory {
	
	/*
	 * You can add a factory to the game world as follows: 
	 * getGameWorld().addEntityFactory(new MyBlockFactory());. 
	 * Then you can spawn an entity, using getGameWorld().spawn("block");, 
	 * which in turn calls your factory method annotated with @Spawns("block").
	 */
	
	  private int tileWidth = 0;
	  private int tileHeight = 0;
	  
	  private static int carCount = 0;
	  
	   @Spawns("construction-man")
	   public Entity newConstructionMan(SpawnData data) {
		   
		   
		   Rectangle rec = getNewRecFill("assets/textures/construction-man.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.CONSTRUCTIONMAN)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("construction-barrier")
	   public Entity newConstructionBarrier(SpawnData data) {
		   Rectangle rec = getNewRecFill("assets/textures/construction-barrier.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.CONSTRUCTIONBARRIER)
				   .viewFromNode(rec)
				   .build();
	    }
	  
	   @Spawns("grass")
	   public Entity newGrass(SpawnData data) {
		   Rectangle rec = getNewRecFill("assets/textures/grass.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.GRASS)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("ground")
	   public Entity newGround(SpawnData data) {
		   Rectangle rec = getNewRecFill("assets/textures/ground.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.GROUND)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("road-horizontal")
	   public Entity newHorizontalRoad(SpawnData data) {
		   Rectangle rec = getNewRecFill("assets/textures/road-horizontal.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.HORIZRD)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("road-verticle")
	   public Entity newVerticleRoad(SpawnData data) {
		   Rectangle rec = getNewRecFill("assets/textures/road-verticle.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.VERTRD)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("stop-sign")
	   public Entity newStopSign(SpawnData data) {
		   Rectangle rec = getNewRecFill("assets/textures/stop-sign.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.STOPSIGN)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("traffic-light")
	   public Entity newTrafficLight(SpawnData data) {
		   Rectangle rec = getNewRecFill("assets/textures/traffic-light-we.png");
		   return Entities.builder()
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.TRAFFICLIGHT)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   /* 
	   @Spawns("car-east")
	   public Entity newCarEast(SpawnData data) {
		   Entity newCar = Entities.builder()
				   .viewFromTextureWithBBox("car-east.png")
				   .from(data)
				   .at(data.getX(), data.getY())
				   .type(EntityType.CAR)
				   .build();
		   
		   
		   
		   return newCar;
	    } */
	   
	   @Spawns("car-east")
	   public Entity newCarEast(SpawnData data) {
		   Rectangle rec = getNewCarEWFill("assets/textures/car-east.png");
		   return Entities.builder()
				   .from(data)
				   //.viewFromTextureWithBBox("car-east.png")
				   .at(data.getX(), data.getY())
				   .type(EntityType.CAR)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("car-north")
	   public Entity newCarNorth(SpawnData data) {
		   Rectangle rec = getNewCarNSFill("assets/textures/car-north.png");
		   return Entities.builder()
				   .from(data)
				   //.viewFromTextureWithBBox("car-north.png")
				   .at(data.getX(), data.getY())
				   .type(EntityType.CAR)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("car-west")
	   public Entity newCarWest(SpawnData data) {
		   Rectangle rec = getNewCarEWFill("assets/textures/car-west.png");
		   return Entities.builder()
				   .from(data)
				   //.viewFromTextureWithBBox("car-west.png")
				   .at(data.getX(), data.getY())
				   .type(EntityType.CAR)
				   .viewFromNode(rec)
				   .build();
	    }
	   
	   @Spawns("car-south")
	   public Entity newCarSouth(SpawnData data) {
		   Rectangle rec = getNewCarNSFill("assets/textures/car-south.png");
		   return Entities.builder()
				   .from(data)
				   //.viewFromTextureWithBBox("car-west.png")
				   .at(data.getX(), data.getY())
				   .type(EntityType.CAR)
				   .viewFromNode(rec)
				   .build();
	    }
	     
	   //imagePath ex: "assets/textures/grass.png"
	   public Rectangle getNewRecFill(String imagePath) {
		   Image im = new Image(imagePath);
		   ImagePattern imP = new ImagePattern(im, 0, 0, 1, 1, true);
		   //Texture text = FXGL.getAssetLoader().loadTexture("grass.png");
		   Rectangle rec = new Rectangle(this.tileWidth, this.tileHeight);
		   rec.setFill(imP);
		   return rec;
	   }
	   
	   //imagePath ex: "assets/textures/grass.png"
	   public Rectangle getNewCarEWFill(String imagePath) {
		   Image im = new Image(imagePath);
		   ImagePattern imP = new ImagePattern(im, 0, 0, 1, 1, true);
		   //Texture text = FXGL.getAssetLoader().loadTexture("grass.png");
		   Rectangle rec = new Rectangle(this.tileWidth/2, this.tileHeight/2);
		   rec.setFill(imP);
		   return rec;
	   }
	   
	   //imagePath ex: "assets/textures/grass.png"
	   public Rectangle getNewCarNSFill(String imagePath) {
		   Image im = new Image(imagePath);
		   ImagePattern imP = new ImagePattern(im, 0, 0, 1, 1, true);
		   //Texture text = FXGL.getAssetLoader().loadTexture("grass.png");
		   Rectangle rec = new Rectangle(this.tileWidth/2, this.tileHeight/2);
		   rec.setFill(imP);
		   return rec;
	   }

	@Override
	public int blockHeight() {
		// TODO Auto-generated method stub
		return 60;
	}

	@Override
	public int blockWidth() {
		// TODO Auto-generated method stub
		return 60;
	}

	@Override
	public char emptyChar() {
		// TODO Auto-generated method stub
		return ' ';
	}
	
	public void setTileWidthHeight(int tileWidth, int tileHeight) {
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
	}


}
