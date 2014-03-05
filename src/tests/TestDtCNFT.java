package tests;

import gui.Printer;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import maps.Map;
import maps.Parameter;
import model.Model;
import model.ModelCNFT;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

public class TestDtCNFT {
	Model cnft;
	String contextScript;
	Map potential;
	Map input;
	@Before
	public void setUp() throws Exception {
		cnft = new ModelCNFT("CNFT_test");
		URL contextPath = null;
		contextPath = new URL("file:./src/tests/context/");
		contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws NullCoordinateException, FileNotFoundException, MalformedURLException, CommandLineFormatException {
		init(0.4);
		initMaps();
		cnft.update(BigDecimal.ZERO);
		
		cnft.update(new BigDecimal("0.4"));
		print();
		
		cnft.update(new BigDecimal("0.8"));
		print();
		
		cnft.update(new BigDecimal("1.2"));
		print();
		
		cnft.update(new BigDecimal("1.4"));
		print();
		
		cnft.update(new BigDecimal("10"));
		print();
		
		
		
		
	}
	
	protected void print(){
		System.out.println(input.display2D());
		System.out.println(potential.display2D());
		
	}
	
	protected void init(double dt) throws NullCoordinateException, FileNotFoundException, MalformedURLException, CommandLineFormatException{
		cnft.initialize(contextScript +
				"tck_dt=" + dt +
				";input_dt=" + dt +
				";dt=" + dt +
				";stat_dt= " + dt +
				";simu_dt= " + dt +
				";tck_nb=1;");
	}
	
	protected void initMaps(){
		potential = (Map) cnft.getParameter(ModelCNFT.POTENTIAL);
		input = (Map) cnft.getParameter(ModelCNFT.INPUT);
		potential = (Map) cnft.getRootParam();
	}

}
