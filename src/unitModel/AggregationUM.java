package unitModel;

import java.util.Iterator;

import maps.AbstractUnitMap;
import maps.Map;
import maps.Parameter;
import maps.ParameterUser;
import maps.Unit;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;
/**
 * Aggregate the values from on parameter of higher dimensional space to this space
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
		if( to_aggregate instanceof AbstractUnitMap){
			Iterator<Unit> it = ((AbstractUnitMap)to_aggregate).getComputationIterator();
			while(it.hasNext() ){
				double val = it.next().get();
				sum += val;
			}
		}else{
			for(int i = 0 ; i < to_aggregate.getSpace().getDiscreteVolume() ; i++){
				sum += to_aggregate.get(i);
			}
		}
		

		return sum;

	}

}
