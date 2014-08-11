package test.java.space;

import static org.junit.Assert.*;

import java.io.IOException;

import main.java.space.Space1D;
import main.java.space.SpaceFactory;

import org.junit.Before;
import org.junit.Test;

public class SpaceFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDim2() throws IOException {
		String file = "src/test/data/signal/testSignalFileReader2.dat";
		String sep = ",";
		Space1D space = SpaceFactory.getSpace1D(file, sep);
		assertEquals("The vector size = 2",(int) 2,space.getDimX());
	}
	
	@Test
	public void testDim1() throws IOException {
		String file = "src/test/data/signal/testSignalFileReader1.dat";
		String sep = ",";
		Space1D space = SpaceFactory.getSpace1D(file, sep);
		assertEquals("The vector size = 1",(int) 1,space.getDimX());
	}

}
