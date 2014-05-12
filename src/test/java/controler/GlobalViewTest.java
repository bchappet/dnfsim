package test.java.controler;

import static org.junit.Assert.fail;

import java.awt.Dimension;

import main.java.controler.GlobalView;
import main.java.controler.Runner;
import main.java.gui.Printer;

import org.junit.Before;
import org.junit.Test;

public class GlobalViewTest {
	
	private GlobalView applet;

	@Before
	public void setUp() throws Exception {
		
		Printer printer = new Printer(2);
		Runner runner = new Runner(printer,"ESN",null,null,true);
		
		applet = runner.getGlobalView();
		applet.setVisible(true);
		
		
	}

	@Test
	public void testGlobalView() throws InterruptedException {
		Thread.sleep(1000);
	}

	public void testLoadModelView() {
		fail("Not yet implemented");
	}

}
