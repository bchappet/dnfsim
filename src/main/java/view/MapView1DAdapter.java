package main.java.view;

import main.java.controler.MapControler;
import main.java.controler.ParameterControler;
import main.java.view.ColorMap;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;
import main.java.view.View1D;
import main.java.view.View2D;
import main.java.view.ViewConfiguration;
import main.java.view.ViewFactory;

public class MapView1DAdapter extends ParamViewAdapter {

	public MapView1DAdapter(ParameterControler paramControler,
			ViewConfiguration vc, ViewFactory vf) {
		super(paramControler, vc, vf);
	}

	public MapView1DAdapter(ParameterControler paramControler, ViewFactory vf) {
		super(paramControler, vf);
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc, ViewFactory vf) {
		
		return new View1D(getParameterControler().getName(),
				((MapControler)getParameterControler()).getArray1D(),
				((ColorMap)vc.get(ViewConfiguration.COLORMAP).get()).clone(),vc.get(ViewConfiguration.GRID));
	}

	@Override
	protected void updateView() {
		
		((View1D)getParamView()).update(
				((MapControler)getParameterControler()).getArray1D());
	}

}
