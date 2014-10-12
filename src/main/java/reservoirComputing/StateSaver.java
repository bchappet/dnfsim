package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.sun.org.apache.xpath.internal.SourceTree;
import main.java.maps.Computable;
import main.java.maps.HasChildren;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.resources.utils.ArrayUtils;
/**
 * Row wise collection of states over time by using  stackStates()
 * The parameter is considered as a  row vector
 * @author bchappet
 *
 * @param <T>
 */
public class StateSaver<T extends Number> implements Computable{
	
	private Parameter<T> linked;
	private Var<BigDecimal> dt;
	private BigDecimal time;
	private List<List<T>> states;
    private int i = 0;
	
	public StateSaver(Parameter<T> linked)
	{
		this.linked= linked;
		this.dt = ((Computable) linked).getDt();
		this.time = BigDecimal.ZERO;
		this.states = new LinkedList<List<T>>();
	}
	
	public double[][] stackStates(){
//        System.out.println("Stack trace of " + this.linked.getName() + " class : " + this.linked.getClass());

        int nbState = this.states.size();
        int lengthData = this.states.get(0).size();
//        System.out.println("length : " + lengthData  + "  i= " + i);
        double[][] ret = new double[nbState][lengthData];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = ArrayUtils.listToPrimitiveArray1D(this.states.get(i));
		}

    //    System.out.println("ret " + ArrayUtils.toString(ret));

        return ret;
	}

	@Override
	public void compute() {
	//	System.out.println("state save " + this.linked.getName() + "classe  : " + this.linked.getClass() + " state " + i);
     //   System.out.println(linked.getValues());
        i++;
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
