/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.network.view;

import java.awt.LayoutManager;
import main.java.view.ViewFactory;
import main.java.view.ViewPanel;

/**
 * La tu fait ce que tu veux
 *
 * @author CARRARA Nicolas
 */
public class GraphView extends ViewPanel {

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
