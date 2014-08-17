package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;

/**
 * The parameters are the choices from which we has to choose value
 * @author bchappet
 *
 * @param <T>
 */
public class RandomlyChoosenFromUniformUM<T> extends UnitModel<T> {
	
	

	public RandomlyChoosenFromUniformUM(T initActivity) {
		super(initActivity);
	}

	@Override
	protected T compute(BigDecimal time, int index, List<Parameter> params) {
		int nbChoice = params.size();
		int choosen = (int)(Math.random()*nbChoice);
		return (T) params.get(choosen).getIndex(index);
		
	}

}
