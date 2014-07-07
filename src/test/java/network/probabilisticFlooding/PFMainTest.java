package test.java.network.probabilisticFlooding;

import main.java.console.CommandLine;
import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.packet.IPv4Datagramme;
import main.java.network.probalisticFlooding.PFCommandLine;
import main.java.network.probalisticFlooding.PFModel;

import org.junit.Before;
import org.junit.Test;

public class PFMainTest {


	PFModel pfm;
	CommandLine cl;
	ModelControler mc;
	ComputationControler cc;
	SpreadingGraph sg;
	
	@Before
	public void setUp() throws Exception {
		pfm = new PFModel("testPF");
        cl = pfm.constructCommandLine();
        cl.setContext(""
        		+PFCommandLine.TRANSITION_MATRIX_FILE+"=src/test/java/network/probabilisticFlooding/1.pf;"
        		+PFCommandLine.WEIGTH+"=0.0;"
        		+PFCommandLine.SIZE+"=3;");
        pfm.initialize(cl);
//        System.out.println(cl.getScript());
        ModelControler mc = new ModelControler(pfm);
		cl.setCurentModelControler(mc);
		cc = new ComputationControler(mc.getTree());
        sg = pfm.getSpreadingGraph();
	}

	@Test
	public void test() throws Exception{
		pfm.addToFIFO(0, new IPv4Datagramme("coucou0"));
		
		pfm.addToFIFO(1, new IPv4Datagramme("coucou1"));	
		
		pfm.addToFIFO(2, new IPv4Datagramme("coucou2"));
//		double j = 0.1;
//		while(j<1){
//			System.out.println("j : "+j);
//			cc.compute(new BigDecimal(j));
//			Thread.sleep(100);
//			for (int i = 0; i < 7; i++) {
//				System.out.println(i+" : "+pfm.getNode(i).getBufferPacket());
//			}
//			j+=0.1;
//			System.out.println("--------------------------------------------------");
//		}
		
		
		cc.compute();
		Thread.sleep(100);
		for (int i = 0; i < 7; i++) {
			System.out.println(i+" : "+pfm.getNode(i).getBufferPacket());
		}
		
		cc.compute();
		Thread.sleep(100);
		for (int i = 0; i < 7; i++) {
			System.out.println(i+" : "+pfm.getNode(i).getBufferPacket());
		}
		
		cc.compute();
		Thread.sleep(100);
		for (int i = 0; i < 7; i++) {
			System.out.println(i+" : "+pfm.getNode(i).getBufferPacket());
		}
		
		
		
//		LinkedList<String> ll = new LinkedList<>();
//		ll.push("coucou1");
//		ll.push("coucou2");
//		for (int i = 0; i < ll.size(); i++) {
//			assertEquals(ll.get(i),((IPv4Datagramme)pfm.getNode(i).getBufferPacket().get(i)).toString());
//		}
		
	}


}
