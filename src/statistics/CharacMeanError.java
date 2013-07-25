package statistics;

import model.Model;
import plot.Trace;
import coordinates.Space;

public class CharacMeanError extends Charac {

	public CharacMeanError(String name, Statistics stats, Space space,
			Model model, Charac conv) {
		super(name, stats, space, model, conv);
	}

	@Override
	public double computeTrajectory(double... param) {
		
		double ret = Statistics.ERROR;
		double convtime = get(Characteristics.CONVERGENCE);
		if(convtime != Statistics.ERROR)
		{
			Trace error = stats.getTrace(Statistics.TRUE_ERROR);
			double mean = 0;
			double nb = 0;
			for(int i = (int) convtime ; i <  error.size() ; i++)
			{
				double err = error.get(i);
				if(err != Statistics.ERROR)
				{
					mean += error.get(i);
					nb ++;
				}
			}
			
			ret = mean / nb;
		}
		
		return ret;

	}

	

}
