package main.java.network.rsdnf;

import main.java.network.generic.SpreadingGraph;

/**
 * 
 * @author CARRARA Nicolas
 */
public class RSDNFSpreadingGraph extends SpreadingGraph<RSDNFTransmitter,SpikeEdge> {

    @Override
    public String getName() {
        return "RSDNF " + super.getName();
    }
    
}
