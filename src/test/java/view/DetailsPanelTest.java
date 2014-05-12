package test.java.view;

import javax.swing.JFrame;

import main.java.controler.MapControler;
import main.java.controler.ParameterControler;
import main.java.controler.VarControler;
import main.java.maps.InfiniteDt;
import main.java.maps.MatrixDouble2D;
import main.java.view.DetailsPanel;
import main.java.view.MapViewAdapter;
import main.java.view.SingleValueParamViewAdapter;

import org.junit.Before;
import org.junit.Test;

public class DetailsPanelTest extends JFrame{
	
	private DetailsPanel uut;

	@Before
	public void setUp() throws Exception {
		
		uut = new DetailsPanel();
		
	}

	@Test
	public void testWithoutParam() throws InterruptedException {
		this.add(uut);
		this.setSize(300, 200);
		this.setVisible(true);
		Thread.sleep(1000);
	}
	
	
	@Test
	public void testWithParamVar() throws InterruptedException {
		ParameterControler pc = new VarControler(new InfiniteDt());
		pc.setParamViewAdapter(new SingleValueParamViewAdapter(pc, null));
		uut.setDisplayedParam("displayedParam:", pc);
		
		this.add(uut);
		this.setSize(300, 700);
		this.setVisible(true);
		Thread.sleep(1000);
	}
	
	@Test
	public void testWithParamMap() throws InterruptedException {
		MatrixDouble2D map = new MatrixDouble2D("map", new InfiniteDt(), new double[][]{{0, 1,2},{3,4,5},{6,7,8}});
		ParameterControler pc = new MapControler(map);
		pc.setParamViewAdapter(new MapViewAdapter(pc, null));
		uut.setDisplayedParam("displayedParam:", pc);
		
		this.add(uut);
		this.setSize(300, 700);
		this.setVisible(true);
		Thread.sleep(1000);
	}

}
