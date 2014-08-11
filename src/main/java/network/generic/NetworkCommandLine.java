package main.java.network.generic;

import main.java.console.CommandLine;

/**
 * @todo @author CARRARA Nicolas
 */
public class NetworkCommandLine extends CommandLine {

//	public static String STOP_CONDITION_ITERATE_VALUE = "v";
	public static final String NETWORK_DT = "network_dt";
	public static final String TRANSITION_MATRIX_FILE = "transition_matrix_file";
	public static final String SIZE = "size";
	public static final String STIMULIS_FILE = "stimulis_file";
	public static final String STIMULIS_DT = "stimulis_dt";
	
	
	public static final String NO_TRANSITION_FILE = "noFile";
	public static final String DEFAULT_STIMULIS = "statistiques/stimulis/default.stimulis";
	
	@Override
	protected String defaultScript() {
		return super.defaultScript() /*+ STOP_CONDITION_ITERATE_VALUE + "=10;"*/+
				TRANSITION_MATRIX_FILE+"="+NO_TRANSITION_FILE+";"+
				NETWORK_DT+ "=bd0.1;"+
				SIZE+"=3;"+
				STIMULIS_FILE+"="+DEFAULT_STIMULIS+";"+
				STIMULIS_DT+"="+"bd0.1;";
	}

}
