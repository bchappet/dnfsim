package main.java.network.generic;

import java.io.File;
import java.math.BigDecimal;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.maps.Var;
import main.java.network.probalisticFlooding.PFCommandLine;
import main.java.network.probalisticFlooding.PFNode;
import main.java.network.probalisticFlooding.PFSpreadingGraph;
import main.java.network.rsdnf.RSDNFCommandLine;
import main.java.network.rsdnf.RSDNFSpreadingGraph;
import main.java.network.rsdnf.RSDNFTransmitter;

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
     */
    public SpreadingGraph constructGraph(File file, TypeGraph typeGraph, CommandLine commandLine) throws CommandLineFormatException {
        double[][] matrice = Utils.parseCSVFile(file);
        return constructGraph(matrice, typeGraph, commandLine);
    }
    
    
    /**
     * Construit un spreadingGraph à partir d'une matrice d'adjacence.
     *
     * @param matrice
     * @param typeGraph
     * @param commandLine
     * @return
     * @throws main.java.console.CommandLineFormatException
     */
    public SpreadingGraph constructGraph(double[][] matrice, TypeGraph typeGraph, CommandLine commandLine) throws CommandLineFormatException {
    SpreadingGraph res;
        switch (typeGraph) {
            case RSDNF:
                RSDNFCommandLine crsdnf = (RSDNFCommandLine)commandLine;
                res = new RSDNFSpreadingGraph((Var<BigDecimal>) crsdnf.get(RSDNFCommandLine.NETWORK_DT));
                for (int i = 0; i < matrice.length; i++) {
                    res.getNodes().add(new RSDNFTransmitter(((Var<Double>)crsdnf.get(RSDNFCommandLine.WEIGTH)).get()));
                }
                System.out.println("Matrix size  x" + matrice.length);
                System.out.println("Matrix size  y" + matrice[0].length);
                for (int l = 0; l < matrice.length; l++) {
                    for (int c = 0; c < matrice[l].length; c++) {
                        if (matrice[l][c] == 1) {
                            ((RSDNFTransmitter)res.getNodes().get(l)).link((RSDNFTransmitter)res.getNodes().get(c));
                        }
                    }
                }
                return res;
                
            case DEFAULT_GRAPH:
                NetworkCommandLine nwcl = (NetworkCommandLine)commandLine;
                res = new SpreadingGraph((Var<BigDecimal>) nwcl.get(NetworkCommandLine.NETWORK_DT));
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
            	PFCommandLine pfcl = (PFCommandLine)commandLine;
                res = new PFSpreadingGraph((Var<BigDecimal>) pfcl.get(PFCommandLine.NETWORK_DT));
                System.out.println("poids : "+((Var<Double>)pfcl.get(PFCommandLine.WEIGTH)).get());
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
        }
        return null;
    }
    
    
}
