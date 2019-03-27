package com.networkflow.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.settings.GameSettings;
import com.sun.javafx.geom.Rectangle;
import com.sun.prism.paint.Color;

public class AppMain extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setTitle("FXGL");
		settings.setWidth(600);
		settings.setHeight(600);
		settings.setVersion("0.1");
	}
	
	private Entity player;
	
	@Override
	public void initGame() {
		 player = Entities.builder()
				 .at(300, 300)
                 //.viewFromNodeWithBBox(new Rectangle(25, 25, Color.BLUE))
                 .buildAndAttach(getGameWorld());
	}
	
	@Override
	public void initInput() {
		
	}
	

	@Override
	public void onUpdate(double tpf) {
		
	}
	
	@Override
	public void initPhysics() {
		
	}
	
	@Override 
	public void initUI() {
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
