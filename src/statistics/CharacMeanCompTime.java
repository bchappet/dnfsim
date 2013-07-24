package statistics;

import model.Model;
import plot.Trace;
import coordinates.Space;

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
		double convtime = get(Characteristics.CONVERGENCE);
		
		Trace time = stats.getTrace(Statistics.COMPTIME );

		double sum = 0;
		double nb = 0;
		for(int i = 0 ; i < time.size() ; i++)
		{
			double val = time.get(i);
			if(val != Statistics.ERROR)
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
