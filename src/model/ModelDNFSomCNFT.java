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
import statistics.Charac;
import statistics.CharacMaxMax;
import statistics.CharacMaxSum;
import statistics.CharacMeanCompTime;
import statistics.CharacNoFocus;
import statistics.CharacTestConvergence;
import statistics.Characteristics;
import statistics.Stat;
import statistics.StatMap;
import statistics.Statistics;
import unitModel.ConstantUnit;
import unitModel.RandTrajUnitModel;
import unitModel.RateCodedUnitModel;
import unitModel.SomUM;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.RoundedSpace;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;

public class ModelDNFSomCNFT extends ModelCNFT {
	

	protected static final String CORTICAL = "cortical";

	protected static final String CORTICAL_POT = "cortical_pot";

	protected AbstractMap cortical;
	//protected AbstractMap corticalPot;//only for display
	public AbstractMap cortNoise;
	
	protected Space inputSpace;//2d space for the inputs

	public ModelDNFSomCNFT(String name) throws CommandLineFormatException {
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
		
		
		final Var vdt = command.get(CNFTCommandLine.DT); //default dt
		Var dt_dnf = command.get(CNFTCommandLine.DT_DNF); //dt for dnf (to wait convergence)
		addParameters(command.get(CNFTCommandLine.LEARNING_RATE));
		
		
		cnft = new FFTConvolutionMatrix2D(CNFT,dt_dnf,extendedSpace);
		potential = new Map(POTENTIAL,new RateCodedUnitModel(),dt_dnf,extendedSpace);


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
		input.addMemories(1,new ConstantUnit(0d));
		
		
	}
	
	public void modifyModel() throws CommandLineFormatException, NullCoordinateException {
		//nothing
	}
	
	protected void initializeStatistics() throws CommandLineFormatException {
		Stat stat = new Stat(command.get(CNFTCommandLine.STAT_DT),noDimSpace,this);
		StatMap wsum = stat.getWsum(new Leaf(potential));
		StatMap sizeBubbleH = stat.getSizeBubbleHeight(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		StatMap sizeBubbleW = stat.getSizeBubbleWidth(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		stats = new Statistics("Stats",command.get(CNFTCommandLine.STAT_DT),noDimSpace,
				wsum,
				stat.getTestConvergence(new Leaf(potential)),
				stat.getMax(new Leaf(potential)),
				stat.getLyapunov(new Leaf(potential), new Leaf(cnft), new Leaf(input)),
				sizeBubbleH,sizeBubbleW
				);
	}
	
	protected  void initializeCharacteristics() throws CommandLineFormatException
	{
		Charac conv = new CharacConvergence2(Characteristics.CONVERGENCE,stats, noDimSpace, this);
		Charac noFocus = new CharacNoFocus(Characteristics.NO_FOCUS, stats, noDimSpace, this, conv);
		Charac maxSum = new CharacMaxSum(Characteristics.MAX_SUM, stats, noDimSpace, this);
		Charac meanCompTime = new CharacMeanCompTime(Characteristics.MEAN_COMP_TIME, stats, noDimSpace, this, conv);
		Charac maxMax = new CharacMaxMax(Characteristics.MAX_MAX,stats,noDimSpace,this);
		Charac testConv = new CharacTestConvergence(Characteristics.TEST_CONV, stats, noDimSpace, this,
				command.get(CNFTCommandLine.WA),command.get(CNFTCommandLine.SHAPE_FACTOR),command.get(CNFTCommandLine.STAB_TIME));

		charac = new Characteristics(noDimSpace, stats, conv,noFocus,maxSum,meanCompTime,maxMax,testConv);

	}

	
	
	


}
