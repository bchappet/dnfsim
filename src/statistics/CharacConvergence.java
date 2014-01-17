package statistics;

import model.Model;
import coordinates.Space;


/**
 * Convergence time: 
 * Convergence if
 * 			1) there is activity
 * 			2) activity bubble size <= tracked bubble size
 * 			3) Dist error with tracked bubble size <= acceptableError 
 * 
 * During stabIteration iteration with the same tracked bubble
 * 
 * 
 * Return stat.ERROR if theris no convergence
 * 
 * @author bchappet
 *
 */
public class CharacConvergence extends Charac{

	//Parameters
	





	public CharacConvergence(String name, Statistics stats, Space space,Model model) {
		super(name, stats, space,model);
	}





	@Override
	public double computeTrajectory(double... param) {
		return stats.get(Statistics.CONVERGENCE);
		
	}






	






	




}
