package hardSimulator;

import maps.AbstractUnitMap;
import maps.Parameter;
import cellularAutomata.PRNGWrapperUM;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class RandomGeneratorCAPRNGHUM extends RandomGeneratorHUM {
	//parameters
	public static final int CA_PRNG_MAP = 0;

	public RandomGeneratorCAPRNGHUM(Parameter dt, Space space,
			Parameter... parameters) {
		super(dt, space, parameters);
	}


	@Override
	public double compute() throws NullCoordinateException {
		//TODO validate map computation
		AbstractUnitMap map = (AbstractUnitMap) params.get(CA_PRNG_MAP);
		PRNGWrapperUM um = (PRNGWrapperUM) map.getUnit(space.coordToIndex(coord)).getUnitModel();
		for(int i = 0 ; i < 8 ; i++){
			outputs[i] = um.getRandomNumber();
		}

		return map.get(coord);
	}


}
