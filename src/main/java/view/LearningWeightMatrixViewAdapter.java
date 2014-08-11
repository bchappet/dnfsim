package main.java.view;

import main.java.controler.ParameterControler;
import main.scripts.gui.LearningWeightMatrixControler;

public class LearningWeightMatrixViewAdapter extends ParamViewAdapter {

	public LearningWeightMatrixViewAdapter(ParameterControler paramControler,
			ViewConfiguration vc, ViewFactory vf) {
		super(paramControler, vc, vf);
	}

	public LearningWeightMatrixViewAdapter(ParameterControler paramControler,
			 ViewFactory vf) {
		super(paramControler,  vf);
	}
	@Override
	protected ParameterView constructView(ViewConfiguration vc, ViewFactory vf) {
		return new LearningWeightMatrixView(getParameterControler().getName(),
				((LearningWeightMatrixControler)getParameterControler()).getArray1D(),
				((ColorMap)vc.get(ViewConfiguration.COLORMAP).get()).clone(),
				vc.get(ViewConfiguration.GRID),(LearningWeightMatrixControler)getParameterControler());
	
	}

	@Override
	protected void updateView() {
		((LearningWeightMatrixView)getParamView()).update(
				((LearningWeightMatrixControler)getParameterControler()).getArray1D());

	}

}
