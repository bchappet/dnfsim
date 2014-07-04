package main.java.statistics;

import main.java.coordinates.Space;
import main.java.model.Model;
import main.java.plot.Trace;

public class CharacMaxMax extends Charac {



	public CharacMaxMax(String name, Statistics stats, Space space,
			Model model) {
		super(name, stats, space, model);
	}

	@Override
	public double computeTrajectory(double... param) {

		double ret = Statistics.ERROR;

			Trace max = stats.getTrace(StatisticsCNFT.MAX);
			for(double d : max.getValues())
			{
				if(d > ret)
					ret = d;
			}
		return ret;

	}

}
