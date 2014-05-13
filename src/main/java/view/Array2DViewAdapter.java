package main.java.view;

import java.math.BigDecimal;

import main.java.controler.ParameterControler;
import main.java.gui.ColorMap;
import main.java.maps.Array2D;
import main.java.maps.Parameter;
import main.resources.utils.ArrayUtils;



/**
 * Adapt the parameter to the main.java.view, whichever it is
 * Well, only compatible with View2D for now
 * @author benoit
 *
 */
public class Array2DViewAdapter extends ParamViewAdapter {
	

	public Array2DViewAdapter(ParameterControler param,ParameterView paramView) {
		super(param,paramView);
	}
	
	
	@Override
	public void updateView(BigDecimal time){
		
		Array2D<Double> array = (Array2D<Double>) getParameterControler();
		
		if(this.getParamView() instanceof View2D ){
			double[][] values = ArrayUtils.toPrimitiveArray(array.get2DArray());
			((View2D)this.getParamView()).update(values);
		}else if(this.getParamView() instanceof View1D ){
			//if the matrix is n * 1 or 1*m we can put it in a 1D main.java.view
			double[] values = ArrayUtils.toPrimitiveArray1D(array.get2DArray());
			((View1D)this.getParamView()).update(values);
			
			
		}else{
			throw new Error("Not implemented yet");
		}
		
	}

	@Override
	protected  ParameterView getDefaultView(ParameterControler param) {
		return new View2D(ArrayUtils.toPrimitiveArray(((Array2D<Number>)param).get2DArray()));
	}


	
	

}
