package main.java.unitModel;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.UnitMap;
import main.java.maps.Var;

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
			ret += p.getIndex(coord);
			((UnitMap)p).getUnit(space.coordToIndex(coord)).reset();
		}
		return ret;
	}

}
