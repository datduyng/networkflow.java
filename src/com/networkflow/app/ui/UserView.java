package com.networkflow.app.ui;

/**
 * JavaFX UI tool
 * https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 */
import com.almasb.fxgl.ui.InGameWindow;
//import com.almasb.fxgl.GameApplication;
//import com.almasb.fxgl.GameSettings;
import com.networkflow.app.AppMain;

import java.util.Timer;

import com.almasb.fxgl.time.TimerAction;
import com.almasb.fxgl.ui.FXGLButton;
import com.almasb.fxgl.ui.FXGLChoiceBox;

import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.VBox;




public class UserView extends InGameWindow{
	public static double opacityNum;
	public static String loadMap;

	public static boolean isLoading;
	public static boolean isActive;

	public static VBox attrBox;

	//	public static Button startBtn;
	//	public static Button stopBtn;

	public UserView(double width, double height) {
		super("User View", WindowDecor.MINIMIZE);

		//width - 202 - 202, height - 315
		relocate(width - 202 - 202, height - 400);
		setBackgroundColor(Color.rgb(25, 25, 10, 0.4));
		//202, 315
		setPrefSize(202, 400);
		setCanResize(false);

		//6
		VBox attrBox = new VBox(10);
		//30
		attrBox.setSpacing(40); 




		//foo button--------------------------------------
		FXGLButton fooBtn = new FXGLButton("foo button");
		EventHandler<ActionEvent> eventFoo = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{
				System.out.println("FX gL button");
			} 
		};
		fooBtn.setOnAction(eventFoo);
		TilePane box = new TilePane();
		box.setPrefSize(160, 15);
		box.getChildren().addAll(fooBtn);
		attrBox.getChildren().add(box);


//		//load map button--------------------------------------
//		Button reLoadMap = new Button("Re-LoadMap");
//		Label inputLoadMap = new Label("Load Map:");
//		TextField input = new TextField("simulation-data/multTurns_test.json");
//		loadMap = input.getText();
//
//		EventHandler<ActionEvent> eventReloading = new EventHandler<ActionEvent>() { 
//			public void handle(ActionEvent e) 
//			{ 
//				if(e.getSource() == reLoadMap){
//					isLoading = true;
//					System.out.println("Map reLoading...");
//					//					try {
//					//						wait();
//					//					} catch (InterruptedException e1) {
//					//						// TODO Auto-generated catch block
//					//						e1.printStackTrace();
//					//					}
//				}
//			}
//		};
//		reLoadMap.setOnAction(eventReloading);

//		TilePane boxLoadMap = new TilePane();
//		boxLoadMap.setPrefSize(160, 15);
//		boxLoadMap.getChildren().add(reLoadMap);
//		boxLoadMap.getChildren().addAll(inputLoadMap, input);
//		attrBox.getChildren().add(boxLoadMap);


		//Simulation speed button--------------------------------------

		//        attrBox.setMargin(box, new Insets(50));
		//        attrBox.setMargin(box2, new Insets(50));
		Font font = Font.font("Lucida Console", 14);

		Label opacityCaption = new Label("Simulation Speed");
		opacityCaption.setFont(font);
		//TODO: check default value = 0.1
		Slider opacityLevel = new Slider(0, 1, 1);
		Label opacityValue = new Label(
				Double.toString(opacityLevel.getValue()));
		//TODO: double check 
		opacityNum = opacityLevel.getValue();
		
		opacityValue.setFont(font);
		opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				System.out.println("New Slider Values" + new_val.doubleValue());
				opacityValue.setText(String.format("%.2f", new_val));
				System.out.println("new value:            "+new_val.doubleValue());
				opacityNum = new_val.doubleValue();
				AppMain.getTimer().update(new_val.doubleValue());


			}
		});

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(10);

		GridPane.setConstraints(opacityLevel, 0, 1);
		grid.getChildren().add(opacityLevel);

		GridPane.setConstraints(opacityValue, 1, 1);
		grid.getChildren().add(opacityValue);

		GridPane.setConstraints(opacityCaption, 0, 0);
		grid.getChildren().add(opacityCaption);

		Pane box3 = new Pane();
		box3.setPrefSize(160, 15);
		box3.getChildren().addAll(grid);
		attrBox.getChildren().add(box3);




		//start and stop button--------------------------------------
		Button startBtn = new Button("Start button"); 
		Button stopBtn = new Button("Stop button"); 
		Label l = new Label("button not selected");

		Double timerUpdateSpeed = opacityNum; 

		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 
				if(e.getSource() == startBtn){
					isActive = true;
					l.setText("   start button   selected    "); 
					Duration.seconds(timerUpdateSpeed);
					System.out.println("Start Simulation");
					//AppMain.getTimer().resume();
				}
				else if(e.getSource() == stopBtn){
					isActive = false;
					l.setText("   stop button   selected    "); 
					//AppMain.getTimer().pause();
				}		
			} 
		}; 
		startBtn.setOnAction(event);
		stopBtn.setOnAction(event);

		TilePane box2 = new TilePane();
		box2.setPrefSize(160, 15);


		box2.getChildren().add(startBtn);
		box2.getChildren().add(stopBtn);
		box2.getChildren().add(l);

		attrBox.getChildren().add(box2);

		//IntValue i = new IntValue(3);

		Pane pane = new Pane();
		pane.getChildren().addAll(attrBox);
		setContentPane(pane);

	}



	public static double getOpacityNum() {
		return opacityNum;
	}
	public static String getLoadMap() {
		return loadMap;
	}
	public static boolean getIsLoading() {
		return isLoading;
	}
	public static boolean getIsActive() {
		return isActive;
	}
	public static VBox getAttrBox() {
		return attrBox;
	}



}



