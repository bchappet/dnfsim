package main.java.unitModel;

import java.util.Iterator;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.ParameterUser;
import main.java.maps.Unit;
import main.java.maps.UnitMap;
import main.java.maps.Var;
/**
 * Aggregate the values from on parameter of higher dimensional main.java.space to this main.java.space
 * Sum
 * @author bchappet
 *
 */
public class AggregationUM extends UnitModel {
	protected static final int TO_AGGREGATE = 0;

	public AggregationUM() {
	}

	public AggregationUM(ParameterUser paramUser) {
		super(paramUser);
	}

	public AggregationUM(Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	public AggregationUM(Var dt, Space space, UnitModel... unitModels) {
		super(dt, space, unitModels);
	}

	@Override
	public double compute() throws NullCoordinateException {

		Parameter to_aggregate = getParam(TO_AGGREGATE);
		double sum = 0;
		if( to_aggregate instanceof UnitMap){
			Iterator<Unit> it = ((UnitMap)to_aggregate).getComputationIterator();
			while(it.hasNext() ){
				double val = it.next().get();
				sum += val;
			}
		}else{
			for(int i = 0 ; i < to_aggregate.getSpace().getDiscreteVolume() ; i++){
				sum += to_aggregate.getIndex(i);
			}
		}
		

		return sum;

	}

}
