package com.networkflow.unitest;

import java.util.ArrayList;

import com.networkflow.component.Car;
import com.networkflow.component.SimulationMap;

public class cs03_08_19_design_map_test {

	public static void main(String args[]) {
		test_03_22_19();
	}
	
	public static void test_03_22_19() {
		SimulationMap map = new SimulationMap(30, 30);
		map.loadComponents("./simulation-data/map03-test-load-component-json.json");
		System.out.println(map.mapToString());
		System.out.println(map.carListToString());
	}
	
	public void test_03_08_09_reid() {
		SimulationMap map = new SimulationMap(30, 30);
		System.out.println("check");
		map.loadComponents("./simulation-data/map02.json");
		System.out.println(map.mapToString());
		System.out.println(map.carListToString());
		
		ArrayList<Car> carList = map.getCarList();
		
		boolean running = true; 
		int count = 0;
		while(running && (count < 95)) {
			//update cars 
			for(int i = 0; i < carList.size(); i++) {
				carList.get(i).move(map.getLayout());
				System.out.println("Current X Pos: " + carList.get(i).getCurrentPosition().getX());
				System.out.println("Current Y Pos: " + carList.get(i).getCurrentPosition().getY());

			}
			//update components 
			//TODO: 
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //sleep for 1/2 second
			count++;
		}
	}
}
