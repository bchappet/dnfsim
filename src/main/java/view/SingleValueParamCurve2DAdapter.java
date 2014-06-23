package main.java.view;

import main.java.controler.ParameterControler;
import main.java.controler.SingleValueControler;

public class SingleValueParamCurve2DAdapter extends ParamViewAdapter {

	public SingleValueParamCurve2DAdapter(ParameterControler paramControler,
			ViewConfiguration vc) {
		super(paramControler, vc);
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc) {
		return new Curve2D(getParameterControler().getName());
	}

	@Override
	public void updateView() {
		Curve2D pv = (Curve2D) this.getParamView();
		SingleValueControler paramC = (SingleValueControler)this.getParameterControler();
		pv.update(((Number) paramC.get()).doubleValue());
	}

}
