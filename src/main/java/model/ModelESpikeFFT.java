package main.java.model;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.fft.FFTConvolutionMatrix2D;
import main.java.maps.AbstractMap;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.Var;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;

public class ModelESpikeFFT extends ModelESpike {

	public ModelESpikeFFT(String name) {
		super(name);
	}
	
	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT);

		

		cnft = new FFTConvolutionMatrix2D(CNFT,vdt,extendedComputationSpace);

		potential = new Map(POTENTIAL,new SpikingPotentialUM(),vdt,extendedComputationSpace);

		
		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),
				vdt,extendedComputationSpace);
		focus = new Map(FOCUS,new SpikingUM(),vdt,extendedComputationSpace);
		
		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		//Init cnftW
		initLateralWeights();
		
		cnft.addParameters(cnftW,focus);
		potential.addParameters(resetedPotential, pTau,input,cnft,ph,pth,new Var("High",0));
		resetedPotential.addParameters(new Leaf(potential),pth,new Leaf(potential),new Var("High",0));
		focus.addParameters( new Leaf(potential), pth, new Var("Low",0),new Var("High",1));
		potential.constructMemory();
		this.root = potential;
	}

}
