package main.java.network.generic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import main.java.maps.Computable;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.network.generic.packet.Packet;

/**
 * Reseau permettant la propagation de paquet via des noeuds et des arêtes.
 *
 * @author CARRARA Nicolas
 * @param <N> Sous classe des noeuds
 * @param <E> Sous classe des arêtes
 */
public class SpreadingGraph<N extends Node, E extends DirectedEdge> implements Parameter<N>, Computable {

    private final List<N> nodes;

    private final List<E> edges;

    private int computations = 0;

    /**
     * Update time for this map*
     */
    private final Var<BigDecimal> dt;
    /**
     * Current time for this map*
     */
    private BigDecimal time;
    
    public SpreadingGraph(Var<BigDecimal> dt) {
        this.dt = dt;
        this.time = new BigDecimal("0");
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nodes + "";
    }

    /**
     * rajoute un packet packet au noeud à la position indexNode de ce graphe
     * @param indexNode la position du noeud dans le graphe.
     * @param packet le packet à rajouter en file d'attente du noeud.
     */
    public void addToFIFO(int indexNode, Packet packet){
    	nodes.get(indexNode).addToFIFO(packet);
    }
    
    
    /**
     *
     * @return le nombre de paquet restant sur le reseau
     */
    public final int getLoadRemaining() {
        int loadRemaining = 0;
        for (N n : getNodes()) {
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
    public final int[] extractCurrentPackets() {
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
    public final double[][] extractAdjacentMatrix() {
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
    public  void compute() {
    	System.out.println("computing "+this+" ...");
        for (N n : getNodes()) {
            n.prepareBeforeSendParallele();
        }
        for (N n : getNodes()) {
            n.sendParallele();
        }
    }

    @Override
    public BigDecimal getTime() {
        return this.time;
    }

    @Override
    public Var<BigDecimal> getDt() {
        return this.dt;
    }

    @Override
    public void setTime(BigDecimal currentTime) {
        this.time = currentTime;
    }

    //-------------------------parameter----------------------------------------
    @Override
    public String getName() {
        return " SpreadingGraph";
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

    /**
     * @return the computations
     */
    public int getComputations() {
        return computations;
    }

    /**
     * @param computations the computations to set
     */
    public void setComputations(int computations) {
        this.computations = computations;
    }

}
