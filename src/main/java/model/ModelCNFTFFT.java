package main.java.model;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.fft.FFTConvolutionMatrix2D;
import main.java.maps.ConvolutionMatrix2D;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.Matrix2D;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.unitModel.GaussianND;
import main.java.unitModel.RateCodedUnitModel;
import main.java.unitModel.Sum;
import main.java.unitModel.UnitModel;

public class ModelCNFTFFT extends ModelCNFT {

	public ModelCNFTFFT(String name) {
		super(name);
	}

	/**
	 * Construct the main.java.model architecture
	 * @throws CommandLineFormatException if the given parameter was not initialized in
	 * the default script or main.java.model script
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT); //default dt

		initLateralWeights();

		cnft = new FFTConvolutionMatrix2D(CNFT,vdt,extendedComputationSpace);

		potential = new Map(POTENTIAL,new RateCodedUnitModel(),vdt,extendedComputationSpace);


		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				input,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));

		cnft.addParameters(cnftW,new Leaf(potential));
		cnft.constructMemory();
		potential.constructMemory();

		this.root = potential;
		

	}

	
}
