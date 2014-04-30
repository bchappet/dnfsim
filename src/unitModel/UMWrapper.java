package unitModel;

import java.util.List;

import maps.NoDimension;
import maps.Parameter;
import coordinates.NullCoordinateException;
/**
 * This unit model only copy the value of the given Var
 * @author bchappet
 *
 * @param <T>
 * @param <C>
 */
public class UMWrapper<T> extends UnitModel<T> {
	
	//Parameter
	/**Var copied into the UnitModel @Precond should be {@link NoDimension} **/
	public static final int VAR = 0;

	public UMWrapper(T initActivity) {
		super(initActivity);
	}

	@Override
	public T compute(List<Parameter<T>> params) {
		return ((NoDimension<T>)params.get(VAR)).get();
	}

}
