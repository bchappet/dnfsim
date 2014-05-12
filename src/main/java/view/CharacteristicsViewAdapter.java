package main.java.view;

import java.math.BigDecimal;

import main.java.controler.ParameterControler;
import main.java.maps.Parameter;

public class CharacteristicsViewAdapter extends ParamViewAdapter {

	public CharacteristicsViewAdapter(ParameterControler param, ParameterView paramView) {
		super(param, paramView);
	}


	@Override
	public void updateView(BigDecimal time) {
		// TODO Auto-generated method stub

	}


	@Override
	protected ParameterView getDefaultView(ParameterControler param) {
		// TODO Auto-generated method stub
		return null;
	}

}
