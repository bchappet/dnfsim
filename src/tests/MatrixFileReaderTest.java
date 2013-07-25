package tests;

import static org.junit.Assert.fail;
import maps.MatrixFileReader;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class MatrixFileReaderTest {

	
	private MatrixFileReader mat;
	@Before
	public void setUp() throws Exception {
		
		Space space = new DefaultRoundedSpace(new Var("res",7), 2, false);
		Var dt = new Var("dt",0.1);
		
		mat = new MatrixFileReader("test",dt,space,"src/tests/files/input");
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
			System.out.println(mat.display2D());
			mat.compute();
			System.out.println(mat.display2D());
			mat.compute();
			System.out.println(mat.display2D());
			mat.compute();
			System.out.println(mat.display2D());
	}

}
