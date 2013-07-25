package tests;

import static org.junit.Assert.assertTrue;
import maps.NeighborhoodMap;
import maps.NullBufferedNeuronUM;
import maps.Unit;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;
import neuronBuffer.BufferedNeuronUM;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import routing.Assymetric2DHardRouting;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class BufferedNeuronUMTest  {
	
	NeighborhoodMap neuron;
	int n = 1;

	@Before
	public void setUp() throws Exception {
		
		Space space = new DefaultRoundedSpace(new Var("res",2), 2, false);
		
		double dt=0.1;
		//TODO Which dt??
		Var dtHard = new Var("dt_hard",dt/20d);

		neuron = new NeighborhoodMap("cnft_+",
				new BufferedNeuronUM(new Assymetric2DHardRouting()),dtHard ,space,new Var("p",1));
		Neighborhood neig = new V4Neighborhood2D(space, new UnitLeaf(neuron));
		neig.setNullUnit(new NullBufferedNeuronUM());
		neuron.addNeighboors(neig);
		neuron.constructMemory();
		neuron.toParallel();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test1(){
		Space space = new DefaultRoundedSpace(new Var("res",1), 2, false);
		
		double dt=0.1;
		//TODO Which dt??
		Var dtHard = new Var("dt_hard",dt/20d);

		neuron = new NeighborhoodMap("cnft_+",
				new BufferedNeuronUM(new Assymetric2DHardRouting(),dtHard ,space,new Var("p",1)));
		Neighborhood neig = new V4Neighborhood2D(space, new UnitLeaf(neuron));
		neig.setNullUnit(new NullBufferedNeuronUM());
		neuron.addNeighboors(neig);
		//System.out.println("______________________________");
		neuron.constructMemory();
		//System.out.println("______________________________");
		neuron.toParallel();
		
		BufferedNeuronUM um0 = (BufferedNeuronUM) neuron.getUnit(0).getUnitModel(0);
		BufferedNeuronUM um1 = (BufferedNeuronUM) neuron.getUnit(0).getUnitModel(1);
		//System.out.println(um0.hashCode());
		//System.out.println(um1.hashCode());
		
		neuron.compute();
	}

	@Test
	public void testOnline() {
		neuron.onLine();
		Unit center = neuron.getUnit(0);
		((BufferedNeuronUM) center.getUnitModel()).setExited(n*20);
		for(int i = 0 ; i < 21 ; i++){
			System.out.println("i : " + i);
			System.out.println(neuron.display2D());
			displayBuffers();
			neuron.compute();
		}
		
		assertTrue(neuron.get(0) == 0);
		assertTrue(neuron.get(1) == 20);
		assertTrue(neuron.get(2) == 20);
		assertTrue(neuron.get(3) == 20);
		
		
	}
	
	@Test
	public void testParallel() {
		Unit center = neuron.getUnit(0);
		((BufferedNeuronUM) center.getUnitModel()).setExited(n*20);
		for(int i = 0 ; i < 21 ; i++){
			System.out.println("i : " + i);
			System.out.println(neuron.display2D());
			displayBuffers();
			neuron.compute();
		}
		
		assertTrue(neuron.get(0) == 0);
		assertTrue(neuron.get(1) == 20);
		assertTrue(neuron.get(2) == 20);
		assertTrue(neuron.get(3) == 19);
		
		
	}
	
	private void displayBuffers()
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
