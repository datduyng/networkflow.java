package com.networkflow.unitest;

import com.networkflow.apputils.AppException;
import com.networkflow.component.SimulationMap;

public class cs03_22_19_test_load_json {
	public static void main(String args[]) {
		test_03_22_19();
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
		System.out.println(map.componentsToString());
		
	}
}
