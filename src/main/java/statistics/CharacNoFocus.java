package main.java.statistics;

import main.java.coordinates.Space;
import main.java.model.Model;
import main.java.plot.Trace;

/**
 * Nb of iterations without focus
 * @author bchappet
 *
 */
public class CharacNoFocus extends Charac {

	public CharacNoFocus(String name, Statistics stats, Space space,
			Model model, Charac conv) {
		super(name, stats, space, model, conv);
	}



	@Override
	public double computeTrajectory(double... param) {
		Trace sum = stats.getTrace(StatisticsCNFT.ACT_SUM);
		int nb =  Statistics.ERROR;
		double convtime = get(CharacteristicsCNFT.CONVERGENCE);
		int convIt = (int) Math.round( (convtime/stats.dt.get()));
		if(convtime != Statistics.ERROR)
		{
			nb = 0;
			for(int i = convIt; i < sum.size() ; i++)
			{
				double val = sum.get(i);
				if(val == 0)
				{
					nb ++;
				}
			}
			
		}
		
		return nb;


	}
}
