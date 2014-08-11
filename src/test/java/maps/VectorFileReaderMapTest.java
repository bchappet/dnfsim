package test.java.maps;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import main.java.maps.VectorFileReaderMap;
import main.java.maps.Var;
import main.java.space.NoDimSpace;
import main.java.space.Space1D;
import main.java.space.SpaceFactory;

import org.junit.Before;
import org.junit.Test;

public class VectorFileReaderMapTest {
	
	private Var<String> sep ;
	private Var<String> file ;
	private VectorFileReaderMap uut;
	private Var<BigDecimal> dt;


	@Before
	public void setUp() throws Exception {
		sep = new Var<String>(",");
		file = new Var<String>("src/test/data/signal/testSignalFileReader1.dat");
		dt = new Var<BigDecimal>(new BigDecimal("0.1"));
		
	}

	@Test
	public void testCompute1() throws FileNotFoundException {
		file = new Var<String>("src/test/data/signal/testSignalFileReader1.dat");
		uut = new VectorFileReaderMap("uut", dt, new NoDimSpace(),	file,sep);

		assertEquals("On init, default is 0: ",(Double)0.,uut.getIndex(0));
		uut.compute();
		assertEquals("Then read every input: ",(Double)1.,uut.getIndex(0));
		uut.compute();
		assertEquals("Then read every input: ",(Double)2.,uut.getIndex(0));
		uut.compute();
		assertEquals("Then read every input: ",(Double)3.,uut.getIndex(0));
		uut.compute();
		assertEquals("Then read every input: ",(Double)4.,uut.getIndex(0));
		uut.compute();
		assertEquals("Then read every input: ",(Double)5.,uut.getIndex(0));

		
	}
	
	
	@Test
	public void testCompute2() throws FileNotFoundException {
		file = new Var<String>("src/test/data/signal/testSignalFileReader2.dat");
		uut = new VectorFileReaderMap("uut", dt, new Space1D(2),	file,sep);

		assertEquals("On init, default is 0: ",Arrays.asList(new Double[]{0.,0.}),uut.getValues());
		uut.compute();
		assertEquals("Then read every input: ",Arrays.asList(new Double[]{1.,0.1}),uut.getValues());
		uut.compute();
		assertEquals("Then read every input: ",Arrays.asList(new Double[]{2.,0.2}),uut.getValues());
		uut.compute();
		assertEquals("Then read every input: ",Arrays.asList(new Double[]{3.,0.3}),uut.getValues());
		uut.compute();
		assertEquals("Then read every input: ",Arrays.asList(new Double[]{4.,0.4}),uut.getValues());
		uut.compute();
		assertEquals("Then read every input: ",Arrays.asList(new Double[]{5.,0.5}),uut.getValues());

		
	}
	


}
