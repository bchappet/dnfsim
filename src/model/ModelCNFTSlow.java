package model;

import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.ConstantNeighborhood;
import neigborhood.WrappedGlobalNeigborhood;
import unitModel.Convolution;
import unitModel.RateCodedUnitModel;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

public class ModelCNFTSlow extends ModelCNFT {

	public ModelCNFTSlow(String name) {
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
		Var vdt = command.get(CNFTCommandLine.DT); //default dt

		
		//Init params
		
		cnftW = (Map) getLateralWeights(CNFTW, vdt, extendedSpace, hpA, pa, hpB, pb);

		/*Unit model convolution ( very slow)*/
				UnitModel cnftUnit = new Convolution(vdt, space2d);
				cnft = new NeighborhoodMap(CNFT, cnftUnit);


		RateCodedUnitModel rc =  new RateCodedUnitModel(vdt,space2d);
		potential = new Map(POTENTIAL,rc);
		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				input,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));


		/*Unit model convolution ( very slow)*/
				((NeighborhoodMap)cnft).addNeighboors(
						new ConstantNeighborhood(
								(int) (extendedSpace.getResolution()/2d), 
								extendedSpace,
								(UnitParameter) cnftW),
						new WrappedGlobalNeigborhood(
								(int) (space2d.getResolution()/2d),
								space2d,
								new UnitLeaf((UnitParameter) potential)));
				cnft.constructMemory();


		this.root = potential;



	}
	
	@Override
	public String getText() {
		return "This CNFT model only use another evaluation of the lateral weights using neighboorhood connections. At the beginin the construction of the neighboorhood takes some time";
	}

}
