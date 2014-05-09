package main.java.view;

import java.math.BigDecimal;

import main.java.gui.ColorMap;
import main.java.maps.Parameter;

public abstract class ParamViewAdapter {
	
	private ParameterView paramView;
	private Parameter param;

	

	/**
	 * Construct a main.java.view adpter which can handle several kind of main.java.view
	 * @param param
	 * @param paramView
	 */
	public ParamViewAdapter(Parameter param,ParameterView paramView) {
		this.paramView = paramView;
		this.param = param;
		
	}
	
	/**
	 * Construct a paramater adpter with the default main.java.view
	 * @param param
	 * @param cm
	 */
	public ParamViewAdapter(Parameter param) {
		this.param = param;
		this.paramView = getDefaultView(param);
		
	}
	
	/**
	 * Return and construct the dafault main.java.view for this kind of parameter
	 * @param param
	 * @param cm
	 * @return
	 */
	protected abstract ParameterView getDefaultView(Parameter param);

	
	/**
	 * Update the main.java.view with parameter value
	 * @param time
	 */
	public abstract void updateView(BigDecimal time);
		
	
	protected Parameter getParameter(){
		return this.param;
	}
	
	
	/**
	 * @return the paramView
	 */
	protected ParameterView getParamView() {
		return paramView;
	}

	/**
	 * @param paramView the paramView to set
	 */
	public void setParamView(ParameterView paramView) {
		this.paramView = paramView;
	}

	
	

}
