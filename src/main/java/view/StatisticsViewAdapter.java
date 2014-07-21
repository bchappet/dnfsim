package main.java.view;

import main.java.controler.ParameterControler;

public class StatisticsViewAdapter extends ParamViewAdapter {

	public StatisticsViewAdapter(ParameterControler paramControler,
			ViewConfiguration vc, ViewFactory vf) {
		super(paramControler, vc, vf);
	}

	public StatisticsViewAdapter(ParameterControler paramControler,
			ViewFactory vf) {
		super(paramControler, vf);
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc, ViewFactory vf) {
		return new StatisticPanel(this.getParameterControler().getName(), vf);
	}

	@Override
	public void updateView() {
		// nothing

	}

}
