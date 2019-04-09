package com.networkflow.component;

import java.util.HashMap;

public class ShiftMapperX {
	
	private static final int pixSize = 60;
	
	/**
	 * Uses a hashmap to map the x direction shift for the car entity 
	 * object when performing a turn in an intersection.
	 *  
	 * @author Reid
	 *
	 */
	
	private static HashMap<String, Double> shiftMappingX = new HashMap<String, Double>() {
		{
			put(">>", new Double(pixSize));
			put(">^", new Double(pixSize));
			put("><", new Double(pixSize/2));
			put(">v", new Double(pixSize/2));
			
			put("^>", new Double(0));
			put("^^", new Double(0));
			put("^<", new Double(-pixSize/2));
			put("^v", new Double(-pixSize/2));
			
			put("<>", new Double(-pixSize/2));
			put("<^", new Double(-pixSize/2));
			put("<<", new Double(-pixSize));
			put("<v", new Double(-pixSize));
			
			put("v>", new Double(pixSize/2));
			put("v^", new Double(pixSize/2));
			put("v<", new Double(0));
			put("vv", new Double(0));
		
		}
		
	};
	
	public static HashMap<String, Double>  getShiftMappingX() {
		return shiftMappingX;
	}
}
	