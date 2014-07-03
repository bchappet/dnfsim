package main.java.network.generic;

import main.java.console.CommandLine;

/**
 * @todo @author CARRARA Nicolas
 */
public class NetworkCommandLine extends CommandLine {

//	public static String STOP_CONDITION_ITERATE_VALUE = "v";
	public static String NETWORK_DT = "network_dt";
	public static String TRANSITION_MATRIX_FILE = "transition_matrix_file";
	public static String SIZE = "size";
	
	
	public static String NO_TRANSITION_FILE = "noFile";
	
	@Override
	protected String defaultScript() {
		return super.defaultScript() /*+ STOP_CONDITION_ITERATE_VALUE + "=10;"*/+
				TRANSITION_MATRIX_FILE+"="+NO_TRANSITION_FILE+";"+
				NETWORK_DT+ "=bd0.1;"+
				SIZE+"=3;";
	}

}
