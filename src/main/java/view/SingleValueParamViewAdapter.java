package main.java.view;

import java.math.BigDecimal;

import main.java.controler.ParameterControler;
import main.java.controler.SingleValueControler;
import main.java.controler.TrajectoryControler;
import main.java.gui.ColorMap;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.maps.Trajectory;
import main.java.space.Coord2D;
import main.java.space.DoubleSpace2D;



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
		SingleValueControler pc = (SingleValueControler) getParameterControler();

		
		if(this.getParamView() instanceof View2D ){
			throw new Error("Not implemented yet");
		}else if(this.getParamView() instanceof Curve2D){
			Coord2D<Double> coor = new Coord2D<Double>(time.doubleValue(),(Double)pc.get());
			((Curve2D)this.getParamView()).update(coor);
		}else if(this.getParamView() instanceof DisplaySampleMap2D){
			((DisplaySampleMap2D)this.getParamView()).update((Coord2D<Double>) pc.get());
				Coord2D<Double> coord = (Coord2D<Double>) pc.get();
		}else{
			throw new Error("Not implemented yet");
		}
		
	}
	@Override
	protected  ParameterView getDefaultView(ParameterControler param) {
		
		if(param instanceof TrajectoryControler){
			TrajectoryControler pc = (TrajectoryControler) param;
			if(pc.get() instanceof Coord2D){
			return new DisplaySampleMap2D(param.getName(),(Coord2D<Double>) pc.get(),((DoubleSpace2D) pc.getValueSpace()).getOrigin(),
					((DoubleSpace2D) pc.getValueSpace()).getLength());
			}else{
				return new Curve2D(param.getName());
			}
		}else{
			return new Curve2D(param.getName());
		}
	}
	
	

}
