package test.java.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.view.AdaptiveAndEquilibratedColorMap;
import main.java.view.ColorMap;
import main.java.view.View1D;

import org.junit.Before;
import org.junit.Test;

public class View1DTest extends JFrame{

	private View1D uut;
	private JPanel borderPanel;
	
	@Before
	public void setUp() throws Exception {
		ColorMap cm = new AdaptiveAndEquilibratedColorMap(new Color[]{Color.BLUE,Color.WHITE,Color.RED});
		uut = new View1D("uut",new double[]{-1,-2,-3},cm);
		
			borderPanel = new JPanel();
			borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.PAGE_AXIS));
			borderPanel.setBorder(BorderFactory.createTitledBorder("uut"));
			borderPanel.add(uut);
			this.add(borderPanel);
			this.setSize(300, 200);
	}
	
	@Test
	public void testRender() throws InterruptedException {
		
		//borderPanel.setPreferredSize(new Dimension(300,300));
		
		this.setVisible(true);
		this.repaint();
		Thread.sleep(1000);
	}

	@Test
	public void testUpdateDoubleArrayArray() throws InterruptedException {
		
		this.setVisible(true);
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{-4,-5,-6});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{4,5,6});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{2,1,0});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{-4,-5,-6});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{4,5,6});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{2,1,0});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{-4,-5,-6});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{4,5,6});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{2,1,0});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{10,11,20});
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[]{-10,-11,-20});
		this.repaint();
		Thread.sleep(5000);
		
	}

}
