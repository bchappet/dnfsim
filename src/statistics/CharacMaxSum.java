package statistics;

import model.Model;
import plot.Trace;
import coordinates.Space;

public class CharacMaxSum extends Charac {



	public CharacMaxSum(String name, Statistics stats, Space space,
			Model model, Charac conv) {
		super(name, stats, space, model, conv);
	}

	@Override
	public double computeTrajectory(double... param) {

		double ret = Statistics.ERROR;

			Trace nbExited = stats.getTrace(Statistics.ACT_SUM);
			for(double d : nbExited.getValues())
			{
				if(d > ret)
					ret = d;
			}
		return ret;

	}

}
