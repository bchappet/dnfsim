package main.java.reservoirComputing;

import main.java.maps.Trajectory;
import main.java.statistics.Characteristics;

public class CharacteristicsESN extends Characteristics {
	
	public static final String NRMSE = "nrmse";

	public CharacteristicsESN( Trajectory<Double>... params) {
		super( params);
	}

}
