package main.java.view;
import main.java.controler.MapControler;
import main.java.controler.ParameterControler;

public class MapView2DAdapter extends ParamViewAdapter{

	public MapView2DAdapter(ParameterControler paramControler,ViewFactory vf) {
		super(paramControler,vf);
//		System.out.println( "construct MapView2d " + paramControler + "@"+System.identityHashCode(paramControler));
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc,ViewFactory vf) {
		return new View2D(getParameterControler().getName(),
				((MapControler)getParameterControler()).getArray(),vc.get(ViewConfiguration.COLORMAP),vc.get(ViewConfiguration.GRID));
	}

	@Override
	public void updateView() {
		((View2D)getParamView()).update(
				((MapControler)getParameterControler()).getArray());
	}
	
	

	

}
