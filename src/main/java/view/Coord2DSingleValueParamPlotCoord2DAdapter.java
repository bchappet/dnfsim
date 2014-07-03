package main.java.view;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.List;

import main.java.controler.ParameterControler;
import main.java.controler.SingleValueControler;
import main.java.space.Coord2D;

public class Coord2DSingleValueParamPlotCoord2DAdapter extends ParamViewAdapter {

	public Coord2DSingleValueParamPlotCoord2DAdapter(ParameterControler paramControler,
			ViewFactory vf) {
		super(paramControler,vf);
	}
	@Override
	protected ParameterView constructView(ViewConfiguration vc,ViewFactory vf) {
		return new PlotCoord2D(getParameterControler().getName());
	}
	@Override
	public void updateView() {
		PlotCoord2D pv = (PlotCoord2D) this.getParamView();
		SingleValueControler paramC = (SingleValueControler)this.getParameterControler();
		pv.update(((Coord2D) paramC.get()));
	}
	
	
	

}
