package model;

import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.ConvolutionMatrix2D;
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
import statistics.CharacConvergence2;
import statistics.CharacMaxMax;
import statistics.CharacMaxSum;
import statistics.CharacMeanCompTime;
import statistics.CharacNoFocus;
import statistics.CharacTestConvergence;
import statistics.CharacteristicsCNFT;
import statistics.Stat;
import statistics.StatMapCNFT;
import statistics.StatisticsCNFT;
import unitModel.ConstantUnit;
import unitModel.SomUM;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import unitModel.Sum;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.RoundedSpace;
import coordinates.Space;

public class ModelDNFSomESpike extends ModelESpike {
	

	protected static final String CORTICAL = "cortical";

	protected static final String CORTICAL_POT = "cortical_pot";

	protected AbstractMap cortical;
	protected AbstractMap corticalPot;//only for display
	
	protected Space inputSpace;//2d space for the inputs

	public ModelDNFSomESpike(String name) throws CommandLineFormatException {
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
		
		
		cnft = new ConvolutionMatrix2D(CNFT,dt_dnf,extendedComputationSpace);
		potential = new Map(POTENTIAL,new SpikingPotentialUM(),dt_dnf,extendedComputationSpace);

		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),
				dt_dnf,extendedComputationSpace);
		focus = new Map(FOCUS,new SpikingUM(),dt_dnf,extendedComputationSpace);

		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		initLateralWeights();
		
		
		cnft.addParameters(cnftW,focus);
		
			
		cortical = new NeighborhoodMap(CORTICAL, 
				new SomUM(inputSpace, vdt, extendedFramedSpace)){
			public  void compute() throws NullCoordinateException{
				super.compute();
				potential.resetState();
				
			}
		};
		
		
		potential.addParameters(resetedPotential, pTau,new Leaf(cortical),cnft,ph,pth,new Var("High",0));
		resetedPotential.addParameters(new Leaf(potential),pth,new Leaf(potential),new Var("High",0));
		focus.addParameters( new Leaf(potential), pth, new Var("Low",0),new Var("High",1));
		potential.constructMemory();
		
		
		
		
		cortical.addParameters(input,potential,command.get(CNFTCommandLine.LEARNING_RATE));
		Neighborhood neigh = new V4Neighborhood2D( extendedFramedSpace, new UnitLeaf((UnitParameter) cortical));
		((NeighborhoodMap)cortical).addNeighboors(neigh);
		
		this.root = cortical;
		
		corticalPot = new Map(CORTICAL_POT, new Sum(vdt, space2d, new Leaf(cortical)));
		this.addParameters(corticalPot);
		
		

	}
	
	
	

	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {input,cortical,corticalPot,potential,focus,cnft};
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
		Stat stat = new Stat(command.get(CNFTCommandLine.STAT_DT),this);
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
