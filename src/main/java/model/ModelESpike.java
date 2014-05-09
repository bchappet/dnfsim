package main.java.model;


import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.Var;
import main.java.unitModel.ExponentialND;
import main.java.unitModel.Sum;

/**
 * Only the lateral weights change, therefore we introduce probability parameter instead of 
 * the weight parameter
 * @author bchappet
 *
 */
public class ModelESpike extends ModelGSpike {



	

	public ModelESpike(String name) {
		super(name);
	}


	/**Updatable accessed by the main.java.model**/


	protected Parameter hppa;
	protected Parameter hppb;


	@Override
	protected void initLateralWeightParams(final Space extendedSpace) throws CommandLineFormatException
	{
		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);


		Parameter ppa = command.get(CNFTCommandLine.WA);
		hppa = new TrajectoryUnitMap("pa_hidden",command.get(CNFTCommandLine.DT),noDimSpace,ppa) {
			@Override
			public double computeTrajectory(double... param)   {
				return Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
			}
		};
		
		Parameter ppb = command.get(CNFTCommandLine.WB);
		hppb = new TrajectoryUnitMap("pb_hidden",command.get(CNFTCommandLine.DT),noDimSpace, ppb) {
			@Override
			public double computeTrajectory(double... param) {
				return Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
			}
		};
		Parameter pA =  command.get(CNFTCommandLine.IA);
		hpA = new TrajectoryUnitMap("A_hidden",command.get(CNFTCommandLine.DT),noDimSpace,pA,alphaP) {
			//A = A /(res*res)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param) {
				return param[0] / 
						(Math.pow(this.space.getSimulationSpace().getResolution(),2)) *
						(40*40)/param[1];
			}
		};
		Parameter pB =  command.get(CNFTCommandLine.IB);
		hpB  = new TrajectoryUnitMap("B_hidden",command.get(CNFTCommandLine.DT),noDimSpace, pB,alphaP) {
			//B = B /(res*res)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param) {
				return param[0]/ 
						(Math.pow(this.space.getSimulationSpace().getResolution(),2)) *
						(40*40)/param[1];
			}
		};
		
		pTau = command.get(CNFTCommandLine.TAU);


		addParameters(ppa,ppb,pA,pB,pTau);
	}

//	protected void initModel() throws CommandLineFormatException
//	{
//		Var vdt = new Var("dt",dt);
//
//		//Init params
//		cnftW = (AbstractMap) getLateralWeights(CNFTW, vdt, refSpace, hpA, hppa, hpB, hppb);
//		cnft = new Convolution("Convolve",vdt,refSpace);
//		potential = new SpikingPotentialMap(POTENTIAL,vdt,refSpace);
//		Map resetedPotential = new SpikingMap("resetedPotential",vdt,refSpace);
//		focus = new SpikingMap(FOCUS,vdt,refSpace);
//		
//		StaticVar th = initScript.get(CNFTCommandLine.THRESHOLD);
//		StaticVar ph =	new StaticVar("H",0);
//		//Link params
//		cnft.addParamNode(cnftW,focus);
//		potential.addParamNode(resetedPotential, pTau,main.java.input,cnft,ph,th,new StaticVar("High",0));
//		resetedPotential.addParamNode(new Leaf(potential),th,new Leaf(potential),new StaticVar("High",0));
//		focus.addParamNode( new Leaf(potential), th, new StaticVar("Low",0),new StaticVar("High",1));
//		
//
////		root = new SpikingNeuronEquation("cnftEq",new StaticVar("dt",dt),refSpace, cnft,
////				main.java.input, (Updatable)pTau, new StaticVar("H", 0),new StaticVar("Threshold", threshold));
////		focus = (MatrixDouble2D) root.getMemory(SpikingNeuronEquation.FOCUS);
//
//		//((ConvolutionMap) cnft).addRecursiveParameter(focus);
//	}
	@Override
	protected void initLateralWeights() throws NullCoordinateException,  CommandLineFormatException 
	{
		cnftW = (AbstractMap) getLateralWeights(CNFTW, command.get(CNFTCommandLine.DT), extendedConvSpace, hpA, hppa, hpB, hppb);
	}
	
	@Override
	public  Parameter getLateralWeights(String name,Var dt,Space extendedSpace,
			Parameter ia,Parameter pa,Parameter ib,Parameter pb) throws CommandLineFormatException 
	{
		ExponentialND a = new ExponentialND(dt, extendedSpace, ia, pa,
				new Var("CenterX",0d),new Var("CenterY",0d));
		Map cnfta = new Map(name + "_A",a);
		
		ExponentialND b = new ExponentialND(dt, extendedSpace, ib, pb,
				new Var("CenterX",0d),new Var("CenterY",0d));
		Map cnftb = new Map(name + "_B",b);
		
		

		Sum s =  new Sum(dt,extendedSpace,cnfta,cnftb);
		Map sum = new Map(name,s);
		sum.toStatic();
		return sum;
	}
	
	@Override
	public String getText() {
		return "Exponential spiking main.java.model : \n" +
				"The lateral weight are modelised with a difference of exponential \n" +
				"Spikes are transmitted if the potential is above a threshold" +
				" this main.java.model is a assymptotic approximation of the Randomly Spiking DNF";
	}

}
