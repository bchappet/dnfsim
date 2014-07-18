package test.java.maps;

import static org.junit.Assert.assertEquals;
import main.java.maps.Var;

import org.junit.Before;
import org.junit.Test;

public class VarTest {
	private Var<Double> uut;

	@Before
	public void setUp() throws Exception {
		uut = new Var<Double>("uut", new Double(0.1));
	}

	@Test
	public void testGet() {
		assertEquals("the Var value should be 0.1", new Double(0.1), uut.get());
	}

	@Test
	public void testGetIndex() {
		assertEquals("get index should return the uut", new Double(0.1), uut.getIndex(1));
	}

	@Test
	public void testGetValues() {
		assertEquals("get index should return the uut", new Double(0.1), uut.getValues().get(0));
	}

	@Test
	public void testClone() {
		assertEquals("The clone val has the same reference", uut.get(), uut
				.clone().get());
	}

	@Test
	public void testSet() {
		uut.set(0.3);
		assertEquals("the Var value should be 0.3", new Double(0.3), uut.get());
	}

}
