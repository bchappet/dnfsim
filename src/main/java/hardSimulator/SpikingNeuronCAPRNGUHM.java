package main.java.hardSimulator;

import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.unitModel.UnitModel;

public class SpikingNeuronCAPRNGUHM extends SpikingNeuronHUM {
	
	//Parameter
	protected static final int CA_PRNG_MAP = 11;
	public static final int NB_VAL = 12;
	

	public SpikingNeuronCAPRNGUHM(Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	
	public SpikingNeuronCAPRNGUHM() {
		super();
	}


	protected void initSubUnits(){
		

		UnitModel sp = new SpikingUnitHUM(dt,space,
				params.getIndex(COMPUTE_CLK),
				params.getIndex(EXC_WEIGHT),
				params.getIndex(INH_WEIGHT),
				params.getIndex(THRESHOLD),
				params.getIndex(BUFFER_WIDTH),
				params.getIndex(INPUT),
				params.getIndex(TAU),
				params.getIndex(INTEGRATION_DT));
		UnitModel excN = new NeuronHUM(dt,space,params.getIndex(EXC_PROBA),params.getIndex(BUFFER_WIDTH),params.getIndex(NB_SPIKE),params.getIndex(COMPUTE_CLK));
		UnitModel inhN = new NeuronHUM(dt,space,params.getIndex(INH_PROBA),params.getIndex(BUFFER_WIDTH),params.getIndex(NB_SPIKE),params.getIndex(COMPUTE_CLK));
		RandomGeneratorHUM rg = new RandomGeneratorCAPRNGHUM(dt,space,params.getIndex(CA_PRNG_MAP),params.getIndex(NB_VAL));
		addSubUnits(sp,excN,inhN,rg);
	}
	
	

}
