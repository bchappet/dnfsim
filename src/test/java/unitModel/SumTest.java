package test.java.unitModel;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import main.java.maps.Map;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.DoubleSpace2D;
import main.java.space.Space;
import main.java.unitModel.GaussianND;
import main.java.unitModel.Sum;

import org.junit.Before;
import org.junit.Test;

public class SumTest {
	UnitMap<Double,Double> uut;
	Map umMap1,umMap2;
	@Before
	public void setUp() throws Exception {
		Var<BigDecimal> dt = new Var<BigDecimal>(new BigDecimal("0.1"));
		GaussianND	gnd1 = new GaussianND(0.);
		Space<Double> space = new DoubleSpace2D(new Var<Double>("OriX",-0.5),new Var<Double>("OriY",-0.5),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),new Var<Integer>(3));
//		Space space = new Space2D(3,3);
		umMap1 = new UnitMap<Double,Double>( "umMap", dt,
				space, gnd1, space,new Var(-0.7),new Var(1.), new Var(0.),new Var(0.));
	
		GaussianND	gnd2 = new GaussianND(0.);
//		Space space = new Space2D(3,3);
		umMap2 = new UnitMap<Double,Double>( "umMap", dt,
				space, gnd2, space,new Var(1.25),new Var(0.10), new Var(0.),new Var(0.));
		
		uut = new UnitMap<Double,Double>("uut", dt, space, new Sum(0d), umMap1,umMap2);
		
	}

	@Test
	public void test() {
		
		umMap1.compute();
		umMap2.compute();
		uut.compute();
		System.out.println(uut);
	}

}
