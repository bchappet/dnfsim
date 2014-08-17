package main.java.view;

import javax.jws.Oneway;

import main.java.controler.MapControler;
import main.java.controler.ParameterControler;

public class MultivaluedParameterCurve2DAdapter extends ParamViewAdapter {

	/**
	 * Curently displayed on the view (default = 0)
	 */
	protected int displayedIndex = 0; 
	/**
	 * Maximal index of the parameter
	 */
	protected int maxIndex= 0;
	
	public MultivaluedParameterCurve2DAdapter(
			ParameterControler paramControler, ViewConfiguration vc,
			ViewFactory vf) {
		super(paramControler, vc, vf);
		this.maxIndex = paramControler.getMaxIndex();
		((IndexSelectorViewPanel)this.getParamView()).onParamViewConstruction(this);
		
	}

	public MultivaluedParameterCurve2DAdapter(
			ParameterControler paramControler, ViewFactory vf) {
		super(paramControler, vf);
		this.maxIndex = paramControler.getMaxIndex();
		((IndexSelectorViewPanel)this.getParamView()).onParamViewConstruction(this);
	}

	@Override
	protected ParameterView constructView(ViewConfiguration vc,ViewFactory vf) {
		return new IndexSelectorViewPanel(getParameterControler().getName(),vf);
	}
	@Override
	public void updateView() {
		IndexSelectorViewPanel pv = (IndexSelectorViewPanel) this.getParamView();
		MapControler paramC = (MapControler)this.getParameterControler();
		pv.update(((Number) paramC.get(displayedIndex)).doubleValue());
	}
	
	public void setDisplayedIndex(int displayedIndex){
		this.displayedIndex = displayedIndex;
	}
	
	public int getMaxIndex(){
		return this.maxIndex;
	}

}
