package hardSimulator;

import maps.AbstractUnitMap;
import maps.Parameter;
import cellularAutomata.PRNGWrapperUM;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class RandomGeneratorCAPRNGHUM extends RandomGeneratorHUM {
	//parameters
	public static final int CA_PRNG_MAP = 0;
	public static final int NB_VAL = 1;

	public RandomGeneratorCAPRNGHUM(Parameter dt, Space space,
			Parameter... parameters) {
		super(dt, space, parameters);
		outputs = new double[(int)params.get(NB_VAL).get()];
	}


	@Override
	public double compute() throws NullCoordinateException {
		//TODO validate map computation
		AbstractUnitMap map = (AbstractUnitMap) params.get(CA_PRNG_MAP);
		PRNGWrapperUM um = (PRNGWrapperUM) map.getUnit(space.coordToIndex(coord)).getUnitModel();
		for(int i = 0 ; i < (int)params.get(NB_VAL).get(coord) ; i++){
			outputs[i] = um.getRandomNumber();
		}

		return map.get(coord);
	}


}
