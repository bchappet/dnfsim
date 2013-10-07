package unitModel;

import maps.Parameter;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class Sum extends UnitModel {

	public Sum(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	public Sum() {
		super();
	}

	@Override
	public double compute() throws NullCoordinateException {
		double ret = 0;
		//System.out.println("params : " + params);
		for(Parameter p : params)
		{//System.out.print( "; " + ret +" += " +p.get(coord));
			ret += p.get(coord);
		}
               
                return ret;
	}

}
