package main.java.network.probalisticFlooding;

import main.java.maps.Var;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.Node;
import main.java.network.generic.packet.Packet;

public class PFNode<P extends Packet> extends Node<P,DirectedEdge<P,PFNode<P>>> {

	private Var<Double> weight;
	
	public PFNode( Var<Double>  weight) {
        super(DirectedEdge.class);
        this.weight = weight;
    }

    public PFNode( Var<Double> weight, PFNode... neightbors) {
        super(DirectedEdge.class,neightbors);
        this.weight = weight;
    }
    public PFNode(int sizeMax, Var<Double> weight) {
        super(DirectedEdge.class,sizeMax);
        this.weight = weight;
    }


    public PFNode(int sizeMax, Var<Double> weight, PFNode... neightbors) {
        super(DirectedEdge.class,sizeMax,neightbors);
        this.weight = weight;
    }
	
	@Override
    public void send() {
        if (isEnabled()) {
            P p = pollPacket();
            if (p != null) {
            	double proba = Math.random();
                if ((proba > weight.get())) {
                    for (DirectedEdge<P,PFNode<P>> e : getEdges()) {
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
