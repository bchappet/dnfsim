package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gui.Printer;
import gui.Runner;
import gui.RunnerGUI;

import java.awt.Dimension;
import java.net.URL;

import model.Model;
import model.ModelCNFT;
import model.ModelNSpike;
import model.Root;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import applet.AppletStub;

import console.CommandLineFormatException;

public class NSpikeOptimisationTest {

	private Model nspike;
	private URL contextPath;

	@Before
	public void setUp() throws Exception {

		nspike = new ModelNSpike("NSpike_test");
		contextPath = new URL("file:./src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+nspike.getName()+".dnfs"));
		nspike.initialize(contextScript);
	}

	@After
	public void tearDown() throws Exception {


	}

	@Test
	public void testComputationTime() throws CommandLineFormatException {

		long t1 = System.currentTimeMillis();

		Printer printer = new Printer(0);
		String scenario = "wait=3;print=all;";
		Runner runner = new Runner(nspike, scenario, printer);
		nspike.getCommandLine().setRunner(runner);
		
//		Root root = new Root();
//		root.addModel(nspike);
//		root.setActiveModel(nspike);
//
//		RunnerGUI applet =  new RunnerGUI(runner,root,contextPath,new Dimension(500,500-50));
//		// Configure the frame to display the Applet
//		applet.setStub(new AppletStub(applet, "CNFT simulation"));
//		//			Thread guiTh = new Thread(applet);
//		//			guiTh.start();
//		runner.setLock(applet.getLock());


		Thread th = new Thread(runner);
		th.start();

		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long t2 = System.currentTimeMillis();

		long diff = t2 - t1;
		System.out.println("diff : " + diff); //4560
		assertTrue(diff < 5000);

	}

}
