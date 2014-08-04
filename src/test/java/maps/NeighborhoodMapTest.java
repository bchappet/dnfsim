package test.java.maps;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import main.java.maps.MatrixDouble2D;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.Unit;
import main.java.maps.Var;
import main.java.neigborhood.Neighborhood;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.routing.Assymetric2DRouting;
import main.java.space.Space2D;
import main.java.unitModel.NSpikeUM;
import main.java.unitModel.NeighborhoodUnitModel;

import org.junit.Before;
import org.junit.Test;

public class NeighborhoodMapTest {
	
	private NeighborhoodMap uut;
	private Neighborhood neigh;

	@Before
	public void setUp() throws Exception {
		Var<BigDecimal> dt = new Var<BigDecimal>(BigDecimal.ZERO);
		Space2D space = new Space2D(3, 3);
		Var na = new Var<Integer>(1);
		Var pa = new Var<Double>(1.);
		Var ia = new Var<Double>(1.);
		Var threshold = new Var<Double>(0.5);
		double[][] val = new double[][]{
				{0,0,0},
				{0,1,0},
				{0,0,0},
		};
		MatrixDouble2D focus = new MatrixDouble2D("Focus", dt, val);
		
		 uut = new NeighborhoodMap("uut",dt,space,new NSpikeUM(new Assymetric2DRouting()),na,pa,ia,focus,threshold);
		 neigh = new V4Neighborhood2D(space, uut);
		 uut.addNeighboors(neigh);
	}

	@Test
	public void testGoodNeighborhood() {
//		Unit[] res = neigh.getNeighborhoodUnits(4);
////		System.out.println("Units : " +  Arrays.toString(res));
//		assertSame("The unit model on the north should be the index 1",uut.getUnit(1) , res[V4Neighborhood2D.NORTH]);
//		assertSame("The unit model on the north should be the index 7",uut.getUnit(7) , res[V4Neighborhood2D.SOUTH]);
//		assertSame("The unit model on the north should be the index 5",uut.getUnit(5) , res[V4Neighborhood2D.EAST]);
//		assertSame("The unit model on the north should be the index 3",uut.getUnit(3) , res[V4Neighborhood2D.WEST]);
//		
		System.out.println(" array :  " + Arrays.toString(((NeighborhoodUnitModel<Double>) uut.getUnit(0).getUnitModel()).getNeighborhood(0)));
		assertNotSame("The unit model on north of 0 should not be itself",uut.getUnit(0) ,
				((NeighborhoodUnitModel<Double>) uut.getUnit(0).getUnitModel()).getNeighborhood(0)[V4Neighborhood2D.NORTH]);
		
		assertSame("The unit model on south of 0 should be the index 3",uut.getUnit(3) ,
				((NeighborhoodUnitModel<Double>) uut.getUnit(0).getUnitModel()).getNeighborhood(0)[V4Neighborhood2D.SOUTH]);
		
		
		System.out.println(uut.getUnit(1) + " vs " + ((NeighborhoodUnitModel<Double>) uut.getUnit(4).getUnitModel()).getNeighborhood(0)[V4Neighborhood2D.NORTH]);
		assertSame("The unit model on the north should be the index 1",uut.getUnit(1) ,
				((NeighborhoodUnitModel<Double>) uut.getUnit(4).getUnitModel()).getNeighborhood(0)[V4Neighborhood2D.NORTH]);
		assertSame("The unit model on the north should be the index 7",uut.getUnit(7) ,
				((NeighborhoodUnitModel<Double>) uut.getUnit(4).getUnitModel()).getNeighborhood(0)[V4Neighborhood2D.SOUTH]);
		assertSame("The unit model on the north should be the index 5",uut.getUnit(5) ,
				((NeighborhoodUnitModel<Double>) uut.getUnit(4).getUnitModel()).getNeighborhood(0)[V4Neighborhood2D.EAST]);
		assertSame("The unit model on the north should be the index 3",uut.getUnit(3) ,
				((NeighborhoodUnitModel<Double>) uut.getUnit(4).getUnitModel()).getNeighborhood(0)[V4Neighborhood2D.WEST]);
	}
	
	
	@Test
	public void test() {
		System.out.println(uut);
		System.out.println("Compute");
		uut.compute();
		System.out.println(uut.toString());
		
	}

}
