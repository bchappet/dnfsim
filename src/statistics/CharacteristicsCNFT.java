package statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import plot.WTrace;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * This Node handle the characteristic computation of the model
 * Characteristics, should be computed at the end of model computation 
 * (and not at each update like statistics)
 * Characteristics should reflect the performance of the model 
 * for the computational aspect (speed of computation...) and
 * for the emergent properties : tracking, convergence etc...  
 * 
 * The result should be displayed with a double vector (WTrace)
 * 
 * It is possible to compute the result at different time of simulation
 * The result will then be a table
 * 
 * @author bchappet
 *
 */
public class CharacteristicsCNFT extends Characteristics{
	
	
	
	//col.names = c("iteration","Convergence","MeanError","Obstinacy","NoFocus","MaxSum","MeanCompTime","AcceptableError","MaxPotential","Test_Conv","Closest_track")
	public static final String CONVERGENCE = "Convergence";
	public static final String MEAN_ERROR = "MeanError";
	public static final String OBSTINACY = "Obstinacy";
	public static final String NO_FOCUS = "NoFocus";
	public static final String MAX_SUM = "MaxSum";
	public static final String MEAN_COMP_TIME = "MeanCompTime" ;
	public static final String ACC_ERROR = "AcceptableError"; 
	public static final String MAX_MAX = "MaxPotential";
	public static final String TEST_CONV = "Test_Conv";
	public static final String CLOSEST_TRACK = "Closest_track"; //closest track on average
	
	
	
	
	
	public CharacteristicsCNFT(Space space,Statistics stats,TrajectoryUnitMap... params)
	{
		super(space,stats,params);
	}
	
	
	
	

	
	

}
