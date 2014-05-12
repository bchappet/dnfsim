package main.java.controler;

import java.math.BigDecimal;

import main.java.statistics.Characteristics;
import main.java.view.CharacteristicsViewAdapter;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;

public class CharacteristicsControler extends  ParameterControler {

	public CharacteristicsControler(Characteristics param) {
		super(param);
	}


	public void compute(BigDecimal currentTime) {
		
			((Characteristics) getParam()).compute();
			if(this.getParamViewAdapter() != null)
				this.getParamViewAdapter().updateView(currentTime);
		
		
	}


	@Override
	protected ParamViewAdapter createParamViewAdapter(ParameterView view) {
		return new CharacteristicsViewAdapter(this, view);
	}


	public String[] getTrajectoryUnitMapsName() {
		return ((Characteristics) getParam()).getTrajectoryUnitMapsName();
	}

}
