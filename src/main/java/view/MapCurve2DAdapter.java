package main.java.view;


import main.java.controler.MapControler;
import main.java.controler.ParameterControler;
import main.java.controler.SingleValueControler;

public class MapCurve2DAdapter extends ParamViewAdapter {

	
	public MapCurve2DAdapter(ParameterControler paramControler,
			ViewFactory vf) {
		super(paramControler,vf);
	}
	@Override
	protected ParameterView constructView(ViewConfiguration vc,ViewFactory vf) {
		return new Curve2D(getParameterControler().getName());
	}
	@Override
	public void updateView() {
		Curve2D pv = (Curve2D) this.getParamView();
		MapControler paramC = (MapControler)this.getParameterControler();
		pv.update(((Number) paramC.get(0)).doubleValue());
	}

}
