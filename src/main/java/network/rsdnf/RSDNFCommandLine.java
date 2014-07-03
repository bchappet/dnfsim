package main.java.network.rsdnf;

import main.java.network.generic.NetworkCommandLine;

/**
 * @author CARRARA Nicolas
 */
public class RSDNFCommandLine extends NetworkCommandLine {

    public static String WEIGTH = "w_rsdnf";
    public static String TRANSMITTER_BY_NEURON = "transmitter_by_neuron";
    public static String WIDTH_NEURON_MAP ="width_neuron_map";
    

    @Override
    protected String defaultScript() {
        return super.defaultScript() + 
        		/* RSDNFCommandLine */
                WEIGTH + "=0.0;" + 
                TRANSMITTER_BY_NEURON + "=4;"+
                WIDTH_NEURON_MAP + "=10;"+
        		/* NetworkCommandLine */
                TRANSITION_MATRIX_FILE + "=RSDNFNetworkTransitionMatrix.rsdnf;" + 
                NETWORK_DT + "=bd0.1,0.01,1,0.01;";
    }

}
