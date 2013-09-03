package statistics;

import model.Model;
import plot.Trace;
import coordinates.Space;

public class CharacMaxMax extends Charac {



	public CharacMaxMax(String name, Statistics stats, Space space,
			Model model, Charac conv) {
		super(name, stats, space, model, conv);
	}

	@Override
	public double computeTrajectory(double... param) {

		double ret = Statistics.ERROR;

			Trace max = stats.getTrace(Statistics.MAX);
			for(double d : max.getValues())
			{
				if(d > ret)
					ret = d;
			}
		return ret;

	}

}
