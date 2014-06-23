package main.java.network.generic;

import main.java.console.CommandLine;

/**
 * @todo @author CARRARA Nicolas
 */
public class NetworkCommandLine extends CommandLine {

    public static String STOP_CONDITION_ITERATE_VALUE = "v";

    @Override
    protected String defaultScript() {
        return super.defaultScript() + STOP_CONDITION_ITERATE_VALUE + "=10;";
    }

}
