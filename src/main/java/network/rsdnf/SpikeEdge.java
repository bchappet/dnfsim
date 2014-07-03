package main.java.network.rsdnf;

import main.java.network.generic.DirectedEdge;
import main.java.network.generic.packet.Spike;

/**
 * 
 * @author CARRARA Nicolas
 */
public class SpikeEdge extends DirectedEdge<Spike,RSDNFTransmitter>  {

    public SpikeEdge(RSDNFTransmitter sender, RSDNFTransmitter receiver) {
        super(sender, receiver);
    }
    
}
