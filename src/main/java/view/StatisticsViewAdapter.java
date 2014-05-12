package main.java.view;

import java.math.BigDecimal;

import main.java.controler.ParameterControler;
import main.java.controler.StatisticsControler;
import main.java.maps.Parameter;

public class StatisticsViewAdapter extends ParamViewAdapter {

	public StatisticsViewAdapter(ParameterControler param, ParameterView paramView) {
		super(param, paramView);
	}


	@Override
	protected  ParameterView getDefaultView(ParameterControler param) {
		return new StatisticView((StatisticsControler) this.getParameter());
	}

	@Override
	public void updateView(BigDecimal time) {
		// TODO make it more MVC complient

	}

}
