package com.networkflow.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.StringBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.networkflow.component.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.*;

public class JSONProcessor {
	
	public static JSONArray getTiles(String filePath) {
		JSONParser parser = new JSONParser();
		JSONArray tiles = null;
		System.out.println("File exist check" + new File(filePath).exists());
		try {
		    FileReader file= new FileReader(filePath);
			Object obj = parser.parse(file);
			JSONObject jsonObject = (JSONObject) obj;
			
			//Process map tiles
			tiles = (JSONArray) jsonObject.get("tiles");
			
		}catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}catch(IOException e) {
			System.out.println("IOException");
		}catch(ParseException e) {
			System.out.println("ParseException");
		}	
		return tiles;
	}
	
	public static JSONArray getCars(String filePath) {
		JSONParser parser = new JSONParser();
		JSONArray cars = null;
		System.out.println("File exist check" + new File(filePath).exists());
		try {
		    FileReader file= new FileReader(filePath);
			Object obj = parser.parse(file);
			JSONObject jsonObject = (JSONObject) obj;
			
			//Process map cars
			cars = (JSONArray) jsonObject.get("cars");
			
		}catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}catch(IOException e) {
			System.out.println("IOException");
		}catch(ParseException e) {
			System.out.println("ParseException");
		}	
		return cars;
	}
	
	public static int getHeight(String filePath) {
		JSONParser parser = new JSONParser();
		int height  = 0;
		System.out.println("File exist check" + new File(filePath).exists());
		try {
		    FileReader file= new FileReader(filePath);
			Object obj = parser.parse(file);
			JSONObject jsonObject = (JSONObject) obj;
			
			// get game height 
			height = Integer.parseInt(jsonObject.get("numHeight").toString());
			
		}catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}catch(IOException e) {
			System.out.println("IOException");
		}catch(ParseException e) {
			System.out.println("ParseException");
		}	
		return height;
	}
	
	public static int getWidth(String filePath) {
		JSONParser parser = new JSONParser();
		int width  = 0;
		System.out.println("File exist check" + new File(filePath).exists());
		try {
		    FileReader file= new FileReader(filePath);
			Object obj = parser.parse(file);
			JSONObject jsonObject = (JSONObject) obj;
			
			// get game height 
			width = Integer.parseInt(jsonObject.get("numWidth").toString());
			
		}catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}catch(IOException e) {
			System.out.println("IOException");
		}catch(ParseException e) {
			System.out.println("ParseException");
		}	
		return width;
	}
	
}


