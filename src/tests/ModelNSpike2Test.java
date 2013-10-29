package tests;

import gui.Printer;

import java.net.URL;

import junit.framework.TestCase;

import model.Model;
import model.ModelNSpike;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

public class ModelNSpike2Test   extends TestCase{

	protected Model model;

	@Before
	public void setUp() throws Exception {

		model = new ModelNSpike("NSpikeAssynch");
		//model.setAssynchronousComputation(true);
		URL contextPath = new URL("file:./src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+model.getName()+".dnfs"));
		model.initialize(contextScript);
		model.getRootParam().constructMemory();

	}

	@After
	public void tearDown() throws Exception {
		model.reset();
	}

	@Test
	public void test() throws NullCoordinateException, CommandLineFormatException {

		for(int nb = 0; nb < 20 ; nb ++){
			System.out.println(nb);
			for(int i = 0 ; i < 50 ; i++){
				model.update();
			}
			model.getCharac().compute();
			System.out.println(model.getCharac());
			model.reset();
		}

	}

}
