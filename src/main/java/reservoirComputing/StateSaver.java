package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import main.java.maps.Computable;
import main.java.maps.HasChildren;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.resources.utils.ArrayUtils;

public class StateSaver<T extends Number> implements Computable{
	
	private Parameter<T> linked;
	private Var<BigDecimal> dt;
	private BigDecimal time;
	private List<List<T>> states;
	
	public StateSaver(Parameter<T> linked)
	{
		this.linked= linked;
		this.dt = ((Computable) linked).getDt();
		this.time = BigDecimal.ZERO;
		this.states = new LinkedList<List<T>>();
	}
	
	public double[][] stackStates(){
		int nbState = this.states.size();
		int lengthData = this.states.get(0).size();
		double[][] ret = new double[nbState][lengthData];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = ArrayUtils.listToPrimitiveArray1D(this.states.get(i));
		}
		
		return ret;
	}

	@Override
	public void compute() {
//		System.err.println("state save" + i);
		states.add(linked.getValues());
	}
	
	public List<List<T>> getStates(){
		return states;
	}

	@Override
	public BigDecimal getTime() {
		return time;
	}

	@Override
	public Var<BigDecimal> getDt() {
		return dt;
	}

	@Override
	public void setTime(BigDecimal currentTime) {
		this.time = currentTime;

	}

	public void reset() {
		this.states.clear();
		
	}

	
}
