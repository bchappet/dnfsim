package main.java.view;
import main.java.controler.ParameterControler;
import main.java.network.view.GraphControler;

public class GraphView2DAdapter extends ParamViewAdapter{

	public GraphView2DAdapter(ParameterControler paramControler,ViewFactory vf) {
		super(paramControler,vf);
//		System.out.println( "construct GraphView2d " + paramControler + "@"+System.identityHashCode(paramControler));
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc,ViewFactory vf) {
		return new View2D(getParameterControler().getName(),
				((GraphControler)getParameterControler()).getArray(),
				((ColorMap)vc.get(ViewConfiguration.COLORMAP).get()).clone(),vc.get(ViewConfiguration.GRID));
	}

	@Override
	public void updateView() {
		((View2D)getParamView()).update(
				((GraphControler)getParameterControler()).getArray());
	}
	
	

	

}
