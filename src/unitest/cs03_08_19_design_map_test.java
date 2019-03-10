package unitest;

import component.SimulationMap;

public class cs03_08_19_design_map_test {

	public static void main(String args[]) {
		SimulationMap map = new SimulationMap(30, 30);
		
		map.loadComponents("./simulation-data/map01.json");

		System.out.println(map.mapToString());
	}
}
