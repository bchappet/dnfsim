package main.java.model;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.Space;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.Var;
import main.java.unitModel.ExponentialND;
import main.java.unitModel.Sum;

public class ModelESpike2 extends ModelESpike {
	
	/**Hidden inhibitory constant**/
	protected Parameter hpCst;

	public ModelESpike2(String name) {
		super(name);
	}
	
	
	@Override
	protected void initLateralWeightParams(final Space extendedSpace) throws CommandLineFormatException
	{
		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);

		Parameter ppa = command.get(CNFTCommandLine.WA);
		hppa = new TrajectoryUnitMap("pa_hidden",command.get(CNFTCommandLine.DT),extendedSpace,ppa) {
			@Override
			public double computeTrajectory(double... param)   {
				return Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
			}
		};
		
		Parameter pA =  command.get(CNFTCommandLine.IA);
		hpA = new TrajectoryUnitMap("A_hidden",command.get(CNFTCommandLine.DT),extendedSpace,pA,alphaP) {
			//A = A /(res*res)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param) {
				return param[0] / 
						(Math.pow(this.space.getSimulationSpace().getResolution(),2)) *
						(40*40)/param[1];
			}
		};

		
		Parameter pCst =  command.get(CNFTCommandLine.INH_CST);
		hpCst  = new TrajectoryUnitMap("B_hidden",command.get(CNFTCommandLine.DT),extendedSpace, pCst,alphaP) {
			//c = c /(res*res)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param) {
				return param[0]/ 
						(Math.pow(this.space.getSimulationSpace().getResolution(),2)) *
						(40*40)/param[1];
			}
		};
		
		pTau = command.get(CNFTCommandLine.TAU);


		addParameters(ppa,pA,pCst,pTau);
		
	}
	
	@Override
	public  Parameter getLateralWeights(String name,Var dt,Space extendedSpace,
			Parameter ia,Parameter pa,Parameter ib,Parameter pb) throws CommandLineFormatException 
	{
		ExponentialND a = new ExponentialND(dt, extendedSpace, ia, pa,
				new Var("CenterX",0d),new Var("CenterY",0d));
		Map cnfta = new Map(name + "_A",a);
		
		Parameter cnftb = hpCst;
		
		addParameters(cnftb);
		
		

		Sum s =  new Sum(dt,extendedSpace,cnfta,cnftb);
		Map sum = new Map(name,s);
		sum.toStatic();
		return sum;
	}

}
