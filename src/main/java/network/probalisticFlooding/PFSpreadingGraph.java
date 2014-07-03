package main.java.network.probalisticFlooding;

import java.math.BigDecimal;

import main.java.maps.Var;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.packet.Packet;

public class PFSpreadingGraph extends SpreadingGraph<PFNode,DirectedEdge<Packet,PFNode>>{

	public PFSpreadingGraph(Var<BigDecimal> dt) {
		super(dt);
	}

    @Override
    public String getName() {
        return "Probilistic flooding" + super.getName();
    }

}
