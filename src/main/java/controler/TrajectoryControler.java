package main.java.controler;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.space.Space;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;
import main.java.view.SingleValueParamViewAdapter;

public class TrajectoryControler extends MapControler implements SingleValueControler {

	public TrajectoryControler(Parameter param) {
		super((Map) param);
	}

	public Object get() {
		return this.getParam().getIndex(0);
	}
	
	public Space getValueSpace(){
		return (Space) ((Map) this.getParam()).getParam(0); //TODO return to prrotected
	}
	
	protected  ParamViewAdapter createParamViewAdapter(ParameterView view){
		return new SingleValueParamViewAdapter(this, view);
	}
	
	
	
	

}
