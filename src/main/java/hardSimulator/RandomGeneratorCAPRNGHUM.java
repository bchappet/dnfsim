package main.java.hardSimulator;

import main.java.cellularAutomata.PRNGWrapperUM;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.UnitMap;

public class RandomGeneratorCAPRNGHUM extends RandomGeneratorHUM {
	//parameters
	public static final int CA_PRNG_MAP = 0;
	public static final int NB_VAL = 1;

	public RandomGeneratorCAPRNGHUM(Parameter dt, Space space,
			Parameter... parameters) {
		super(dt, space, parameters);
		outputs = new double[(int)params.getIndex(NB_VAL).get()];
	}


	@Override
	public double compute() throws NullCoordinateException {
		//TODO validate map computation
		UnitMap map = (UnitMap) params.getIndex(CA_PRNG_MAP);
		PRNGWrapperUM um = (PRNGWrapperUM) map.getUnit(space.coordIntToIndex(coord)).getUnitModel();
		for(int i = 0 ; i < (int)params.getIndex(NB_VAL).getIndex(coord) ; i++){
			outputs[i] = um.getRandomNumber();
		}

		return map.get(coord);
	}


}
