package main.java.view;

import java.math.BigDecimal;
import java.util.logging.Logger;

import main.java.controler.ComputableControler;
import main.java.controler.ParameterControler;

public abstract class ParamViewAdapter {
	
	private ParameterView paramView;
	private ParameterControler paramControler;
	private final transient Logger LOGGER = Logger.getLogger(ParamViewAdapter.class.getName());

	

	/**
	 * Construct a main.java.view adpter which can handle several kind of main.java.view
	 * @paramControler paramControler
	 * @paramControler paramView
	 */
	public ParamViewAdapter(ParameterControler paramControler,ViewConfiguration vc) {
		if(paramControler == null){
			throw new IllegalArgumentException("the ParameterControler whas null ");
		}
		LOGGER.info("Construct ParamViewAdapter : " + this.getClass());
		this.paramControler = paramControler;
		this.paramView = this.constructView(vc);
		this.paramControler.setParamViewAdapter(this);
	}
	
	
	protected abstract ParameterView constructView(ViewConfiguration vc);


	/**
	 * Update the main.java.view with parameter value
	 * @paramControler time
	 */
	public abstract void updateView();
		
	
	protected ParameterControler getParameterControler(){
		return this.paramControler;
	}
	
	/**
	 * @return the paramView
	 */
	public ParameterView getParamView() {
		return paramView;
	}


	


	

	

	
	

}
