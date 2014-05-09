package main.java.statistics;

import main.java.coordinates.Space;
import main.java.model.Model;
import main.java.plot.Trace;

/**
 * Mean of computational time
 * @author bchappet
 *
 */
public class CharacMeanCompTime extends Charac {

	public CharacMeanCompTime(String name, Statistics stats, Space space,
			Model model, Charac conv) {
				super(name, stats, space, model, conv);
	}


	@Override
	public double computeTrajectory(double... param) {
		double ret = Statistics.ERROR;
		
		Trace time = stats.getTrace(StatisticsCNFT.COMPTIME );

		double sum = 0;
		double nb = 0;
		for(int i = 0 ; i < time.size() ; i++)
		{
			double val = time.get(i);
			if(val != StatisticsCNFT.ERROR)
			{
				sum += val;
				nb++;
			}
		}
		if(nb !=0)
		{
			ret = sum/nb;
		}
		return ret;
	}

}
