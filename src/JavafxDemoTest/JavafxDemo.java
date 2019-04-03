package JavafxDemoTest;

import com.networkflow.component.JSONProcessor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavafxDemo extends Application {
	Button button;
	private final Integer startSecond = 0;
	private Integer seconds;
	public Double updateSpeed;
	
	 
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("test");

		StackPane layout = new StackPane();


		//start button
		Button startBtn = new Button("Start button"); 
		Button stopBtn = new Button("Stop button");

		Label inputUpdateSpeed = new Label("timer update speed");
		TextField input = new TextField();

		Label l = new Label("button not selected");

		//TODO:
		//Double timerUpdateSpeed = Double.valueOf(input.getText());
		updateSpeed = 1000.00;
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) 
			{ 		
				Timeline timeline = new Timeline();
				if(e.getSource() == startBtn){
					seconds = startSecond;
					l.setText("   start button   selected    "); 
					System.out.println("Start Simulation");
					timeline.setCycleCount(Timeline.INDEFINITE);
					KeyFrame frame = new KeyFrame(Duration.millis(updateSpeed), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							seconds++;
							System.out.println("current second: " +seconds);
						} 
					});
					timeline.getKeyFrames().add(frame);
					timeline.play();
					//startTimer();
					System.out.println("current second: " +seconds);
				}
				else if(e.getSource() == stopBtn){
					l.setText("   stop button   selected    "); 
					System.out.println("Stop Simulation");
					System.out.println("total time used: " +(seconds-startSecond));
					seconds = -1;
					//timeline.stop();
				}		
			} 
		}; 

		startBtn.setOnAction(event);
		stopBtn.setOnAction(event);


		Button loadMapBtn = new Button("Load Map");
		EventHandler<ActionEvent> eventLoadMap = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("JavaFX Projects");
				chooser.showDialog(primaryStage);
				System.out.println("loading new map...");
				System.out.println(chooser.showDialog(primaryStage).getAbsolutePath());
			} 
		}; 
		loadMapBtn.setOnAction(eventLoadMap);


		TilePane box2 = new TilePane();
		box2.setPrefSize(160, 15);

		box2.getChildren().addAll(inputUpdateSpeed, input);
		box2.getChildren().add(startBtn);
		box2.getChildren().add(stopBtn);
		box2.getChildren().add(l);
		box2.getChildren().add(loadMapBtn);
		layout.getChildren().add(box2);

		Scene scene = new Scene(layout, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	private void startTimer(){
		Timeline time = new Timeline();
		if(seconds>=0){
			time.setCycleCount(Timeline.INDEFINITE);
			KeyFrame frame = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					seconds++;
					System.out.println("current second: " +seconds);
				} 
			});
			time.getKeyFrames().add(frame);
			time.play();
		}else{
			time.stop();
		}
		
	}
//	
//	private void stoptimer(){
//		
//	}




}

//map button: start, stop, speed, load map