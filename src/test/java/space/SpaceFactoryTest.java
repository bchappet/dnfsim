package test.java.space;

import static org.junit.Assert.*;

import java.io.IOException;

import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
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
		Space1D space = SpaceFactory.getSpace1D(file);
		assertEquals("The vector size = 2",(int) 2,space.getDimX());
	}
	
	@Test
	public void testDim1() throws IOException {
		String file = "src/test/data/signal/testSignalFileReader1.dat";
		Space1D space = SpaceFactory.getSpace1D(file);
		assertEquals("The vector size = 1",(int) 1,space.getDimX());
	}

    @Test
    public void testmatrixProductSpace(){
        Space a = new Space2D(3,4);
        Space b = new Space2D(4,7);

        Space res = SpaceFactory.matrixProductSpace(a,b);
        //System.out.println(res);
        assertEquals("The space should be 3,7",new Space2D(3,7),res);
    }

}
