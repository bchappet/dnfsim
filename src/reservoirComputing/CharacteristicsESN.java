package reservoirComputing;

import maps.TrajectoryUnitMap;
import coordinates.Space;
import statistics.Characteristics;
import statistics.Statistics;
import statistics.StatisticsCNFT;

public class CharacteristicsESN extends Characteristics {
	
	public static final String NRMSE = "nrmse";

	public CharacteristicsESN(Space space, Statistics stats,
			TrajectoryUnitMap... params) {
		super(space, stats, params);
	}

}
