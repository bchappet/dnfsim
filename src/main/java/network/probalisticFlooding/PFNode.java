package main.java.network.probalisticFlooding;

import main.java.network.generic.DirectedEdge;
import main.java.network.generic.Node;
import main.java.network.generic.packet.Packet;

public class PFNode extends Node<Packet,DirectedEdge<Packet,PFNode>> {

	private double weight;
	
	public PFNode(double weight) {
        super(DirectedEdge.class);
        this.weight = weight;
    }

    public PFNode(double weight, PFNode... neightbors) {
        super(DirectedEdge.class,neightbors);
        this.weight = weight;
    }
    public PFNode(int sizeMax,double weight) {
        super(DirectedEdge.class,sizeMax);
        this.weight = weight;
    }


    public PFNode(int sizeMax,double weight, PFNode... neightbors) {
        super(DirectedEdge.class,sizeMax,neightbors);
        this.weight = weight;
    }
	
	@Override
    public void send() {
        if (isEnabled()) {
            Packet p = pollPacket();
            if (p != null) {
            	double proba = Math.random();
                if ((proba > weight)) {
                    for (DirectedEdge<Packet,PFNode> e : getEdges()) {
                        e.transfer(p);
                    }
                }
            }
        }
    }
	
	@Override
	public Node constructTemporary(){
		return new PFNode(weight);
	}
	

}
