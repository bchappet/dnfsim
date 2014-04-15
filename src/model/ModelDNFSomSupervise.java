package model;

import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.CoorWinnerMap;
import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.SampleMap;
import maps.TrajectoryUnitMap;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import maps.VectorMap;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;
import statistics.Stat;
import statistics.StatMapCNFT;
import statistics.StatisticsCNFT;
import unitModel.ConstantUnit;
import unitModel.GaussianND;
import unitModel.SomUM;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.RoundedSpace;
import coordinates.Space;

public class ModelDNFSomSupervise extends ModelDNFSom {

	public ModelDNFSomSupervise(String name) throws CommandLineFormatException {
		super(name);
		
	}
	
	@Override
	protected void initLateralWeightParams(final Space extendedSpace) throws CommandLineFormatException
	{

		//Parameter of the solution (gaussian of wtm)
		hppa = command.get(CNFTCommandLine.WA);
		hpA =  command.get(CNFTCommandLine.IA);
		addParameters(hppa,hpA);
		addParameters(command.get(CNFTCommandLine.LEARNING_RATE));
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
		
		cortical = new NeighborhoodMap(CORTICAL, 
				new SomUM(inputSpace, vdt, extendedFramedSpace)){
			public  void compute() throws NullCoordinateException{
				super.compute();
				potential.resetState();
			}
		};
		
		
		potential = getPotential(vdt);
		
		
		cortical.addParameters(input,potential,command.get(CNFTCommandLine.LEARNING_RATE));
		Neighborhood neigh = new V4Neighborhood2D( extendedFramedSpace, new UnitLeaf((UnitParameter) cortical));
		neigh.setNullUnit(new ConstantUnit(new Var(0)));
		((NeighborhoodMap)cortical).addNeighboors(neigh);
		
		
		this.root = cortical;
		
		corticalPot = new Map(CORTICAL_POT, new Sum(vdt, extendedFramedSpace, new Leaf(cortical)));
		this.addParameters(corticalPot);
		
		

	}
	
	protected AbstractMap getPotential(Var vdt){
		VectorMap winner = new CoorWinnerMap("Winner",vdt,extendedComputationSpace,new Leaf(cortical));
		Parameter wx = new Map("winnerX",new UnitModel(vdt,noDimSpace,winner) {
			
			@Override
			public double compute() throws NullCoordinateException {
				return ((VectorMap)params.get(0)).getVector()[Space.X];
			}
		}) ;
		Parameter wy = new Map("winnerY",new UnitModel(vdt,noDimSpace,winner) {
			
			@Override
			public double compute() throws NullCoordinateException {
				return ((VectorMap)params.get(0)).getVector()[Space.Y];
			}
		}) ;
		return new Map(POTENTIAL,new GaussianND(vdt, extendedFramedSpace, hpA, hppa,wx,wy));
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
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {input,cortical,corticalPot,potential};
		return Arrays.asList(ret);
	}
	
	protected void initializeStatistics() throws CommandLineFormatException {
		Stat stat = new Stat(command.get(CNFTCommandLine.STAT_DT),this);
		StatMapCNFT wsum = stat.getWsum(new Leaf(potential));
		StatMapCNFT sizeBubbleH = stat.getSizeBubbleHeight(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		StatMapCNFT sizeBubbleW = stat.getSizeBubbleWidth(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		StatMapCNFT meanSquareError = stat.getMeanSquareSOM(new Leaf(cortical));
		stats = new StatisticsCNFT("Stats",command.get(CNFTCommandLine.STAT_DT),noDimSpace,
				wsum,
				stat.getTestConvergence(new Leaf(potential)),
				stat.getMax(new Leaf(potential)),
				sizeBubbleH,sizeBubbleW,meanSquareError
				);
	}
	

}
