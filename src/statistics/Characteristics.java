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
public class Characteristics {
	
	/**This name**/
	public final static String NAME = "Charac";
	/**Fields name**/
	
	//col.names = c("iteration","Convergence","MeanError","Obstinacy","NoFocus","MaxSum","MeanCompTime","AcceptableError")
	public static final String CONVERGENCE = "Convergence";
	public static final String MEAN_ERROR = "MeanError";
	public static final String OBSTINACY = "Obstinacy";
	public static final String NO_FOCUS = "NoFocus";
	public static final String MAX_SUM = "MaxSum";
	public static final String MEAN_COMP_TIME = "MeanCompTime" ;
	public static final String ACC_ERROR = "AcceptableError"; 
	
	
	protected String name;
	protected Space space;
	protected WTrace wtrace;
	protected Statistics stats;
	protected List<TrajectoryUnitMap> params;
	
	
	public Characteristics(Space space,Statistics stats,TrajectoryUnitMap... params)
	{
		this.name = NAME;
		this.stats = stats;
		this.space = space;
		this.params = new ArrayList<TrajectoryUnitMap>();
		this.params.addAll(Arrays.asList(params));
		this.constructMemory();//As we will ask several time the value
		this.wtrace = new WTrace(getTrajectoryUnitMapsName());
	}
	
	/**
	 * Construct all map memory to save the state after the computation
	 */
	protected void constructMemory() {
		for(TrajectoryUnitMap tum : params){
			tum.constructMemory();
		}
		
	}

	/**
	 * Return the result of each characteristic computation
	 * @return a WTrace with each result
	 * @throws NullCoordinateException 
	 */
	public WTrace compute() throws NullCoordinateException
	{
		
		for(TrajectoryUnitMap p : params){
			((TrajectoryUnitMap)p).compute();
		}
		
		wtrace.add(getTrajectoryUnitMapsState());
		return wtrace;
	}
	
	public void reset()
	{
		wtrace.clear();
	}
	
	public String[] getTrajectoryUnitMapsName() {
		String[] ret = new String[params.size()];
		for(int i = 0 ; i < params.size() ; i++)
		{
			ret[i] = ((TrajectoryUnitMap) params.get(i)).getName();
		}
		return ret;
	}
	
	private double[] getTrajectoryUnitMapsState() throws NullCoordinateException {
		double[] ret = new double[params.size()];
		for(int i = 0 ; i < params.size() ; i++)
		{
			ret[i] =  params.get(i).get();
		}
		return ret;
	}

	public WTrace getWtrace() {
		return wtrace;
	}
	
	public Parameter getParam(String name)
	{
		for(Parameter p : params)
		{
			if(p.getName().equals(name))
				return p;
		}
		return null;
	}
	
	public String toString()
	{
		return wtrace.toString();
	}
	
	

	
	

}
