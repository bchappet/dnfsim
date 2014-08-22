package test.java.view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.maps.Var;
import main.java.view.AdaptiveAndEquilibratedColorMap;
import main.java.view.ColorMap;
import main.java.view.View2D;

import org.junit.Before;
import org.junit.Test;

public class View2DTest extends JFrame{

	private View2D uut;
	private JPanel borderPanel;
	
	@Before
	public void setUp() throws Exception {
		ColorMap cm = new AdaptiveAndEquilibratedColorMap(new Color[]{Color.BLUE,Color.WHITE,Color.RED});
		Var<Boolean> grid = new Var(Boolean.TRUE);
		uut = new View2D("uut",new double[][]{{-1,-2,-3},
										{10,5,1}},cm,grid);
		
			borderPanel = uut.getBorderPane();
			this.add(borderPanel);
			this.setSize(600, 600);
	}
	
	@Test
	public void testRender() throws InterruptedException {
		
		//borderPanel.setPreferredSize(new Dimension(300,300));
		
		this.setVisible(true);
		this.repaint();
		Thread.sleep(500);
	}

	@Test
	public void testUpdateDoubleArrayArray() throws InterruptedException {
		
		this.setVisible(true);
		this.repaint();
		Thread.sleep(500);
		uut.update(new double[][]{{-1,-2,-3},{-4,-5,-6}});
		this.repaint();
		Thread.sleep(500);
	}
	
	@Test
	public void testDoubleBigArrayArray() throws InterruptedException {
		
		this.setVisible(true);
		uut.update(new double[][]{{-1,-2,-3,4,3,2,1},
									{-4,-5,-6,-2,-3,4,3},
									{-4,-5,-6,-2,-3,4,3},
									{-4,-5,-6,-2,-3,4,3},
									{-4,-5,-6,-2,-3,4,3},
									{-4,-5,-6,-2,-3,4,3},
									{-1,-2,-3,4,3,2,1}
									});
		this.repaint();
		Thread.sleep(50000000);
	}

}
