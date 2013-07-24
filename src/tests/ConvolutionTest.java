package tests;

import static org.junit.Assert.assertTrue;
import maps.AbstractMap;
import maps.Map;
import maps.NeighborhoodMap;
import maps.UnitParameter;
import maps.Var;
import model.ModelCNFT;
import neigborhood.ConstantNeighborhood;
import neigborhood.WrappedGlobalNeigborhood;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CommandLineFormatException;

import unitModel.ConstantUnit;
import unitModel.Convolution;
import unitModel.UnitModel;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class ConvolutionTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws NullCoordinateException, CloneNotSupportedException, CommandLineFormatException {
		Space space= new DefaultRoundedSpace(new Var(3), 2, true);
		ModelCNFT model = new ModelCNFT("CNFT");
		AbstractMap kernel = (Map) model.getLateralWeights("kernel",new Var(0.1),space,
				 new Var(1.25), new Var(0.11), new Var(-0.71), new Var(1.01));
		
		System.out.println(kernel.display2D());
		System.out.println(kernel.get(0d,0d));
		System.out.println(kernel.get(0.49d,0.49d));
		assertTrue(kernel.get(0d,0d) == 0.54);
		kernel.constructMemory();
		kernel.update(0.1);
		System.out.println(kernel.display2D());
		System.out.println(kernel.get(0d,0d));
		System.out.println(kernel.get(0.49d,0.49d));
		assertTrue(kernel.get(0d,0d) == 0.54);
		
		AbstractMap input =  new Map("input",new ConstantUnit(new Var(0.1),space,new Var(1d)));
		assertTrue(input.get(0d,0d) == 1d);
		input.constructMemory();
		input.update(0.1);
		assertTrue(input.get(0d,0d) == 1d);
		
		System.out.println(input.display2D());
		
		UnitModel cnftUnit = new Convolution(new Var(0.1), space);
		AbstractMap conv = new NeighborhoodMap("conv", cnftUnit,
						new ConstantNeighborhood((int)(space.getResolution()-1)/2,space,(UnitParameter) kernel),
						new WrappedGlobalNeigborhood((int)(space.getResolution()-1)/2, space,(UnitParameter)input));
		conv.constructMemory();
		conv.update(0.1);
		System.out.println(conv.get(0d,0d));
		System.out.println(conv.display2D());
		System.out.println(conv.get(0d,-0.5d));
		assertTrue(conv.get(0d,-0.5d) == -4.29047307403258);//TODO improve tests
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
