package com.networkflow.component;

import java.util.HashMap;

public class ShiftMapperY {
	
	private static final int pixSize = 60;
	
	private static HashMap<String, Double> shiftMappingY = new HashMap<String, Double>() {
		{
			put(">>", new Double(0));
			put(">^", new Double(-(pixSize/2)));
			put("><", new Double(-(pixSize/2)));
			put(">v", new Double(0));
			
			put("^>", new Double(90));
			put("^^", new Double(0));
			put("^<", new Double(270));
			put("^v", new Double(180));
			put("<>", new Double(180));
			put("<^", new Double(90));
			put("<<", new Double(0));
			put("<v", new Double(270));
			put("v>", new Double(270));
			put("v^", new Double(180));
			put("v<", new Double(90));
			put("vv", new Double(0));
		
		}
		
	};
	
	public static HashMap<String, Double>  getShiftMappingY() {
		return shiftMappingY;
	}
}
	