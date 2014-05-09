package main.java.controler;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.view.ParamViewAdapter;
import main.java.view.SingleValueParamViewAdapter;

public class TrajectoryControler extends MapControler {

	public TrajectoryControler(Parameter param) {
		super((Map) param, new SingleValueParamViewAdapter(param));
	}
	
	
	
	

}
