package main.java.network.rsdnf;

import main.java.network.generic.NetworkCommandLine;

/**
 * @author CARRARA Nicolas
 */
public class RSDNFCommandLine extends NetworkCommandLine {

    public static String WEIGTH = "w";
    public static String INIBITORY_WEIGTH = "i_w";
    public static String EXITATORY_WEIGTH = "e_w";
    public static String PATH_RSDNF_FILE = "path_rsdnf";

    @Override
    protected String defaultScript() {
        return super.defaultScript() + 
                WEIGTH + "=0.0;" + 
                PATH_RSDNF_FILE + "=RSDNFNetworkTransitionMatrix.rsdnf;" + 
                INIBITORY_WEIGTH + "=0.2;" + 
                EXITATORY_WEIGTH + "=0.4;";
    }

}
