/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.network.rsdnf;

import main.java.network.rsdnf.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Arrays;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.SpreadingGraphFactory;
import static main.java.network.rsdnf.RSDNFCommandLine.WEIGTH;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CARRARA Nicolas
 */
public class RSDNFModelTest {

    private RSDNFModel rsdnf;

    public RSDNFModelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws CommandLineFormatException, FileNotFoundException, MalformedURLException {
        rsdnf = new RSDNFModel("testRSDNF");
        CommandLine cl = new RSDNFCommandLine() {
            @Override
            public String defaultScript() {
                return super.defaultScript() + WEIGTH + "=0.0;";
            }
        };//rsdnf.constructCommandLine();
        cl.setContext("");
        rsdnf.initialize(cl);
    }

    @After
    public void tearDown() {
    }

    /**
     * @throws CommandLineFormatException
     */
    @Test
    public void paralleleTest() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 1},
            {0, 0, 0},
            {0, 0, 0}};
        SpreadingGraph spreadingGraph = SpreadingGraphFactory.getInstance().constructGraph(
                transitionMatrix,
                SpreadingGraphFactory.TypeGraph.RSDNF,
                rsdnf.getCommandLine());
        rsdnf.setSpreadingGraph(spreadingGraph);
        ((RSDNFTransmitter) spreadingGraph.getIndex(0)).addToFIFO(new Spike());
        rsdnf.computeAtomique();
        int[] packetsTheory = {0, 0, 1};
        assertArrayEquals(spreadingGraph.extractCurrentPackets(), packetsTheory);
    }

    private void coreTest(boolean sout, double[][] transitionMatrix, int[] addToFIFO, int[] packetTheory, int nbComputation) throws CommandLineFormatException {
        SpreadingGraph spreadingGraph = SpreadingGraphFactory.getInstance().constructGraph(
                transitionMatrix,
                SpreadingGraphFactory.TypeGraph.RSDNF,
                rsdnf.getCommandLine());
        rsdnf.setSpreadingGraph(spreadingGraph);
        for (int i = 0; i < addToFIFO.length; i++) {
            for (int j = 0; j < addToFIFO[i]; j++) {
                ((RSDNFTransmitter) spreadingGraph.getIndex(i)).addToFIFO(new Spike());
            }
        }
        if (sout) {
            System.out.println("depart  : " + Arrays.toString(spreadingGraph.extractCurrentPackets()));
        }
        for (int i = 0; i < nbComputation; i++) {
            rsdnf.computeAtomique();
            if (sout) {
                System.out.println("etape " + i + " : " + Arrays.toString(spreadingGraph.extractCurrentPackets()));
            }
        }
//        rsdnf.compute(nbComputation);
        assertArrayEquals(spreadingGraph.extractCurrentPackets(), packetTheory);
    }

    @Test
    public void paralleleTest1() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 1},
            {0, 0, 0},
            {0, 0, 0}};
        int[] packetsTheory = {0, 0, 1};
        int nbComputation = 1;
        int[] addToFIFO = {1, 0, 0};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest2() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 1},
            {1, 0, 0},
            {0, 0, 0}};
        int nbComputation = 1;
        int[] addToFIFO = {1, 1, 1};
        int[] packetsTheory = {1, 0, 1};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest3() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 1},
            {1, 0, 1},
            {0, 0, 0}};
        int nbComputation = 1;
        int[] addToFIFO = {1, 1, 1};
        int[] packetsTheory = {1, 0, 2};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest4() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}};
        int nbComputation = 1;
        int[] addToFIFO = {1, 1, 1};
        int[] packetsTheory = {3, 3, 3};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest5() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}};
        int nbComputation = 1;
        int[] addToFIFO = {1, 1, 1};
        int[] packetsTheory = {0, 0, 0};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest6() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 1},
            {0, 0, 0},
            {1, 1, 0}};
        int nbComputation = 2;
        int[] addToFIFO = {4, 1, 1};
        int[] packetsTheory = {4, 1, 1};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest7() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 1},
            {0, 0, 0},
            {1, 0, 0}};
        int nbComputation = 100;
        int[] addToFIFO = {4, 1, 1};
        int[] packetsTheory = {4, 0, 1};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest8() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 0},
            {0, 0, 0},
            {1, 1, 0}};
        int nbComputation = 50;
        int[] addToFIFO = {0, 0, 100};
        int[] packetsTheory = {1, 1, 50};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest9() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 0},
            {1, 0, 0},
            {1, 1, 0}};
        int nbComputation = 1;
        int[] addToFIFO = {0, 0, 100};
        int[] packetsTheory = {1, 1, 99};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest10() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 0},
            {1, 0, 0},
            {1, 1, 0}};
        int nbComputation = 2;
        int[] addToFIFO = {0, 0, 100};
        int[] packetsTheory = {2, 1, 98};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest11() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 0},
            {1, 0, 0},
            {1, 1, 0}};
        int nbComputation = 100;
        int[] addToFIFO = {0, 0, 100};
        int[] packetsTheory = {100, 1, 0};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest12() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 0},
            {1, 0, 0},
            {1, 1, 0}};
        int nbComputation = 101;
        int[] addToFIFO = {0, 0, 100};
        int[] packetsTheory = {100, 0, 0};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

    @Test
    public void paralleleTest13() throws CommandLineFormatException {
        double[][] transitionMatrix = {
            {0, 0, 0},
            {1, 0, 0},
            {1, 1, 0}};
        int nbComputation = 201;
        int[] addToFIFO = {0, 0, 100};
        int[] packetsTheory = {0, 0, 0};
        coreTest(false, transitionMatrix, addToFIFO, packetsTheory, nbComputation);
    }

}
