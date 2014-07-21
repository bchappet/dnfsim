package main.java.controler;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import main.java.maps.Var;

public class VarControler extends ParameterControler implements SingleValueControler,Observer{
	private final transient Logger LOGGER = Logger.getLogger(VarControler.class.getName());
	
	public VarControler(Var param) {
		super(param);
		param.addObserver(this);
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
	public void update(Observable o, Object arg) {
		if(this.getParamViewAdapter() != null){
			//TODO also delay to reduce display frequency
			LOGGER.info("******************update view : " + this.getName());
			this.getParamViewAdapter().updateViewAndRepaint();
			
		}
	}
	
	

}
