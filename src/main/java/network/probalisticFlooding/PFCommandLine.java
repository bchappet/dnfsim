package main.java.network.probalisticFlooding;

import main.java.network.generic.NetworkCommandLine;

public class PFCommandLine extends NetworkCommandLine{
	public static final String WEIGTH = "w_pf";
//	public static final String INTIALISATION_GRAPH = "init";
	
//	public static final String DEFAULT_INITAILISATION = "di";
//	public static final String TWO_CORNER = "tc";
//	public static final String FOUR_CORNER ="fc";
//	public static final String FOUR_CORNER_WITH_PROBA = "fcwp";
	
	
	@Override
    protected String defaultScript() {
        return super.defaultScript() + 
        		/* PFCommandLine */
                WEIGTH + "=0.0;" +
        		TRANSITION_MATRIX_FILE + "=src/main/java/network/ressource/1.pf;";
//                +
//                INTIALISATION_GRAPH + DEFAULT_INITAILISATION;
        		
        		/* NetworkCommandLine */
//                NETWORK_DT + "=bd0.1,0.01,1,0.01;";
    }
	
	
}
