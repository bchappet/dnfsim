package main.java.network.rsdnf;

import main.java.network.generic.DirectedEdge;
import main.java.network.generic.Node;

/**
 *
 * @author CARRARA Nicolas
 */
public class RSDNFTransmitter extends Node<Spike, SpikeEdge> {

    private final double weight;

    public RSDNFTransmitter(double weight) {
        super(SpikeEdge.class);
        this.weight = weight;
    }

    public RSDNFTransmitter(double weight, RSDNFTransmitter... neightbors) {
        super(SpikeEdge.class,neightbors);
        this.weight = weight;
    }
    public RSDNFTransmitter(int sizeMax,double weight) {
        super(SpikeEdge.class,sizeMax);
        this.weight = weight;
    }


    public RSDNFTransmitter(int sizeMax,double weight, RSDNFTransmitter... neightbors) {
        super(SpikeEdge.class,sizeMax,neightbors);
        this.weight = weight;
    }

    /**
     * Demande le transfert du premier spike de la list fifo par toutes ses
     * arrêtes. On fait un bernouilli pour savoir si oui ou non on transmet ce
     * spike.Se decharge d'un paquet meme si il n'a pas de voisin.
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
    
    @Override
    public Node<Spike, SpikeEdge> constructTemporary(){
    	return new RSDNFTransmitter(weight);
    }

//    @Override
//    public void prepareBeforeSendParallele() {
//        RSDNFTransmitter tmp = new RSDNFTransmitter(this.weight);
//        tmp.setBufferPacket((LinkedList<Spike>) getBufferPacket().clone());
//        tmp.setEdges(this.getEdges());
//        tmp.setNeighbors(this.getNeighbors());
//        tmp.setEnabled(this.isEnabled());
//        setTemporary(tmp);
//        /**
//         * on retire un packet de la liste (celui envoyé) car c'est temporary
//         * qui va send et pas this.
//         */
//        pollPacket();
//    }

//    @Override
//    public void sendParallele() {
//        getTemporary().send();
//    }

    
    /**
     * @return the inhibitoryWeight
     */
    public double getWeight() {
        return weight;
    }

    public double getEWeight() {
        return weight;
    }

}
