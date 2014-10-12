package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.unitModel.UnitModel;
import main.resources.utils.MathUtils;

/**
 * tanh⁻¹
 * @author bchappet
 *
 */
public class TanhInvUM extends UnitModel<Double> {
	protected static final int PARAM = 0;
	public TanhInvUM(Double initActivity) {
		super(initActivity);
	}
	
	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		return MathUtils.artanh((double) params.get(PARAM).getIndex(index));
	}

}
