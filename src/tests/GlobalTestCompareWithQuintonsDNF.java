package tests;

import static org.junit.Assert.assertTrue;

import gui.Printer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import maps.Parameter;
import model.Model;
import model.ModelCNFT;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import statistics.Characteristics;
import dnf.CNFT;
import dnf.DenseCNFT;
import dnf.DiscreteCNFT;
import dnf.Map;
import dnf.Statistics;
import dnf.UniformNoise;

/**
 * The refSpace discretization is different => we cannot compare the two implementation
 * point to point TODO find a way
 * @author bchappet
 *
 */
public class GlobalTestCompareWithQuintonsDNF {
	private Model cnft;
	private CNFT jcqcnft;
	private double dt = 0.1;
	private Statistics stats;

	@Before
	public void setUp() throws Exception {

		cnft = new ModelCNFT("CNFT_test");
		File f = new File("");
		URL contextPath = null;
		contextPath = new URL(f.toURI().toURL()+"/src/tests/context/");
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
		stats = new Statistics(jcqcnft);

	}

	@After
	public void tearDown() throws Exception {
	}

	private String jcqFile = "src/tests/jcq";
	private String cnftFile = "src/tests/cnft";
	@Test
	public void test() throws Exception {
		
		String script = 
				"noise_amp=0;" +
						"tck_nb=1";
		cnft.getCommandLine().parseCommand(script);

		Runtime r = Runtime.getRuntime();

		for(int i = 0 ; i < 100 ; i++){
			
			cnft.update();
			jcqcnft.update(jcqcnft.getTime()+dt);
			stats.update();

//			cnft.save(cnftFile);
//			jcqcnft.saveMaps(jcqFile);
//
//			compareMaps(cnftFile,jcqFile);
//
//			for(Parameter p : cnft.getDefaultDisplayedParameter())
//			{
//				Process proc = r.exec("rm  "+cnftFile+"_"+p.getName()+".csv");
//				proc.waitFor();
//			}

		}
		
		Characteristics c =  cnft.getCharac();
		c.compute();
		System.out.println(c);
		cnft.getStatistics().save(cnftFile+"testStats.csv");
		stats.save(jcqFile+"testStats.csv");
		
	}

	/**
	 * Compare file for each map
	 * @param cnftFile2
	 * @param jcqFile2
	 * @throws IOException 
	 */
	private void compareMaps(String file1, String file2) throws IOException {
		FileReader fr1 = null;
		FileReader fr2 = null;

		for(Parameter p : cnft.getDefaultDisplayedParameter())
		{
			fr1= new FileReader(file1+"_"+p.getName()+".csv");
			BufferedReader br1 = new BufferedReader(fr1);
			fr2= new FileReader(file2+"_"+p.getName()+".csv");
			BufferedReader br2 = new BufferedReader(fr2);

			String line1;
			String line2;
			int i = 0;
			while( ((line1 = br1.readLine()) != null) && ((line2 = br2.readLine()) != null))
			{
				System.out.println("Ligne : " + i);
				System.out.println("cnft : " + line1);
				System.out.println("jcq : " + line2);
				assertTrue(line1.equals(line2));
				i ++;
			}

			br1.close();
			br2.close();

		}

	}

}
