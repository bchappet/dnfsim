package main.java.model;

import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.RoundedSpace;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.CoorWinnerMap;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.SampleMap;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.UnitLeaf;
import main.java.maps.UnitParameter;
import main.java.maps.Var;
import main.java.maps.VectorMap;
import main.java.neigborhood.Neighborhood;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.statistics.StatCNFT;
import main.java.statistics.StatMapCNFT;
import main.java.statistics.StatisticsCNFT;
import main.java.unitModel.GaussianND;
import main.java.unitModel.SomUM;
import main.java.unitModel.Sum;
import main.java.unitModel.UMWrapper;
import main.java.unitModel.UnitModel;

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
	 * Construct the main.java.model architecture
	 * @throws CommandLineFormatException if the given parameter was not initialized in
	 * the default script or main.java.model script
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
		neigh.setNullUnit(new UMWrapper(new Var(0)));
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
				return ((VectorMap)params.getIndex(0)).getVector()[Space.X];
			}
		}) ;
		Parameter wy = new Map("winnerY",new UnitModel(vdt,noDimSpace,winner) {
			
			@Override
			public double compute() throws NullCoordinateException {
				return ((VectorMap)params.getIndex(0)).getVector()[Space.Y];
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
		input.addMemories(1,new UMWrapper(0d));
		
		
	}
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {input,cortical,corticalPot,potential};
		return Arrays.asList(ret);
	}
	
	protected void initializeStatistics() throws CommandLineFormatException {
		StatCNFT stat = new StatCNFT(command.get(CNFTCommandLine.STAT_DT),this);
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
