/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import maps.Matrix;
import maps.MultiplicationMatrix;
import maps.Var;

import org.junit.Before;
import org.junit.Test;

import coordinates.DiscreteSquareSpace;
import coordinates.Space;

/**
 * @author benoit
 *
 */
public class MultiplicationMatrixTest {
	
	private MultiplicationMatrix uut;
	private Space space;
	private int length = 5;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		space = new DiscreteSquareSpace(new Var(length), 1, false);
		uut = new MultiplicationMatrix("uut",new Var(0.1),space);
		
	}

	/**
	 * Test method for {@link maps.MultiplicationMatrix#compute()}.
	 */
	@Test
	public void testCompute() {
		Space space2D =  new DiscreteSquareSpace(new Var(length), 2, false);
		Matrix a = new Matrix("a", new Var(0.1), space2D, new double[]{0,1,2,3,4,
																	1,2,3,4,5,
																	9,8,7,6,6,
																	7,8,9,1,2,
																	7,2,3,0,0});
		Matrix b = new Matrix("b", new Var(0.1), space, new double[]{0,1,2,3,4});
		
		uut.addParameters(a,b);
		uut.compute();
		System.out.println(Arrays.toString(uut.getValues()));
		assertTrue(Arrays.equals(uut.getValues(), new double[]{30.0, 40.0, 64.0, 37.0, 8.0}));
		
	}

	/**
	 * Test method for {@link maps.MultiplicationMatrix#toJamaMatrix(maps.Parameter)}.
	 */
	@Test
	public void testToJamaMatrix() {
		Matrix mat = new Matrix("test", new Var(0.1), space, new double[]{0,1,2,3,4});
		Jama.Matrix jama = MultiplicationMatrix.toJamaMatrix(mat);
		jama.print(0, 0);
		
	}

}
