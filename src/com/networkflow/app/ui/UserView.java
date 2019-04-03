package com.networkflow.app.ui;

/**
 * JavaFX UI tool
 * https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 */
//import com.almasb.fxgl.ui.InGameWindow;

import com.almasb.fxgl.ui.DialogBox;
import com.almasb.fxgl.ui.Display;

import com.almasb.fxgl.ui.FXGLButton;
import com.almasb.fxgl.ui.FXGLChoiceBox;
import com.almasb.fxgl.ui.InGameWindow;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;


public class UserView extends InGameWindow{
	public UserView(String title, double width, double height) {
		super(title, WindowDecor.MINIMIZE);
		
		relocate(width - 202 - 202, height - 315);
		
        setBackgroundColor(Color.rgb(25, 25, 10, 0.4));
        setPrefSize(202, 315);
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

        Button startBtn = new Button("Start button"); 
        Label l = new Label("button not selected");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                l.setText("  cancel  button    selected    "); 
            } 
        }; 
        
        startBtn.setOnAction(event);
        Pane box2 = new Pane();
        box2.setPrefSize(160, 15);
        box2.getChildren().addAll(startBtn);
        attrBox.getChildren().add(box2);
        
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
	}


}


