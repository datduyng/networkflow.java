package com.networkflow.junitest;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.networkflow.apputils.AppException;
import com.networkflow.apputils.StatusMessage;
import com.networkflow.component.Car;
import com.networkflow.component.Intersection;
import com.networkflow.component.Point;
import com.networkflow.component.SimulationMap;
import com.networkflow.component.StopSign;
import com.networkflow.component.Tile;
import com.networkflow.component.TrafficLight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;



/**
 * 
 * @author datng
 */
public class TestJunit {
   
   /**
    * Test if system json parser is correct
    */
   @Test
   public void test_F2_N3b_N4b_01() {
	   String expected = "ground,ground,traffic-light,\n" + 
	   					 "stop-sign,ground,ground,\n" + 
	   					 "road-horizontal,road-horizontal,road-horizontal,\n";
	   
	SimulationMap simMap = null;
	try {
		simMap = new SimulationMap("src/com/networkflow/junitest/test_F2_N3b_N4b_01_map.json");
	} catch (AppException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   assertEquals(simMap.mapToString(), expected);
	   
   }
   
   @Test
   public void test_F2_N3b_N4b_02() throws AppException {
	   SimulationMap simMap; 
	   Throwable t = assertThrows(AppException.class, () -> 
	   	new SimulationMap("src/com/networkflow/junitest/test_F2_N3b_N4b_02.random"));

	   assertEquals(t.getMessage(), "Not JSON file");
   
   }
   
  @Test 
  public void test_F4_N1a_N3a_01() {
		SimulationMap simMap = null;
		try {
			simMap = new SimulationMap("src/com/networkflow/junitest/test_F4_N1a_N3a_01_map.json");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Point currentIndex = simMap.getCarList().get(0).getCurrentIndex();
		Point nextIndex = simMap.getCarList().get(0).getCarNextPoint(currentIndex);
		boolean isNextTileOk = simMap.getCarList().get(0).checkTile(nextIndex, simMap.getLayout());
		assertTrue(isNextTileOk);
  }
  
  @Test 
  public void test_F4_N1a_N3a_02() {
		SimulationMap simMap = null;
		try {
			simMap = new SimulationMap("src/com/networkflow/junitest/test_F4_N1a_N3a_02_map.json");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Point currentIndex = simMap.getCarList().get(0).getCurrentIndex();
		Point nextIndex = simMap.getCarList().get(0).getCarNextPoint(currentIndex);
		
		boolean isNextTileOk = simMap.getCarList().get(0).checkTile(nextIndex, simMap.getLayout());
		assertFalse(isNextTileOk);
  }
   
   
   @Test 
   public void test_N2b() {
	   StatusMessage status = null;
	   SimulationMap simMap = new SimulationMap();
		try {
			   status = simMap.loadComponents("src/com/networkflow/junitest/test_N2b_map.json");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   assertEquals(status, StatusMessage.NoBuiltInDirection);
	   
   }
   
   /**
    * Test system to handle traffic with multiple traffics components and cars
    * Cars and components need to be placed at the right location
    */
   @Test
   public void test_N3a_N3b() {
		SimulationMap simMap = null;
		try {
			simMap = new SimulationMap("src/com/networkflow/junitest/test_N3a_N3b_map.json");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Intersection intersection : simMap.getTrafficComponents()) {
			if(intersection.getClassType().equalsIgnoreCase("stop-sign")){
				assertTrue(intersection instanceof StopSign);
			}else if(intersection.getClassType().equalsIgnoreCase("traffic-light")){
				assertTrue(intersection instanceof TrafficLight);
			}
		}

		assertEqual(4, simMap.getTrafficComponents().size());
		assertEqual(4, simMap.getCarList().size());
   }

	public static void assertEqual(Number number1, Number number2) {
		// TODO Auto-generated method stub
		assertEquals(number1.intValue(), number2.intValue());
	}
   
}