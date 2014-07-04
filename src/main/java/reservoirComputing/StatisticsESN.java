package main.java.reservoirComputing;

import java.math.BigDecimal;

import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.statistics.Statistics;

public class StatisticsESN extends Statistics {

	public static final String ERROR_DIST = "error_dist";
	public static final String TARGET_OUTPUT = ModelESN.TARGET_OUTPUT;
	public static final String OUTPUT = "output";

	public StatisticsESN(String name, Var<BigDecimal> dt,Parameter... trajectoryUnitMap) {
		super(name, dt, trajectoryUnitMap);
	}

}
