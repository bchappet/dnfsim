package main.java.view;

import java.math.BigDecimal;

import main.java.maps.Parameter;

public class StatisticsViewAdapter extends ParamViewAdapter {

	public StatisticsViewAdapter(Parameter param, ParameterView paramView) {
		super(param, paramView);
		// TODO Auto-generated constructor stub
	}

	public StatisticsViewAdapter(Parameter param) {
		super(param);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ParameterView getDefaultView(Parameter param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateView(BigDecimal time) {
		// TODO Auto-generated method stub

	}

}
