package main.java.model;

import java.math.BigDecimal;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.routing.Assymetric2DRouting;
import main.java.space.Space;
import main.java.unitModel.NSpikeUM;
import main.java.unitModel.NSpikeUMTransientFault;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;
import main.java.unitModel.Sum;

public class ModelNSpikeTransientFault extends ModelNSpike {

	public ModelNSpikeTransientFault(String name) {
		super(name);
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
		
		Parameter transientFault = command.get(CNFTCommandLine.TRANSIENT_FAULT_PROBA);
		Parameter currentSep = command.get(CNFTCommandLine.SEP);
		Map permanentFaultReader = new BooleanMatrixCSVFileReader("PermanentFaultMap",new InfiniteDt(),space,command.get(CNFTCommandLine.PERMANENT_FAULT_FILE),currentSep);
		this.addParameters(transientFault);
		
		 NeighborhoodMap cnfta = new NeighborhoodMap(name+"_A",dt,space,
				new  NSpikeUMTransientFault(new Assymetric2DRouting()),na,pa,ia,focus,threshold,transientFault,permanentFaultReader);

		cnfta.addNeighboors(new V4Neighborhood2D(space, cnfta));
		 NeighborhoodMap cnftb = new NeighborhoodMap(name+"_B",dt,space,
				new NSpikeUMTransientFault(new Assymetric2DRouting()),nb,pb,ib,focus,threshold,transientFault,permanentFaultReader);

		cnftb.addNeighboors(new V4Neighborhood2D(space,cnftb));

		Map sum = new UnitMap(CNFT,dt,space,new Sum(0.), cnfta, cnftb);
		return sum;
	}

}
