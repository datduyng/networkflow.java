package unitest;

import java.util.ArrayList;

import component.Car;
import component.SimulationMap;

public class cs03_08_19_design_map_test {

	public static void main(String args[]) {
		SimulationMap map = new SimulationMap(30, 30);
		
		map.loadComponents("./simulation-data/map02.json");
		System.out.println(map.mapToString());
		System.out.println(map.carListToString());
		
		ArrayList<Car> carList = map.getCarList();
		
		boolean running = true; 
		int count = 0;
		while(running && (count < 65)) {
			//update cars 
			for(int i = 0; i < carList.size(); i++) {
				carList.get(i).move();
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
