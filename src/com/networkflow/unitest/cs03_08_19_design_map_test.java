package com.networkflow.unitest;

import java.util.ArrayList;

import com.networkflow.apputils.AppException;
import com.networkflow.component.Car;
import com.networkflow.component.Intersection;
import com.networkflow.component.SimulationMap;

public class cs03_08_19_design_map_test {

	   public static String simulateGame() {
		   int incTimer = 300;//count down timer
			SimulationMap simMap = null;
			try {
				simMap = new SimulationMap("src/com/networkflow/testing/test_F3_N5a_map.json");
			} catch (AppException e) {
				e.printStackTrace();
			}

		   for(int t = 0; t < incTimer;t++) {
				//update cars
				for(int i = 0; i < simMap.getCarList().size(); i++) {
					simMap.getCarList().get(i).move(simMap.getLayout());
				}
		
				//update components 
				for(int i  = 0; i < simMap.getTrafficComponents().size(); i++) {
					((Intersection) SimulationMap.getTileAtIndex(simMap.getTrafficComponents().get(i).getMapIndex())).deQueue();
				}
				System.out.println("t"+t);
		   }
		   
		   //trace car log
		   
		   System.out.println("here");
		   System.out.println(simMap.getCarList().get(0).getLogger());
		   return simMap.getCarList().get(0).getLogger();
	   }
	public static void main(String args[]) {
		simulateGame();
//		SimulationMap simMap =  new SimulationMap("src/com/networkflow/testing/test_F2_N3b_N4b_02.random");
	}
	
	public static void test_03_22_19() {
		SimulationMap map = null;
		try {
			map = new SimulationMap("./simulation-data/map03-test-load-component-json.json");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map.mapToString());
		System.out.println(map.carListToString());
	}
	
	public void test_03_08_09_reid() {
		SimulationMap map = null;
		try {
			map = new SimulationMap("./simulation-data/map03-test-load-component-json.json");
		} catch (AppException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("check");
		System.out.println(map.mapToString());
		System.out.println(map.carListToString());
		
		ArrayList<Car> carList = map.getCarList();
		
		boolean running = true; 
		int count = 0;
		while(running && (count < 95)) {
			//update cars 
			for(int i = 0; i < carList.size(); i++) {
				carList.get(i).move(map.getLayout());
				System.out.println("Current X Pos: " + carList.get(i).getCurrentIndex().getX());
				System.out.println("Current Y Pos: " + carList.get(i).getCurrentIndex().getY());

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
