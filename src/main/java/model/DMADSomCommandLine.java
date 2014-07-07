package main.java.model;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;

/**
 * Define parameters to provide
 * @author benoit
 *
 */
public class DMADSomCommandLine extends CommandLine {

	
	public static final String RESOLUTION = "resolution";
	public static final String LATERAL_WEIGHTS = "lateral_weights_init";
	public static final String LATERAL_ALPHA = "lateral_alpha";
	public static final String SIGMA = "sigma";//the listening distance
	public static final String LATERAL_LEARNING_RATE = "lateral_learning_rate";
	public static final String AFFERENT_LEARNING_RATE = "afferent_learning_rate";
	
	
	public static final String DT_GLOBAL = "dt_global";
	
	
	public DMADSomCommandLine() throws CommandLineFormatException {
		super();
	}
	
	protected  String defaultScript()
	{
		return super.defaultScript() + ""; //TODO
	}

}
