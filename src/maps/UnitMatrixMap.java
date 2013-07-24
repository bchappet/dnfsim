package maps;

import java.util.ArrayList;

import unitModel.ConstantUnit;
import coordinates.NullCoordinateException;
import coordinates.Space;
/**
 * Init a UnitMap with a matrix of values
 * Implementation with an ArrayList
 * Utility for test only
 * @author bchappet
 *
 */
public class UnitMatrixMap extends AbstractUnitMap {

	protected double[] values;

	public UnitMatrixMap(String name, Var dt, Space space,
			double[] values) throws NullCoordinateException {
		super(name,null, dt, space);
		this.values = values;
		constructMemory();
	}

	@Override
	public void constructMemory() throws NullCoordinateException {
		if(!isMemory)
		{
			this.units =  new ArrayList<Unit>();
			for(int i = 0 ; i < values.length ; i++)
			{
				units.add(new Unit(new ConstantUnit(dt, space,new Var(values[i]))));
			}
			this.isMemory = true;
		}

	}
	

	@Override
	public void compute() throws NullCoordinateException {
		//Nothing
	}

}
