package main.java.network.probalisticFlooding;

import java.math.BigDecimal;

import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.Var;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.packet.IPv4Datagramme;
import main.java.network.generic.packet.Packet;

public class PFSpreadingGraph extends SpreadingGraph<PFNode,DirectedEdge<Packet,PFNode>>{

	private boolean isFirstComputatution = true;
	
	public PFSpreadingGraph(Var<BigDecimal> dt) {
		super(dt);
	}
	
	
	
	public void compute(){
		if(this.isFirstComputatution){
			this.firstComputation();
			this.isFirstComputatution = false;
		}
		super.compute();
		
	}
	
	public void firstComputation(){
		for (int i = 0; i < 10; i++) {
			
			this.getIndex(0).addToFIFO(new IPv4Datagramme("first"));
		}
	}

    @Override
    public String getName() {
        return "Probilistic flooding" + super.getName();
    }

}
