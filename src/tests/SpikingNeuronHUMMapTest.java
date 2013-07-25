package tests;

import hardSimulator.NullSpikingNeuronHUM;
import hardSimulator.SpikingNeuronHUM;

import java.util.AbstractList;

import maps.AbstractUnitMap;
import maps.Matrix;
import maps.NeighborhoodMap;
import maps.SubUnitMap;
import maps.Unit;
import maps.UnitLeaf;
import maps.UnitParameter;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.V4Neighborhood2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import precision.PrecisionVar;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class SpikingNeuronHUMMapTest {
	
	private AbstractUnitMap potential;
	private AbstractUnitMap focus,exc,inh;
	private Var precision = new Var(10);
	private SpikingNeuronHUM neuron;
	
	

	@Before
	public void setUp() throws Exception {
		Space space2d = new DefaultRoundedSpace(new Var(2), 2, false);
		potential = new NeighborhoodMap("map",new SpikingNeuronHUM(),new Var(0.1),
						space2d,
						new Var(1),//command.get(CNFTCommandLine.COMPUTE_CLK),
						new Var(0.15),//command.get(CNFTCommandLine.P_IA),
						new Var(0.075),//command.get(CNFTCommandLine.P_IB),
						new Var(1d),//command.get(CNFTCommandLine.P_WA),
						new Var(1d),//command.get(CNFTCommandLine.P_WB),
						new Var(0.75),//command.get(CNFTCommandLine.P_THRESHOLD),
						precision,//command.get(CNFTCommandLine.PRECISION),
						new Var(2),//command.get(CNFTCommandLine.P_N),
						new Matrix("input", new Var(0.1), space2d, new double[]{
//								2000,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,0,
//								0,0,0,0,0,0,0,0,0,2000
							2000,0,
							0,0 //input
								}),new Var(0.65));//tau  
		Neighborhood nei = new V4Neighborhood2D(space2d,
				new UnitLeaf((UnitParameter) potential));
		nei.setNullUnit(new NullSpikingNeuronHUM());
		((NeighborhoodMap)potential).addNeighboors(nei);
		
		focus = new SubUnitMap("focus", (AbstractUnitMap) potential, SpikingNeuronHUM.SPIKING_UNIT);
		exc = new SubUnitMap("exc", (AbstractUnitMap) potential, SpikingNeuronHUM.NEURON_EXC);
		inh = new SubUnitMap("inh", (AbstractUnitMap) potential, SpikingNeuronHUM.NEURON_INH);
		
		potential.constructMemory();
		focus.constructMemory();
		exc.constructMemory();
		inh.constructMemory();
		
		
		neuron = (SpikingNeuronHUM) potential.getUnit(0).getUnitModel();
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testParallel(){
		potential.toParallel();
		
		 for(double i = 0 ; i < 10 ; i += 0.1)
		 {
			 potential.update(i);
			 System.out.println(i + "===========================================");
			 System.out.println(potential.display2D());
			 System.out.println(focus.display2D());
			 System.out.println(exc.display2D());
		 }
		
		
	}
//	@Test
//	public void test() {
//		
//		 for(double i = 0 ; i < 10 ; i += 0.1)
//		 {
//			 potential.update(i);
//			 System.out.println(i + "===========================================");
//			 System.out.println(potential.display2D());
//			 System.out.println(focus.display2D());
//			 System.out.println(exc.display2D());
//		 }
//		
//	}

}
