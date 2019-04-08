package com.networkflow.menu;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.menu.MenuType;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;

public class MainMenuTest extends Application {

	public static MainMenuTest fxglMenu;
	private Desktop desktop = Desktop.getDesktop();
	public static GameMenu gameMenu;
	public static Stage stagePrimary;
	public static String map;
	
	public static String getMap() {
		return map;
	}
	public static void setMap(String map) {
		fxglMenu.map = map;
	}
	public static Stage getStagePrimary() {
		return stagePrimary;
	}
	public static MainMenuTest getFxglMenu() {
		return fxglMenu;
	}
	
//	public MainMenuTest (GameApplication app, MenuType type) {
//		super(app, type);
//	}

	
	public void start(Stage primaryStage) throws Exception {

		Pane root = new Pane();
		root.setPrefSize(800, 600);

		InputStream is = Files.newInputStream(Paths.get("resource/Pac-Man-Maze.jpg"));
		Image img = new Image(is);
		is.close();

		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitHeight(600);

		gameMenu = new GameMenu();
		gameMenu.setVisible(false);

		root.getChildren().addAll(imgView, gameMenu);

		//ESCAPE keycode control
		Scene scene = new Scene(root);
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				if (!gameMenu.isVisible()) {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
					ft.setFromValue(0);
					ft.setToValue(1);

					gameMenu.setVisible(true);
					ft.play();
				}
				else {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.setOnFinished(evt -> gameMenu.setVisible(false));
					ft.play();
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private class GameMenu extends Parent {
		public GameMenu() {
			//menu0 for start/ stop
			VBox menu0 = new VBox(10);

			//menu0 for map selecting 
			VBox menu1 = new VBox(10); 

			menu0.setTranslateX(100);
			menu0.setTranslateY(200);
			menu1.setTranslateX(100);
			menu1.setTranslateY(200);
			final int offset = 400;
			menu1.setTranslateX(offset);

			MenuButton btnResume = new MenuButton("RESUME");
			btnResume.setOnMouseClicked(event -> {
				FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setOnFinished(evt -> setVisible(false));
				ft.play();
			});

			//------------------------------------------------- load map
			MenuButton btnOptions = new MenuButton("New Map");
			btnOptions.setOnMouseClicked(event ->{
				//map options
				getChildren().add(menu1);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt.setToX(menu0.getTranslateX() - offset);

				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
				tt1.setToX(menu0.getTranslateX());

				tt.play();
				tt1.play();

				tt.setOnFinished(evt -> {
					getChildren().remove(menu0);
				});

				//use file chooser
				//				FileChooser chooser = new FileChooser();
				//				File file = chooser.showOpenDialog(stagePrimary);
				//				if (file != null) {
				//					openFile(file);
				//					System.out.println(file.getAbsolutePath());
				//					map = file.getPath();
				//				}
			});

			//back option use default map map11
			MenuButton btnBack = new MenuButton("BACK");
			btnBack.setOnMouseClicked(event -> {
				getChildren().add(menu0);

				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
				tt.setToX(menu1.getTranslateX() + offset);

				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
				tt1.setToX(menu1.getTranslateX());

				tt.play();
				tt1.play();

				tt.setOnFinished(evt -> {
					getChildren().remove(menu1);
				});
			});

			//test map = map08
			MenuButton btnMap01 = new MenuButton("Test Map 1");
			btnMap01.setOnMouseClicked(event -> {
				map = "simulation-data/map08.json";
				System.out.println(map);
			});

			//test map = map11
			MenuButton btnMap02 = new MenuButton("Test Map 2");
			btnMap02.setOnMouseClicked(event -> {
				map = "simulation-data/map11.json";
				System.out.println(map);
			});

			//test map = multTurns_test
			MenuButton btnMap03 = new MenuButton("Test Map 3");
			btnMap03.setOnMouseClicked(event -> {
				map = "simulation-data/multTurns_test.json";
				System.out.println(map);
			});

			MenuButton btnExit = new MenuButton("EXIT");
			btnExit.setOnMouseClicked(event -> {
				System.exit(0);
			});

			menu0.getChildren().addAll(btnResume, btnOptions, btnExit);
			menu1.getChildren().addAll(btnBack, btnMap01, btnMap02, btnMap03);


			Rectangle bg = new Rectangle(800, 600);
			bg.setFill(Color.GREY);
			bg.setOpacity(0.4);

			getChildren().addAll(bg, menu0);

		}
	}


	//use file chooser
	//	private void openFile(File file) {
	//		try {
	//			desktop.open(file);
	//		} catch (IOException ex) {
	//			Logger.getLogger(
	//					FileChooserTest.class.getName()).log(
	//							Level.SEVERE, null, ex
	//							);
	//		}
	//	}

	private static class MenuButton extends StackPane {
		private Text text;

		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(text.getFont().font(20));
			text.setFill(Color.WHITE);

			Rectangle bg = new Rectangle(250, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));

			setAlignment(Pos.CENTER_LEFT);
			setRotate(-0.5);
			getChildren().addAll(bg, text);

			setOnMouseEntered(event -> {
				bg.setTranslateX(10);
				text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});

			setOnMouseExited(event -> {
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});

			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());

			setOnMousePressed(event -> setEffect(drop));
			setOnMouseReleased(event -> setEffect(null));
		}
	}

//	@Override
//	protected Button createActionButton(String arg0, Runnable arg1) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	protected Button createActionButton(StringBinding arg0, Runnable arg1) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	protected Node createBackground(double arg0, double arg1) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	protected Node createProfileView(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	protected Node createTitleView(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	protected Node createVersionView(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public static void main(String[] args) {
	launch(args);
}
}