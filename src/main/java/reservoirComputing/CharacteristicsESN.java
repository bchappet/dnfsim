package main.java.reservoirComputing;

import main.java.maps.Trajectory;
import main.java.statistics.Characteristics;
import main.java.statistics.Statistics;

public class CharacteristicsESN extends Characteristics {
	
	public static final String NRMSE = "nrmse";

	public CharacteristicsESN( Trajectory<Double>... params) {
		super( params);
	}

}
