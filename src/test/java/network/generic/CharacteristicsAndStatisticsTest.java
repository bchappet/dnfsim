package test.java.network.generic;

import java.math.BigDecimal;

import main.java.console.CommandLine;
import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.network.generic.NetworkCommandLine;
import main.java.network.generic.NetworkModel;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.packet.Spike;

import org.junit.Before;
import org.junit.Test;

public class CharacteristicsAndStatisticsTest {

	NetworkModel nm;
	CommandLine cl;
	ModelControler mc;
	ComputationControler cc;
	SpreadingGraph sg;
	
	@Before
	public void setUp() throws Exception {
		nm = new NetworkModel("testRSDNF");
        cl = nm.constructCommandLine();
        cl.setContext(""+NetworkCommandLine.TRANSITION_MATRIX_FILE+"=src/test/java/network/generic/1.rsdnf;");
        nm.initialize(cl);
        System.out.println(cl.getScript());
        ModelControler mc = new ModelControler(nm);
		cl.setCurentModelControler(mc);
		cc = new ComputationControler(mc.getTree());
        sg = nm.getSpreadingGraph();
	}

	@Test
	public void test() throws Exception{
		
		sg.addToFIFO(0, new Spike());
		sg.addToFIFO(0, new Spike());
		sg.addToFIFO(0, new Spike());
		cc.compute();
		Thread.sleep(100);
		cc.compute();
		Thread.sleep(100);
		cc.compute();
		Thread.sleep(100);
		cc.compute();
//		cc.compute(new BigDecimal("0.2"));
//		Thread.sleep(100);
//		cc.compute(new BigDecimal("0.3"));
//		Thread.sleep(100);
//		rsdnf.getSpreadingGraph().compute();
//		System.out.println(rsdnf.getTime());;
////		System.out.println("test : "+rsdnf.getCharac().getWtrace().get(0));
//		System.out.println("test : "+rsdnf.getStatistics().getIndex(0).getIndex(0));
	}

}
