package test.java.view;

import java.awt.Dimension;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.controler.StatisticsControler;
import main.java.maps.Var;
import main.java.statistics.Statistics;
import main.java.view.StatisticView;

import org.junit.Before;
import org.junit.Test;

public class StatisticsViewTest extends JFrame{

	private StatisticView uut;
	private JPanel borderPanel;
	private StatisticsControler statsControler;

	@Before
	public void setUp() throws Exception {

		Var<Double> var1 = new Var<Double>("var1",10d);
		Var<Double> var2 = new Var<Double>("var2",20d);
		Statistics stats = new Statistics("uut", "CompTime", new Var<BigDecimal>(new BigDecimal("1")),var1,var2);
		statsControler = new StatisticsControler(stats);
		uut = new StatisticView(statsControler);
		statsControler.initView(uut);
		


	}

	@Test
	public void test() throws InterruptedException {
		//		borderPanel = uut.getBorderPane();
		this.add(uut);
		this.setSize(500, 500);
		this.setVisible(true);
		for(int i = 0 ; i < 10 ; i++){
			Thread.sleep(100);
			statsControler.compute(new BigDecimal(""+i));
			this.repaint();
		}
		Thread.sleep(100000);
	}

}
