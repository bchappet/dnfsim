package main.java.model;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.UnitLeaf;
import main.java.maps.UnitParameter;
import main.java.maps.Var;
import main.java.neigborhood.ConstantNeighborhood;
import main.java.neigborhood.WrappedGlobalNeigborhood;
import main.java.unitModel.Convolution;
import main.java.unitModel.RateCodedUnitModel;
import main.java.unitModel.UnitModel;

public class ModelCNFTSlow extends ModelCNFT {

	public ModelCNFTSlow(String name) {
		super(name);
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
		Var vdt = command.get(CNFTCommandLine.DT); //default dt

		
		//Init params
		
		cnftW = (Map) getLateralWeights(CNFTW, vdt, extendedFramedSpace, hpA, pa, hpB, pb);

		/*Unit main.java.model convolution ( very slow)*/
				UnitModel cnftUnit = new Convolution(vdt, space2d);
				cnft = new NeighborhoodMap(CNFT, cnftUnit);


		RateCodedUnitModel rc =  new RateCodedUnitModel(vdt,space2d);
		potential = new Map(POTENTIAL,rc);
		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				input,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));


		/*Unit main.java.model convolution ( very slow)*/
				((NeighborhoodMap)cnft).addNeighboors(
						new ConstantNeighborhood(
								(int) (extendedComputationSpace.getResolution()/2d), 
								extendedComputationSpace,
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
		return "This CNFT main.java.model only use another evaluation of the lateral weights using neighboorhood connections. At the beginin the construction of the neighboorhood takes some time";
	}

}
