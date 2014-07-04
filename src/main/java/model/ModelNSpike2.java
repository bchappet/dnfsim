package main.java.model;

import main.java.coordinates.Space;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.UnitLeaf;
import main.java.maps.Var;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.routing.Assymetric2DRouting;
import main.java.unitModel.NSpikeUM;
import main.java.unitModel.SumAndResetUM;

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
		return "Assynchronous Randomly spiking main.java.model. For each iteration, resolution² neurons are randomly " +
				"choosen and updated." ;
	}


}
