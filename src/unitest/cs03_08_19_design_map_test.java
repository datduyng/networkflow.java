package unitest;

import component.SimulationMap;
import component.Tile;

public class cs03_08_19_design_map_test {

	public static void main(String args[]) {
		SimulationMap map = new SimulationMap(30, 30);
		
		map.loadComponents("./simulation-data/map03.json");
		
		System.out.println(map.mapToString());
	}
}
