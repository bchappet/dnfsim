package test.java.maps;

import static org.junit.Assert.assertEquals;
import main.java.maps.InfiniteDt;
import main.java.maps.MatrixDouble2D;
import main.java.maps.MultiplicationMatrix;
import main.java.space.Space2D;

import org.junit.Before;
import org.junit.Test;

public class MultiplicationMatrixTest {
	
	MultiplicationMatrix uut;

	@Before
	public void setUp() throws Exception {
		InfiniteDt dt = new InfiniteDt();
		MatrixDouble2D a = new MatrixDouble2D("a", dt, new double[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}});
		MatrixDouble2D b = new MatrixDouble2D("a", dt, new double[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12}});
		
		//System.out.println("a\n" +a);
		//System.out.println("b\n"+b);
		this.uut = new MultiplicationMatrix("uut", dt, new Space2D(3, 4), a,b);
	}

	@Test
	public void testCompute() {
		
		this.uut.compute();
		//System.out.println("a*b\n"+uut);
		MatrixDouble2D expected = new MatrixDouble2D("test", new InfiniteDt(), 
				new double[][]{{38.0,44.0,50.0,56.0}, {83.0,98.0,113.0,128.0}, {128.0,152.0,176.0,200.0}, {173.0,206.0,239.0,272.0}} );
		
		assertEquals("The mult of a*b should be good",expected,uut);
		
	}

}
