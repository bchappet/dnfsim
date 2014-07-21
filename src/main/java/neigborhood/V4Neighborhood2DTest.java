package main.java.neigborhood;

import main.java.maps.Var;
import main.java.space.DoubleSpace2D;

import org.junit.Before;
import org.junit.Test;

public class V4Neighborhood2DTest {
	
	private V4Neighborhood2D<Double> neigh;

	@Before
	public void setUp() throws Exception {
		Var<Double> ori = new Var<Double>("ori",0d);
		Var<Double> length = new Var<Double>("length",1d);
		Var<Integer> res = new Var<Integer>("res",3);
		DoubleSpace2D space = new DoubleSpace2D(ori, ori, length, length, res);
		neigh = new V4Neighborhood2D<Double>(space);
	}

	@Test
	public void testGetNeighborhood() {
		int[] res = neigh.getNeighborhood(2);
//		System.out.println(Arrays.toString(res));
	}

}
