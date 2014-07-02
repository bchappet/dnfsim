/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.network.view;

import java.awt.LayoutManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.network.rsdnf.RSDNFCommandLine;
import static main.java.network.rsdnf.RSDNFCommandLine.WEIGTH;
import main.java.network.rsdnf.RSDNFModel;
import main.java.view.ViewConfiguration;
import main.java.view.ViewFactory;
import main.java.view.ViewPanel;

/**
 * La tu fait ce que tu veux
 *
 * @author CARRARA Nicolas
 */
public class GraphView extends ViewPanel {
    
    public static void main(String[] args){
        try {
            JFrame jf = new JFrame("mainGraphView");
            RSDNFModel rsdnf = new RSDNFModel("testRSDNF");
            CommandLine cl = rsdnf.constructCommandLine();
            cl.setContext("");
            rsdnf.initialize(cl);
//            System.out.println(""+rsdnf.getParameters());
//            ModelControler mc = new ModelControler(rsdnf);
//            cl.setCurentModelControler(mc);
//            ComputationControler cc = new ComputationControler(mc.getTree());
//            
//            ViewFactory vf = new ViewFactory(new ViewConfiguration("src/main/java/network/view/GraphViewPanel.gui"), mc.getTree());
//            
//            SpreadingGraphRepresentation sgr = new SpreadingGraphRepresentation();
//            
//            sgr.bufferLoad = new int[]{0, 1, 6, 7};
//            
//            GraphView gv = new GraphView("coucou", vf, sgr);
//            
//            jf.setVisible(true);
//            jf.add(gv);
//            jf.setSize(300, 300);
//            
        } catch (Exception ex) {
            Logger.getLogger(GraphView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        ModelESN model = new ModelESN("test_esn");
//        ESNCommandLine cl = (ESNCommandLine) model.constructCommandLine();
//        cl.setContext("");
//        model.initialize(cl);
//        ModelControler mc = new ModelControler(model);
//        cl.setCurentModelControler(mc);
//        cc = new ComputationControler(mc.getTree());
//
//        ViewFactory vf = new ViewFactory(new ViewConfiguration("src/test/scripts/gui/ModelPanelTest.gui"), mc.getTree());
//        Dimension dim = new Dimension(800, 600);
//        uut = new ModelView("uut", vf, dim);
//        this.setVisible(true);
//
//        this.add(uut);
//        this.setSize(dim);
    }
    

    public GraphView(
            String name, 
            ViewFactory vf, 
            SpreadingGraphRepresentation spreadingGraphRepresentation) {
        super(name, vf);
        // todo
    }

    public GraphView(
            String name, 
            ViewFactory vf, 
            LayoutManager layout, 
            SpreadingGraphRepresentation spreadingGraphRepresentation) {
        super(name, vf, layout);
        // todo
    }
    
    public void update(SpreadingGraphRepresentation spreadingGraphRepresentation){
        //todo
    }

}
