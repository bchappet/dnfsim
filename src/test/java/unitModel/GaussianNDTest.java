package test.java.unitModel;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.DoubleSpace2D;
import main.java.space.Space;
import main.java.space.WrappableDouble2DSpace;
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
	public void testInit() {
		assertEquals("The size should be 9 " ,9,umMap.getValues().size());
		System.out.println(umMap);
	}
	
	@Test
	public void testCompute() {
		umMap.compute();
		System.out.println("UMAP");
		System.out.println(umMap);
	}
	
	@Test
	public void testWA() {
		uut = new GaussianND(0.);
		Space<Double> space = new DoubleSpace2D(new Var<Double>("OriX",-0.5),new Var<Double>("OriY",-0.5),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),new Var<Integer>(3));
//		Space space = new Space2D(3,3);
		umMap = new UnitMap<Double,Double>( "umMap", new Var<BigDecimal>(new BigDecimal("0.1")),
				space, uut, space,new Var(-0.7),new Var(1.), new Var(0.),new Var(0.));
		umMap.compute();
		System.out.println("WB");
		System.out.println(umMap);
	}
	
	@Test
	public void testWB() {
		uut = new GaussianND(0.);
		Space<Double> space = new DoubleSpace2D(new Var<Double>("OriX",-0.5),new Var<Double>("OriY",-0.5),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),new Var<Integer>(3));
//		Space space = new Space2D(3,3);
		umMap = new UnitMap<Double,Double>( "umMap", new Var<BigDecimal>(new BigDecimal("0.1")),
				space, uut, space,new Var(1.25),new Var(0.10), new Var(0.),new Var(0.));
		umMap.compute();
		System.out.println("WA");
		System.out.println(umMap);
	}
	
	@Test
	public void testWrap() {
		Space<Double> space = new WrappableDouble2DSpace(new Var<Double>("OriX",-0.5),new Var<Double>("OriY",-0.5),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),new Var<Integer>(3));
//		Space space = new Space2D(3,3);
		umMap = new UnitMap<Double,Double>( "umMap", new Var<BigDecimal>(new BigDecimal("0.1")),
				space, uut, space,new Var(1),new Var(1.), new Var(-0.5),new Var(-0.5));
		umMap.compute();
		
		System.out.println("Wrap");
		System.out.println(umMap);
		
	}
	
	

}
