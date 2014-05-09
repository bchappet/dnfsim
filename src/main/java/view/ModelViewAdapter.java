package main.java.view;

import java.math.BigDecimal;

import main.java.maps.Parameter;

public class ModelViewAdapter extends ParamViewAdapter {

	public ModelViewAdapter(Parameter param, ParameterView paramView) {
		super(param, paramView);
	}

	public ModelViewAdapter(Parameter param) {
		super(param);
	}

	@Override
	protected ParameterView getDefaultView(Parameter param) {
		return new ModelView();
	}

	@Override
	public void updateView(BigDecimal time) {
		// TODO Auto-generated method stub

	}

}
