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
	
	/**
	 * Create the ParamViewAdpter if it does not exist
	 * @param paramView (optional) if null, will construct default view
	 */
	public void initView(ParameterView paramView) {
		if(this.paraView == null){
			LOGGER.info("INIT view of   paramType : " + param.getClass().getName() + " name : " + param.getName());
			this.paraView = this.createParamViewAdapter(paramView);
		}
	}
	
	public void addChild(ParameterControler child){
		this.children.add(child);
	}
	
	public ParameterControler getChild(int index){
		return this.children.get(index);
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	/**
	 * Construct the ParamViewAdapter
	 * @param view (optional)
	 * @return
	 */
	protected abstract ParamViewAdapter createParamViewAdapter(ParameterView view);

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
		return paraView.getParamView();
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

	
	
	
	/**
	 * Free the memory associated with the view
	 */
	public void deleteView(){
		this.paraView = null;
	}

	public String getName() {
		return getParam().getName();
	}
	
	/**
	 * Return the controler of given parameter name
	 * @param name
	 * @return
	 */
	public ParameterControler getControler(String name) {
		if(this.getName() == null){
			return null;
		}
		 if( this.getName().equals(name)){
			return this;
		}else{
			ParameterControler ret = null;
			for(int i = 0 ; i < this.getChildCount() ; i++){
				ret = getChild(i).getControler(name);
				if(ret != null){
					return ret;
				}
			}
			return ret;
		}
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

	
	
	
	
	

}
