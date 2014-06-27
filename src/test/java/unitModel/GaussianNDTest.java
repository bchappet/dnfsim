package test.java.unitModel;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.DoubleSpace2D;
import main.java.space.Space;
import main.java.space.Space2D;
import main.java.unitModel.GaussianND;

import org.junit.Before;
import org.junit.Test;

public class GaussianNDTest {
	
	private GaussianND uut;
	private UnitMap<Double, Double> umMap;

	@Before
	public void setUp() throws Exception {
		
		uut = new GaussianND(0.);
		Space<Double> space = new DoubleSpace2D(new Var<Double>("OriX",-0.5),new Var<Double>("OriY",-0.5),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),new Var<Integer>(3));
//		Space space = new Space2D(3,3);
		umMap = new UnitMap<Double,Double>( "umMap", new Var<BigDecimal>(new BigDecimal("0.1")),
				space, uut, space,new Var(0.1),new Var(1.), new Var(0.),new Var(0.));
		
	}

	@Test
	public void test() {
		System.out.println(umMap.getValues().size());
		System.out.println(umMap);
	}

}
