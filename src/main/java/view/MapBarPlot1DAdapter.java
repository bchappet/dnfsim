package main.java.view;

import main.java.controler.MapControler;
import main.java.controler.ParameterControler;

public class MapBarPlot1DAdapter extends ParamViewAdapter {

	public MapBarPlot1DAdapter(ParameterControler paramControler,
			ViewConfiguration vc, ViewFactory vf) {
		super(paramControler, vc, vf);
	}

	public MapBarPlot1DAdapter(ParameterControler paramControler, ViewFactory vf) {
		super(paramControler, vf);
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc, ViewFactory vf) {
		
		return new BarPlot1D(getParameterControler().getName(),
				((MapControler)getParameterControler()).getArray1D(),
				((ColorMap)vc.get(ViewConfiguration.COLORMAP).get()).clone());
	}

	@Override
	protected void updateView() {
		
		((BarPlot1D)getParamView()).update(
				((MapControler)getParameterControler()).getArray1D());
	}

}
