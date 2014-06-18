package test.java.view;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import main.java.view.*;

import javax.swing.JFrame;
import javax.swing.JPanel;



import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;

import org.junit.Before;
import org.junit.Test;

public class Plot2DTest extends JFrame{
	
	private Plot2D uut;
	private ComputationControler cc;
	@Before
	public void setUp() throws Exception {

		ModelESN model = new ModelESN("test_esn");
		ESNCommandLine cl = (ESNCommandLine) model.constructCommandLine();
		cl.setContext("");
		model.initialize(cl);
		ModelControler mc = new ModelControler(model);
		cl.setCurentModelControler(mc);
		 cc = new ComputationControler(mc.getTree());
	
		ViewFactory vf = new ViewFactory(new ViewConfiguration("src/test/scripts/gui/Plot2DTest.gui"),mc.getTree());
		
		uut = new Plot2D("uut",vf);
		this.setVisible(true);

		this.add(uut);
		this.setSize(300, 300);
//		this.pack();
	}

	@Test
	public void testRender() throws InterruptedException {
		cc.compute(new BigDecimal("0.1"));
		
		Thread.sleep(100);
		this.repaint();
		cc.compute(new BigDecimal("0.2"));
		Thread.sleep(100);
		this.repaint();
		cc.compute(new BigDecimal("0.3"));
		Thread.sleep(100);
		this.repaint();
		Thread.sleep(10000);
		
		assertTrue("visual test ",true);
	}

}
