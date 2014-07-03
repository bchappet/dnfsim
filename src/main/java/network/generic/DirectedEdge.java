package main.java.network.generic;

import main.java.network.generic.packet.Packet;
import main.java.network.probalisticFlooding.PFNode;

/**
 * 
 * @author CARRARA Nicolas
 * @param <P>
 * @param <N> 
 */
public class DirectedEdge<P extends Packet,N extends Node<P,?>> {

//    private DirectedEdge<P,N> previous;
    
    private final N sender;
    
    private final N receiver;
        
    private int bandWidth;

    private boolean enabled;

    public DirectedEdge(N sender, N receiver){
        this.sender = sender;
        this.receiver = receiver;
    }
    
//    public DirectedEdge(PFNode sender, PFNode receiver){
//        this.sender = sender;
//        this.receiver = receiver;
//    }
    
    public void transfer(P p) {
        getReceiver().receive(p);
    }

//    /**
//     * @return the previous
//     */
//    public DirectedEdge<P,N> getPrevious() {
//        return previous;
//    }
//
//    /**
//     * @param previous the previous to set
//     */
//    public void setPrevious(DirectedEdge<P,N> previous) {
//        this.previous = previous;
//    }

    /**
     * @return the sender
     */
    public N getSender() {
        return sender;
    }

    /**
     * @return the receiver
     */
    public N getReceiver() {
        return receiver;
    }

    /**
     * @return the bandWidth
     */
    public int getBandWidth() {
        return bandWidth;
    }

    /**
     * @param bandWidth the bandWidth to set
     */
    public void setBandWidth(int bandWidth) {
        this.bandWidth = bandWidth;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    
    
}
