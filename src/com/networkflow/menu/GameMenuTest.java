package com.networkflow.menu;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.extra.scene.menu.GTAVMenu;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.MenuType;

import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class GameMenuTest {

	public class MyMenu extends FXGLMenu {
		public MyMenu (GameApplication app, MenuType type) {
			super(app, type);

			// code to customize the view of your menu
		}

		@Override
		protected Button createActionButton(String arg0, Runnable arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Button createActionButton(StringBinding arg0, Runnable arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Node createBackground(double arg0, double arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Node createProfileView(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Node createTitleView(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Node createVersionView(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}


	public class MySceneFactory extends SceneFactory {
		@Override
		public FXGLMenu newMainMenu(GameApplication app) {
			return new GTAVMenu(app, MenuType.MAIN_MENU);
		}
		@Override
		public FXGLMenu newGameMenu(GameApplication app) {
			return new GTAVMenu(app, MenuType.GAME_MENU);
		}
	}
	
	
//	public static void main(String[] args) {
//		launch(args);
//	}

}
