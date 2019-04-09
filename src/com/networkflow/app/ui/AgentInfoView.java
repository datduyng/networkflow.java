package com.networkflow.app.ui;

import com.almasb.fxgl.ui.InGameWindow;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.beans.value.ObservableValue;

public class AgentInfoView extends InGameWindow{
	
	private static StringProperty speedStr = new SimpleStringProperty();
	private static StringProperty stateStr = new SimpleStringProperty();
	
	double speedDouble = 0.0;
	
	public AgentInfoView(String title) {
		super("Agent Info", WindowDecor.MINIMIZE);
		relocate(0, 240);
		
        setBackgroundColor(Color.rgb(25, 25, 133, 0.4));
        setPrefSize(340, 340);
        setCanResize(false);
        
        //TODO:
//        Cursor cursorQuestion = new ImageCursor(FXGL.getAssetLoader().loadCursorImage("question.png"), 52, 10);
        Font font = Font.font("Lucida Console", 14);
        VBox attrBox = new VBox(5);
        attrBox.setPadding(new Insets(10));
        attrBox.setTranslateY(20);
        Text speedTxt = new Text("Speed");
        speedTxt.setFont(font);
        Text speedVal = new Text();
        speedVal.setFont(font);
        speedVal.textProperty().bindBidirectional(speedStr);
        speedVal.setTranslateX(70);
        stateStr.set("Idleing");
        
        Text state = new Text("state");
        state.setFont(font);
        Text stateVal = new Text();
        stateVal.setFont(font);
        stateVal.textProperty().bindBidirectional(stateStr);
        stateVal.setTranslateX(70);
        
        Text mBtn = new Text("-");
        mBtn.setStroke(Color.YELLOWGREEN.brighter());
        mBtn.setStrokeWidth(3);
        mBtn.setFont(font);
        mBtn.setOnMouseClicked(event -> {
        	speedDouble -= 1;
        	this.speedStr.set(Double.toString(this.speedDouble));
        });
        mBtn.setTranslateX(130);
        
        Text pBtn = new Text("+");
        pBtn.setStroke(Color.YELLOWGREEN.brighter());
        pBtn.setStrokeWidth(3);
        pBtn.setFont(font);
        pBtn.setOnMouseClicked(event -> {
        	speedDouble += 1;
        	this.speedStr.set(Double.toString(this.speedDouble));
        });
        pBtn.setTranslateX(149);
        
        Pane box = new Pane();
        box.setPrefSize(160, 15);
        box.getChildren().addAll(speedTxt, speedVal, mBtn, pBtn);
        attrBox.getChildren().add(box);
        
        Pane box2 = new Pane();
        box2.setPrefSize(160, 15);
        box2.getChildren().addAll(state, stateVal);
        attrBox.getChildren().add(box2);
        
        Pane statBox = new Pane();
        
        Pane root = new Pane(new HBox(10, attrBox, new Separator(Orientation.VERTICAL), statBox));
        setContentPane(root);
	}
	
	public static StringProperty getSpeedStr() {
		return speedStr;
	}


	public static void setSpeedStr(StringProperty speedStr) {
		AgentInfoView.speedStr = speedStr;
	}


	public static StringProperty getStateStr() {
		return stateStr;
	}


	public static void setStateStr(StringProperty stateStr) {
		AgentInfoView.stateStr = stateStr;
	}

	
	public static void setViewingObject(Object obj) {
		
	}

}
