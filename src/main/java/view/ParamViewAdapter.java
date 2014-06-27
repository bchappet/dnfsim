package main.java.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;







import main.java.controler.ParameterControler;

public abstract class ParamViewAdapter {
	
	private List<ParameterView> paramViews;
	private ParameterControler paramControler;
	private ViewFactory viewFactory; //Optional
	private final transient Logger LOGGER = Logger.getLogger(ParamViewAdapter.class.getName());
	

	/**
	 * Construct a main.java.view adpter which can handle several kind of main.java.view
	 * @paramControler paramControler
	 * @paramControler paramView
	 * @param ViewFactory vf optional
	 */
	public ParamViewAdapter(ParameterControler paramControler,ViewConfiguration vc,ViewFactory vf) {
		if(paramControler == null){
			throw new IllegalArgumentException("the ParameterControler whas null ");
		}
		LOGGER.info("Construct ParamViewAdapter : " + this.getClass());
		this.paramControler = paramControler;
		this.paramControler.setParamViewAdapter(this);
		this.viewFactory = vf;
		this.paramViews = new ArrayList<ParameterView>(); 
		this.paramViews.add(this.constructView(vc, vf));
	}
	
	public ParamViewAdapter(ParameterControler paramControler,ViewFactory vf){
		this(paramControler,vf.getViewConfiguration(),vf);
	}
	
	protected abstract ParameterView constructView(ViewConfiguration vc,ViewFactory vf);

	/**
	 * Update the main.java.view with parameter value
	 * @paramControler time
	 */
	public void updateViewAndRepaint(){
		this.updateView();
		for(ParameterView pv : paramViews){
			pv.repaint();
		}
	}
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
		return paramViews.get(0);
	}

	public void addView(ParameterView pv) {
		paramViews.add(pv);
		
	}

	/**
	 * Remove last view ( maybe use a llinked list
	 * TODO maybe use a best managment of views
	 */
	public void removeLastView() {
		paramViews.remove(paramViews.size()-1);
		
	}


	


	

	

	
	

}
