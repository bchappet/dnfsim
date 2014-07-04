package main.java.statistics;

import main.java.coordinates.Space;
import main.java.model.Model;
import main.java.plot.Trace;

public class CharacMaxSum extends Charac {



	public CharacMaxSum(String name, Statistics stats, Space space,
			Model model) {
		super(name, stats, space, model);
	}

	@Override
	public double computeTrajectory(double... param) {

		double ret = Statistics.ERROR;

			Trace nbExited = stats.getTrace(StatisticsCNFT.ACT_SUM);
			for(double d : nbExited.getValues())
			{
				if(d > ret)
					ret = d;
			}
		return ret;

	}

}
