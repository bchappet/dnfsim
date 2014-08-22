package main.java.unitModel;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;

/**
 * Return tanh of param
 * @author bchappet
 *
 */
public class TanhUM extends UnitModel<Double> {

	protected static final int PARAM = 0;
	public TanhUM(Double initActivity) {
		super(initActivity);
	}

	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		return Math.tanh((double) params.get(PARAM).getIndex(index));
	}

}
