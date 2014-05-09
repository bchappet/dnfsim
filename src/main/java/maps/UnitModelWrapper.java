package main.java.maps;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.unitModel.UnitModel;

/**
 * Wrap a parameter to acces it through a UnitModel 
 * For instance, if we need to construct a neighborhood using a Matrix,
 * we can create a UnitModelWrapper with the Matrix as parameter, then construct a Map with this UnitModelWrapper
 * @author bchappet
 *
 */
public class UnitModelWrapper extends UnitModel {

	/**The linked param**/
	public static final int LINKED = 0;

	public UnitModelWrapper(Var dt, Space space, Parameter parameter) {
		super(dt, space, parameter);
	}


	@Override
	public double compute() throws NullCoordinateException {
		return params.getIndex(LINKED).getIndex(coord);
	}

}
