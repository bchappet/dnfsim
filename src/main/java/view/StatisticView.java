package main.java.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.EventObject;

import javax.swing.JPanel;

import main.java.controler.StatisticsControler;
import main.java.plot.Curve2D;
import main.java.plot.Plotter;
import main.java.statistics.Statistics;

/**
 *  TODO make it more MVC complient:
 *  we should be able to print any kind of statistic (NoDim), 1D...
 *  define as well an export file formater
 *  
 *  One idea could be to have a special kind of map: statMap
 *  which save every previous state (to get rid of wtrace) and then
 *  several a StatMapViewAdapter etc..
 * @author benoit
 *
 */
public class StatisticView extends ParameterViewPanel {
	
	protected StatisticsControler statsControler;
	protected String startY;
	protected Plotter plotter;
	protected int border = 10;

	public StatisticView(StatisticsControler stat) {
		super(stat.getName());
		this.statsControler = stat;
		this.startY = stat.getDefaultDisplayedStat(); //TODO not very MVC
		Curve2D curve = new Curve2D(statsControler.getWtrace(),statsControler.getIndex(Statistics.TIME),statsControler.getIndex(startY));////TODO not very MVC
		this.plotter = new Plotter(curve);
		this.setLayout(new BorderLayout());
		
		this.add(curve.getControls(),BorderLayout.NORTH);
		this.add(plotter,BorderLayout.CENTER);
		//this.plotter.setPreferredSize(new Dimension(dim.width/4,(dim.height/18*8)));
		// Statistics panel
//		setLayout(new BorderLayout());
//		setBorder(BorderFactory.createTitledBorder("Statistics"));
	}
	
	public Plotter getPlotter(){
		return this.plotter;//TODO find another way to change its size...
	}

	

	

	
	

}
