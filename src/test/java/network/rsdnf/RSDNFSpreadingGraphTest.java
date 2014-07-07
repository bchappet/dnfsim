/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.network.rsdnf;

import static org.junit.Assert.assertArrayEquals;
import main.java.network.rsdnf.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.SpreadingGraphFactory;
import main.java.network.generic.TypeGraph;
import main.java.network.generic.Utils;
import main.java.network.rsdnf.RSDNFCommandLine;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author CARRARA Nicolas
 */
public class RSDNFSpreadingGraphTest {

    RSDNFNetworkModel rsdnf;

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
        rsdnf = new RSDNFNetworkModel("testRSDNF");
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


    @Test
    public void finalTest() throws CommandLineFormatException, FileNotFoundException {
        System.out.println("finalTest");
        File result = rsdnf.writeRSDNFNetworkFile();
        double[][] matrixA = Utils.parseCSVFile(result);
        SpreadingGraph spreadingGraph = SpreadingGraphFactory.getInstance().constructGraph(result, TypeGraph.RSDNF, rsdnf.getCommandLine());
        double[][] matrixB = spreadingGraph.extractAdjacentMatrix();
        assertArrayEquals(matrixA, matrixB);
    }
}
