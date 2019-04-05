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
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.parser.tiled.TiledMap;
import com.almasb.fxgl.physics.box2d.collision.shapes.Shape;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.TimerAction;
import com.networkflow.app.ui.AgentInfoView;
import com.networkflow.app.ui.UserView;
import com.networkflow.component.Car;
import com.networkflow.component.Intersection;
import com.networkflow.component.JSONProcessor;
import com.networkflow.component.Point;
import com.networkflow.component.SimulationMap;
import com.networkflow.component.StopSign;
import com.networkflow.component.Tile;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public class AppMain extends GameApplication {

	public static TimerAction timer;
	public static SimulationMap simulationMap;


	protected final int gameWidth = 960;
	protected final int gameHeight = 720;

	//TODO:
	protected void initMainMenu(Pane mainMenuRoot){
		Rectangle bg = new Rectangle(gameWidth,gameHeight);
		Font font = Font.font(72);
		
		Button btnGameStart = new Button("Start Simulation");
		btnGameStart.setFont(font);
		//btnGameStart.setOnAction(event -> startNewGame());
		
		Button btnGameStop = new Button("Exit Simulation");
		btnGameStop.setFont(font);
		//btnGameStop.setOnAction(event -> exit());
		
//		UserView.getAttrBox().setTranslateX(300);
//		UserView.getAttrBox().setTranslateY(300);
		
		VBox box = new VBox(50,btnGameStart,btnGameStop);
		
//		UserView.getAttrBox().getChildren().add(btnGameStart);
//		UserView.getAttrBox().getChildren().add(btnGameStop);
//		mainMenuRoot.getChildren().addAll(bg,vbox,UserView.getAttrBox());
		mainMenuRoot.getChildren().addAll(bg,box);
	}

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("FXGL");
		settings.setWidth(gameWidth);
		settings.setHeight(gameHeight);
		settings.setVersion("0.1");
	}
	
	

	//private Entity player;
	

	public void initAssets() {
		if(UserView.getIsLoading() == true){
			simulationMap = new SimulationMap(UserView.getLoadMap());
		}else{
			simulationMap = new SimulationMap("simulation-data/multTurns_test.json");
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

	//Pane gameRoot
	public void initGame() {
		initAssets();

		ArrayList<Car> carList = simulationMap.getCarList();
		ArrayList<Intersection> trafficCompList = simulationMap.getTrafficCompList();

		timer = getMasterTimer().runAtInterval(() ->{
			if(UserView.getIsActive() == true){

				/*update cars: click start button, 
				 * if car is not in destination then move it
				 * else restart it
				 */

				for(int i = 0; i < carList.size(); i++) {
					if(carList.get(i).getCurrentIndex() != carList.get(i).getStopIndex() ){
						carList.get(i).move(simulationMap.getLayout());
						System.out.println("Current X Pos: " + carList.get(i).getCurrentIndex().getX());
						System.out.println("Current Y Pos: " + carList.get(i).getCurrentIndex().getY());
						System.out.println("Current State: " + carList.get(i).getState());
					}else{
						carList.get(i).setCurrentIndex(carList.get(i).getStartIndex());
						carList.get(i).move(simulationMap.getLayout());
						System.out.println("Current X Pos: " + carList.get(i).getCurrentIndex().getX());
						System.out.println("Current Y Pos: " + carList.get(i).getCurrentIndex().getY());
						System.out.println("Current State: " + carList.get(i).getState());
					}
				}

				System.out.println();
				//update components 
				//TODO: 
				for(int i  = 0; i < trafficCompList.size(); i++) {
					((Intersection) SimulationMap.getTileAtIndex(trafficCompList.get(i).getMapIndex())).deQueue();
				}
				System.out.println();

			}
			//TODO: UserView.getOpacityNum()
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
				getGameWorld().spawn(classType, spawnX, spawnY);
			}
		}
	}

	//imagePath ex: "assets/textures/grass.png"
	public Entity getNewCarWFill(String imagePath, String direction, double spawnX, double spawnY) {
		Image im = new Image(imagePath);
		ImagePattern imP = new ImagePattern(im, 0, 0, 1, 1, true);
		//Texture text = FXGL.getAssetLoader().loadTexture("grass.png");
		Rectangle rec = new Rectangle(simulationMap.getPixelSize()/2, simulationMap.getPixelSize()/2);
		rec.setFill(imP);

		switch (direction)
		{	  
		case "^":
			rec.getTransforms().add(new Rotate(90, 0, 0));
			break;
		case "<":
			rec.getTransforms().add(new Rotate(180, 0, 0));
			break;
		case "v":
			rec.getTransforms().add(new Rotate(270, 0, 0));
			break;
		default:
			// east 
			break;
		}
		return Entities.builder()
				.at(spawnX, spawnY)
				.type(EntityType.CAR)
				.viewFromNode(rec)
				.build();
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
		Input input = getInput();
		if(input.isHeld(KeyCode.ESCAPE)){
			//openMainMenu();
		}
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


	@Override
	public void onUpdate(double tpf) {

		/*
		for(int i = 1; i < simulationMap.getCarList().size(); i++) {
			 String carnX = "car" + i + "x"; 
			 String carnY = "car" + i + "y"; 

			int carnXPixVal = getGameState().getInt(carnX).intValue();
			int carnYPixVal = getGameState().getInt(carnY).intValue();

			int carnXCurrPixVal = simulationMap.getPixelSize() * simulationMap.getCarList().get(i).getCurrentIndex().getX();
			int carnYCurrPixVal = simulationMap.getPixelSize() * simulationMap.getCarList().get(i).getCurrentIndex().getY();

			if(carnXPixVal != carnXCurrPixVal) {
				getGameState().setValue(carnX, carnXCurrPixVal);
			}

			if(carnYPixVal != carnYCurrPixVal) {
				getGameState().setValue(carnY, carnYCurrPixVal);
			}
		 */



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
		//getGameScene().addGameView(view);
	}

	public void initLayout(Tile[][] layout) {

	}





	public static TimerAction getTimer() {
		return timer;
	}
	public static SimulationMap getSimulationMap() {
		return simulationMap;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
