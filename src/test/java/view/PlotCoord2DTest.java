package test.java.view;

import main.java.space.Coord;
import main.java.view.Curve2D;
import main.java.view.PlotCoord2D;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class PlotCoord2DTest extends JFrame {

    private PlotCoord2D uut;
    private JPanel borderPanel;

    @Before
    public void setUp() throws Exception {



        uut = new PlotCoord2D("uut");

        borderPanel = uut.getBorderPane();
        this.add(borderPanel);
        this.setSize(300, 200);


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

        this.setVisible(true);
        uut.update(new Coord(0d,0d));
        uut.update(new Coord(1d,1d));
        uut.update(new Coord(1.5d,1d));

        System.out.println("repaint");

        Thread.sleep(10000);


    }
}