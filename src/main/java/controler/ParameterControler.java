package main.java.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import main.java.maps.Parameter;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;


/**
 * Will control one parameter main.java.model and main.java.view
 * @author benoit
 * @version 10/05/2014
 *
 */
public abstract class ParameterControler {
	
	private final transient Logger LOGGER = Logger.getLogger(ParameterControler.class.getName());

	
	private Parameter param;
	private List<ParameterControler> children;
	private ParamViewAdapter paraView;//Optional

	/**
	 * Construct a parameter comtroler
	 * @param param
	 */
	public ParameterControler(Parameter param) {
		this.param = param ;
		this.paraView = null;
		this.children = new ArrayList<ParameterControler>();
		LOGGER.info("Contruct parameterControler: " + "paramType : " + param.getClass().getName() + " name : " + param.getName());
	}
	
	public List getValues(){
		return param.getValues();
	}
	
	public void addChild(ParameterControler child){
		this.children.add(child);
	}
	
	public ParameterControler getChild(int index){
		return this.children.get(index);
	}
	
	@Override
	public String toString() {
		return this.getName();// " @" + System.identityHashCode(this) + " param @ " + System.identityHashCode(param);
	}
	
	
	/**
	 * @return the param
	 */
	protected Parameter getParam() {
		return param;
	}
	
	public void setParamViewAdapter(ParamViewAdapter paramViewAdapter){
		this.paraView = paramViewAdapter;
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
	protected ParamViewAdapter getParamViewAdapter() {
		return paraView;
	}
	
	public ParameterView getParamView(){
		if(this.paraView == null){
			return null;
		}else{
			return this.paraView.getParamView();
		}
	}
	
	

	

	public int getChildCount() {
		return children.size();
	}

	public int indexOf(ParameterControler child2) {
		return children.indexOf(child2);
	}

	
	
	
	/**
	 * Free the memory associated with the view
	 */
	public void deleteView(){
		this.paraView = null;
	}

	public String getName() {
		return getParam().getName();
	}
	

	public String displayText() {
		return toString();
	}
	/**
	 * Return the first element to display
	 * @return
	 */
	public Object getObjectUnitToDisplay() {
		return this.param.getIndex(0);
	}



	/**
	 * Add a view to the parameter controler
	 * @param pv
	 */
	public void addParamView(ParameterView pv) {
		this.getParamViewAdapter().addView(pv);
		
	}



	public void removeView() {
		this.getParamViewAdapter().removeLastView();
		
	}

	public void reset() {
		param.reset();
		for (ParameterControler pc : this.children) {
			pc.reset();
		}
		if(this.paraView != null){
			paraView.reset();
			paraView.updateViewAndRepaint();
		}
		
	}

	
	
	
	
	

}
