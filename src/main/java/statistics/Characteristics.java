package main.java.statistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.Computable;
import main.java.maps.HasChildren;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.maps.Var;
import main.java.plot.WTrace;

/**
 * This Node handle the characteristic computation of the main.java.model
 * Characteristics, should be computed at the end of main.java.model computation 
 * (and not at each update like main.java.statistics)
 * Characteristics should reflect the performance of the main.java.model 
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
public class Characteristics implements HasChildren<Parameter>,Computable {
	
	/**This name**/
	public final static String NAME = "Charac";
	
	
	
	protected String name;
	protected WTrace wtrace;
	protected List<Parameter> params;



	private BigDecimal time;



	private Var<BigDecimal> dt;
	
	
	public Characteristics(Parameter... params)
	{
//		System.out.println("MEM:"+"construct:"+this.getClass());
//		System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
		this.name = NAME;
		this.time = new BigDecimal("0");
		this.dt = new InfiniteDt();
		this.params = new ArrayList<Parameter>();
		this.params.addAll(Arrays.asList(params));
		this.wtrace = new WTrace(getTrajectoryUnitMapsName());
	}
	
	

	/**
	 * Return the result of each characteristic computation
	 */
	public void compute() 
	{
		
		for(Parameter p : params){
			((Map)p).compute();
		}
		
		wtrace.add(getTrajectoryUnitMapsState());
	}
	
	public void reset()
	{
		wtrace.clear();
		this.time = BigDecimal.ZERO;
//		for (Parameter p : params) {
//			
//			p.reset();
//		}
	}
	
	public String[] getTrajectoryUnitMapsName() {
		String[] ret = new String[params.size()];
		for(int i = 0 ; i < params.size() ; i++)
		{
			ret[i] = ((Parameter) params.get(i)).getName();
		}
		return ret;
	}
	
	private double[] getTrajectoryUnitMapsState() throws NullCoordinateException {
		double[] ret = new double[params.size()];
		for(int i = 0 ; i < params.size() ; i++)
		{
			ret[i] =  ((SingleValueParam<Number>) params.get(i)).get().doubleValue();
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



	@Override
	public Parameter getIndex(int index) {
		return params.get(index);
	}



	@Override
	public List<Parameter> getValues() {
		return params;
	}
	
	@Override
	public Characteristics clone(){
		Characteristics clone = null;
		try {
			clone = (Characteristics) super.clone();
			clone.name = this.name+"_clone";
			clone.params = (List<Parameter>) ((ArrayList<Parameter>) this.params).clone();
			clone.wtrace = this.wtrace; //todo clone
		} catch (CloneNotSupportedException e) {
			// compatible
			e.printStackTrace();
		}
		
		return clone;
	}



	@Override
	public String getName() {
		return this.name;
	}



	@Override
	public List<Parameter> getParameters() {
		return params;
	}



	public BigDecimal getTime() {
		return this.time;
	}



	public Var<BigDecimal> getDt() {
		return this.dt;
	}



	public void setTime(BigDecimal currentTime) {
		this.time = currentTime;
		
	}



	public int getNbParam() {
		return params.size();
	}
	
	

	
	

}
