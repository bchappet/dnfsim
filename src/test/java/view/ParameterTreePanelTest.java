package test.java.view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;

import main.java.controler.ModelControler;
import main.java.model.Model;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;
import main.java.view.DetailsPanel;
import main.java.view.ModelView;
import main.java.view.ParameterTreePanel;

import org.junit.Before;
import org.junit.Test;

public class ParameterTreePanelTest extends JFrame{
	
	Model model;
	ModelControler modelControler;
	private ParameterTreePanel uut ;
	private JPanel borderPanel;
	
	

	@Before
	public void setUp() throws Exception {
		ESNCommandLine cl = new ESNCommandLine("");
		model = new ModelESN("test_esn");
		model.initialize(cl);
		this.modelControler = new ModelControler(model,cl);
		cl.setCurentModelControler(modelControler);
	}
		

	@Test
	public void testModelView() throws InterruptedException {
		uut = new ParameterTreePanel(new JTree( modelControler.getTree()),new DetailsPanel());
		this.add(uut);
		this.setSize(1000, 600);
		this.setVisible(true);
		Thread.sleep(10000);
	}

}
