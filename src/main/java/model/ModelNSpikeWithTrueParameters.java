package main.java.model;

import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.UnitLeaf;
import main.java.maps.Var;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.routing.Assymetric2DRouting;
import main.java.unitModel.NSpikeUM;
import main.java.unitModel.PrecisionStudySpikingPotentialUM;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;
import main.java.unitModel.Sum;


/**
 * RSDNF validation main.java.model : spike are truly transmitted with a given probability
 * 
 * @author bchappet
 * 
 */
public class ModelNSpikeWithTrueParameters extends ModelNSpike{


	protected Parameter pTau_dt;//tau / dt

	public ModelNSpikeWithTrueParameters(String name) {
		super(name);
	}


	@Override
	protected void initLateralWeightParams(final Space extendedSpace) throws CommandLineFormatException
	{
		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);
		pn = command.get(CNFTCommandLine.N);
		hppa = command.get(CNFTCommandLine.WA);
		hppb = command.get(CNFTCommandLine.WB);
		hpA =  command.get(CNFTCommandLine.IA);
		hpB =  command.get(CNFTCommandLine.IB);
		pTau_dt = command.get(CNFTCommandLine.TAU_DT);
		addParameters(hppa,hppb,hpA,hpB,pTau,pn,alphaP);
	}

	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT);

		potential = new Map(POTENTIAL,new PrecisionStudySpikingPotentialUM(),vdt,space2d);

		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),
				vdt,space2d);
		focus = new Map(FOCUS,new SpikingUM(),vdt,space2d);

		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		//Init cnft
		initLateralWeights();
		//replace tau by 1 as it is included in lateral weights
		potential.addParameters(resetedPotential, pTau_dt,input,cnft,ph,pth,new Var(0));
		resetedPotential.addParameters(new Leaf(potential),pth,new Leaf(potential),new Var(0));
		focus.addParameters( new Leaf(potential), pth, new Var(0),new Var(1));

		this.root = potential;
		
		root.constructMemory();
		focus.constructMemory();
	}


	

	/**
	 * 
	 * @param name
	 * @param dt
	 * @param space2D
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
	protected  Parameter getLateralWeights(String name,Var dt,Space space2D,
			Parameter na,Parameter ia,Parameter pa,Parameter nb,Parameter ib,Parameter pb,
			Parameter focus,Parameter threshold) throws CommandLineFormatException
	{
		 NeighborhoodMap cnfta = new NeighborhoodMap(name+"_A",
				new NSpikeUM(new Assymetric2DRouting())
		,dt,space2D,na,pa,ia,focus,threshold);

		cnfta.addNeighboors(new V4Neighborhood2D(space2D, new UnitLeaf(cnfta)));
		cnfta.constructMemory();

		 NeighborhoodMap cnftb = new NeighborhoodMap(name+"_B",
				new NSpikeUM(new Assymetric2DRouting())
		,dt,space2D,nb,pb,ib,focus,threshold);

		cnftb.addNeighboors(new V4Neighborhood2D(space2D, new UnitLeaf(cnftb)));
		cnftb.constructMemory();

		Map sum = new Map(CNFT,new Sum(),dt,space2D, cnfta, cnftb);
		sum.constructMemory();
		return sum;
	}

	







}
