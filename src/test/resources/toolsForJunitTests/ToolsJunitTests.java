package test.resources.toolsForJunitTests;

import static org.junit.Assert.assertTrue;
import main.java.space.Coord;

public class ToolsJunitTests {
	
	private static final double LEVEL = 0.00001;

	public static void assertEqualsRough(String msg,Coord<Double> coordExpected , Coord<Double> coordFound){
//		System.out.println(coordExpected + " VS " + coordFound);
		for(int i = 0 ; i < coordExpected.getSize() ; i++){
//			System.out.println(Math.abs(coordFound.getIndex(i)) + " < " + (Math.abs(coordExpected.getIndex(i)) + LEVEL));
			assertTrue(msg,Math.abs(coordFound.getIndex(i)) < Math.abs(coordExpected.getIndex(i))+ LEVEL &&  
					Math.abs(coordFound.getIndex(i)) > Math.abs(coordExpected.getIndex(i))- LEVEL);
		}
		
		
		
	}

}
