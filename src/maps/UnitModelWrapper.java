package maps;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

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
		return params.get(LINKED).get(coord);
	}

}
