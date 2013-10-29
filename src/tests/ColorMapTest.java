package tests;

import static org.junit.Assert.assertTrue;
import gui.ColorMap;

import java.awt.Color;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColorMapTest  extends TestCase{
	private ColorMap cmap;

	@Before
	public void setUp() throws Exception {
		Color[] scale = {Color.BLUE,Color.WHITE,Color.RED};
		cmap = new ColorMap(scale);
	}

	@After
	public void tearDown() throws Exception {
		cmap = null;
	}

	@Test
	public void testColor() {
			double pt = 0;
			System.out.println(cmap.closestPoints(pt)[0]);
			System.out.println(cmap.closestPoints(pt)[1]);
			System.out.println(cmap.getColor(pt));
	}

	@Test
	public void testGetColorRGB() {
		Color color = new Color(1,2,3);
		assertTrue(ColorMap.getColorRGBA(color,0)==1);
		assertTrue(ColorMap.getColorRGBA(color,1)==2);
		assertTrue(ColorMap.getColorRGBA(color,2)==3);
	}

	@Test
	public void testClosestPoints() {
			double pt = 0.99;
			assertTrue(cmap.closestPoints(pt)[0]==1);
			assertTrue(cmap.closestPoints(pt)[1]==2);
			
			pt = -1;
			assertTrue(cmap.closestPoints(pt)[0]==0);
			assertTrue(cmap.closestPoints(pt)[1]==1);
	}

}
