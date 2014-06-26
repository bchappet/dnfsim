package main.java.view;

import java.util.logging.Logger;





import main.java.controler.ParameterControler;

public abstract class ParamViewAdapter {
	
	private ParameterView paramView;
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
		this.paramView = this.constructView(vc, vf);
	}
	
	public ParamViewAdapter(ParameterControler paramControler,ViewFactory vf){
		this(paramControler,vf.getViewConfiguration(),vf);
	}
	
	protected abstract ParameterView constructView(ViewConfiguration vc,ViewFactory vf);

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
