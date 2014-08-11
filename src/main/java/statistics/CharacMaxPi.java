package main.java.statistics;

import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.maps.Parameter;
import main.java.model.Model;
import main.java.plot.Trace;
import main.java.space.DoubleSpace2D;
import main.java.space.Space2D;
import main.java.unitModel.UnitModel;
/**
 * TODO only saqure space compatible
 * @author bchappet
 *
 */
public class CharacMaxPi extends Charac {

	
	//Params
	private static final int SHAPE_FACT = 0;
	private static final int TCK_WIDTH = 1;
	private static final int SPACE = 2;
	
	
	public CharacMaxPi(String name, Statistics stats,
			Model model, Parameter... params) {
		super(name, stats, model, params);
	}

	@Override
	protected Double computeTrajectory(List<Parameter> params) {
		double ret = Statistics.ERROR;
		double shapeFactor = (double) params.get(SHAPE_FACT).getIndex(index);
		double track_width = (double) params.get(TCK_WIDTH).getIndex(index);
		DoubleSpace2D space = (DoubleSpace2D) params.get(SPACE);
		
		Trace width = stats.getTrace(StatisticsCNFT.WIDTH);
		Trace height = stats.getTrace(StatisticsCNFT.HEIGHT);
//		System.out.println("Size : " + width.size());
		for(int i = 0 ; i < width.size() ; i++)
		{
			
			double w = width.get(i);
			double h = height.get(i);
//			System.out.println("h: " + h + " w: " + w);
//			System.out.println("shapeFactor*track_width " + shapeFactor*track_width);
			//Check convergenc TODO do it properly qvec charac cov
			if(w != StatisticsCNFT.ERROR && w <= shapeFactor*track_width
					&& h <= shapeFactor*track_width && w > 0 && h > 0){
				double maxWH = Math.max(w, h);
				if(maxWH > ret){
					ret = maxWH;
				}
			}
			
		}
		
		if(ret != Statistics.ERROR){
			//Discretize it to have pi
			ret= space.intDistProj(ret,Space2D.X);
		}
		
		return ret;

	}

}
