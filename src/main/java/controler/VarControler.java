package main.java.controler;

import main.java.maps.Var;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;
import main.java.view.SingleValueParamViewAdapter;

public class VarControler extends ParameterControler {

	public VarControler(Var param) {
		super(param);
	}

	@Override
	public String toString() {
		return ((Var)getParam()).getName();
	}

	public Object get() {
		return ((Var)getParam()).get();
	}

	public void set(Object d) {
		((Var)getParam()).set(d);
		
	}

	@Override
	protected ParamViewAdapter createParamViewAdapter(ParameterView view) {
		return new SingleValueParamViewAdapter(this,view);
	}
	
	

}
