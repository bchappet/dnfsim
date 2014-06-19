package main.java.view;
import java.util.List;

import main.java.controler.MapControler;
import main.java.controler.ParameterControler;
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
		return new View2D(getParameterControler().getName(),getArray((MapControler) getParameterControler()),vc.getColorMap());
	}

	@Override
	public void updateView() {
		((View2D)getParamView()).update(getArray((MapControler) getParameterControler()));
	}
	/**
	 * 
	 * @param paramControler
	 * @return
	 */
	protected Number[][] getArray(MapControler  paramControler){
		MapControler pc = (MapControler) paramControler;
		Parameter param = pc.getParam();
		Number[][] ret;
		if(param instanceof Array2D){
			ret =  ((Array2D<? extends Number>)param).get2DArray();
		}else{
			List<? extends Number> list = pc.getValues(); 
			Coord<SingleValueParam<Integer>> dim =  pc.getSpace().getDimensions();
			ret = ArrayUtils.toPrimitiveArray(list, dim.getIndex(0).get(), dim.getIndex(1).get());
		}
		
		return ret;
	}

	

}
