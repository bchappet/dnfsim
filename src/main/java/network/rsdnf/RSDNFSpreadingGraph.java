package main.java.network.rsdnf;

import java.math.BigDecimal;

import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.StimulisMap;
import main.java.network.generic.packet.Spike;

/**
 * 
 * @author CARRARA Nicolas
 */
public class RSDNFSpreadingGraph extends SpreadingGraph<RSDNFTransmitter,SpikeEdge,Spike> {
	private boolean isFirstComputatution = true;

    public RSDNFSpreadingGraph(Var<BigDecimal> dt,StimulisMap sm,Parameter ... parameters) {
        super(dt,sm,parameters);
    }
    
	
	
	public void compute(){
		if(this.isFirstComputatution){
			this.firstComputation();
			this.isFirstComputatution = false;
		}
		super.compute();
		
	}
	
	public void firstComputation(){
		int n = 10;
		for (int i = 0; i < n; i++) {
			this.getIndex(0).addToFIFO(new Spike());
		}
	}

    @Override
    public String getName() {
        return "RSDNF_" + super.getName();
    }
    
}
