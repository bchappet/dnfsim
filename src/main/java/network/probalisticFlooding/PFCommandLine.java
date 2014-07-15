package main.java.network.probalisticFlooding;

import main.java.network.generic.NetworkCommandLine;

public class PFCommandLine extends NetworkCommandLine{
	public static final String WEIGTH = "weigth";
	public static final String PACKET_INTIALISATION = "packet_initialisation";

	public static final String A_SEND = "a_send";
	public static final String B_SEND =  "b_send";
	public static final String AB_SEND = "ab_send";
	public static final String WRITE_TRANSITION_MATRIX_FILE = "write_transition_matrix_file";

	@Override
	protected String defaultScript() {
		return super.defaultScript() + 
				/* PFCommandLine */
				WEIGTH + "=0.0;" +  
				TRANSITION_MATRIX_FILE + "=PFTransitionMatrixFile;"+
				PACKET_INTIALISATION+ "=" + AB_SEND + ";"+
				WRITE_TRANSITION_MATRIX_FILE + "="+true+";";
		/* NetworkCommandLine */
		//                NETWORK_DT + "=bd0.1,0.01,1,0.01;";
	}


}
