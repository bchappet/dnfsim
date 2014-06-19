package main.java.network.rsdnf;

import main.java.maps.Var;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.Node;

/**
 *
 * @author CARRARA Nicolas
 */
public class RSDNFTransmitter extends Node<Spike, SpikeEdge> {

    private final double weight;

    public RSDNFTransmitter(boolean constructPrevious,double weight) {
        super(constructPrevious);
        this.weight = weight;
    }

    public RSDNFTransmitter(boolean constructPrevious,Var<Double> weight) {
        super(constructPrevious);
        this.weight = weight.get();
    }

    public RSDNFTransmitter(boolean constructPrevious,double weight, RSDNFTransmitter... neightbors) {
        super(constructPrevious,neightbors);
        this.weight = weight;
    }

    public RSDNFTransmitter(boolean constructPrevious,Var<Double> weight, RSDNFTransmitter... neightbors) {
        super(constructPrevious,neightbors);
        this.weight = weight.get();
    }

    /**
     * Demande le transfert du premier spike de la list fifo par toutes ses
     * arrêtes. On fait un bernouilli pour savoir si oui ou non on transmet ce
     * spike.
     */
    @Override
    public void send() {
        if (isEnabled()) {
            Spike s = pollPacket();
            if (s != null) {
                if ((Math.random() > weight)) {
                    for (DirectedEdge e : getEdges()) {
                        e.transfer(s);
                    }
                }
            }
        }
    }

    /**
     * @return the inhibitoryWeight
     */
    public double getWeight() {
        return weight;
    }

    public double getEWeight() {
        return weight;
    }

    @Override
    protected Node<Spike, SpikeEdge> constructPrevious() {
        RSDNFTransmitter clone = new RSDNFTransmitter(false,this.weight);
        clone.setBufferPacket(this.getBufferPacket());
        clone.setEdges(this.getEdges());
        clone.setNeighbors(this.getNeighbors());
        clone.setEnabled(this.isEnabled());
        return clone;
    }

}
