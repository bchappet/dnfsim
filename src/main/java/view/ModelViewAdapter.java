package main.java.view;

import main.java.controler.ParameterControler;

public class ModelViewAdapter extends ParamViewAdapter {

	public ModelViewAdapter(ParameterControler paramControler,
			ViewConfiguration vc, ViewFactory vf) {
		super(paramControler, vc, vf);
		// TODO Auto-generated constructor stub
	}

	public ModelViewAdapter(ParameterControler paramControler, ViewFactory vf) {
		super(paramControler, vf);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc, ViewFactory vf) {
		return null;
	}

	@Override
	protected void updateView() {
		// TODO Auto-generated method stub

	}

}
