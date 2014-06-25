package test.java.view;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.math.BigDecimal;

import javax.swing.JPanel;

import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.model.Model;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;
import main.java.view.*;
import main.java.view.ViewConfiguration;
import main.java.view.ViewFactory;

import org.junit.Before;
import org.junit.Test;

public class GlobalViewTest extends JPanel{
	
	Model model;
	ModelControler modelControler;
	
	private GlobalView uut;
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
	
		ViewFactory vf = new ViewFactory(new ViewConfiguration("src/test/scripts/gui/ModelPanelTest.gui"),mc.getTree());
		Dimension dim = new Dimension(800, 600);
		uut = new GlobalView("uut",vf,dim);
		this.setVisible(true);

		this.add(uut);
		this.setSize(dim);
	}

	@Test
	public void test() {
cc.compute(new BigDecimal("0.1"));
		
		Thread.sleep(100);
		this.repaint();
		cc.compute(new BigDecimal("0.2"));
		Thread.sleep(100);
		this.repaint();
		cc.compute(new BigDecimal("0.3"));
		Thread.sleep(100);
		this.repaint();
		Thread.sleep(100000);
		
		assertTrue("visual test ",true);
	}

}
