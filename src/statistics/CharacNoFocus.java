package statistics;

import model.Model;
import plot.Trace;
import coordinates.Space;

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
		Trace sum = stats.getTrace(Statistics.ACT_SUM);
		int nb =  Statistics.ERROR;
		double convtime = get(Characteristics.CONVERGENCE);
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
