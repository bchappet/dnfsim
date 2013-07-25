package hardSimulator;

import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;
import utils.Hardware;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class SpikingUnitHUM extends UnitModel {

	//Parameter 
	protected static int COMPUTE_CLK = 0;
	protected static int EXC_WEIGHT = 1;
	protected static int INH_WEIGHT = 2;
	protected static int THRESHOLD = 3;
	protected static int FRAC = 4;
	protected static int INPUT = 5;
	protected static int TAU = 6;
	protected static int INTEGRATION_DT = 7;

	//clk handling
	private	Var clk;

	//input
	protected int[] inPortsExc;
	protected int[] inPortsInh;
	//output
	protected int activate;

	//var
	protected Var summedExc; 
	protected Var summedInh;
	protected Var potential;
	
	//displayed result
	protected Var activation;

	public SpikingUnitHUM(Parameter dt,Space space,Parameter ... params2){
		super(dt,space,params2);
		
		activate = 0;
		potential = new Var(0);
		summedExc = new Var(0);
		summedInh = new Var(0);
		clk = new Var(0);
		activation = new Var(0);

		inPortsExc = new int[4];
		inPortsInh = new int[4];
	}
	
	public SpikingUnitHUM clone(){
		SpikingUnitHUM clone =(SpikingUnitHUM) super.clone();
		
		clone.potential = this.potential.clone();
		clone.summedExc = this.summedExc.clone();
		clone.summedInh = this.summedInh.clone();
		clone.clk = this.clk.clone();
		clone.activation = this.activation.clone()	;
		
		return clone;
	}
	
	@Override
	public SpikingUnitHUM clone2(){
		SpikingUnitHUM clone =(SpikingUnitHUM) super.clone2();
//		clone.inPortsExc = new int[4];
//		clone.inPortsInh = new int[4];

		clone.potential = this.potential;
		clone.summedExc = this.summedExc;
		clone.summedInh = this.summedInh;
		clone.clk = this.clk;
		clone.activation = this.activation;

		return clone;

	}
	@Override
	public void setCoord(Double... coord){
		super.setCoord(coord);
	}

	@Override
	public double compute() throws NullCoordinateException {
		
		int compute_clk =  (int) params.get(COMPUTE_CLK).get(coord);
		double exc_weight = params.get(EXC_WEIGHT).get(coord);
		double inh_weight =  params.get(INH_WEIGHT).get(coord);
		double threshold =  params.get(THRESHOLD).get(coord);

		int nbExc = TransmitterHUM.nbActiveInput(inPortsExc);
		int nbInh = TransmitterHUM.nbActiveInput(inPortsInh);

//		System.out.println("exc_weight : " + exc_weight);
		summedExc.addThis(exc_weight * nbExc);
//		System.out.println("nbExc : " + nbExc + " summedExc : " + summedExc.get());
		summedInh.addThis(inh_weight * nbInh);


		activate = 0;

		if((int)clk.get() % compute_clk == 0){
//			System.out.println("Compute clk");

			double input = params.get(INPUT).get(coord);
			double dt_tau = params.get(INTEGRATION_DT).get()/params.get(TAU).get();
			double div = Hardware.shiftRight(potential.get(),1/dt_tau,(int)params.get(FRAC).get());//simulate fp bit shifting
//			System.out.println("div : " + div);
			double sum = input - div;
//			System.out.println("sum : " + sum);
			
			
			//we applied standard spiking cnft equation : p = p + dt/tau(-p +i) + 1/tau*spikes
			potential.addThis(sum + summedExc.get() - summedInh.get());
//			System.out.println("Potential : " + potential.get());
			
			//potential = max(0,potential)
			if(potential.get() < 0d){
				potential.set(0);
			}

			if(potential.get() >= threshold){
				activate = 1;
				activation.set(1);
				potential.set(0);
			}else{
				activate = 0;
				activation.set(0);
			}

			summedExc.set(0);
			summedInh.set(0);
		}


		clk.set(clk.get()+1);
		return activation.get();
	}









	public void setInPortsExc(int[] inPortsExc2) {
		this.inPortsExc = inPortsExc2;

	}

	public void setInPortsInh(int[] inPortsInh2) {
		this.inPortsInh = inPortsInh2;

	}




	public int getActivate() {
		return activate;
	}




	public Var getPotential() {
		return potential;
	}






}
