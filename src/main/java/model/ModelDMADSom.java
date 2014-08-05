package main.java.model;

import java.math.BigDecimal;
import java.util.List;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.space.Coord;
import main.java.space.Coord2D;
import main.java.space.DoubleSpace2D;
import main.java.space.Space2D;
import main.java.statistics.Characteristics;
import main.java.statistics.Statistics;
import main.java.unitModel.Sum;
import main.java.unitModel.UnitModel;
import main.java.unitModel.DMADSom.AfferentWeightsUnitModel;
import main.java.unitModel.DMADSom.LateralInputUM;
import main.java.unitModel.DMADSom.LateralWeightsUnitModel;
import main.java.unitModel.DMADSom.ModulationMapUM;
import main.java.unitModel.DMADSom.NeuralInputUM;

public class ModelDMADSom extends Model {
	
	
	private static final String INPUT = "input";
	private static final String POTENTIAL = "potential";
	private static final String AFFERENT_WEIGHTS = "aff_weights";
	private static final String LATERAL_WEIGHTS = "lateral_weights";
	
	
	public ModelDMADSom(String name) {
		super(name);
	}

	@Override
	protected void initializeParameters() throws CommandLineFormatException,
			NullCoordinateException {

		
		
		Var<Integer> res = command.get(DMADSomCommandLine.RESOLUTION);
		Space2D space = new Space2D(res, res);
		Var<BigDecimal> dt = command.get(DMADSomCommandLine.DT_GLOBAL);
		
		Double lateralWeightInit = (Double) command.get(DMADSomCommandLine.LATERAL_WEIGHTS).get();
		
		
		DoubleSpace2D inputSpace = new DoubleSpace2D(new Var<Double>("OriX",0.),new Var<Double>("OriY",0.),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),res);
		Trajectory<Coord2D<Double>> input = new Trajectory<Coord2D<Double>>(INPUT, dt, new UnitModel<Coord2D<Double>>(new Coord2D<Double>(0d, 0d)){
			@Override
			protected Coord2D<Double> compute(BigDecimal time, int index,
					List<Parameter> params) {
				DoubleSpace2D inputSpace = (DoubleSpace2D)params.get(0);
				return inputSpace.getGaussianSample();
			}
		}, inputSpace);
		
		
		
		//4D map for 4 neighboors
		NeighborhoodMap<Coord<Double>,Integer> lateralWeights = new NeighborhoodMap<Coord<Double>, Integer>(LATERAL_WEIGHTS, dt, space,
				new LateralWeightsUnitModel(new Coord<Double>(lateralWeightInit,lateralWeightInit,lateralWeightInit,lateralWeightInit)),
				this.command.get(DMADSomCommandLine.LATERAL_LEARNING_RATE),this.command.get(DMADSomCommandLine.LATERAL_ALPHA));//afferent_input,
		
		//Put the afferent delta w to 0 when we have a good fit of the input
		UnitMap<Double,Integer> modulationMap =  new UnitMap<Double,Integer>("modulation_map",dt,space,new ModulationMapUM(0d),lateralWeights); //potential
		
		
		//init afferent weight de facon ordon�e 2D
		AfferentWeightsUnitModel um = new AfferentWeightsUnitModel(new Coord2D<Double>(0.5d,0.5d)); 
		
		UnitMap<Coord2D<Double>,Integer> afferent_weights = new UnitMap<Coord2D<Double>,Integer>(AFFERENT_WEIGHTS,dt,space,um,
				this.command.get(DMADSomCommandLine.AFFERENT_LEARNING_RATE),modulationMap,input);//afferent_weights
		for(int i = 0 ; i < afferent_weights.getSpace().getVolume() ; i++){
			Coord2D<Double> coord = (Coord2D<Double>) inputSpace.indexToCoord(i);
			afferent_weights.set(i, coord);
			
		}
		System.out.println(afferent_weights);
		
		//X is the afferent potential neural input 1D
		UnitMap<Double,Integer> afferent_input = new UnitMap<Double,Integer>("neural_input",dt,space,new NeuralInputUM(0d),
				input,this.command.get(DMADSomCommandLine.SIGMA) );//afferent_weights
		
		//Y is the lateral potential
		NeighborhoodMap<Double,Integer> lateralInput = new NeighborhoodMap<Double,Integer>("lateral_input",dt,space,new LateralInputUM(0d),
				lateralWeights);//set neighboors
		
		//U is the potential
		UnitMap<Double, Integer> potential = new UnitMap<Double, Integer>(POTENTIAL, dt, space, new Sum(0d),
				afferent_input,lateralInput);
		
		//Link other parameter
		lateralWeights.addParameters(afferent_input,lateralWeights);
		lateralWeights.addNeighboors(new V4Neighborhood2D<Integer>(space, afferent_input));
//		lateralWeights.initNeighboorhood();
		
		modulationMap.addParameters(potential);
		afferent_input.addParameters(afferent_weights);
		
		afferent_weights.addParameters(afferent_weights);
		
		//setNeighbourhood
		lateralInput.addNeighboors(new V4Neighborhood2D<Integer>(space,afferent_input));
//		lateralInput.initNeighboorhood();
		
		
		this.root = potential;
		
				
		

	}
	
	
	
	
	

	@Override
	protected void initializeStatistics() throws CommandLineFormatException {
		Var<BigDecimal> dt = command.get(DMADSomCommandLine.DT_GLOBAL);
		this.stats = new Statistics(Statistics.NAME, dt);

	}

	@Override
	protected void initializeCharacteristics()
			throws CommandLineFormatException {
		this.charac = new Characteristics(new Var<Double>("no",0.));

	}

//	@Override
//	public String[] getDefaultDisplayedParameter() {
//		return new String[]{INPUT,AFFERENT_WEIGHTS,"neural_input",POTENTIAL,"modulation_map"};//AFFERENT_WEIGHTS,LATERAL_WEIGHTS};
////		return new String[]{INPUT,POTENTIAL};
//	}

	@Override
	public void modifyModel() throws CommandLineFormatException,
			NullCoordinateException {
		// TODO Auto-generated method stub

	}

//	@Override
//	public String getDefaultDisplayedStatistic() {
//		return Statistics.TIME;
//	}

	@Override
	public CommandLine constructCommandLine() {
		// TODO Auto-generated method stub
		return null;
	}

}
