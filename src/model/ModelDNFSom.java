package model;

import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.SampleMap;
import maps.TrajectoryUnitMap;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;
import unitModel.RateCodedUnitModel;
import unitModel.SomUM;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.RoundedSpace;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;

public class ModelDNFSom extends ModelCNFT {
	

	private static final String CORTICAL = "cortical";

	protected AbstractMap cortical;
	
	protected Space inputSpace;//2d space for the inputs

	public ModelDNFSom(String name) {
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
		Var dt_dnf = command.get(CNFTCommandLine.DT_DNF); //dt for dnf (to wait convergence)

		initLateralWeights();

		cnft = new FFTConvolutionMatrix2D(CNFT,vdt,space2d);

		potential = new Map(POTENTIAL,new RateCodedUnitModel(),vdt,space2d);
		
		cortical = new NeighborhoodMap(CORTICAL, 
				new SomUM(inputSpace, vdt, space2d));
		Neighborhood neigh = new V4Neighborhood2D(space2d, new UnitLeaf((UnitParameter) cortical));
		((NeighborhoodMap)cortical).addNeighboors(neigh);

		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				new Leaf(cortical),cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));

		cnft.addParameters(cnftW,new Leaf(potential));
		cnft.constructMemory();
		
		cortical.addParameters(input,potential,command.get(CNFTCommandLine.LEARNING_RATE));
		
		
		this.root = cortical;
		
		

	}
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {input,cortical,potential,cnftW,cnft};
		return Arrays.asList(ret);
	}

	

	protected void initDefaultInput() throws CommandLineFormatException  {
		Var inputDt = command.get(CNFTCommandLine.INPUT_DT);
		this.inputSpace = new RoundedSpace(new Double[]{-1d,-1d},new Double[]{2d,2d}
		, command.get(CNFTCommandLine.RESOLUTION), command.getBool(CNFTCommandLine.WRAP));
		
		TrajectoryUnitMap xMap = new TrajectoryUnitMap(INPUT+"_x",inputDt,inputSpace
				) {
			@Override
			public double computeTrajectory(double... param) {
				return inputSpace.getUniformSample(Space.X);
			}
		};
		
		TrajectoryUnitMap yMap = new TrajectoryUnitMap(INPUT+"_y",inputDt,inputSpace
				) {
			@Override
			public double computeTrajectory(double... param) {
				return inputSpace.getUniformSample(Space.Y);
			}
		};
		
		
		
		input = new SampleMap(INPUT, inputDt, inputSpace, xMap,yMap);
		
		
	}
	
	


}
