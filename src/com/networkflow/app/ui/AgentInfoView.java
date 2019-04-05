package com.networkflow.app.ui;

import java.util.ArrayList;

import com.almasb.fxgl.ui.InGameWindow;
import com.networkflow.app.AppMain;
import com.networkflow.component.Car;
import com.networkflow.component.Intersection;

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

	StringProperty speedStr = new SimpleStringProperty();
	StringProperty stateStr = new SimpleStringProperty();
	
	
	ArrayList<Car> carList = AppMain.getSimulationMap().getCarList();
	ArrayList<Intersection> trafficCompList = AppMain.getSimulationMap().getTrafficCompList();

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


//		for(int i=0;i<carList.size();i++){
//			stateStr = carList.get(i).stateProperty();
//			stateStr.addListener( (v,oldValue,newValue)->{
//				//System.out.println(newValue);
//			});
//		}
//
//
//		for(int i=0;i<carList.size();i++){
//			stateStr.set(carList.get(i).getState());
//			stateVal.textProperty().bindBidirectional(stateStr);
//		}




		//        stateVal.textProperty().bindBidirectional(stateStr);
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

}
