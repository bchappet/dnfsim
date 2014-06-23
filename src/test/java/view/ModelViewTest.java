package test.java.view;

import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.controler.ComputationControler;
import main.java.controler.MapControler;
import main.java.controler.ModelControler;
import main.java.controler.StatisticsControler;
import main.java.model.Model;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;
import main.java.statistics.Statistics;
import main.java.view.CanvaPanel;
import main.java.view.ModelView;
import main.java.view.ParameterView;
import main.java.view.StatisticPanel;
import main.java.view.ViewConfiguration;
import main.java.view.ViewFactory;

import org.junit.Before;
import org.junit.Test;

public class ModelViewTest extends JFrame{
	
	Model model;
	ModelControler modelControler;
	private JPanel borderPanel;
	
	private ModelView uut;
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
		
		uut = new ModelView("uut",vf);
		this.setVisible(true);

		this.add(uut);
		this.setSize(300, 300);
	}
	
	@Test
	public void test() throws InterruptedException {
		cc.compute(new BigDecimal("0.1"));
		
		Thread.sleep(100);
		this.repaint();
		cc.compute(new BigDecimal("0.2"));
		Thread.sleep(100);
		this.repaint();
		cc.compute(new BigDecimal("0.3"));
		Thread.sleep(100);
		this.repaint();
		Thread.sleep(1000);
		
		assertTrue("visual test ",true);
	}
		

//	@Test
//	public void testModelViewVoid() throws InterruptedException {
//		Dimension dim = new Dimension(1000, 600);
//		uut = new ModelView(dim,"uut", modelControler.getTree());
//		this.add(uut);
//		this.setSize(dim);
//		
//		this.setVisible(true);
//		Thread.sleep(1000);
//	}

//	@Test
//	public void testModelViewStat() throws InterruptedException {
//		Dimension dim = new Dimension(1000, 600);
//		uut = new ModelView(dim,"uut", modelControler.getTree());
//		
//		StatisticsControler statsControler = (StatisticsControler) modelControler.getControler(Statistics.NAME);
//		StatisticPanel statView = new StatisticPanel(statsControler);
//		statsControler.initView(statView);
//		
//		uut.addStatisticsView(statView);
//		
//		
//		this.add(uut);
//		this.setSize(dim);
//		
//		this.setVisible(true);
//		Thread.sleep(1000);
//	}
//	
//	@Test
//	public void testModelViewStatnCanvas() throws InterruptedException {
//		Dimension dim = new Dimension(1000, 600);
//		uut = new ModelView(dim,"uut", modelControler.getTree());
//		
//		StatisticsControler statsControler = new StatisticsControler(model.getStatistics());
//		StatisticPanel statView = new StatisticPanel(statsControler);
//		statsControler.initView(statView);
//		
//		uut.addStatisticsView(statView);
//		
//		MapControler mapC = (MapControler) modelControler.getControler(ModelESN.WEIGHTS_RR);
//		mapC.initView(null);
//		ParameterView mapView = mapC.getParamView();
//		uut.addView( mapView);
//		
//		this.add(uut);
//		this.setSize(dim);
//		
//		this.setVisible(true);
//		Thread.sleep(1000);
//	}

}