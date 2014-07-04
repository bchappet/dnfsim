package main.java.model;

import java.util.Arrays;

import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.NeighborhoodMap;
import main.java.maps.NullBufferedNeuronUM;
import main.java.maps.Parameter;
import main.java.maps.UnitLeaf;
import main.java.maps.Var;
import main.java.neigborhood.Neighborhood;
import main.java.neigborhood.OneToOneNeighborhood;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.neuronBuffer.BufferedNeuronUM;
import main.java.routing.Assymetric2DHardRouting;
import main.java.unitModel.PotentialNeuronUM;

public class ModelRSDNFMixte extends ModelRSDNF {

	public ModelRSDNFMixte(String name) {
		super(name);
		this.setAssynchronousComputation(true);

	}
	
	@Override
	public void update() throws NullCoordinateException, CommandLineFormatException {
		this.modifyModel();
		this.time += displayDt.get();
		
		//Synchronizly update hard map
		exc.update(time);
		inh.update(time);
		
		//Assynchronily update one neuron potential
		int size = refSpace.getDiscreteVolume();
		root.compute((int) (Math.random()*size));
			
		stats.update(time);
	}
	
	

	protected  Parameter getLateralWeights(String name,Var dt,Var dtHard,Space space2D,
			Parameter na,Parameter ia,Parameter pa,Parameter nb,Parameter ib,Parameter pb,
			Parameter focus,Parameter threshold,Parameter capacity)
	{
		exc = new NeighborhoodMap(CNFTW+"_+",
				new BufferedNeuronUM(new Assymetric2DHardRouting(),dtHard ,space2d,hppa,capacity));
		Neighborhood neigh = new V4Neighborhood2D(space2d, new UnitLeaf(exc));
		exc.addNeighboors(neigh);
		neigh.setNullUnit(new NullBufferedNeuronUM());
		exc.constructMemory();
		exc.toParallel();

		inh = new NeighborhoodMap(CNFTW+"_-",
				new BufferedNeuronUM(new Assymetric2DHardRouting(),dtHard ,space2d,hppb,capacity));
		Neighborhood neigh2 = new V4Neighborhood2D(space2d, new UnitLeaf(inh));
		inh.addNeighboors(neigh2);
		neigh2.setNullUnit(new NullBufferedNeuronUM());
		inh.constructMemory();
		inh.toParallel();
		
		NeighborhoodMap sum = new NeighborhoodMap(CNFT,new PotentialNeuronUM(),
				dt,space2d,threshold,pn,focus,hpA,hpB){
			//Here we want to avoid the assynchronous updating of exc and inh
			public void addNeighboors(Neighborhood... neighs) {
				neighborhood.addAll(Arrays.asList(neighs));
				//Add neigboorhood map as parameter : NO
//				for(Neighborhood nei : neighborhood)
//					addParameters(nei.getMap());
			}
		
			
		};
		
		this.addParameters(exc,inh);
		
		sum.addNeighboors(new OneToOneNeighborhood(space2d, exc),
				new OneToOneNeighborhood(space2d, inh));
		sum.constructMemory();
		
		return sum;
		
	}
	
	@Override
	public String getText() {
		return "Similar to RSDNF main.java.model but the spikes are transmitted synchronously but the potentials are updated assynchronously." ;
	}
	
	

}
