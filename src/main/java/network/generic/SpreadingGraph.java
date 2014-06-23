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
public abstract class SpreadingGraph<N extends Node, E extends DirectedEdge> implements Parameter<N>, Computable {

    private final List<N> nodes;

    private final List<E> edges;

    public SpreadingGraph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nodes + "";
    }

    /**
     * 
     * @return le nombre de paquet restant sur le reseau
     */
    public final int getLoadRemaining(){
        int loadRemaining = 0;
        for(N n: getNodes()){
            loadRemaining += n.getLoad();
        }
        return loadRemaining;
    }
    
    /**
     *
     * @return the node with the maximum load, null if nodes is empty
     */
    public final N getMostLoadedNode() {
        N res = null;
        if (!nodes.isEmpty()) {
            res = nodes.get(0);
            for (N n : getNodes()) {
                if (n.getBufferPacket().size() > res.getBufferPacket().size()) {
                    res = n;
                }
            }
        }
        return res;
    }

    /**
     *
     * @return an array with the number of packet in each node
     */
    public int[] extractCurrentPackets() {
        int[] packets = new int[nodes.size()];
        for (int i = 0; i < packets.length; i++) {
            packets[i] = nodes.get(i).getBufferPacket().size();
        }

        return packets;
    }

    /**
     * 
     * @return the transition matrix of this spreading graph
     */
    public double[][] extractAdjacentMatrix() {
        int n = nodes.size();
        double[][] matrix = new double[n][n];
        for (int l = 0; l < n; l++) {
            for (int c = 0; c < n; c++) {
                if (nodes.get(l).isNeightBorTo(nodes.get(c))) {
                    matrix[l][c] = 1;
                }
            }
        }
        return matrix;
    }

    //-------------------------computable---------------------------------------
    
    /**
     * todo ?
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Chaque noeud procède à un envoi et se met à jour.
     */
    @Override
    public final void compute() {
        for (N n : getNodes()) {
            n.prepareBeforeSendParallele();
        }
        for (N n : getNodes()) {
            n.sendParallele();
        }
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

    //-------------------------parameter----------------------------------------
    @Override
    public String getName() {
        return "Spreading Graph";
    }

    @Override
    public N getIndex(int index) {
        return nodes.get(index);
    }

    @Override
    public List<N> getValues() {
        return nodes;
    }

    //-------------------------getter/setter------------------------------------
    public List<N> getNodes() {
        return nodes;
    }

    public List<E> getEdges() {
        return edges;
    }

}
