package main.java.view;

import java.math.BigDecimal;

import main.java.controler.MapControler;
import main.java.controler.ParameterControler;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.space.Coord2D;
import main.java.space.Space;
import main.java.space.Space2D;
import main.resources.utils.ArrayUtils;



/**
 * Adapt the parameter to the main.java.view, whichever it is
 * Well, only compatible with View2D for now
 * @author benoit
 *
 */
public class MapViewAdapter extends ParamViewAdapter {
	

	public MapViewAdapter(ParameterControler param,ParameterView paramView) {
		super(param,paramView);
	}
	
	
	@Override
	public void updateView(BigDecimal time){
		
		MapControler mapControler = (MapControler) getParameter();
		
		if(this.getParamView() instanceof View2D ){
			((View2D)this.getParamView()).update(getValuesForView2D(mapControler));
		}else if(this.getParamView() instanceof View1D ){
			//if the matrix is n * 1 or 1*m we can put it in a 1D main.java.view
			double[] values = ArrayUtils.toPrimitiveArray1D(mapControler.getValues());
			((View1D)this.getParamView()).update(values);
			
			
		}else{
			throw new Error("Not implemented yet");
		}
		
	}
	
	/**
	 * To avoid code copy
	 * @param map
	 * @return
	 */
	private static double[][] getValuesForView2D(MapControler mapC){
		Coord2D<Integer> dim = get2DDimFromSpace(mapC.getSpace());
		double[][] values = ArrayUtils.toPrimitiveArray(mapC.getValues(),dim.x,dim.y);
		return values;
	}
	
	@Override
	protected ParameterView getDefaultView(ParameterControler pc){
		return new View2D(pc.getName(),getValuesForView2D((MapControler)pc));
	}

	/**
	 * Return the best dimension to fit the main.java.space in a 2D array even if it is one dimensional
	 * @param main.java.space
	 * @return
	 */
	private static Coord2D<Integer> get2DDimFromSpace(Space space) {
		//TODO finish
		
		return new Coord2D<Integer>((Integer)space.getDimensions()[Space2D.X].get(),(Integer) space.getDimensions()[Space2D.Y].get());
	}
	
	

}
