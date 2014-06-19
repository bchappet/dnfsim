/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.network.rsdnf;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.SpreadingGraphFactory;
import main.java.network.generic.Utils;
import main.java.network.rsdnf.RSDNFModel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author CARRARA Nicolas
 */
public class RSDNFSpreadingGraphTest {

    RSDNFModel rsdnf;

    public RSDNFSpreadingGraphTest() {
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
        CommandLine cl = rsdnf.constructCommandLine();
        cl.setContext("");
        rsdnf.initialize(cl);
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void paralleleTest() throws CommandLineFormatException{
        double[][] transitionMatrix = {
            {0,1,1},
            {1,0,0},
            {1,1,0}};
            
        SpreadingGraph spreadingGraph = SpreadingGraphFactory.getInstance().constructGraph(transitionMatrix, SpreadingGraphFactory.TypeGraph.RSDNF, rsdnf.getCommandLine());
        
        rsdnf.setSpreadingGraph(spreadingGraph);
        
    
    }
    
    @Test
    public void finalTest() throws CommandLineFormatException, FileNotFoundException {
        System.out.println("finalTest");
        File result = rsdnf.writeNetworkFile();
        double[][] matrixA = Utils.parseCSVFile(result);
//        CommandLine c = new RSDNFCommandLine();
//        c.reinitialize();
//        c.parseCommand(c.getScript());
        SpreadingGraph spreadingGraph = SpreadingGraphFactory.getInstance().constructGraph(result, SpreadingGraphFactory.TypeGraph.RSDNF, rsdnf.getCommandLine());
        double[][] matrixB = spreadingGraph.extractAdjacentMatrix();
//        System.out.println(Arrays.deepToString(matrixB));
        assertArrayEquals(matrixA, matrixB);
    }
}
