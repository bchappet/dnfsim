package test.java.space;

import static org.junit.Assert.*;
import main.java.maps.Var;
import main.java.space.Coord;
import main.java.space.DoubleSpace;
import main.java.space.Space2D;

import org.junit.Before;
import org.junit.Test;



public class DoubleSpaceTest {
	
	private DoubleSpace uut;
	private static final double DELTA = 0.00000001;

	@Before
	public void setUp() throws Exception {
		uut = new DoubleSpace(new Coord<Var<Double>>(new Var<Double>(-0.5),new Var<Double>(-0.5)),
				new Coord<Var<Double>>(new Var<Double>(1.),new Var<Double>(1.)),
				new Var<Integer>(5));
	}

	@Test
	public void testGetVolume() {
		assertEquals("The volume should be 25", 25,uut.getVolume());
	}

	@Test
	public void testGetResolution() {
		assertEquals("The resolution should be 5",(Integer)5,uut.getResolution().get());
	}

	@Test
	public void testTypeAxisProj() {
		assertEquals("The projection on the axis should be correct: ",new Double(-0.4),uut.typeAxisProj(0, Space2D.X),DELTA);
		assertEquals("The projection on the axis should be correct: ",new Double(-0.4),uut.typeAxisProj(0, Space2D.Y),DELTA);
		
		assertEquals("The projection on the axis should be correct: ",new Double(-0.2),uut.typeAxisProj(1, Space2D.X),DELTA);
		assertEquals("The projection on the axis should be correct: ",new Double(0.0),uut.typeAxisProj(2, Space2D.X),DELTA);
		assertEquals("The projection on the axis should be correct: ",new Double(0.2),uut.typeAxisProj(3, Space2D.X),DELTA);
		assertEquals("The projection on the axis should be correct: ",new Double(0.4),uut.typeAxisProj(4, Space2D.X),DELTA);
		
		
		
	}
	
	@Test
	public void testTypeDistProj() {
		assertEquals("The projection on the dist should be correct: ",new Double(0),uut.typeDistProj(0, Space2D.X),DELTA);
		assertEquals("The projection on the dist should be correct: ",new Double(0),uut.typeDistProj(0, Space2D.Y),DELTA);
		
		assertEquals("The projection on the dist should be correct: ",new Double(0.2),uut.typeDistProj(1, Space2D.X),DELTA);
		assertEquals("The projection on the dist should be correct: ",new Double(0.4),uut.typeDistProj(2, Space2D.X),DELTA);
		assertEquals("The projection on the dist should be correct: ",new Double(0.6),uut.typeDistProj(3, Space2D.X),DELTA);
		assertEquals("The projection on the dist should be correct: ",new Double(0.8),uut.typeDistProj(4, Space2D.X),DELTA);
		
	}
	
	@Test
	public void testIntDistProj() {
		assertEquals("The projection on the dist should be correct: ",0,(int)uut.intDistProj(0., Space2D.X));
		assertEquals("The projection on the dist should be correct: ",0,(int)uut.intDistProj(0., Space2D.Y));
		
		
		assertEquals("The projection on the dist should be correct: ",1,(int)uut.intDistProj(0.2, Space2D.X));
		assertEquals("The projection on the dist should be correct: ",2,(int)uut.intDistProj(0.4, Space2D.X));
		assertEquals("The projection on the dist should be correct: ",3,(int)uut.intDistProj(0.6, Space2D.X));
		assertEquals("The projection on the dist should be correct: ",4,(int)uut.intDistProj(0.8, Space2D.X));
		
	}


}
