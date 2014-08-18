/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.network.view;

import java.util.ArrayList;
import java.util.List;

import main.java.controler.ComputableControler;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.maps.Var;
import main.java.network.generic.Node;
import main.java.network.generic.SpreadingGraph;
import main.java.space.Coord;
import main.java.space.Coord2D;
import main.resources.utils.ArrayUtils;

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

	public double[][] getArray() {
		SpreadingGraph param = (SpreadingGraph) this.getParam();
//		System.out.println(ArrayUtils.toString(param.extractAdjacentMatrix()));
		double[][] ret;
			List<Node> listN = param.getValues(); 
		
			List<Double> list = new ArrayList<Double>(listN.size()); //TODO optimize
			for (int i = 0; i < listN.size(); i++) {
				list.add((double) listN.get(i).getTotalPacketReceived());
				
			}
			
			Var<Integer> width = new Var<Integer>((int)Math.sqrt(list.size()));
//			System.out.println("Node    jj " + listN.get(0));
			Coord<SingleValueParam<Integer>> dim =  new Coord2D(width,width);
			ret = ArrayUtils.toPrimitiveDoubleArray2D(list, dim.getIndex(0).get(), dim.getIndex(1).get());
		return ret;
	
	}

	@Override
	public int getMaxIndex() {
		SpreadingGraph sg = (SpreadingGraph) getParam();
		return sg.getNodes().size();
	}

}
