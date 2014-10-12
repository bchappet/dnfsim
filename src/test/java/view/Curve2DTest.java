package test.java.view;

import static org.junit.Assert.assertTrue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.view.Curve2D;

import org.junit.Before;
import org.junit.Test;

public class Curve2DTest  extends JFrame {

	private Curve2D uut;
	private JPanel borderPanel;

	@Before
	public void setUp() throws Exception {
		uut = new Curve2D("uut");
		borderPanel = uut.getBorderPane();
		this.add(borderPanel);
		this.setSize(300, 200);
	}

	@Test
	public void testUpdateCoord2DOfDouble() throws InterruptedException {

		
		this.setVisible(true);
        this.repaint();

		uut.update(0.1);
		uut.update(0.2);
        uut.update(0.3);
        uut.update(0.4);
        uut.update(0.45);

		this.repaint();
		Thread.sleep(10000);
		assertTrue("visual test ",true);
	}

}
