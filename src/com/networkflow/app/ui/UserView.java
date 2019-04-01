package com.networkflow.app.ui;

/**
 * JavaFX UI tool
 * https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 */
import com.almasb.fxgl.ui.InGameWindow;
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
	public UserView(double width, double height) {
		super("User View", WindowDecor.MINIMIZE);
		
		//Stage primaryStage = new Stage();
		
		relocate(width - 202 - 202, height - 315);

		setBackgroundColor(Color.rgb(25, 25, 10, 0.4));
		setPrefSize(202, 315);
		setCanResize(false);

		VBox attrBox = new VBox(6);
		attrBox.setSpacing(30); 

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
		
		
		//Input for updating timer speed button--------------------------------------
		Label inputUpdateSpeed = new Label("timer update speed");
		TextField input = new TextField();

		//start and stop button--------------------------------------
		Button startBtn = new Button("Start button"); 
		
		Button stopBtn = new Button("Stop button"); 
		
		Label l = new Label("button not selected");
		
		//TODO: syntax error 
		Double timerUpdateSpeed = 500.00; //Double.valueOf(input.getText());
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 
				Timeline timeline = new Timeline();
				if(e.getSource() == startBtn){
					l.setText("   start button   selected    "); 
					Duration.millis(timerUpdateSpeed);
					System.out.println("Start Simulation");
					timeline.play();
				}
				else if(e.getSource() == stopBtn){
					l.setText("   stop button   selected    "); 
					timeline.stop();
				}		
			} 
		}; 
		startBtn.setOnAction(event);
		stopBtn.setOnAction(event);
		
		TilePane box2 = new TilePane();
		box2.setPrefSize(160, 15);
		
		box2.getChildren().addAll(inputUpdateSpeed, input);
		box2.getChildren().add(startBtn);
		box2.getChildren().add(stopBtn);
		box2.getChildren().add(l);
		
		attrBox.getChildren().add(box2);



		//load map button--------------------------------------
		Button loadMapBtn = new Button("Load Map");
		EventHandler<ActionEvent> eventLoadMap = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 
				System.out.println("loading new map...");
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("JavaFX Projects");
				//TODO: how to showDialog on scene
				chooser.showDialog(primaryStage);
				System.out.println(chooser.showDialog(primaryStage).getAbsolutePath());
			} 
		}; 
		loadMapBtn.setOnAction(eventLoadMap);
		TilePane boxLoadMap = new TilePane();
		boxLoadMap.setPrefSize(160, 15);
		boxLoadMap.getChildren().addAll(loadMapBtn);
		attrBox.getChildren().add(boxLoadMap);

		



		//Simulation speed button--------------------------------------

		//        attrBox.setMargin(box, new Insets(50));
		//        attrBox.setMargin(box2, new Insets(50));
		Font font = Font.font("Lucida Console", 14);

		Label opacityCaption = new Label("Simulation Speed");
		opacityCaption.setFont(font);
		Slider opacityLevel = new Slider(0, 1, 1);
		Label opacityValue = new Label(
				Double.toString(opacityLevel.getValue()));
		opacityValue.setFont(font);
		opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				System.out.println("New Slider Values" + new_val.doubleValue());
				opacityValue.setText(String.format("%.2f", new_val));
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



		Pane pane = new Pane();
		pane.getChildren().addAll(attrBox);
		setContentPane(pane);
		
//		Scene scene = new Scene(pane, width, height);
//		primaryStage.setScene(scene);
//		primaryStage.show();
		
	
		
	}
}


