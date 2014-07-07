package test.java.unitModel;

import static org.junit.Assert.assertEquals;
import main.java.maps.InfiniteDt;
import main.java.maps.Trajectory;
import main.java.maps.Var;
import main.java.space.DoubleSpace2D;
import main.java.space.Space;
import main.java.unitModel.ComputeUM;

import org.junit.Before;
import org.junit.Test;

public class ComputeUMTest {
	
	private Trajectory<Double> uut;
	private Var<String> script;
	Space<Double> space;

	@Before
	public void setUp() throws Exception {
		script = new Var<String>("Script","");
		uut = new Trajectory<Double>("uut", new InfiniteDt(),new ComputeUM(0d),script);
		
		space = new DoubleSpace2D(new Var<Double>("OriX",-0.5),new Var<Double>("OriY",-0.5),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),new Var<Integer>(3));
		
	}

	@Test
	public void testComputer() {
		script.set("$1+$2*$1+10*1.99");
		Var<Double> a = new Var<Double>(9d);
		Var<Double> b = new Var<Double>(-5d);
		uut.addParameters(a,b);
		uut.compute();
		assertEquals("Computation should respect the script",new Double(-16.1d),uut.get());
	}
	
//	@Test
//	public void testComputer2() {
//		Var<String> equationWeights = new Var<String>("Equation Weights","$1/($2*$2)*(40*40)/$3");
//		Var<Double> pa = new Var<Double>("pa",0.1);
//		Var<Double> alphaP = new Var<Double>("pa",10.);
////		Trajectory<Double> hpA = new Trajectory<Double>("A_hidden",new InfiniteDt(),new ComputeUM(0d),
////			equationWeights,pa,space.getIndex(0),alphaP);
//		
//		hpA.compute();
//		assertEquals("should be good",(Double)1.777777777777778d,hpA.get());
//	}
}
