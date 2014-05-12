package test.java.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.controler.MapControler;
import main.java.controler.ModelControler;
import main.java.controler.StatisticsControler;
import main.java.model.Model;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;
import main.java.statistics.Statistics;
import main.java.view.ModelView;
import main.java.view.ParameterView;
import main.java.view.StatisticView;

import org.junit.Before;
import org.junit.Test;

public class ModelViewTest extends JFrame{
	
	Model model;
	ModelControler modelControler;
	ModelView uut ;
	private JPanel borderPanel;
	
	

	@Before
	public void setUp() throws Exception {
		ESNCommandLine cl = new ESNCommandLine("");
		model = new ModelESN("test_esn");
		model.initialize(cl);
		this.modelControler = new ModelControler(model);
		cl.setCurentModelControler(modelControler);
	}
		

	@Test
	public void testModelViewVoid() throws InterruptedException {
		uut = new ModelView("uut", modelControler.getTree());
		this.add(uut);
		this.setSize(1000, 600);
		
		this.setVisible(true);
		Thread.sleep(1000);
	}

	@Test
	public void testModelViewStat() throws InterruptedException {
		uut = new ModelView("uut", modelControler.getTree());
		
		StatisticsControler statsControler = (StatisticsControler) modelControler.getControler(Statistics.NAME);
		StatisticView statView = new StatisticView(statsControler);
		statsControler.initView(statView);
		
		uut.addStatisticsView(statView);
		
		
		this.add(uut);
		this.setSize(1000, 600);
		
		this.setVisible(true);
		Thread.sleep(1000);
	}
	
	@Test
	public void testModelViewStatnCanvas() throws InterruptedException {
		uut = new ModelView("uut", modelControler.getTree());
		
		StatisticsControler statsControler = new StatisticsControler(model.getStatistics());
		StatisticView statView = new StatisticView(statsControler);
		statsControler.initView(statView);
		
		uut.addStatisticsView(statView);
		
		MapControler mapC = (MapControler) modelControler.getControler(ModelESN.WEIGHTS_RR);
		mapC.initView(null);
		ParameterView mapView = mapC.getParamView();
		uut.addView( mapView);
		
		this.add(uut);
		this.setSize(1000, 600);
		
		this.setVisible(true);
		Thread.sleep(1000);
	}

}
