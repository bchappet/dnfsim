package main.java.statistics;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.HasChildren;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.maps.Trajectory;
import main.java.maps.Var;
import main.java.plot.Trace;
import main.java.plot.WTrace;


/**
 * 
 * @author bchappet
 *
 */
public class Statistics implements HasChildren<Parameter>{


	/** Name of statistic fields**/
	
	public static final String COMPTIME = "CompTime";
	public static final String TIME = "Time";


	/**Determin the update time of the dynamic parameter**/
	protected Var<BigDecimal> dt; 


	/**Current time in second**/
	protected Var<BigDecimal> time;


	/**List of the parameter nodes**/
	protected List<Parameter> paramNodes; 

	/**Computational time**/
	protected Var<BigDecimal> compTime;
	/**Last time of computation**/
	protected long lastTime;


	/**Name of the node**/
	protected String name;

	public static final int ERROR = -1;




	/**Compatibility with old plotting method**/
	protected WTrace wtrace;



	public Statistics(String name, Var<BigDecimal> dt,Parameter... parameter) {
		this.name = name;
		this.paramNodes = new LinkedList<Parameter>();
		this.dt = dt;
		this.time = new Var<BigDecimal>(TIME,new BigDecimal("0"));
		this.compTime = new Var<BigDecimal>(COMPTIME,new BigDecimal(ERROR));
		this.lastTime = System.currentTimeMillis();
		paramNodes.addAll(Arrays.asList(time,compTime));
		paramNodes.addAll(Arrays.asList(parameter));
		this.wtrace = new WTrace(getParametersName());
	}




	/**
	 * Add a map to the list and add a trace
	 * @param map
	 */
	public void add(Parameter map){
		paramNodes.add(map);
		wtrace.addTrace(map.getName());
	}

	/**
	 * Remove map to the list but not to the wtrace
	 * @param map
	 */
	public void remove(Parameter map){
		paramNodes.remove(map);
		wtrace.removeTrace(map.getName());
	}


	/**
	 * Reset time clear wtrace
	 */
	public void reset() {
		//System.err.println("reset stat");

		time.set(new BigDecimal("0"));
		wtrace.clear();
		for(Parameter p : paramNodes){
			p.reset(); //TODO
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
			if(p.getName() != null && p.getName().equals(ref)){
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
	public double get(int index, int time) {
		return wtrace.get(index, time);
	}

	public Trace getTrace(String ref)
	{
		return wtrace.getCoord(ref);
	}



	private String[] getParametersName() {
		String[] ret = new String[paramNodes.size()];
		for(int i = 0 ; i < paramNodes.size() ; i++)
		{
			ret[i] = (paramNodes.get(i)).getName();
		}
		return ret;
	}

	private List<Number> getParametersState() throws NullCoordinateException {
		List<Number> ret = new ArrayList<Number>();
		int i = 0;
		while( i < paramNodes.size())
		{
			
			Object value = ( paramNodes.get(i)).getIndex(0);
			if(value instanceof Number){
				ret.add((Number)value);
			}else{
				throw new Error("You should implement a behaviour for value of type " + value.getClass());
			}
			//			System.out.print(paramNodes.get(i).getName()+",");
			i++;
		}
		//		System.out.println();
		//		System.out.println(ret.size() + " parameters!!!!!");
		return ret;
	}




	

	public void compute() throws NullCoordinateException {
		List<Number> paramTest = getParametersState();
		double[] paramArray = new double[paramTest.size()];
		for(int i = 0 ; i < paramArray.length ; i++){
			paramArray[i] = paramTest.get(i).doubleValue();
		}
		wtrace.add(paramArray);
	}

	public double get(String name) {
		return wtrace.getLast(name);
	}

	/** Save the main.java.statistics with the default filename 
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




	public Var<BigDecimal> getDt() {
		return dt;
	}




	@Override
	public Parameter getIndex(int index) {
		return this.paramNodes.get(index);
	}




	@Override
	public List<Parameter> getValues() {
		return this.paramNodes;
	}






	@Override
	public Statistics clone(){
		return null; //TODO
	}




	@Override
	public List<Parameter>getParameters() {
		return paramNodes;
	}




	@Override
	public String getName() {
		return this.name;
	}


	public BigDecimal getTime() {
		return this.time.get();
	}




	public void setTime(BigDecimal currentTime) {
		this.time.set(currentTime);
		
	}















}
