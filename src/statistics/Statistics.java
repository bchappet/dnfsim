package statistics;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import maps.AbstractMap;
import maps.Parameter;
import maps.Var;
import model.Model;
import plot.Trace;
import plot.WTrace;
import coordinates.NullCoordinateException;
import coordinates.Space;


/**
 * 
 * @author bchappet
 *
 */
public class Statistics{









	/** Name of statistic fields**/
	public static final String ACT_SUM = "ActivitySum";
	public static final String CENTER_X = "CenterX";
	public static final String CENTER_Y = "CenterY";
	public static final String CLOSEST_TRACK = "ClosestTrack";
	public static final String TRACK_Y = "TrackY";
	public static final String TRACK_X = "TrackX";
	public static final String ERROR_DIST = "ErrorDist";
	public static final String COMPTIME = "CompTime";
	public static final String TIME = "Time";

	public static final String WIDTH = "Bubble Width";
	public static final String HEIGHT = "Bubble Height";
	public static final String MAX_WEIGHT = "Weight max";
	public static final String CONVERGENCE = "Convergence";
	public static final String ACC_ERROR = "Acceptable Error";
	public static final String FOCUS = "Focus";
	public static final String TRUE_ERROR ="True Error";
	public static final String MAX ="Max";
	public static final String TEST_CONV = "TestConv";
	public static final String LYAPUNOV = "Lyapunov";
	public static final String MSE_SOM = "MeanSquareError SOM";


	/**Determin the update time of the dynamic parameter**/
	protected Var dt; 


	/**Current time in second**/
	protected Var time;

	/**Space of the parameter**/
	protected Space space;

	/**List of the parameter nodes**/
	protected List<Parameter> paramNodes; 

	/**Computational time**/
	protected Var compTime;
	/**Last time of computation**/
	protected long lastTime;


	/**Name of the node**/
	protected String name;

	public static final int ERROR = -1;




	/**Compatibility with old plotting method**/
	protected WTrace wtrace;



	public Statistics(String name, Var dt, Space space,StatMap... trajectoryUnitMap) {
		this.name = name;
		this.paramNodes = new LinkedList<Parameter>();
		this.space = space;
		this.dt = dt;
		this.time = new Var(TIME,0);
		this.compTime = new Var(COMPTIME,ERROR);
		this.lastTime = System.currentTimeMillis();
		paramNodes.addAll(Arrays.asList(time,compTime));
		paramNodes.addAll(Arrays.asList(trajectoryUnitMap));
		this.wtrace = new WTrace(getParametersName());
		//Compute memory as we will ask several time the value
		for(StatMap st :trajectoryUnitMap ){
			st.constructMemory();
		}
	}




	/**
	 * Add a map to the list and add a trace
	 * @param map
	 */
	public void addStatisticMap(StatMap map){
		paramNodes.add(map);
		wtrace.addTrace(map.getName());
	}

	/**
	 * Remove map to the list but not to the wtrace
	 * @param map
	 */
	public void removeStatisticMap(StatMap map){
		paramNodes.remove(map);
		wtrace.removeTrace(map.getName());
	}


	/**
	 * Reset time clear wtrace
	 */
	public void reset() {
		//System.err.println("reset stat");

		time.set(0);
		wtrace.clear();
		for(Parameter p : paramNodes){
			p.reset();
			//			wtrace.addTrace(p.getName());
		}
	}

	public int getIndexOf(String ref)
	{
		return wtrace.getIndex(ref);
	}

	/**
	 * Return the last entry for the given index
	 * @param ref
	 * @return
	 */
	public double getLast(String ref) {
		return wtrace.get(getIndexOf(ref), wtrace.length()-1);
	}

	/**
	 * Return the statistic parameter having the name ref
	 * @param ref
	 * @return
	 */
	public Parameter getParam(String ref){
		for(Parameter p : paramNodes){
			if(p.getName().equals(ref)){
				return p;
			}
		}
		return null;
	}



	/**
	 * Return str for given trace at given time
	 * @param ref : index of the trace
	 * @param i : time of entry
	 * @return 
	 */
	public double get(int ref, int i) {
		return wtrace.get(ref, i);
	}

	public Trace getTrace(String ref)
	{
		return wtrace.getCoord(ref);
	}



	private String[] getParametersName() {
		String[] ret = new String[paramNodes.size()];
		for(int i = 0 ; i < paramNodes.size() ; i++)
		{
			ret[i] = ((Parameter) paramNodes.get(i)).getName();
		}
		return ret;
	}

	private List<Double> getParametersState() throws NullCoordinateException {
		List<Double> ret = new ArrayList<Double>();
		int i = 0;
		while( i < paramNodes.size())
		{
			ret.add(paramNodes.get(i).get());
			//			System.out.print(paramNodes.get(i).getName()+",");
			i++;
		}
		//		System.out.println();
		//		System.out.println(ret.size() + " parameters!!!!!");
		return ret;
	}




	/**
	 * Update the map until the time reaches the given timelimit(ms)
	 * @param timeLimit (ms)
	 * @throws NullCoordinateException 
	 */
	public void update(BigDecimal timeLimit) throws NullCoordinateException
	{
		long systemTime = System.currentTimeMillis();
		this.compTime.set(systemTime-lastTime);
		lastTime = systemTime;

		//		while (time.get()<timeLimit) {
		//			//Update the children params
		//			for(Parameter m : paramNodes) 
		//			{
		//				if(m instanceof AbstractMap)
		//					((AbstractMap) m).update(timeLimit);
		//			}
		//			// Compute the new state
		//			compute();
		//			// Update the time
		//			time.set(time.get()+ dt.get());
		//		}
		//		/System.out.println(this);

		//Update the children params
		for(Parameter m : paramNodes) {
			if(m instanceof AbstractMap){
				((AbstractMap) m).update(timeLimit);
			}
		}
		
		
		//update this if necessary
				BigDecimal bTime = new BigDecimal(time.val );
				bTime = bTime.setScale(Model.SCALE_LIMIT,Model.ROUDING_MODE);
				if(timeLimit.compareTo(bTime) >= 0){
					//System.out.println("Update stats : " + this.time.val);
					this.compute();
					this.time.val = bTime.doubleValue() + dt.get();
				}
	}

	public void compute() throws NullCoordinateException {
		List<Double> paramTest = getParametersState();
		double[] paramArray = new double[paramTest.size()];
		for(int i = 0 ; i < paramArray.length ; i++){
			paramArray[i] = paramTest.get(i);
		}
		wtrace.add(paramArray);
	}

	public double get(String name) {
		return wtrace.getLast(name);
	}

	/** Save the statistics with the default filename 
	 * @throws IOException */
	public void save(String file) throws IOException {
		wtrace.save(file);
	}

	public int getIndex(String str) {
		return wtrace.getIndex(str);
	}

	public WTrace getWtrace() {
		return wtrace;
	}

	/**
	 * Return the length of the Wtrace
	 * @return
	 */
	public int length() {
		return wtrace.length();
	}


	public String toString()
	{
		return wtrace.toString();
	}




	public Var getDt() {
		return dt;
	}






















}
