package test.java.view;

import static org.junit.Assert.assertTrue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.math.BigDecimal;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;
import main.java.view.DetailsPanel;
import main.java.view.ParameterTreePanel;
import main.java.view.ViewConfiguration;
import main.java.view.ViewFactory;

import org.junit.Before;
import org.junit.Test;

public class DetailsPanelTest extends JFrame{
	
	private DetailsPanel uut;
	private ParameterTreePanel parameterTreePanel;
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

		ViewFactory vf = new ViewFactory(new ViewConfiguration("src/test/scripts/gui/CanvaPanelTest.gui"),mc.getTree());

		uut = new DetailsPanel("uut",vf);
		uut.setPreferredSize(new Dimension(150,300));
		parameterTreePanel = new ParameterTreePanel("uut",vf,uut);
		this.setVisible(true);
		
		JPanel jpane = new JPanel();
		BorderLayout bl = new BorderLayout();
		jpane.setLayout(bl);
		jpane.add(parameterTreePanel,BorderLayout.LINE_START);
		jpane.add(uut,BorderLayout.LINE_END);
		
		getContentPane().add(jpane);
		//jpane.setPreferredSize(new Dimension(300, 300));
		this.setPreferredSize(new Dimension(300, 300));
		this.pack();
	}


	@Test
	public void testModelView() throws InterruptedException {
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
