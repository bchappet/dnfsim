package hardSimulator;

import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;
import coordinates.Space;

public class SpikingNeuronCAPRNGUHM extends SpikingNeuronHUM {
	
	//Parameter
	protected static final int CA_PRNG_MAP = 11;
	

	public SpikingNeuronCAPRNGUHM(Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	
	public SpikingNeuronCAPRNGUHM() {
		super();
	}


	protected void initSubUnits(){
		

		UnitModel sp = new SpikingUnitHUM(dt,space,
				params.get(COMPUTE_CLK),
				params.get(EXC_WEIGHT),
				params.get(INH_WEIGHT),
				params.get(THRESHOLD),
				params.get(PRECISION),
				params.get(INPUT),
				params.get(TAU),
				params.get(INTEGRATION_DT));
		UnitModel excN = new NeuronHUM(dt,space,params.get(EXC_PROBA),params.get(PRECISION),params.get(NB_SPIKE),params.get(COMPUTE_CLK));
		UnitModel inhN = new NeuronHUM(dt,space,params.get(INH_PROBA),params.get(PRECISION),params.get(NB_SPIKE),params.get(COMPUTE_CLK));
		RandomGeneratorHUM rg = new RandomGeneratorCAPRNGHUM(dt,space,params.get(CA_PRNG_MAP));
		addSubUnits(sp,excN,inhN,rg);
	}
	
	

}
