package main.java.controler;

import java.util.ArrayList;
import java.util.List;

import main.java.maps.Parameter;
import main.java.view.ParamViewAdapter;


/**
 * Will control one parameter main.java.model and main.java.view
 * @author benoit
 *
 */
public abstract class ParameterControler {
	
	private Parameter param;
	private List<ParameterControler> children;
	private ParamViewAdapter paraView;

	public ParameterControler(Parameter param,ParamViewAdapter paramView) {
		this.param = param ;
		this.paraView = paramView;
		this.children = new ArrayList<ParameterControler>();
	}
	
	public void addChild(ParameterControler child){
		this.children.add(child);
	}
	
	public ParameterControler getChild(int index){
		return this.children.get(index);
	}
	
	@Override
	public String toString() {
		return getParam().getName();
	}

	/**
	 * @return the param
	 */
	protected Parameter getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	protected void setParam(Parameter param) {
		this.param = param;
	}

	/**
	 * @return the paraView
	 */
	protected ParamViewAdapter getParaView() {
		return paraView;
	}

	/**
	 * @param paraView the paraView to set
	 */
	protected void setParaView(ParamViewAdapter paraView) {
		this.paraView = paraView;
	}

	public int getChildCount() {
		return children.size();
	}

	public int indexOf(ParameterControler child2) {
		return children.indexOf(child2);
	}
	
	
	
	

}
