package main.java.network.generic;

import java.io.File;
import java.math.BigDecimal;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.network.probalisticFlooding.PFCommandLine;
import main.java.network.probalisticFlooding.PFNode;
import main.java.network.probalisticFlooding.PFSpreadingGraph;
import main.java.network.rsdnf.RSDNFCommandLine;
import main.java.network.rsdnf.RSDNFSpreadingGraph;
import main.java.network.rsdnf.RSDNFTransmitter;
import main.java.pfspike.PFSCommandLine;
import main.java.pfspike.PFSSpreadingGraph;

import org.jdom2.DataConversionException;

/**
 *
 * @author CARRARA Nicolas
 */
public class SpreadingGraphFactory {

	private static SpreadingGraphFactory instance = null;



	private SpreadingGraphFactory() {

	}

	public static SpreadingGraphFactory getInstance() {
		if (instance == null) {
			instance = new SpreadingGraphFactory();
		}
		return instance;
	}

	/**
	 * parse un fichier afin de construire un spreading graphe. lit le fichier
	 * comme une matrice d'adjacence.
	 *
	 * @param typeGraph
	 * @param file
	 * @param commandLine
	 * @return
	 * @throws main.java.console.CommandLineFormatException
	 * @throws NetworkException 
	 */
	public SpreadingGraph constructGraph(File file, TypeGraph typeGraph, CommandLine commandLine,Parameter ... params) throws CommandLineFormatException, NetworkException {
		double[][] matrice = Utils.parseCSVFile(file);
		return constructGraph(matrice, typeGraph, commandLine,params);
	}



	/**
	 * Construit un spreadingGraph à partir d'une matrice d'adjacence.
	 *
	 * @param matrice
	 * @param typeGraph
	 * @param commandLine
	 * @return
	 * @throws main.java.console.CommandLineFormatException
	 * @throws NetworkException 
	 */
	public SpreadingGraph constructGraph(double[][] matrice, TypeGraph typeGraph, CommandLine commandLine,Parameter ... params) throws CommandLineFormatException, NetworkException {
		SpreadingGraph res;
		StimulisMap sm;
		Var<BigDecimal> tempdt= new Var<BigDecimal>(BigDecimal.ZERO);
		try {


			switch (typeGraph) {
			case RSDNF:
				RSDNFCommandLine crsdnf = (RSDNFCommandLine)commandLine;
				sm = new StimulisMap(
						(Var<String>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.STIMULIS_FILE),
						(Var<BigDecimal>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.STIMULIS_DT),
						(Var<BigDecimal>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.SIZE));
				res = new RSDNFSpreadingGraph((Var<BigDecimal>) crsdnf.get(RSDNFCommandLine.NETWORK_DT),sm);
				for (int i = 0; i < matrice.length; i++) {
					res.getNodes().add(new RSDNFTransmitter(((Var<Double>)crsdnf.get(RSDNFCommandLine.WEIGTH)).get()));
				}
				//                System.out.println("Matrix size  x" + matrice.length);
				//                System.out.println("Matrix size  y" + matrice[0].length);
				for (int l = 0; l < matrice.length; l++) {
					for (int c = 0; c < matrice[l].length; c++) {
						if (matrice[l][c] == 1) {
							((RSDNFTransmitter)res.getNodes().get(l)).link((RSDNFTransmitter)res.getNodes().get(c));
						}
					}
				}
				return res;

			case DEFAULT_GRAPH:
				sm = new StimulisMap(
						(Var<String>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.STIMULIS_FILE),
						(Var<BigDecimal>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.STIMULIS_DT),
						(Var<BigDecimal>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.SIZE));
				NetworkCommandLine nwcl = (NetworkCommandLine)commandLine;
				res = new SpreadingGraph((Var<BigDecimal>) nwcl.get(NetworkCommandLine.NETWORK_DT),sm);
				for (int i = 0; i < matrice.length; i++) {
					res.getNodes().add(new Node(DirectedEdge.class));
				}
				for (int l = 0; l < matrice.length; l++) {
					for (int c = 0; c < matrice[l].length; c++) {
						if (matrice[l][c] == 1) {
							((Node)res.getNodes().get(l)).link((Node)res.getNodes().get(c));
						}
					}
				}
				return res;

			case PROBABILISTIC_FLOODING:
				sm = new StimulisMap(
						(Var<String>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.STIMULIS_FILE),
						(Var<BigDecimal>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.STIMULIS_DT),
						(Var<BigDecimal>)((NetworkCommandLine)commandLine).get(NetworkCommandLine.SIZE));
				PFCommandLine pfcl = (PFCommandLine)commandLine;
				//Var<String> init = pfcl.get(PFCommandLine.PACKET_INTIALISATION);
				res = new PFSpreadingGraph((Var<BigDecimal>) pfcl.get(PFCommandLine.NETWORK_DT),sm);
				//                System.out.println("poids : "+((Var<Double>)pfcl.get(PFCommandLine.WEIGTH)).get());
				for (int i = 0; i < matrice.length; i++) {
					res.getNodes().add(new PFNode(((Var<Double>)pfcl.get(PFCommandLine.WEIGTH)).get()));
				}
				for (int l = 0; l < matrice.length; l++) {
					for (int c = 0; c < matrice[l].length; c++) {
						if (matrice[l][c] == 1) {
							((PFNode)res.getNodes().get(l)).link((PFNode)res.getNodes().get(c));
						}
					}
				}
				return res;
			case INHIBITORY_GRAPH_PFSPIKE:
				PFSCommandLine pfscl = (PFSCommandLine)commandLine;
				Var<BigDecimal> maindt = (Var<BigDecimal>) pfscl.get(PFSCommandLine.MAIN_DT);
				Var<Integer> nbcomputation = (Var<Integer>) pfscl.get(PFSCommandLine.I_NBCOMPUTATION);
				Var<BigDecimal> threshold = (Var<BigDecimal>) pfscl.get(PFSCommandLine.THRESHOLD);
				Var<Integer> nbSpike = (Var<Integer>) pfscl.get(PFSCommandLine.NB_SPIKE);
				Var<Integer> size = (Var<Integer>) pfscl.get(PFSCommandLine.SIZE);
				sm = StimulisMap.NO_STIMULIS_MAP;
				Map focus = (Map) params[0];
				BigDecimal weigth = ((Var<BigDecimal>)pfscl.get(PFSCommandLine.I_WEIGTH)).get();


				/*public static final int FOCUS = 6;
				public static final int NB_SPIKE = 5;
				public static final int TRESHOLD = 4;	
				public static final int NB_COMPUTATION = 3;
				public static final int MAIN_DT = 2;*/

				res = new PFSSpreadingGraph(tempdt,sm, maindt,nbcomputation, threshold, nbSpike, focus,new Var("IPFSSpreadingGraph"));
				for (int i = 0; i < matrice.length; i++) {
					res.getNodes().add(new PFNode(weigth.doubleValue()));
				}
				for (int l = 0; l < matrice.length; l++) {
					for (int c = 0; c < matrice[l].length; c++) {
						if (matrice[l][c] == 1) {
							((PFNode)res.getNodes().get(l)).link((PFNode)res.getNodes().get(c));
						}
					}
				}
				res.getParameters().add(focus);
				return res;
			case EXCITATORY_GRAPH_PFSPIKE:
				pfscl = (PFSCommandLine)commandLine;
				maindt = (Var<BigDecimal>) pfscl.get(PFSCommandLine.MAIN_DT);
				nbcomputation = (Var<Integer>) pfscl.get(PFSCommandLine.E_NBCOMPUTATION);
				threshold = (Var<BigDecimal>) pfscl.get(PFSCommandLine.THRESHOLD);
				nbSpike = (Var<Integer>) pfscl.get(PFSCommandLine.NB_SPIKE);
				size = (Var<Integer>) pfscl.get(PFSCommandLine.SIZE);
				sm = StimulisMap.NO_STIMULIS_MAP;
				focus = (Map) params[0];
				weigth = ((Var<BigDecimal>)pfscl.get(PFSCommandLine.E_WEIGTH)).get();
				res = new PFSSpreadingGraph(tempdt, sm,maindt,nbcomputation, threshold, nbSpike, focus,new Var("EPFSSpreadingGraph"));
				for (int i = 0; i < matrice.length; i++) {
					res.getNodes().add(new PFNode(weigth.doubleValue()));
				}
				for (int l = 0; l < matrice.length; l++) {
					for (int c = 0; c < matrice[l].length; c++) {
						if (matrice[l][c] == 1) {
							((PFNode)res.getNodes().get(l)).link((PFNode)res.getNodes().get(c));
						}
					}
				}
				res.getParameters().add(focus);
				//System.out.println(res.getParameters());
				return res;
			}
		} catch (DataConversionException e) {
			// TODO Auto-generated catch block
			throw new NetworkException("Fichier .stimulis corrompu\n"+e);
		}
		return null;
	}


}
