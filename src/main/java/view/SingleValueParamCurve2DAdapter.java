package main.java.view;

import main.java.controler.ParameterControler;
import main.java.maps.SingleValueParam;

public class SingleValueParamCurve2DAdapter extends ParamViewAdapter {

	public SingleValueParamCurve2DAdapter(ParameterControler paramControler,
			ViewConfiguration vc) {
		super(paramControler, vc);
	}

	@Override
	protected ParameterView constructView(ParameterControler paramControler,
			ViewConfiguration vc) {
		return new Curve2D(paramControler.getName());
	}

	@Override
	public void updateView() {
		Curve2D pv = (Curve2D) this.getParamView();
		SingleValueParam<? extends Number> param = (SingleValueParam<? extends Number> )this.getParameterControler().getParam();
		pv.update(param.get().doubleValue());

	}

}
