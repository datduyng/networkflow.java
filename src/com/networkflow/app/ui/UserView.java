package com.networkflow.app.ui;

/**
 * JavaFX UI tool
 * https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 */
import com.almasb.fxgl.ui.InGameWindow;
import com.networkflow.app.AppMain;
import com.networkflow.apputils.AppException;
import com.networkflow.component.SimulationMap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.FXGLButton;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.scene.layout.VBox;


public class UserView extends InGameWindow{
	
	private int viewWidth = 202; 
	private int viewHeight = 315;
	
	private String tiledMapResources = "src/resources/tiledmaps/";
	public <T> UserView(double gameWidth, double gameHeight) {
		super("User View", WindowDecor.MINIMIZE);
		
		relocate(gameWidth - viewWidth, 0);
		
        setBackgroundColor(Color.rgb(25, 25, 10, 0.4));
        setPrefSize(viewWidth, viewHeight);
        setCanResize(false);
        
        VBox attrBox = new VBox(6);
        attrBox.setSpacing(30); 

        
        FXGLButton fooBtn = new FXGLButton("foo button");
        EventHandler<ActionEvent> eventFoo = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                System.out.println("FX gL button");
            } 
        };
        fooBtn.setOnAction(eventFoo);
        Pane box = new Pane();
        box.setPrefSize(160, 15);
        box.getChildren().addAll(fooBtn);
        attrBox.getChildren().add(box);

        Button startBtn = new Button("Start"); 
        startBtn.setStyle("-fx-text-fill: green");
        
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                AppMain.setSimulationRunning(!AppMain.getSimulationRunnning());
                
                if(AppMain.getSimulationRunnning()) {
                	startBtn.setText("Pause");
                	startBtn.setStyle("-fx-text-fill: red");
                }else {
                	startBtn.setText("Start");
                	startBtn.setStyle("-fx-text-fill: green");
                }
           
            } 
        }; 
        startBtn.setOnAction(event);
        
        
        Button restartBtn = new Button("Restart"); 
        EventHandler<ActionEvent> restartBtnEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e){ 
            	AppMain am = (AppMain) FXGL.getApp();
            	am.getGameWorld().clear();
            	am.initGame();
            } 
        }; 
        restartBtn.setOnAction(restartBtnEvent);
        
        Pane box2 = new Pane();
        box2.setPrefSize(160, 15);
        box2.getChildren().addAll(startBtn);
        
        Pane box33 = new Pane();
        box33.setPrefSize(160, 15);
        box33.getChildren().addAll(restartBtn);
        attrBox.getChildren().addAll(box2, box33);
        
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
                    
                    AppMain.setTimeUnit(new_val.doubleValue());
//                this.g
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
        
        
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        

        final Button chooseBtn = new Button("Choose file..");
        chooseBtn.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File selectedFile = fileChooser.showOpenDialog(null);
                        
                        if (selectedFile != null) {
                        	System.out.println("File Selected" + selectedFile.getName());
                        	/*
                        	AppMain am = (AppMain) FXGL.getApp();
                        	am.getGameWorld().clear();
                        	am.setMapPath(newMapPath);
                        	am.initGame();
                        	*/
                        }
                        else {
                            System.out.println("File selection cancelled.");
                        }
                    }
        });
        
        GridPane.setConstraints(chooseBtn, 0, 2);//width, height	
        grid.getChildren().add(chooseBtn);
        
        /***SET UP DROP DOWN MENU**/
        ArrayList<String> fileNames = this.getAllFileNameUnder(this.tiledMapResources);
        System.out.println(fileNames);
        ObservableList<String> options = 
        	    FXCollections.observableArrayList(fileNames);
        final ComboBox comboBox = new ComboBox(options);
        
        //make nice display
        //thanks to: https://stackoverflow.com/questions/22190370/how-to-set-width-of-drop-down-of-combobox-in-java-fx
        comboBox.setPromptText("pre-setup map");
        comboBox.setValue(Font.getDefault().getFamily());
        comboBox.setStyle("-fx-pref-width: 150;");
        
        comboBox.setOnAction(
        	 new EventHandler<ActionEvent>() {
                 @Override
                 public void handle(final ActionEvent e) {
                     File selectedFile = fileChooser.showOpenDialog(null);
                     
                     if (selectedFile != null) {
                     	System.out.println("File Selected" + selectedFile.getName());
                     	/*
                     	AppMain am = (AppMain) FXGL.getApp();
                     	am.getGameWorld().clear();
                     	am.setMapPath(newMapPath);
                     	am.initGame();
                     	*/
                     }
                     else {
                         System.out.println("File selection cancelled.");
                     }
                 }	
        });

        GridPane.setConstraints(comboBox, 0, 3);//width, height	
        grid.getChildren().add(comboBox);
        
        attrBox.getChildren().add(box3);
        Pane pane = new Pane();
        pane.getChildren().addAll(attrBox);
        
        setContentPane(pane);
	}
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	/**
	 * Thanks to:https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
	 * @param pathToDirs
	 * @return
	 */
	public ArrayList<String> getAllFileNameUnder(String pathToDirs){
		ArrayList<String> results = new ArrayList<String>();
		File[] files = new File(pathToDirs).listFiles();
		System.out.println("files" + files.toString()); 
		//If this pathname does not denote a directory, then listFiles() returns null. 
		if(files != null)
			for (File file : files) {
			    if (file.isFile()) {
			        results.add(file.getName());
			    }
			}
		return results;
	}
}


