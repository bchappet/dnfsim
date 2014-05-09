package main.java.statistics;

import main.java.coordinates.Space;
import main.java.model.Model;
import main.java.plot.Trace;

public class CharacMeanError extends Charac {

	public CharacMeanError(String name, Statistics stats, Space space,
			Model model, Charac conv) {
		super(name, stats, space, model, conv);
	}

	@Override
	public double computeTrajectory(double... param) {
		
		double ret = Statistics.ERROR;
		double convtime = get(CharacteristicsCNFT.CONVERGENCE);
		int convIt = (int) Math.round( (convtime/stats.dt.get()));
		if(convtime != StatisticsCNFT.ERROR)
		{
			Trace error = stats.getTrace(StatisticsCNFT.TRUE_ERROR);
			double mean = 0;
			double nb = 0;
			for(int i = convIt ; i <  error.size() ; i++)
			{
				double err = error.get(i);
				if(err != StatisticsCNFT.ERROR)
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
