package tests;

import maps.Map;
import maps.NeighborhoodMap;
import maps.NullBufferedNeuronUM;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.OneToOneNeighborhood;
import neigborhood.V4Neighborhood2D;
import neuronBuffer.BufferedNeuronUM;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import routing.Assymetric2DRouting;
import unitModel.PotentialNeuronUM;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class PotentialNeuronUMTest {
	
	NeighborhoodMap exc;
	NeighborhoodMap inh;
	Map potential;

	@Before
	public void setUp() throws Exception {
		
		Space space2d = new DefaultRoundedSpace(new Var(2), 2, false);
		Var vdt = new Var(0.1);
		Var dtHard = new Var(0.01);
		Var hppa = new Var(1);
		Var hppb = new Var(1);
		Var pn = new Var(10);
		Var input = new Var(1);
		Var hpA = new Var(1);
		Var hpB = new Var(1);
		
		exc = new NeighborhoodMap("cnftw"+"_+",
				new BufferedNeuronUM(new Assymetric2DRouting()),dtHard ,space2d,hppa);
		Neighborhood neigh = new V4Neighborhood2D(space2d, new UnitLeaf(exc));
		neigh.setNullUnit(new NullBufferedNeuronUM());
		exc.addNeighboors(neigh);
		exc.constructMemory();
		exc.toParallel();

		inh = new NeighborhoodMap("cnftw"+"_-",
				new BufferedNeuronUM(new Assymetric2DRouting()),dtHard ,space2d,hppb);
		Neighborhood neigh2 = new V4Neighborhood2D(space2d, new UnitLeaf(inh));
		inh.addNeighboors(neigh2);
		neigh2.setNullUnit(new NullBufferedNeuronUM());
		inh.constructMemory();
		inh.toParallel();

		Var pth = new Var(0.8);

		potential = new NeighborhoodMap("potential",new PotentialNeuronUM(),
				vdt,space2d,pth,pn,input,hpA,hpB);
		((NeighborhoodMap) potential).addNeighboors(new OneToOneNeighborhood(space2d, exc),
				new OneToOneNeighborhood(space2d, inh));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		potential.constructMemory();
		
		potential.update(0.3);
		System.out.println(potential.display2D());
		potential.update(0.4);
		System.out.println(potential.display2D());
		System.out.println(exc.display2D());
		displayBuffers(exc);
		potential.update(0.5);
		displayBuffers(exc);
		potential.update(0.6);
		displayBuffers(exc);
		System.out.println(potential.display2D());
		
		for(int i = 0 ; i < 10 ; i++){
			System.out.println("i " + i);
			potential.update(0.1*i);
			System.out.println(exc.display2D());
			displayBuffers(exc);
			System.out.println(potential.display2D());
		}
		
//		for(int i = 0 ; i < 10 ; i++){
//			System.out.println("i " + i);
//			potential.update(0.1*i);
//			System.out.println(exc.display2D());
//			System.out.println(potential.display2D());
//		}
	}
	
	private void displayBuffers(Map neuron)
	{
		double res = neuron.getSpace().getResolution();
		int k = 0;
		for(int i = 0 ; i < (int) res ; i++){
			for(int j = 0 ; j < (int) res ; j++){
				System.out.print(((BufferedNeuronUM)neuron.getUnit(k).getUnitModel(0))+"::"
			+((BufferedNeuronUM)neuron.getUnit(k).getUnitModel(1) + "|---|"));
//				System.out.print(((BufferedNeuronUM)neuron.getUnit(k).getUnitModel())+";");
				k ++;
			}
			System.out.println();
			
		}
	}

}
