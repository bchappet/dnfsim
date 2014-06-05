package test.java.controler;

import static org.junit.Assert.*;
import main.java.controler.Runner;
import main.java.gui.Printer;
import main.java.view.GlobalView;

import org.junit.Before;
import org.junit.Test;

public class RunnerTest {
	
	private GlobalView applet;
	private Runner uut;

	@Before
	public void setUp() throws Exception {
		
		Printer printer = new Printer(0);
		uut = new Runner(printer,"ESN",null,null,true);
		
		applet = uut.getGlobalView();
		applet.setVisible(true);
	}

	@Test
	public void test() throws InterruptedException {
		Thread th = new Thread(uut);
		th.start();
		Thread.sleep(10000000);
	}

}
