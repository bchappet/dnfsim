package reservoirComputing;

import maps.Var;
import coordinates.Space;
import statistics.StatMap;
import statistics.Statistics;

public class StatisticsESN extends Statistics {

	public static final String ERROR_DIST = "error_dist";
	public static final String TARGETED_OUTPUT = "targeted_output";
	public static final String OUTPUT = "output";

	public StatisticsESN(String name, Var dt, Space space,
			StatMap... trajectoryUnitMap) {
		super(name, dt, space, trajectoryUnitMap);
	}

}
