package mapTests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import maps.MatrixDouble2D;

import org.junit.Before;
import org.junit.Test;

import space.Space2D;
import utils.ArrayUtils;

public class MatrixDouble2DTest extends TestCase {
	
	protected MatrixDouble2D uut;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		uut = new MatrixDouble2D("uut", new double[][]{{0,1,2},{3,4,5},{6,7,8},{9,10,11}});
	}

	@Test
	public void testConstructerNToString1() {
		String expected = "0.0,1.0,2.0\n"+
						"3.0,4.0,5.0\n"+
						"6.0,7.0,8.0\n"+
						"9.0,10.0,11.0";
		assertEquals("The matrix should have the values expected",expected,uut.toString());
		assertEquals("The space should be of dim 3,4",Arrays.toString(new int[]{3,4}),Arrays.toString(uut.getSpace().getDimensions()));
	}
	
	@Test
	public void testConstructerCst() {
		uut = new MatrixDouble2D("uut",new Space2D(3,2));
		String expected = "0.0,0.0,0.0\n"+
						"0.0,0.0,0.0";
		assertEquals("The matrix should have the values expected",expected,uut.toString());
		
		uut = new MatrixDouble2D("uut",new Space2D(2,3),10);
		String expected2 = "10.0,10.0\n"+
						"10.0,10.0\n"+
						"10.0,10.0";
		assertEquals("The matrix should have the values expected",expected2,uut.toString());
	}
	
	@Test
	public void testGetIndex(){
		assertEquals("The element at the index should be good",10.,uut.getIndex(10));
		assertEquals("The element at the index should be good",5.,uut.getIndex(5));
		assertEquals("The element at the index should be good",1.,uut.getIndex(1));
	}
	
	@Test
	public void testGetFast(){
		assertEquals("The element at coordinate should be good",0.,uut.getFast(0, 0));
		assertEquals("The element at coordinate should be good",2.,uut.getFast(2, 0));
		assertEquals("The element at coordinate should be good",3.,uut.getFast(0, 1));
		assertEquals("The element at coordinate should be good",7.,uut.getFast(1, 2));
		assertEquals("The element at coordinate should be good",10.,uut.getFast(1, 3));
		assertEquals("The element at coordinate should be good",11.,uut.getFast(2, 3));
	}
	
	@Test
	public void testGet2DArray(){
		Double[][] expected = new Double[][]{{0d,1d,2d},{3d,4d,5d},{6d,7d,8d},{9d,10d,11d}};
		Double[][] result = uut.get2DArray();
		
		assertTrue("The returned array should be equal",ArrayUtils.equals2D(expected,result));
	}
	
	@Test
	public void testSetIndex(){
		uut.setIndex(0, 10d);
		uut.setIndex(11, 100d);
		assertEquals("The set index should modifie the value on the right place",10d,uut.getIndex(0));
		assertEquals("The set index should modifie the value on the right place",100d,uut.getIndex(11));
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
	}
	
	
	

}
