package unitModel;

import maps.AbstractUnitMap;
import maps.Parameter;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * memory should be initialized
 * @author bchappet
 *
 */
public class SumAndResetUM extends Sum {
	
	public SumAndResetUM(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	public SumAndResetUM() {
		super();
	}
	
	@Override
	public double compute() throws NullCoordinateException {
		double ret = 0;
		//System.out.print("Sum : ("+name+") "  );
		for(Parameter p : params)
		{
			//System.out.print( "; " + ret +" += " +p.get(coord));
			
			//Reset activity as we get it
			ret += p.get(coord);
			((AbstractUnitMap)p).getUnit(space.coordToIndex(coord)).reset();
		}
		return ret;
	}

}
