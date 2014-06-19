package main.java.view;
import java.util.List;

import main.java.controler.MapControler;
import main.java.controler.ParameterControler;
import main.java.maps.Array2DDouble;
import main.java.maps.Parameter;
import main.java.maps.Array2D;
import main.java.maps.SingleValueParam;
import main.java.space.Coord;
import main.resources.utils.ArrayUtils;

public class MapView2DAdapter extends ParamViewAdapter{

	public MapView2DAdapter(ParameterControler paramControler,
			ViewConfiguration vc) {
		super(paramControler, vc);
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc) {
		return new View2D(getParameterControler().getName(),
				((MapControler)getParameterControler()).getArray(),vc.getColorMap());
	}

	@Override
	public void updateView() {
		((View2D)getParamView()).update(
				((MapControler)getParameterControler()).getArray());
	}
	
	

	

}
