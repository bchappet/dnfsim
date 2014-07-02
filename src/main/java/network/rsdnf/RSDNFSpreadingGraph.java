package main.java.network.rsdnf;

import java.math.BigDecimal;
import main.java.maps.Var;
import main.java.network.generic.SpreadingGraph;

/**
 * 
 * @author CARRARA Nicolas
 */
public class RSDNFSpreadingGraph extends SpreadingGraph<RSDNFTransmitter,SpikeEdge> {

    public RSDNFSpreadingGraph(Var<BigDecimal> dt) {
        super(dt);
    }

    @Override
    public String getName() {
        return "RSDNF " + super.getName();
    }
    
}
