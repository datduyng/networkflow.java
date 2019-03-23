package unitest;

import component.SimulationMap;

public class cs03_22_19_test_load_json {
	public static void main(String args[]) {
		test_03_22_19();
	}
	
	public static void test_03_22_19() {
		SimulationMap map = new SimulationMap(30, 30);
		map.loadComponents("./simulation-data/map03-test-load-component-json.json");
		
		System.out.println(map.mapToString());
		System.out.println(map.carListToString());
		System.out.println(map.componentsToString());
		
	}
}
