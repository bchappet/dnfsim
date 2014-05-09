package test.java.viewTests;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.space.Coord2D;
import main.java.view.Curve2D;

import org.junit.Before;
import org.junit.Test;

public class Curve2DTest  extends JFrame {

	private Curve2D uut;
	private JPanel borderPanel;

	@Before
	public void setUp() throws Exception {
		uut = new Curve2D();

		borderPanel = new JPanel();
		borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.PAGE_AXIS));
		borderPanel.setBorder(BorderFactory.createTitledBorder("uut"));
		borderPanel.add(uut);
		this.add(borderPanel);
		this.setSize(300, 200);
	}

	@Test
	public void testUpdateCoord2DOfDouble() throws InterruptedException {

		
		this.setVisible(true);
		this.repaint();
		Thread.sleep(100);
		uut.update(new Coord2D<Double>(0.1,0.1));
		this.repaint();
		Thread.sleep(100);
		uut.update(new Coord2D<Double>(0.2,0.2));
		this.repaint();
		Thread.sleep(100);
		uut.update(new Coord2D<Double>(0.5,0.2));
		this.repaint();
		Thread.sleep(1000);
	}

}
