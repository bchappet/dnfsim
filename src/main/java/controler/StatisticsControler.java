package main.java.controler;

import java.math.BigDecimal;
import java.util.logging.Logger;

import main.java.plot.WTrace;
import main.java.statistics.Statistics;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;
import main.java.view.StatisticsViewAdapter;

public class StatisticsControler extends  ComputableControler{
	
	


	public StatisticsControler(Statistics param) {
		super(param);
	}

	

	@Override
	protected ParamViewAdapter createParamViewAdapter(ParameterView view) {
		return new StatisticsViewAdapter(this,view);
	}

	public WTrace getWtrace() {
		return ((Statistics) this.getParam()).getWtrace();
	}

	public int getIndex(String str) {
		return ((Statistics) this.getParam()).getIndex(str);
	}

	public String getDefaultDisplayedStat() {
		return ((Statistics) this.getParam()).getDefaultDisplayedStat();
	}





}
