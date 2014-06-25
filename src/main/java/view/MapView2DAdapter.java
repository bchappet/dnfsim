package main.java.view;
import main.java.controler.MapControler;
import main.java.controler.ParameterControler;

public class MapView2DAdapter extends ParamViewAdapter{

	public MapView2DAdapter(ParameterControler paramControler,
			ViewConfiguration vc) {
		super(paramControler, vc);
		System.out.println( "construct MapView2d " + paramControler + "@"+System.identityHashCode(paramControler));
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
