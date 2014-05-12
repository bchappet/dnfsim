package main.java.view;

import java.math.BigDecimal;

import main.java.controler.ParameterControler;
import main.java.maps.Parameter;

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
	
	protected abstract  ParameterView getDefaultView(ParameterControler param);

	
	/**
	 * Update the main.java.view with parameter value
	 * @param time
	 */
	public abstract void updateView(BigDecimal time);
		
	
	protected ParameterControler getParameter(){
		return this.param;
	}
	
	
	/**
	 * @return the paramView
	 */
	public ParameterView getParamView() {
		return paramView;
	}

	/**
	 * @param paramView the paramView to set
	 */
	public void setParamView(ParameterView paramView) {
		this.paramView = paramView;
	}

	
	

}
