package test.java.view;

import static org.junit.Assert.fail;

import javax.swing.JFrame;

import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;
import main.java.view.StatisticPanel;
import main.java.view.CanvaPanel;
import main.java.view.ViewConfiguration;
import main.java.view.ViewFactory;

import org.junit.Before;
import org.junit.Test;

public class CanvaPanelTest extends JFrame {
	
	private CanvaPanel uut;
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
		
		uut = new CanvaPanel("uut",vf);
		this.setVisible(true);

		this.add(uut);
		this.setSize(300, 300);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
