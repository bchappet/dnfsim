package main.java.model;

import java.math.BigDecimal;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.Space;
import main.java.unitModel.ComputeUM;
import main.java.unitModel.Sum;

public class ModelESpike extends ModelGSpike {
	
	/**Updatable accessed by the model**/
	protected Parameter hppa;
	protected Parameter hppb;

	public ModelESpike(String name) {
		super(name);
	}
	
	@Override
	protected void initLateralWeightParams() throws CommandLineFormatException
	{
		super.initLateralWeightParams();
		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);
		//ppa^{1/res}
		Var<String> equationProba = new Var<String>("Equation Proba","Math.pow($1,(1./$2))");

		Parameter<Double> ppa = command.get(CNFTCommandLine.WA);
		hppa = new Trajectory<Double>("pa_hidden",new InfiniteDt(),new ComputeUM<Double>(0d),
				equationProba,ppa,space.getResolution());
	((Trajectory)hppa).compute();
//	System.out.println(ppa.getIndex(0) + " " +space.getResolution().get());
//	System.out.println(hppa.getIndex(0));
		Parameter ppb = command.get(CNFTCommandLine.WB);
		hppb = new Trajectory<Double>("pb_hidden",new InfiniteDt(),new ComputeUM<Double>(0d),
				equationProba,ppb,space.getResolution());
	}
	
	@Override
	protected void initLateralWeights() throws  CommandLineFormatException 
	{
		this.cnftW =  getLateralWeights(CNFTW, new InfiniteDt(), space, hpA, hppa, hpB, hppb);
	}
	
	@Override
	public  Map getLateralWeights(String name,Var dt,Space extendedSpace,
			Parameter ia,Parameter pa,Parameter ib,Parameter pb) throws CommandLineFormatException 
	{
		
		Map cnfta = new UnitMap(name + "_A",dt,space,new ExponentialND(0.0),space, ia, pa,
				new Var("CenterX",0d),new Var("CenterY",0d));
		
		Map cnftb = new UnitMap(name + "_B",dt,space,new ExponentialND(0.0),space, ib, pb,
				new Var("CenterX",0d),new Var("CenterY",0d));

		Map sum = new UnitMap(name,new InfiniteDt(),space,new Sum(0.),cnfta,cnftb);
		return sum;
	}
	
	@Override
	public String getText() {
		return "Exponential spiking model : \n" +
				"The lateral weight are modelised with a difference of exponential \n" +
				"Spikes are transmitted if the potential is above a threshold" +
				" this model is a assymptotic approximation of the Randomly Spiking DNF";
	}
	
}
