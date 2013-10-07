package model;

import console.CNFTCommandLine;
import console.CommandLineFormatException;
import maps.ConvolutionMatrix2D;
import maps.Leaf;
import maps.Map;
import maps.Matrix2D;
import maps.Parameter;
import maps.Var;
import unitModel.GaussianND;
import unitModel.RateCodedUnitModel;
import unitModel.Sum;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;

public class ModelCNFTFFT extends ModelCNFT {

	public ModelCNFTFFT(String name) {
		super(name);
	}

	/**
	 * Construct the model architecture
	 * @throws CommandLineFormatException if the given parameter was not initialized in
	 * the default script or model script
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT); //default dt

		initLateralWeights();

		cnft = new FFTConvolutionMatrix2D(CNFT,vdt,extendedSpace);

		potential = new Map(POTENTIAL,new RateCodedUnitModel(),vdt,extendedSpace);


		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				input,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));

		cnft.addParameters(cnftW,new Leaf(potential));
		cnft.constructMemory();

		this.root = potential;



	}

	
}
