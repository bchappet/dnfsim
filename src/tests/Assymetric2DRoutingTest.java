package tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import routing.Assymetric2DRouting;
import routing.Routing;

public class Assymetric2DRoutingTest {

	private Routing routing;
	
	@Before
	public void setUp() throws Exception {
		routing = new Assymetric2DRouting();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int[][] first = routing.getFirstTargets();
		for(int i = 0 ; i < first.length ; i++)
			System.out.println(first[i][0] + "==>" + first[i][1]);
		
		System.out.println("Targets...");
		int[][] targets = routing.getTargets(0);
		for(int i = 0 ; i < targets.length ; i++)
			System.out.println(targets[i][0] + "==>" + targets[i][1]);
	}

}
