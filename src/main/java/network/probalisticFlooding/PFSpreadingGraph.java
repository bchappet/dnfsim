package main.java.network.probalisticFlooding;

import java.math.BigDecimal;

import main.java.maps.Var;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.packet.IPv4Datagramme;
import main.java.network.generic.packet.Packet;

public class PFSpreadingGraph extends SpreadingGraph<PFNode,DirectedEdge<Packet,PFNode>>{

	private boolean isFirstComputatution = true;
	
	/*private final String initialisation;*/
	
	public PFSpreadingGraph(Var<BigDecimal> dt/*, String intialisation*/) {
		super(dt);
		/*this.initialisation = intialisation;*/
	}
	
	
	
	public void compute(){
		if(this.isFirstComputatution){
			this.firstComputation();
			this.isFirstComputatution = false;
		}
		super.compute();
		
	}
	
	public void firstComputation(){
//		switch(initialisation){
//		case PFCommandLine.DEFAULT_INITAILISATION :
//			break;
//		case PFCommandLine.TWO_CORNER :
//			break;
//		case PFCommandLine.FOUR_CORNER :
//			break;
//		case PFCommandLine.FOUR_CORNER_WITH_PROBA:
//			break;
//		}
		for (int i = 0; i < 10; i++) {
			
			this.getIndex(0).addToFIFO(new IPv4Datagramme("first"));
		}
		
		
		
		
//		for (int i = 0; i < 10; i++) {
//			
//			this.getIndex(0).addToFIFO(new IPv4Datagramme("first"));
//			this.getIndex(0+8).addToFIFO(new IPv4Datagramme("first"));
//			this.getIndex(9*8).addToFIFO(new IPv4Datagramme("first"));
//			this.getIndex(0+8+9*8).addToFIFO(new IPv4Datagramme("first"));
//		}
	}
	
	public void reset(){
		super.reset();
		this.isFirstComputatution = true;
	}

    @Override
    public String getName() {
        return "Probabilistic flooding" + super.getName();
    }

}
