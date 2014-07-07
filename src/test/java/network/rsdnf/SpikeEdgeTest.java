/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.java.network.rsdnf;

import static org.junit.Assert.assertEquals;
import main.java.network.generic.packet.Spike;
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
public class SpikeEdgeTest {
    private RSDNFTransmitter sender;
    private RSDNFTransmitter receiver;
    private SpikeEdge se;
    
    public SpikeEdgeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        sender = new RSDNFTransmitter(0.5);
        receiver = new RSDNFTransmitter(0.5);
        se = new SpikeEdge(sender, receiver);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testTransfert() {
        Spike s = new Spike();
        se.transfer(s);
        assertEquals(receiver.getBufferPacket().getLast(),s);
    }
    
}
