package test.java.view;

import static org.junit.Assert.fail;

import javax.swing.JFrame;

import main.java.controler.VarControler;
import main.java.maps.Var;
import main.java.view.ParameterModifierPanel;

import org.junit.Before;
import org.junit.Test;

public class ParameterModifierPanelTest extends JFrame {
	private ParameterModifierPanel uut;
	private Var var;
	@Before
	public void setUp() throws Exception {
		
	 var = new Var<Double>("test",0.1);
		VarControler vc = new VarControler(var);
		uut = new ParameterModifierPanel(vc);
		this.add(uut);
		this.setSize(200, 200);
		this.setVisible(true);
	}

	@Test
	public void testParameterModifierPanel() throws InterruptedException {
		
		Thread.sleep(1000);
	}

	@Test
	public void testUpdate() throws InterruptedException {
		Thread.sleep(1000);
		var.set(10d);
		uut.update();
		Thread.sleep(1000);
	}

}
