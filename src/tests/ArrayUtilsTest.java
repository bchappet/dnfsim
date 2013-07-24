package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.ArrayUtils;
import utils.Cloneable;

public class ArrayUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConcat() {
		Integer[] a = {1,2};
		Integer[] b = {};
		Integer[] res = ArrayUtils.concat(a, b);
		assertTrue(Arrays.equals(res, new Integer[]{1,2}));
		System.out.println(Arrays.toString(a) +" + " + Arrays.toString(b));
		System.out.println(Arrays.toString(res));
		
	}
	
	@Test
	public void testDeepCopy() throws CloneNotSupportedException{
		
		List<AnObject> l1 = new ArrayList<AnObject>();
		l1.add(new AnObject(new AnOtherObject(1)));
		l1.add(new AnObject(new AnOtherObject(2)));
		l1.add(new AnObject(new AnOtherObject(3)));
		List<AnObject> l2 = ArrayUtils.deepCopy(l1);
		System.out.println(l1);
		System.out.println(l2);
		assertFalse(l1.get(0).equals(l2.get(0)));
		assertFalse(l1.get(0).o.equals(l2.get(0).o));
		assertTrue(l1.get(0).o.i == l2.get(0).o.i);
		
		
	}
	
	public class AnObject implements Cloneable{
		protected AnOtherObject o;
		public AnObject(AnOtherObject o){
			this.o = o;
		}
		public AnObject clone() throws CloneNotSupportedException
		{
			AnObject clone = (AnObject) super.clone();
			clone.o = (AnOtherObject) this.o.clone();
			return clone;
		}
		
		public String toString()
		{
			return super.toString() + "->" + o.toString();
		}
	}
	
	public class AnOtherObject implements Cloneable{
		protected int i;
		public AnOtherObject(int i){
			this.i = i;
		}
		public AnOtherObject clone() throws CloneNotSupportedException
		{
			return (AnOtherObject) super.clone();
		}
		
	}
	
	@Test
	public void reverseTest() {
		
		int[] test1 = {0,1,2,3,4};
		int[] test2 = {0,1,2,3};
		
		ArrayUtils.reverse(test1);
		ArrayUtils.reverse(test2);
		
		System.out.println(	Arrays.toString(test1));
		System.out.println(	Arrays.toString(test2));
		
		assertTrue(	Arrays.equals(test1,new int[]{4,3,2,1,0}));
		assertTrue(	Arrays.equals(test2,new int[]{3,2,1,0}));
	}
		

}
