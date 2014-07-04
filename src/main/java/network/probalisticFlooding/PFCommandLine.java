package main.java.network.probalisticFlooding;

import main.java.network.generic.NetworkCommandLine;

public class PFCommandLine extends NetworkCommandLine{
	public static String WEIGTH = "weigth";
	
	
	@Override
    protected String defaultScript() {
        return super.defaultScript() + 
        		/* PFCommandLine */
                WEIGTH + "=0.0;" +  
        		TRANSITION_MATRIX_FILE + "=src/main/java/network/ressource/1.pf;";
        		/* NetworkCommandLine */
//                NETWORK_DT + "=bd0.1,0.01,1,0.01;";
    }
	
	
}
