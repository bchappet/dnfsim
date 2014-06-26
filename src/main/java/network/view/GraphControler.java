/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.network.view;

import main.java.controler.ComputableControler;
import main.java.maps.Parameter;
import main.java.network.generic.SpreadingGraph;

/**
 * La en théorie on devrait faire des méthodes pour accéder à des info sur le
 * paramètre. Mais j'avoue que j'ai été un peu flemmard et j'utilise parfois la
 * méthode getParam(); (qui devrait être protected)
 *
 * @author CARRARA Nicolas
 */
public class GraphControler extends ComputableControler {

    public GraphControler(Parameter spreadingGraph) {
        super(spreadingGraph);
    }

    public SpreadingGraphRepresentation getSpreadingGraphRepresentation() {
        
        SpreadingGraphRepresentation spr = new SpreadingGraphRepresentation();

        SpreadingGraph sg = (SpreadingGraph) getParam();
        
        /**
         * todo, on récupère des info de spreading graph pour creer spreading
         * graph representation
         */
        spr.bufferLoad = sg.extractCurrentPackets();
        
        return spr;
    }

}
