package tests;

import static org.junit.Assert.*;
import gui.Printer;

import java.net.URL;

import junit.framework.TestCase;

import model.Model;
import model.ModelCNFTFFT;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

import dnf.CNFT;
import dnf.DenseCNFT;
import dnf.DiscreteCNFT;
import dnf.Map;
import dnf.Statistics;
import dnf.UniformNoise;

public class GlobalTestCompareSpeedQuinton   extends TestCase{

	private Model cnft;
	private CNFT jcqcnft;
	private StatisticsCNFT stats;
	private double dt = 0.1;
	@Before
	public void setUp() throws Exception {

		cnft = new ModelCNFTFFT("CNFTFFT_test");
		URL contextPath = null;
		contextPath = new URL("file:./src/tests/context/");
		String contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
		cnft.initialize(contextScript);

		//Init jcq model
		int track_nb = 1;
		int distr_nb = 0;
		double noise = 0.0;
		double resolution = 49;
		double a1=1.25;
		double  s1=0.1;
		double a2=-0.7;
		double s2=1.0;

		jcqcnft =  new DiscreteCNFT(resolution);
		Map input = jcqcnft.getDefaultInputs(track_nb,distr_nb,new UniformNoise(),noise);
		jcqcnft.put(input);
		jcqcnft.put(jcqcnft.getCNFTWeights(a1,s1,a2,s2));
		((DenseCNFT)jcqcnft).setResolution(resolution);
		stats = new StatisticsCNFT(jcqcnft);
	}

	@After
	public void tearDown() throws Exception {


	}

	@Test
	public void test() throws Exception {
		long t1 = System.currentTimeMillis();
		for(int i = 0 ; i < 10 ; i++){
			cnft.update();
		}
		long t2 = System.currentTimeMillis();
		
		for(int i = 0 ; i < 10 ; i++){
			jcqcnft.update(jcqcnft.getTime()+dt);
			stats.update();
		}
		long t3 = System.currentTimeMillis();
		
		System.out.println((t2 - t1) + " vs " + (t3 - t2));
		//4212 vs 733

		

	}
}
