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
	/**Acceptable error : error threshold to ensure tracking**/
	public final static String ACCERROR = "accError";
	/**Number of iteration to ensure convergence**/
	public final static String STABIT = "stabIt";
	
	/**factor applied to the stimulu shape to infer minimum aera**/
	public final static String SHAPE_FACTOR = "shapeFactor";





	public CharacConvergence(String name, Statistics stats, Space space,Model model) {
		super(name, stats, space,model);
	}





	@Override
	public double computeTrajectory(double... param) {
		return stats.get(Statistics.CONVERGENCE);
		
	}






	






	




}
