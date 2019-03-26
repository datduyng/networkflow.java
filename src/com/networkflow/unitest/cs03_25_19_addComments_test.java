package com.networkflow.unitest;

import java.util.ArrayList;

import com.networkflow.component.Car;
import com.networkflow.component.Intersection;
import com.networkflow.component.SimulationMap;
import com.networkflow.component.StopSign;
import com.networkflow.component.TrafficLight;


public class cs03_25_19_addComments_test {

	public static void main(String args[]) {
		SimulationMap map = new SimulationMap(30, 30);
		
		map.loadComponents("./simulation-data/map08.json");
		System.out.println(map.mapToString());
		System.out.println(map.carListToString());
		
		ArrayList<Car> carList = map.getCarList();
		ArrayList<Intersection> trafficCompList = map.getTrafficCompList();
		String builtDirections = "<>^v";
		boolean running = true; 
		int count = 0;
		while(running && (count < 400)) {
			//update cars 
			for(int i = 0; i < carList.size(); i++) {
				carList.get(i).move(map.getLayout());
				System.out.println("Current X Pos: " + carList.get(i).getCurrentPosition().getX());
				System.out.println("Current Y Pos: " + carList.get(i).getCurrentPosition().getY());
				System.out.println("Current State: " + carList.get(i).getState());
			}
			
			System.out.println();
			//update components 
			//TODO: 
			for(int i  = 0; i < trafficCompList.size(); i++) {
				trafficCompList.get(i).updateIncrement();
			}
			System.out.println();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} //sleep for 1/2 second
			count++;
		}
	}
}
