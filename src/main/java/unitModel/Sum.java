package main.java.unitModel;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;

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
			ret += p.getIndex(coord);
		}
               
                return ret;
	}

}
