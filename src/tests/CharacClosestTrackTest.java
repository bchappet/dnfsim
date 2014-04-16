package tests;

import static org.junit.Assert.*;
import gui.Printer;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import maps.BadPathException;
import model.Model;
import model.ModelCNFT;
import model.ModelGSpikeFFT;

import org.junit.Before;
import org.junit.Test;

import statistics.CharacteristicsCNFT;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

public class CharacClosestTrackTest extends TestCase {

	private Model cnft;
	private String contextScript;

	@Before
	public void setUp() throws Exception {

		cnft = new ModelCNFT("CNFT_test");
		URL contextPath = null;
		contextPath = new URL("file:./src/tests/context/");
		 contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+cnft.getName()+".dnfs"));
		


	}

	@Test
	public void testConverge() throws NullCoordinateException, CommandLineFormatException, NumberFormatException, BadPathException, FileNotFoundException, MalformedURLException {
		//		for(int i = 0 ; i < 100 ; i++){
		//			cnft.update(new BigDecimal("" + i * 0.1));
		//		}

		cnft.initialize(contextScript);
		cnft.getCommandLine().parseCommand("tck_nb=2;"
				+"tck_period=1E-5;"
				+"beta=0.0001;"
				+"track_0.tck_i.clone=tck_i0;"
				+"tck_i0=tck_i*beta+tck_i;");
		for(int i = 0 ; i < 100 ; i++){
			cnft.update(new BigDecimal("" + i * 0.1));
		}
		//System.out.println("all : " + cnft.getCommandLine().parseCommand("print=all;"));
		//System.out.println("closest : " + cnft.getCommandLine().parseCommand("print=ClosestTrack,track_0;"));
				

		cnft.getCharac().compute();
		//System.out.println(cnft.getCharac());
		int closest_tck = (int) cnft.getCharac().getParam(CharacteristicsCNFT.CLOSEST_TRACK).get();
		//System.out.println(closest_tck);
		//System.out.println(cnft.getCommandLine().parseCommand("print=track_0;"));
		assertEquals("The cnft model should converge on the more proeminent stimulus: track0",Integer.parseInt(cnft.getCommandLine().parseCommand("print=track_0;")),closest_tck);

	}
	
	@Test
	public void testSmallDt() throws NullCoordinateException, CommandLineFormatException, NumberFormatException, BadPathException, FileNotFoundException, MalformedURLException {
		//		for(int i = 0 ; i < 100 ; i++){
		//			cnft.update(new BigDecimal("" + i * 0.1));
		//		}
		cnft = new ModelGSpikeFFT("GSpike_test");
		URL contextPath = null;
		contextPath = new URL("file:./src/tests/context/");
		cnft.initialize(contextScript+"resolution=51;");
		cnft.getCommandLine().parseCommand("dt=0.01;stat_dt=0.01;input_dt=0.01;tck_dt=0.01;simu_dt=0.01;");
		cnft.getCommandLine().parseCommand("tck_nb=2;"
				+"tck_period=1E-5;"
				+"beta=0.0001;"
				+"track_0.tck_i.clone=tck_i0;"
				+"tck_i0=tck_i*beta+tck_i;");
		for(int i = 0 ; i < 60 ; i++){
			cnft.update(new BigDecimal("" + i * 0.1));
		}
		//System.out.println("all : " + cnft.getCommandLine().parseCommand("print=all;"));
		//System.out.println("closest : " + cnft.getCommandLine().parseCommand("print=ClosestTrack,track_0;"));
				

		cnft.getCharac().compute();
		//System.out.println(cnft.getCharac());
		int closest_tck = (int) cnft.getCharac().getParam(CharacteristicsCNFT.CLOSEST_TRACK).get();
		//System.out.println(closest_tck);
		//System.out.println(cnft.getCommandLine().parseCommand("print=track_0;"));
		assertEquals("The cnft model should converge on the more proeminent stimulus: track0",Integer.parseInt(cnft.getCommandLine().parseCommand("print=track_0;")),closest_tck);

	}

}
