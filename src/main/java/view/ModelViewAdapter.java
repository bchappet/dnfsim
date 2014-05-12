package main.java.view;

import java.math.BigDecimal;

import javax.swing.JPanel;

import main.java.controler.ModelControler;
import main.java.controler.ParameterControler;

public class ModelViewAdapter extends ParamViewAdapter {

	/**
	 * Here paramView is mandatory
	 * @param param
	 * @param paramView
	 */
	public ModelViewAdapter(ModelControler param, ModelView paramView) {
		super(param, paramView);
	}


	@Override
	public void updateView(BigDecimal time) {
		//((JPanel) this.getParamView()).repaint();

	}


	@Override
	protected ParameterView getDefaultView(ParameterControler param) {
		throw new Error("Should be constructed with paramView != null");
	}

}
