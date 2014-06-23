/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.java.network.generic;

import main.java.network.generic.*;
import test.java.network.generic.*;
import main.java.network.rsdnf.RSDNFTransmitter;
import main.java.network.rsdnf.SpikeEdge;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author CARRARA Nicolas
 */
public class NodeTest {
    
    public NodeTest() {
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

  
    @Test
    public void generic(){
        RSDNFTransmitter t = new RSDNFTransmitter(5);
        SpikeEdge instance = t.getInstance(t,t);
    }
    
}
