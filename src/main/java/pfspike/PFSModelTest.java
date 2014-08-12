package main.java.pfspike;

import main.java.console.CommandLine;
import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.network.generic.packet.IPv4Datagramme;
import main.java.network.probalisticFlooding.PFCommandLine;

import org.junit.Before;
import org.junit.Test;

public class PFSModelTest {

	private PFSModel pfsm;
	private CommandLine cl;
	private ComputationControler cc;

	@Before
	public void setUp() throws Exception {
		pfsm = new PFSModel("testPF");
		cl = pfsm.constructCommandLine();
		cl.setContext(""
				+ "stimulis_file=statistiques/stimulis/a_send.stimulis;"
				+ "weigth=0.0;"
				+ "size=9;"
				+ "mapToSave=ReceiveMap;"
				//+ "pathToSave=statistisques/data/b_send/size9/time1/weigth0.0/;"
				+ "transition_matrix_file=PFTransitionMatrixFile9;"
				+ "write_transition_matrix_file=False;"
				+ " core=2");
		//        System.out.println(cl.getScript());
		ModelControler mc = new ModelControler(pfsm);
		cl.setCurentModelControler(mc);
		cc = new ComputationControler(mc.getTree());

	}

	@Test
	public void test() throws Exception{
		cc.compute();
		Thread.sleep(100);
	}
}
