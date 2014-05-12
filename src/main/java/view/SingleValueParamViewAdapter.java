package main.java.view;

import java.math.BigDecimal;

import main.java.controler.ParameterControler;
import main.java.gui.ColorMap;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.space.Coord2D;



/**
 * Adapt the parameter to the main.java.view, whichever it is
 * Well, only compatible with Curve2D for now
 * @author benoit
 *
 */
public class SingleValueParamViewAdapter extends ParamViewAdapter {
	
	/**
	 * 
	 * @param param
	 * @param paramView (optional) 
	 */
	public SingleValueParamViewAdapter(ParameterControler param,ParameterView paramView) {
		super(param,paramView);
	}
	
	
	public void updateView(BigDecimal time){
		
		SingleValueParam svp = (SingleValueParam) getParameter();
		
		if(this.getParamView() instanceof View2D ){
			throw new Error("Not implemented yet");
		}else if(this.getParamView() instanceof Curve2D){
			Coord2D<Double> coor = new Coord2D<Double>(time.doubleValue(),(Double) svp.get());
			((Curve2D)this.getParamView()).update(coor);
		}else{
			throw new Error("Not implemented yet");
		}
		
	}
	@Override
	protected  ParameterView getDefaultView(ParameterControler param) {
		return new Curve2D(param.getName());
	}
	
	

}
