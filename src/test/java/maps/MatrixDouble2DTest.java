package test.java.maps;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import main.java.maps.InfiniteDt;
import main.java.maps.MatrixDouble2D;
import main.java.maps.Var;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.resources.utils.ArrayUtils;

import org.junit.Before;
import org.junit.Test;

public class MatrixDouble2DTest {
	
	protected MatrixDouble2D uut;
	private Var<BigDecimal> dt;

	@Before
	public void setUp() throws Exception {
		dt = new Var<BigDecimal>("dt",new BigDecimal("0.1"));
		uut = new MatrixDouble2D("uut",dt, new double[][]{{0,1,2},{3,4,5},{6,7,8},{9,10,11}});
	
	}



	@Test
	public void testConstructFromSpace(){
		Space2D space = new Space2D(3, 4);
		uut = new MatrixDouble2D("uut", dt, space);
		assertEquals("The jamat should be good " ,4,uut.getMat().getNbColumns());
		assertEquals("The jamat should be good " ,3,uut.getMat().getNbRows());
	}

	@Test
	public void testConstructerNToString1() {
		String expected = "0.0,1.0,2.0\n"+
						"3.0,4.0,5.0\n"+
						"6.0,7.0,8.0\n"+
						"9.0,10.0,11.0";
		assertEquals("The matrix should have the values expected",expected,uut.toString());
		assertEquals("The main.java.space should be of dim 3,4",Arrays.toString(new int[]{3,4}),Arrays.toString(new int[]{((Space2D) uut.getSpace()).getDimX(),((Space2D) uut.getSpace()).getDimY()}));
	}
	
	@Test
	public void testConstructerCst() {
		uut = new MatrixDouble2D("uut",dt,new Space2D(3,2));
		String expected = "0.0,0.0,0.0\n"+
						"0.0,0.0,0.0";
		assertEquals("The matrix should have the values expected",expected,uut.toString());
		
		uut = new MatrixDouble2D("uut",dt,new Space2D(2,3),10);
		String expected2 = "10.0,10.0\n"+
						"10.0,10.0\n"+
						"10.0,10.0";
		assertEquals("The matrix should have the values expected",expected2,uut.toString());
	}
	
	@Test
	public void testGetIndex(){
		assertEquals("The element at the index should be good",(Double)10.,uut.getIndex(10));
		assertEquals("The element at the index should be good",(Double)5.,uut.getIndex(5));
		assertEquals("The element at the index should be good",(Double)1.,uut.getIndex(1));
	}
	
	@Test
	public void testGetFast(){
		assertEquals("The element at coordinate should be good",(Double)0.,uut.getFast(0, 0));
		assertEquals("The element at coordinate should be good",(Double)2.,uut.getFast(2, 0));
		assertEquals("The element at coordinate should be good",(Double)3.,uut.getFast(0, 1));
		assertEquals("The element at coordinate should be good",(Double)7.,uut.getFast(1, 2));
		assertEquals("The element at coordinate should be good",(Double)10.,uut.getFast(1, 3));
		assertEquals("The element at coordinate should be good",(Double)11.,uut.getFast(2, 3));
	}
	
	@Test
	public void testGet2DArray(){
		Double[][] expected = new Double[][]{{0d,1d,2d},{3d,4d,5d},{6d,7d,8d},{9d,10d,11d}};
		Double[][] result = uut.get2DArray();
		
		assertTrue("The returned array should be equal",ArrayUtils.equals2D(expected,result));
	}
	
	
	@Test
	public void testGetValues(){
		List<Double> values = uut.getValues();
		List<Double> expected = Arrays.asList(new Double[]{0d,1d,2d,3d,4d,5d,6d,7d,8d,9d,10d,11d});
		assertEquals("The getValue methode should give the good list",expected,values);
	}
	
	@Test
	public void testClone(){
		MatrixDouble2D clone = uut.clone();
		assertEquals("the clone name should be good",uut.getName()+"_clone",clone.getName());
		assertSame("The main.java.space should be the same",uut.getSpace(),clone.getSpace());
		
		assertEquals("The values should be the same:",uut.toString(),clone.toString());
		
		
	}
	
	
	@Test 
	public void setFast(){
		uut.setFast(1, 1, 99);
		List<Double> values = uut.getValues();
		List<Double> expected = Arrays.asList(new Double[]{0d,1d,2d,3d,99d,5d,6d,7d,8d,9d,10d,11d});
		assertEquals("The getValue methode should give the good list",expected,values);
		
	}
	
	@Test
	public void constructVector(){
		uut = new MatrixDouble2D("uut", new InfiniteDt(), new Space1D(4), 7.);
		List<Double> values = uut.getValues();
		List<Double> expected = Arrays.asList(new Double[]{7d,7d,7d,7d});
		assertEquals("The getValue methode should give the good list",expected,values);
		
	}
	
	
	
	

}
