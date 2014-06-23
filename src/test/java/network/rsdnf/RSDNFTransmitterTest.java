/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.network.rsdnf;

import main.java.network.rsdnf.*;
import test.java.network.rsdnf.*;
import main.java.network.generic.Node;
import main.java.network.rsdnf.RSDNFTransmitter;
import main.java.network.rsdnf.Spike;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author CARRARA Nicolas
 */
public class RSDNFTransmitterTest {

    private RSDNFTransmitter n1;
    private RSDNFTransmitter n2;
    private RSDNFTransmitter n3;
    private RSDNFTransmitter n4;
    private RSDNFTransmitter n5;
    private RSDNFTransmitter n6;
    private RSDNFTransmitter t;

    public RSDNFTransmitterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        n1 = new RSDNFTransmitter(1);
        n2 = new RSDNFTransmitter(1);
        n3 = new RSDNFTransmitter(1);
        n4 = new RSDNFTransmitter(1);
        n5 = new RSDNFTransmitter(1);
        n6 = new RSDNFTransmitter(1);
        t = new RSDNFTransmitter(0, n1, n2, n3, n4, n5, n6);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of send method, of class RSDNFTransmitter.
     */
    @Test
    public void testSend() {
        System.out.println("send");
        Spike s = new Spike();
        t.addToFIFO(s);
        t.send();
        for (Node n : t.getNeighbors()) {
            assertEquals(s, n.getBufferPacket().getLast());
        }
    }

    @Test
    public void testSend2() {
        System.out.println("send2");
        double errorAllowed = 0.2;
        Spike s;
        for (int j = 0; j < 100; j++) {
            double p = Math.random();
            int n = (int) (Math.random() * 100000);
            t = new RSDNFTransmitter(1-p, n1, n2, n3, n4, n5, n6);
            for (int i = 0; i < n; i++) {
                s = new Spike();
                t.addToFIFO(s);
                t.send();
            }
            int spikeSent = 0;
            for (Node nt : t.getNeighbors()) {
                spikeSent += nt.getBufferPacket().size();
                nt.getBufferPacket().clear();
            }
            int theoricalSpikeSent = (int) (n * p * t.getNeighbors().size());
            assert (Math.abs(1.0-spikeSent/(double)theoricalSpikeSent) < errorAllowed) : "p : " + p + "\nn : " + n + "\ntheory : " + theoricalSpikeSent + "\nexp : " + spikeSent + "";
        }
    }
    


    /**
     * Test of link method, of class RSDNFTransmitter.
     */
    @Test
    public void testLink() {
        System.out.println("link");
        RSDNFTransmitter neightbor = new RSDNFTransmitter(0.5);
        RSDNFTransmitter instance = new RSDNFTransmitter(0.5);
        instance.link(neightbor);
        assertTrue(instance.isNeightBorTo(neightbor));
    }

    /**
     * Test of link method, of class RSDNFTransmitter.
     */
    @Test
    public void testLink2() {
        System.out.println("link2");
        RSDNFTransmitter neightbor = new RSDNFTransmitter(0);
        RSDNFTransmitter instance = new RSDNFTransmitter(0);
        instance.link(neightbor);
        Spike s = new Spike();
        instance.addToFIFO(s);
        instance.send();
        assertEquals(s, neightbor.getBufferPacket().getLast());
    }
}
