package main.java.network.probalisticFlooding;

import main.java.network.generic.NetworkCommandLine;

public class PFCommandLine extends NetworkCommandLine{
	public static String WEIGTH = "w_pf";
	
	
	@Override
    protected String defaultScript() {
        return super.defaultScript() + 
        		/* PFCommandLine */
                WEIGTH + "=0.0;" ;//+ 
        		/* NetworkCommandLine */
//                NETWORK_DT + "=bd0.1,0.01,1,0.01;";
    }
	
	
}
