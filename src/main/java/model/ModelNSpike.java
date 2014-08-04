package main.java.model;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.routing.Assymetric2DRouting;
import main.java.space.Space;
import main.java.unitModel.ComputeUM;
import main.java.unitModel.NSpikeUM;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;
import main.java.unitModel.Sum;
import main.java.unitModel.UnitModel;


/**
 * RSDNF validation model : spike are truly transmitted with a given probability
 * 
 * @author bchappet
 * 
 */
public class ModelNSpike extends ModelESpike{




	/** Amount of spike emitted when a neuron is firing **/
	protected Parameter pn;
	

	public ModelNSpike(String name) {
		super(name);
	}


	@Override
	protected void initLateralWeightParams() throws CommandLineFormatException
	{
		super.initLateralWeightParams();
		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);

		pn = command.get(CNFTCommandLine.N);
		pTau = command.get(CNFTCommandLine.TAU);
		pa = command.get(CNFTCommandLine.IA);
		pb = command.get(CNFTCommandLine.IB);


		//wa = wa_/(res^2*n)*40^2/alpha 
		Var<String> equationWeights = new Var<String>("Equation Weights","($1/($2*$2*$3))*((40*40)/$4)");
		hpA = new Trajectory<Double>("A_hidden",new InfiniteDt(),new ComputeUM(0d),
				equationWeights,pa,space.getResolution(),pn,alphaP);
		hpB = new Trajectory<Double>("B_hidden",new InfiniteDt(),new ComputeUM(0d),
			equationWeights,pb,space.getResolution(),pn,alphaP);
		
	}

	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var<BigDecimal> vdt = command.get(CNFTCommandLine.DT);

		potential = new UnitMap(POTENTIAL,vdt,space,new SpikingPotentialUM(0.));

		UnitMap resetedPotential = new UnitMap("resetedPotential",vdt,space,new SpikingUM(0.));
		focus = new UnitMap(FOCUS,vdt,space,new SpikingUM(0.));

		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		//Init cnft
		initLateralWeights();

		potential.addParameters(resetedPotential, pTau,input,cnft,ph,pth,new Var(0));
		resetedPotential.addParameters(potential,pth,potential,new Var(0));
		focus.addParameters( potential, pth, new Var(0),new Var(1));

		this.root = potential;
		
	}

	@Override
	protected  void initializeStatistics()
			throws CommandLineFormatException{
		super. initializeStatistics();
		
//		Trajectory<Integer> pi = new Trajectory<>(name, stats.getDt(), new UnitModel<Integer>(0) {
//
//			@Override
//			protected Integer compute(BigDecimal time, int index,
//					List<Parameter> params) {
//				UnitMap< Integer, Double> focus = (UnitMap<Integer, Double>) params.get(0);
//				
//				Space space = focus.getSpace();
//				
//				for(int i =0 ; i < space.getVolume() ; i++){
//					
//				}
//				
//				
//				return 0;
//				
//				
//			}
//		}, focus);
		
	}






	@Override
	protected void initLateralWeights() throws NullCoordinateException, CommandLineFormatException 
	{
		cnft =   getLateralWeights(
				CNFT,command.get(CNFTCommandLine.DT),space,
				pn,hpA,hppa,pn,hpB,hppb,focus,new Var("focusThreshold",0));
	}

	/**
	 * 
	 * @param name
	 * @param dt
	 * @param space
	 * @param na
	 * @param ia
	 * @param pa
	 * @param nb
	 * @param ib
	 * @param pb
	 * @param focus
	 * @return
	 * @throws CommandLineFormatException 
	 */
	protected  Map getLateralWeights(String name,Var dt,Space space,
			Parameter na,Parameter ia,Parameter pa,Parameter nb,Parameter ib,Parameter pb,
			Parameter focus,Parameter threshold) throws CommandLineFormatException
	{
		 NeighborhoodMap cnfta = new NeighborhoodMap(name+"_A",dt,space,
				new NSpikeUM(new Assymetric2DRouting()),na,pa,ia,focus,threshold);

		cnfta.addNeighboors(new V4Neighborhood2D(space, cnfta));

		 NeighborhoodMap cnftb = new NeighborhoodMap(name+"_B",dt,space,
				new NSpikeUM(new Assymetric2DRouting()),nb,pb,ib,focus,threshold);

		cnftb.addNeighboors(new V4Neighborhood2D(space,cnftb));

		Map sum = new UnitMap(CNFT,dt,space,new Sum(0.), cnfta, cnftb);
		return sum;
	}

	


}