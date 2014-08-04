package main.java.model;

import java.math.BigDecimal;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.ConvolutionMatrix2D;
import main.java.maps.Map;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;

public class ModelGSpike extends ModelCNFT {
	
	/** The map focus will contains the **/
	public static final String FOCUS = "Focus";
	protected Map focus;

	public ModelGSpike(String name) {
		super(name);
	}
	
	
	
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		
		Var<BigDecimal> dt = this.command.get(CNFTCommandLine.DT);
		initLateralWeights();
		cnft = new ConvolutionMatrix2D(CNFT,dt,space);
		potential = new UnitMap<Double, Double>(POTENTIAL,dt,space,new SpikingPotentialUM(0.));
		Map resetedPotential = new UnitMap("resetedPotential",dt,space,new SpikingUM(0.));
		focus = new UnitMap(FOCUS,dt,space,new SpikingUM(0.));
		
		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		initLateralWeights();
		
		Var high = new Var("high",0);
		cnft.addParameters(cnftW,focus);
		potential.addParameters(resetedPotential, pTau,input,cnft,ph,pth, high);
		resetedPotential.addParameters(potential,pth,potential, high);
		focus.addParameters(potential, pth, new Var("Low",0),new Var("High",1));
		this.root = potential;
	
	}
	

}
