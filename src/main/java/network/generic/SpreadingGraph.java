package main.java.network.generic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import main.java.maps.Computable;
import main.java.maps.Parameter;
import main.java.maps.Var;

/**
 * Reseau permettant la propagation de paquet via des noeuds et des arêtes.
 *
 * @author CARRARA Nicolas
 * @param <N> Sous classe des noeuds
 * @param <E> Sous classe des arêtes
 */
public abstract class SpreadingGraph<N extends Node, E extends DirectedEdge> implements Parameter, Computable {

    private final List<N> nodes;

    private final List<E> edges;

    public SpreadingGraph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }
    
    /**
     * todo ?
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    /**
     * Chaque noeud procède à un envoi et se met à jour.
     */
    @Override
    public final void compute() {
        for(N n : getNodes()){
            n.sendParallele();
        }
        for(N n : getNodes()){
            n.updatePrevious();
        }
    }

    public double[][] extractAdjacentMatrix(){
        int n = nodes.size();
        double[][] matrix = new double[n][n];
        for (int l = 0; l < n; l++) {
            for (int c = 0; c < n; c++) {
                if(nodes.get(l).isNeightBorTo(nodes.get(c))){
                    matrix[l][c] = 1 ;
                }
            }
        }
        return matrix;
    }
    
    @Override
    public BigDecimal getTime() {
        //todo
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Var<BigDecimal> getDt() {
        //todo
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTime(BigDecimal currentTime) {
        //todo
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<N> getNodes() {
        return nodes;
    }

    public List<E> getEdges() {
        return edges;
    }
    
    @Override
    public String getName(){
        return "Spreading Graph";
    }

    @Override
    public Object getIndex(int index) {
        return nodes.get(index);
    }

    @Override
    public List getValues() {
        return nodes;
    }

}