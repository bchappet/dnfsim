package test.java.controler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.model.ModelCNFT;
import main.java.reservoirComputing.ESNCommandLine;
import main.java.reservoirComputing.ModelESN;

import org.junit.Before;
import org.junit.Test;

public class ComputationControlerTest {
	
	private ComputationControler uut;
	private ModelControler mc;
	private ModelESN model;
//	@Before
//	public void setUp() throws Exception {
//		
//		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
//	    logger.setLevel(Level.INFO);
//	    FileHandler fileTxt = new FileHandler("Logging.txt");
//	    SimpleFormatter formatterTxt = new SimpleFormatter();
//	    fileTxt.setFormatter(formatterTxt);
//	    logger.addHandler(fileTxt);
//
//		
//		ESNCommandLine cl = new ESNCommandLine();
//		model = new ModelESN("test_esn");
//		cl.setContext("");
//		model.initialize(cl);
//		
//		mc = new ModelControler(model);
//		cl.setCurentModelControler(mc);
//		uut = new ComputationControler(mc.getTree());
//		
//		
//	}
//
//	@Test
//	public void testCompute() throws IOException {
//		
//		System.out.println(mc.getTree());
//		
//		uut.compute(new BigDecimal("0.39"));
//		
//	}
	
	@Test
	public void testComputeCNFT() throws IOException, CommandLineFormatException {
		
		CNFTCommandLine cl = new CNFTCommandLine();
		ModelCNFT model = new ModelCNFT("test_cnft");
		cl.setContext("");
		model.initialize(cl);
		
		mc = new ModelControler(model);
		cl.setCurentModelControler(mc);
		uut = new ComputationControler(mc.getTree());
		
		System.out.println(mc.getTree());
		
		uut.compute(new BigDecimal("0.39"));
		
	}
	
	

}
