package test.java.maps;

import static org.junit.Assert.*;
import main.java.maps.InfiniteDt;
import main.java.maps.MatrixDouble2D;
import main.java.maps.MatrixDouble2DWrapper;
import main.java.maps.MultiplicationMatrix;
import main.java.maps.TransposedMatrix;
import main.java.maps.UnitMap;
import main.java.space.NoDimSpace;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.java.unitModel.UMWrapper;
import main.resources.utils.ArrayUtils;

import org.junit.Before;
import org.junit.Test;

public class MultiplicationMatrixTest {
	
	MultiplicationMatrix uut;

	@Before
	public void setUp() throws Exception {
		InfiniteDt dt = new InfiniteDt();
		MatrixDouble2D a = new MatrixDouble2D("a", dt, new double[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}});
		MatrixDouble2D b = new MatrixDouble2D("b", dt, new double[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12}});
		
		this.uut = new MultiplicationMatrix("uut", dt, new Space2D(3, 4), a,b);
	}
//
//	@Test
//	public void testCompute() {
//		
//		this.uut.compute();
//		MatrixDouble2D expected = new MatrixDouble2D("test", new InfiniteDt(), 
//				new double[][]{{38.0,44.0,50.0,56.0}, {83.0,98.0,113.0,128.0}, {128.0,152.0,176.0,200.0}, {173.0,206.0,239.0,272.0}} );
//		assertTrue("The mult of a*b should be good",ArrayUtils.equals2D( expected.get2DArrayDouble(),uut.get2DArrayDouble()));
//		
//	}
//	
//	//there should be dimension not agree
//	@Test(expected=IllegalArgumentException.class)
//	public void testVectorMultiplication(){
//		MatrixDouble2D v1 = new MatrixDouble2D("v1", new InfiniteDt(), new double[]{1,2,3});
//		
//		MatrixDouble2D v2 = new MatrixDouble2D("v2", new InfiniteDt(), new double[]{4,5,6});
//		
//		this.uut = new MultiplicationMatrix("uut", new InfiniteDt(), new NoDimSpace(), v1,v2);
//		
//		
//		this.uut.compute();
//		
//	}
//	
//	//there should be dimension not agree
//		@Test
//		public void testVectorMultiplication2(){
//			MatrixDouble2D v1 = new MatrixDouble2D("v1", new InfiniteDt(), new double[]{1,2,3});
//			
//			MatrixDouble2D v2 = new MatrixDouble2D("v2", new InfiniteDt(), new double[]{4,5,6});
//			
//			MatrixDouble2D transpose = new TransposedMatrix("v2t", new InfiniteDt(), v2);
//			transpose.compute();
//			this.uut = new MultiplicationMatrix("uut", new InfiniteDt(), new NoDimSpace(), v1,transpose);
//			
//			
//			this.uut.compute();
//			
//			assertEquals("the multiplication should be 32",(Double)32.,uut.getIndex(0));
//			
//		}
		
		@Test
		public void testMatrixPerVectW_x() throws Exception{
			
			MatrixDouble2D w = new MatrixDouble2D("w", new InfiniteDt(), new double[][]{{1,2,3},{1,2,3},{1,2,3}});
			
			MatrixDouble2D x = new MatrixDouble2D("x", new InfiniteDt(), new double[]{4,5,6});
			
			MatrixDouble2D wrap = new MatrixDouble2DWrapper(x);
			wrap.compute();
			
			System.out.println("w \n" + w);
			System.out.println("x \n" + x);
			System.out.println("wrap \n" + wrap);
			
			this.uut = new MultiplicationMatrix("uut", new InfiniteDt(), new NoDimSpace(), w,x);
			
			this.uut.compute();
			System.out.println("uut \n " +uut);
			
		}

}
