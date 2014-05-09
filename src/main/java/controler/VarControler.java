package main.java.controler;

import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.view.ParamViewAdapter;
import main.java.view.SingleValueParamViewAdapter;

public class VarControler extends ParameterControler {

	public VarControler(Var param) {
		super(param, new SingleValueParamViewAdapter(param));
	}

	@Override
	public String toString() {
		return ((Var)getParam()).getName();
	}
	
	

}
