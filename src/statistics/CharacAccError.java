package statistics;

import console.CNFTCommandLine;
import console.CommandLine;
import plot.Trace;
import maps.Parameter;
import model.Model;
import coordinates.Space;

/**
 * Add 1 when the error is more than the parameter acc_error
 * @author bchappet
 *
 */
public class CharacAccError extends Charac {
	
	public static final int STAB_IT = 1;

	public CharacAccError(String name, Statistics stats, Space space,
			Model model, Parameter... params) {
		super(name, stats, space, model, params);
	}

	@Override
	public double computeTrajectory(double... param) {
		double ret = Statistics.ERROR;
		double convtime = get(Characteristics.CONVERGENCE);
		if(convtime != Statistics.ERROR)
		{
			
			Trace accError = stats.getTrace(Statistics.ACC_ERROR);
			
			ret = 0;
			for(int i = (int) convtime; i < accError.size() ; i++){
				if(accError.get(i) != 1.0){
					ret ++;
				}
			}
			int stabIt = (int) params.get(STAB_IT).get();
			ret = ret - stabIt + 2;
		}
		
		return ret;
	}

}
