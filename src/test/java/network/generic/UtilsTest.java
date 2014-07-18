/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.network.generic;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import main.java.network.generic.Utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author CARRARA Nicolas
 */
public class UtilsTest {

    public UtilsTest() { 
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseCSVFile method, of class Utils.
     */
    @Test
    public void testParseCSVFile1() {
        System.out.println("parseCSVFile1");
        File file = new File("testData/1.csv");
        double[][] expResult
                = {{0, 1, 0},
                {1, 1, 1},
                {0, 1, 1}};

        double[][] result = Utils.parseCSVFile(file);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testParseCSVFile2() {
        System.out.println("parseCSVFile2");
        File file = new File("testData/2.csv");
        double[][] expResult
                = {{1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0},
                {0, 0, 2, 3, 4},
                {1, 1, 1, 0, 0}};

        double[][] result = Utils.parseCSVFile(file);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testParseCSVFile3() {
        System.out.println("parseCSVFile3");
        File file = new File("testData/3.csv");
        double[][] expResult
                = {{0.0, 0.5, 0.2},
                {0.1, 0.23, 0.36},
                {0.0, 0.0, 0.0},
                {0.1, 0.2, 0.9}};

        double[][] result = Utils.parseCSVFile(file);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testParseCSVFile4() {
        System.out.println("parseCSVFile4");
        File file = new File("testData/4.csv");
        double[][] expResult
                = {};

        double[][] result = Utils.parseCSVFile(file);
        assertArrayEquals(expResult, result);
    }



    /**
     * Test of writeCSVFile method, of class Utils.
     */
    @Test
    public void testWriteCSVFile1() {
        System.out.println("writeCSVFile1");
        File file = new File("testData/writeCSVFile1.csv");
        double[][] matrice = {{0,0,0,1},{0,0,2,0}};
        Utils.writeCSVFile(file, matrice);
        assertArrayEquals(matrice,Utils.parseCSVFile(file));
    }
    
    @Test
    public void testWriteCSVFile2() {
        System.out.println("writeCSVFile2");
        File file = new File("testData/writeCSVFile2.csv");
        int w = (int) (Math.random() * 100);
        int h = (int) (Math.random() * 100);
        double[][] matrice = new double[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                matrice[i][j] = Math.random();
            }
        }
        Utils.writeCSVFile(file, matrice);
        assertArrayEquals(matrice,Utils.parseCSVFile(file));
    }
    
    

}
