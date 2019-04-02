package com.networkflow.component;

import java.util.HashMap;

public class ShiftMapperX {
	
	private static final int pixSize = 60;
	
	private static HashMap<String, Double> shiftMappingX = new HashMap<String, Double>() {
		{
			put(">>", new Double(pixSize)* 1.5);
			put(">^", new Double(pixSize * 1.5));
			put("><", new Double(0));
			put(">v", new Double(pixSize));
			
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
	
	public static HashMap<String, Double>  getShiftMappingX() {
		return shiftMappingX;
	}
}
	