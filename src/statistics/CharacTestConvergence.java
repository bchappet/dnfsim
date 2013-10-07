package statistics;

import maps.Parameter;
import model.Model;
import plot.Trace;
import coordinates.Space;


/**

 * 
 * @author bchappet
 *
 */
public class CharacTestConvergence extends Charac{


	//params
	public static final int WA  = 0;
	public static final int SHAPE_FACTOR = 1;
	public static final int STABIT = 2;




	public CharacTestConvergence(String name, Statistics stats, Space space,Model model,Parameter wa,Parameter shape_factor,Parameter stabIt) {
		super(name, stats, space,model,wa,shape_factor,stabIt);
	}





	@Override
	public double computeTrajectory(double... param) {
		double ret = Statistics.ERROR;

		Trace width = stats.getTrace(Statistics.WIDTH);
		Trace height = stats.getTrace(Statistics.HEIGHT);
		
		int stabIt = (int)param[STABIT];
		double shape_max = param[SHAPE_FACTOR] * param[WA];
		
		int convIt = 0;
		boolean inConv = false;
		
		int iteration;
		for( iteration = 0 ; iteration < width.size() ; iteration++)
		{
			//System.out.println("width: " + width.get(iteration));
				boolean shape_ok = width.get(iteration) != Statistics.ERROR 
						&&height.get(iteration)!=Statistics.ERROR 
						&& width.get(iteration) <= shape_max 
						&& height.get(iteration) <= shape_max;
				
				if(shape_ok){
					convIt ++;
					//System.out.println(convIt);
					if(convIt == stabIt){
						break;
					}
				}else{
					convIt = 0;
				}
					
				
		}
		
		if(convIt == stabIt){
			ret = iteration - stabIt;
		}else{
			ret = Statistics.ERROR;
		}
		
		return ret;

	}


















}
