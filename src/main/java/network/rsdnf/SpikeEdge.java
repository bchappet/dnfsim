package main.java.network.rsdnf;

import main.java.network.generic.DirectedEdge;

/**
 * 
 * @author CARRARA Nicolas
 */
public class SpikeEdge extends DirectedEdge<Spike,RSDNFTransmitter>  {

    public SpikeEdge(RSDNFTransmitter sender, RSDNFTransmitter receiver) {
        super(sender, receiver);
    }
    
}
