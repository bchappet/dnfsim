package main.java.controler;

import java.math.BigDecimal;

import main.java.statistics.Statistics;
import main.java.view.StatisticsViewAdapter;

public class StatisticsControler extends ParameterControler implements ComputableControler{

	public StatisticsControler(Statistics param) {
		super(param, new StatisticsViewAdapter(param));
	}

	@Override
	public void compute(BigDecimal currentTime) {
		Statistics map = ((Statistics)getParam());
		
		if(map.getTime().add((BigDecimal) map.getDt().get()).compareTo(currentTime) == 0){
			map.setTime(currentTime);
			map.compute();
			this.getParaView().updateView(currentTime);
		}
		
	}

	@Override
	public BigDecimal getNextTime() {
		Statistics map = (Statistics) this.getParam();
		BigDecimal nextTime = map.getTime().add((BigDecimal) map.getDt().get());
		return nextTime;
	}

}
