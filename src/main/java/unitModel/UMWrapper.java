package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
/**
 * This unit main.java.model only copy the value of the given Var
 * @author bchappet
 *
 * @param <T>
 * @param <C>
 */
public class UMWrapper<T> extends UnitModel<T> {
	
	//Parameter
	/**Var copied into the UnitModel @Precond should be {@link SingleValueParam} **/
	public static final int VAR = 0;

	public UMWrapper(T initActivity) {
		super(initActivity);
	}

	@Override
	public T compute(BigDecimal time,int index,List<Parameter> params) {
		return (T) ((SingleValueParam<T>) params.get(VAR)).get();
	}

}
