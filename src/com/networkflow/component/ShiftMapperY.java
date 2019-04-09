package com.networkflow.component;

import java.util.HashMap;

public class ShiftMapperY {
	
	private static final int pixSize = 60;
	
/**
 * Uses a hashmap to map the y direction shift for the car entity 
 * object when performing a turn in an intersection.
 *  
 * @author Reid
 *
 */
	
	private static HashMap<String, Double> shiftMappingY = new HashMap<String, Double>() {
		{
			put(">>", new Double(0));
			put(">^", new Double(-(pixSize/2)));
			put("><", new Double(-(pixSize/2)));
			put(">v", new Double(0));
			
			put("^>", new Double(-pixSize/2));
			put("^^", new Double(-pixSize));
			put("^<", new Double(-pixSize));
			put("^v", new Double(-pixSize/2));
			
			put("<>", new Double(pixSize/2));
			put("<^", new Double(0));
			put("<<", new Double(0));
			put("<v", new Double(pixSize/2));
			
			put("v>", new Double(pixSize));
			put("v^", new Double(pixSize/2));
			put("v<", new Double(pixSize/2));
			put("vv", new Double(pixSize));
		
		}
		
	};
	
	public static HashMap<String, Double>  getShiftMappingY() {
		return shiftMappingY;
	}
}
	