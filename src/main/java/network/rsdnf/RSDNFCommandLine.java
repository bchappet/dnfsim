package main.java.network.rsdnf;

import main.java.network.generic.NetworkCommandLine;

/**
 * @author CARRARA Nicolas
 */
public class RSDNFCommandLine extends NetworkCommandLine {

    public static String WEIGTH = "w_rsdnf";
    public static String PATH_RSDNF_FILE = "path_rsdnf";
    public static String DT = "dt_rsdnf";

    @Override
    protected String defaultScript() {
        return super.defaultScript() + 
                WEIGTH + "=0.0;" + 
                PATH_RSDNF_FILE + "=RSDNFNetworkTransitionMatrix.rsdnf;" + 
                DT + "=bd0.1,0.01,1,0.01;";
    }

}
