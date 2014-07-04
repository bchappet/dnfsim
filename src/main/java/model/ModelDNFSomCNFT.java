package main.java.model;

import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.RoundedSpace;
import main.java.coordinates.Space;
import main.java.fft.FFTConvolutionMatrix2D;
import main.java.maps.AbstractMap;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.SampleMap;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.UnitLeaf;
import main.java.maps.UnitParameter;
import main.java.maps.Var;
import main.java.neigborhood.Neighborhood;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.statistics.Charac;
import main.java.statistics.CharacConvergence2;
import main.java.statistics.CharacMaxMax;
import main.java.statistics.CharacMaxSum;
import main.java.statistics.CharacMeanCompTime;
import main.java.statistics.CharacNoFocus;
import main.java.statistics.CharacTestConvergence;
import main.java.statistics.CharacteristicsCNFT;
import main.java.statistics.StatCNFT;
import main.java.statistics.StatMapCNFT;
import main.java.statistics.StatisticsCNFT;
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.RateCodedUnitModel;
import main.java.unitModel.SomUM;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;
import main.java.unitModel.Sum;
import main.java.unitModel.UMWrapper;
import main.java.unitModel.UnitModel;

public class ModelDNFSomCNFT extends ModelCNFT {
	

	protected static final String CORTICAL = "cortical";

	protected static final String CORTICAL_POT = "cortical_pot";

	protected AbstractMap cortical;
	//protected AbstractMap corticalPot;//only for display
	public AbstractMap cortNoise;
	
	protected Space inputSpace;//2d main.java.space for the inputs

	public ModelDNFSomCNFT(String name) throws CommandLineFormatException {
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
		
		
		final Var vdt = command.get(CNFTCommandLine.DT); //default dt
		Var dt_dnf = command.get(CNFTCommandLine.DT_DNF); //dt for dnf (to wait convergence)
		addParameters(command.get(CNFTCommandLine.LEARNING_RATE));
		
		
		cnft = new FFTConvolutionMatrix2D(CNFT,dt_dnf,extendedComputationSpace);
		potential = new Map(POTENTIAL,new RateCodedUnitModel(),dt_dnf,extendedComputationSpace);


		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		initLateralWeights();
		
		
		
			
		cortical = new NeighborhoodMap(CORTICAL, 
				new SomUM(inputSpace, vdt, extendedFramedSpace)){
			public  void compute() throws NullCoordinateException{
				super.compute();
				potential.resetState();
				
			}
		};
		
		
		UnitModel noise = new RandTrajUnitModel(dt_dnf,extendedFramedSpace,
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
		Map mNoise = new Map("Noise",noise);
		mNoise.constructMemory();
		
		
		cortNoise = new Map("cortNoise",new Sum(dt_dnf,extendedFramedSpace,new Leaf(cortical),mNoise));
		
		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				cortNoise,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));
		cnft.addParameters(cnftW,new Leaf(potential));
		cnft.constructMemory();
		potential.constructMemory();
		
		
		
		
		cortical.addParameters(input,potential,command.get(CNFTCommandLine.LEARNING_RATE));
		Neighborhood neigh = new V4Neighborhood2D( extendedFramedSpace, new UnitLeaf((UnitParameter) cortical));
		((NeighborhoodMap)cortical).addNeighboors(neigh);
		
		this.root = cortical;
		
//		corticalPot = new Map(CORTICAL_POT, new Sum(vdt, space2d, new Leaf(cortical)));
//		this.addParameters(corticalPot);
		
		

	}
	
	
	

	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {input,cortical,cortNoise,potential,cnftW,cnft};
		return Arrays.asList(ret);
	}
	
	
	
	@Override
	protected void initDefaultInput() throws CommandLineFormatException  {
		Var inputDt = command.get(CNFTCommandLine.DT);
		this.inputSpace = new RoundedSpace(new Double[]{-1d,-1d},new Double[]{2d,2d}
		, command.get(CNFTCommandLine.RESOLUTION), command.getBool(CNFTCommandLine.WRAP));
		
		Space noDimInputSpace = inputSpace.clone();
		noDimInputSpace.setDimension(new int[]{0,0});
		
		TrajectoryUnitMap xMap = new TrajectoryUnitMap(INPUT+"_x",inputDt,noDimInputSpace
				) {
			@Override
			public double computeTrajectory(double... param) {
//				return inputSpace.getUniformSample(Space.X);
				return inputSpace.getGaussianSample(Space.X,0.3);
			}
		};
		
		TrajectoryUnitMap yMap = new TrajectoryUnitMap(INPUT+"_y",inputDt,noDimInputSpace
				) {
			@Override
			public double computeTrajectory(double... param) {
//				return inputSpace.getUniformSample(Space.Y);
				return inputSpace.getGaussianSample(Space.Y,0.3);
			}
		};
		
		
		
		input = new SampleMap(INPUT, inputDt, inputSpace, xMap,yMap);
		input.addMemories(1,new UMWrapper(0d));
		
		
	}
	
	public void modifyModel() throws CommandLineFormatException, NullCoordinateException {
		//nothing
	}
	
	protected void initializeStatistics() throws CommandLineFormatException {
		StatCNFT stat = new StatCNFT(command.get(CNFTCommandLine.STAT_DT),this);
		StatMapCNFT wsum = stat.getWsum(new Leaf(potential));
		StatMapCNFT sizeBubbleH = stat.getSizeBubbleHeight(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		StatMapCNFT sizeBubbleW = stat.getSizeBubbleWidth(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		stats = new StatisticsCNFT("Stats",command.get(CNFTCommandLine.STAT_DT),noDimSpace,
				wsum,
				stat.getTestConvergence(new Leaf(potential)),
				stat.getMax(new Leaf(potential)),
				stat.getLyapunov(new Leaf(potential), new Leaf(cnft), new Leaf(input)),
				sizeBubbleH,sizeBubbleW
				);
	}
	
	protected  void initializeCharacteristics() throws CommandLineFormatException
	{
		Charac conv = new CharacConvergence2(CharacteristicsCNFT.CONVERGENCE,stats, noDimSpace, this);
		Charac noFocus = new CharacNoFocus(CharacteristicsCNFT.NO_FOCUS, stats, noDimSpace, this, conv);
		Charac maxSum = new CharacMaxSum(CharacteristicsCNFT.MAX_SUM, stats, noDimSpace, this);
		Charac meanCompTime = new CharacMeanCompTime(CharacteristicsCNFT.MEAN_COMP_TIME, stats, noDimSpace, this, conv);
		Charac maxMax = new CharacMaxMax(CharacteristicsCNFT.MAX_MAX,stats,noDimSpace,this);
		Charac testConv = new CharacTestConvergence(CharacteristicsCNFT.TEST_CONV, stats, noDimSpace, this,
				command.get(CNFTCommandLine.WA),command.get(CNFTCommandLine.SHAPE_FACTOR),command.get(CNFTCommandLine.STAB_TIME));

		charac = new CharacteristicsCNFT(noDimSpace, stats, conv,noFocus,maxSum,meanCompTime,maxMax,testConv);

	}

	
	
	


}
