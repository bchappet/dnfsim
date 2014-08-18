package test.java.view;

import static org.junit.Assert.*;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.maps.Var;
import main.java.view.AdaptiveAndEquilibratedColorMap;
import main.java.view.BarPlot1D;
import main.java.view.ColorMap;
import main.java.view.View1D;

import org.junit.Before;
import org.junit.Test;

public class BarPlot1DTest extends JFrame {
	
	private BarPlot1D uut;
	private JPanel borderPanel;

	@Before
	public void setUp() throws Exception {
		ColorMap cm = new AdaptiveAndEquilibratedColorMap(new Color[]{Color.BLUE,Color.WHITE,Color.RED});
		uut = new BarPlot1D("uut",new double[]{3,4,-6},cm);
		
			borderPanel = new JPanel();
			borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.PAGE_AXIS));
			borderPanel.setBorder(BorderFactory.createTitledBorder("uut"));
			borderPanel.add(uut);
			this.add(borderPanel);
			this.setSize(300, 200);
	}

	@Test
	public void testDoubleArray() throws InterruptedException {
		this.setVisible(true);
		this.repaint();
		Thread.sleep(5000);
		
	}

}
