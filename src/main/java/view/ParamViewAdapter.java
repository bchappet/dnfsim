package main.java.view;

import java.math.BigDecimal;

import main.java.controler.ParameterControler;

public abstract class ParamViewAdapter {
	
	private ParameterView paramView;
	private ParameterControler param;

	

	/**
	 * Construct a main.java.view adpter which can handle several kind of main.java.view
	 * @param param
	 * @param paramView
	 */
	public ParamViewAdapter(ParameterControler param,ParameterView paramView) {
		this.param = param;
		if(paramView == null){
			this.paramView = this.getDefaultView(param);
		}else{
			this.paramView = paramView;
		}
		
	}
	/**
	 * Return the default view associated with the parameter controller
	 * @param param
	 * @return
	 */
	protected abstract  ParameterView getDefaultView(ParameterControler param);

	
	/**
	 * Update the main.java.view with parameter value
	 * @param time
	 */
	public abstract void updateView(BigDecimal time);
		
	
	protected ParameterControler getParameterControler(){
		return this.param;
	}
	
	
	/**
	 * @return the paramView
	 */
	public ParameterView getParamView() {
		return paramView;
	}

	

	
	

}
