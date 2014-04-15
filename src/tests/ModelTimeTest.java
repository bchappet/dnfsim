package tests;

import gui.Printer;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;

import junit.framework.TestCase;
import model.Model;
import model.ModelBilayerSpike;
import model.ModelRSDNF;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelTimeTest   extends TestCase{
	
	int nb_it = 10;
	double time_limit = 1;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testCNFT() throws Exception {
//		
//		long t1 = System.currentTimeMillis();
//		
//		Model cnft = new ModelCNFT("CNFT_test");
//		File f = new File("");
//		URL contextPath = null;
//		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
//		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
//		cnft.initialize(contextScript);
//		
//		long t2 = System.currentTimeMillis();
//		
//		while(cnft.getTime() < time_limit){
//			cnft.update();
//		}
//		
//		long t3 = System.currentTimeMillis();
//		
//		printTime(cnft.getName(),t1, t2, t3);
//		
//	}
	
//	@Test
//	public void testCNFTFFT() throws Exception {
//		
//		long t1 = System.currentTimeMillis();
//		
//		Model cnft = new ModelCNFTFFT("CNFTFFT_test");
//		File f = new File("");
//		URL contextPath = null;
//		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
//		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
//		cnft.initialize(contextScript);
//		
//		long t2 = System.currentTimeMillis();
//		
//		while(cnft.getTime() < time_limit){
//			cnft.update();
//		}
//		
//		long t3 = System.currentTimeMillis();
//		
//		printTime(cnft.getName(),t1, t2, t3);
//		
//	}
	
	
	
//	@Test
//	public void testESpike() throws Exception {
//		
//		long t1 = System.currentTimeMillis();
//		
//		Model cnft = new ModelESpike("ESpike_test");
//		File f = new File("");
//		URL contextPath = null;
//		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
//		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
//		cnft.initialize(contextScript);
//		
//		long t2 = System.currentTimeMillis();
//		
//		while(cnft.getTime() < time_limit){
//			cnft.update();
//		}
//		
//		long t3 = System.currentTimeMillis();
//		
//		printTime(cnft.getName(),t1, t2, t3);
//		
//	}
	
//	@Test
//	public void testNSpike() throws Exception {
//		
//		long t1 = System.currentTimeMillis();
//		
//		Model cnft = new ModelNSpike("NSpike_test");
//		File f = new File("");
//		URL contextPath = null;
//		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
//		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
//		cnft.initialize(contextScript);
//		
//		long t2 = System.currentTimeMillis();
//		
//		while(cnft.getTime() < time_limit){
//			cnft.update();
//		}
//		
//		long t3 = System.currentTimeMillis();
//		
//		printTime(cnft.getName(),t1, t2, t3);
//		
//	}
//	CNFT("CNFT",ModelCNFT.class,false),
//	CNFTFFT("CNFTFFT",ModelCNFTFFT.class,false),
//	GSPIKE("GSpike",ModelGSpike.class,false),
//	ESPIKE("ESpike",ModelESpike.class,false),
//	NSPIKE("NSpike",ModelNSpike.class,false),
//	NSPIKE2("NSpikeAssynch",ModelNSpike2.class,true),
//	BILAYER_SPIKE("BilayerSpike",ModelBilayerSpike.class,false),
//	RSDNF("RSDNF",ModelRSDNF.class,false),
//	RSDNF_MIXTE("RSDNF_Mixte",ModelRSDNFMixte.class,false),
//	HARDWARE("Hardware",ModelHardware.class,false),
//	CNFT_SLOW("CNFTAssynch",ModelCNFTSlow.class,true);
	
	@Test
	public void testBilayer() throws Exception {
		
		long t1 = System.currentTimeMillis();
		
		Model cnft = new ModelBilayerSpike("BilayerSpike_test");
		File f = new File("");
		URL contextPath = null;
		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
		cnft.initialize(contextScript);
		
		long t2 = System.currentTimeMillis();
		
		while(cnft.getTime().compareTo(new BigDecimal(""+ time_limit)) < 0){
			cnft.update(new BigDecimal("0.1"));
		}
		
		long t3 = System.currentTimeMillis();
		
		printTime(cnft.getName(),t1, t2, t3);
		
	}
	@Test
	public void testRSDNF() throws Exception {
		
		long t1 = System.currentTimeMillis();
		
		Model cnft = new ModelRSDNF("RSDNF_test");
		File f = new File("");
		URL contextPath = null;
		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
		cnft.initialize(contextScript);
		
		long t2 = System.currentTimeMillis();
		
		int i= 0;
		while(cnft.getTime().compareTo(new BigDecimal(""+ time_limit)) < 0){
			cnft.update(new BigDecimal("0.1"));
			i++;
		}
		
		long t3 = System.currentTimeMillis();
		
		printTime(cnft.getName(),t1, t2, t3);
		
	}
	
//	@Test
//	public void testHardware() throws Exception {
//		
//		long t1 = System.currentTimeMillis();
//		
//		Model cnft = new ModelHardware("Hardware_test");
//		File f = new File("");
//		URL contextPath = null;
//		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
//		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
//		cnft.initialize(contextScript);
//		
//		long t2 = System.currentTimeMillis();
//		
//		while(cnft.getTime() < time_limit){
//			cnft.update();
//		}
//		
//		long t3 = System.currentTimeMillis();
//		
//		printTime(cnft.getName(),t1, t2, t3);
//		
//	}
//	
//	@Test
//	public void testCNFTSlow() throws Exception {
//		
//		long t1 = System.currentTimeMillis();
//		
//		Model cnft = new ModelCNFTSlow("CNFT_test");
//		File f = new File("");
//		URL contextPath = null;
//		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
//		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
//		cnft.initialize(contextScript);
//		
//		long t2 = System.currentTimeMillis();
//		
//		while(cnft.getTime() < time_limit){
//			cnft.update();
//		}
//		
//		long t3 = System.currentTimeMillis();
//		
//		printTime("CNFTAssynch",t1, t2, t3);
//		
//	}
	
	
	
	
	private static void printTime(String name,long t1,long t2,long t3){
		System.out.println("==> " + name + " : ");
		System.out.println("Initialization time : " + (t2 - t1));
		System.out.println("Computation time : " + (t3 - t2));
	}

}
