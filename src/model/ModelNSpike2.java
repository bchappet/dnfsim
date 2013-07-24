package model;

import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.V4Neighborhood2D;
import routing.Assymetric2DRouting;
import unitModel.NSpikeUM;
import unitModel.SumAndResetUM;
import coordinates.Space;

/**
 * Assynch
 * @author bchappet
 *
 */
public class ModelNSpike2 extends ModelNSpike {

	public ModelNSpike2(String name) {
		super(name);
	}
	
	@Override
	protected  Parameter getLateralWeights(String name,Var dt,Space space2D,
			Parameter na,Parameter ia,Parameter pa,Parameter nb,Parameter ib,Parameter pb,
			Parameter focus,Parameter threshold)
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

		Map sum = new Map(CNFT,new SumAndResetUM(),dt,space2D, cnfta, cnftb);
		sum.constructMemory();
		return sum;
	}
	
	@Override
	public String getText() {
		return "Assynchronous Randomly spiking model. For each iteration, resolution² neurons are randomly " +
				"choosen and updated." ;
	}


}
