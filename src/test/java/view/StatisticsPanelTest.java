package test.java.view;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.swing.JFrame;

import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;
import main.java.view.StatisticPanel;
import main.java.view.ViewConfiguration;
import main.java.view.ViewFactory;

import org.junit.Before;
import org.junit.Test;

public class StatisticsPanelTest extends JFrame{

	private StatisticPanel uut;
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
	
		ViewFactory vf = new ViewFactory(new ViewConfiguration("src/test/scripts/gui/StatisticsPanelTest.gui"),mc.getTree());
		
		uut = new StatisticPanel("uut",vf);
		this.setVisible(true);

		this.add(uut);
		this.setSize(300, 300);
		


	}

	@Test
	public void test() throws InterruptedException {
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
