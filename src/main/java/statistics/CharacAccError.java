package main.java.statistics;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLine;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.model.Model;
import main.java.plot.Trace;

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
		double convtime = get(CharacteristicsCNFT.CONVERGENCE);
		int convIt = (int) Math.round( (convtime/stats.dt.get()));
		int stabIt =  (int) Math.round( (params.getIndex(STAB_IT).get()/stats.dt.get()));
		if(convtime != Statistics.ERROR)
		{
			
			Trace accError = stats.getTrace(StatisticsCNFT.ACC_ERROR);
			
			ret = 0;
			for(int i = convIt; i < accError.size() ; i++){
			
				if(accError.get(i) == 0){
					ret ++;
				}
			}
			
			
		}
		//load=tracking.dnfs;print=all;
		
		return ret;
	}

}
