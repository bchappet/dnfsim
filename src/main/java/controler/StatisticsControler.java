package main.java.controler;

import java.io.IOException;

import main.java.maps.Parameter;
import main.java.statistics.Statistics;

public class StatisticsControler extends ComputableControler {

	public StatisticsControler(Parameter param) {
		super(param);
	}

	@Override
	public int getMaxIndex() {
		return ((Statistics ) getParam()).getValues().size();
	}
	
	public void saveStats(){
		try {
			((Statistics ) getParam()).save("stats.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
