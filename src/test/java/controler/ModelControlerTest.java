package test.java.controler;

import static org.junit.Assert.*;
import main.java.controler.ModelControler;
import main.java.maps.Map;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;

import org.junit.Before;
import org.junit.Test;

public class ModelControlerTest {
	
	private ModelControler uut;
	private Map root;
	private ModelESN model;

	@Before
	public void setUp() throws Exception {
		
//		Space<Integer> main.java.space = new Space2D(2, 2);
//		this.root = new UnitMap<Double, Integer>("A",new Var<BigDecimal>("dtA",new BigDecimal("0.1")), main.java.space, new UMWrapper<Double>(2d));
//		Trajectory<Double> traj = new Trajectory<Double>("B",new Var<BigDecimal>("dtB",new BigDecimal("0.3")), new UMWrapper<Double>(10d));
//		Trajectory<Double> traj2 = new Trajectory<Double>("C",new Var<BigDecimal>("dtC",new BigDecimal("0.35")), new UMWrapper<Double>(10d),new Var<Double>(9d));
//		traj.addParameters(traj2);
//		this.root.addParameters(traj);
		
		ESNCommandLine cl = new ESNCommandLine("");
		model = new ModelESN("test_esn");
		model.initialize(cl);
		uut = new ModelControler(model);
		cl.setCurentModelControler(uut);
		
		
		
		
	}

	@Test
	public void testContructTree() {
		
		String expected = "test_esn\n"+
	"	target_output\n"+
	"		main.java.input\n"+
	"		targeted_output_coeficients\n"+
	"	output\n"+
	"		output_tmp\n"+
	"			WeightsRO\n"+
	"				reservoir\n"+
	"					conv_WRR_R\n"+
	"						WeightsRR\n"+
	"					conv_WIR_I\n"+
	"						WeightsIR\n"+
	"	Stats\n"+
	"		error_dist\n"+
	"	Charac\n"+
	"		error_dist\n"+
	"";
		
		System.out.println(uut.getTree());
		
		assertEquals("The tree should be good ",expected,uut.getTree().toString());
	}
	
	@Test
	public void testFindParameter() {
		assertEquals("We should find the good controler","test_esn",""+uut.getControler("test_esn").getName());
		assertEquals("We should find the good controler","conv_WRR_R",""+uut.getControler("conv_WRR_R").getName());
	}

}
