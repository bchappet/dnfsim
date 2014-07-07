package test.java.controler;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import main.java.controler.Runner;
import main.java.view.GlobalView;

import org.junit.Before;
import org.junit.Test;

import main.java.controler.*;

public class GlobalViewTest {
	
	private GlobalView applet;

	@Before
	public void setUp() throws Exception {
		
		Printer printer = new Printer(2);
		Runner runner = new Runner(printer,"ESN",null,null,true,new ArrayList<Integer>());
		
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
