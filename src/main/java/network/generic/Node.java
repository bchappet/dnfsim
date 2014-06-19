package main.java.network.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Un noeud du reseau. Envoie et reçoit des paquets.
 *
 * Ce noeud et sa version antérieure sont liés avec un ensemble de noeud. Pour
 * faire un calcul parralèle, il faut appeler d'abord sendParallele sur tout les
 * noeuds d'un graphe, puis mettre à jour avec update previous.
 *
 * @author CARRARA Nicolas
 * @param <P>
 * @param <E>
 */
public abstract class Node<P extends Packet, E extends DirectedEdge> {

    private Node<P, E> previous;

    private boolean enabled = true;

    private LinkedList<P> bufferPacket;

    private List<E> edges;

    private List<Node<P, E>> neighbors;

    public Node(boolean constructPrevious, Node<P, E>... neigthbours) {
        bufferPacket = new LinkedList();
        edges = new ArrayList<>();
        neighbors = new ArrayList<>();
        constructNeigthborhood(neigthbours);
        if (constructPrevious) {
            previous = constructPrevious(); //(Node<P, E>) clone();//
        }
    }

    public Node(boolean constructPrevious) {
        bufferPacket = new LinkedList();
        edges = new ArrayList<>();
        neighbors = new ArrayList<>();
        if (constructPrevious) {
            previous = constructPrevious();
        }
    }

    /**
     * Transfert un ou plusieurs paquets à un ou plusieurs voisins
     */
    protected abstract void send();

    /**
     * Le previous fait un send sur les voisins
     */
    public final void sendParallele() {
        getPrevious().send();
    }

    /**
     * clone this to previous
     */
    public final void updatePrevious() {
        setPrevious(constructPrevious());
    }

    /**
     *
     * @return un clone de this.
     */
    protected abstract Node<P, E> constructPrevious();

    /**
     * crée un object de type E au runtime
     *
     * @param n
     * @param neightbor
     * @return
     */
    public final E getInstance(Node<P, E> n, Node<P, E> neightbor) {
        try {
            Class<E> clazzOfE = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[1];
            Class clazzOfThis = getClass();
            Constructor c = clazzOfE.getConstructor(clazzOfThis, clazzOfThis);
            return (E) c.newInstance(n, neightbor);
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | SecurityException |
                IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * lie ce noeud s à neightbor
     *
     * @param neightbor
     */
    public final void link(Node<P, E> neightbor) {
        getNeighbors().add(neightbor);
        E e = getInstance(this, neightbor);
        getEdges().add(e);
//        if (getPrevious() != null) {
//            E ePrevious = getInstance(getPrevious(), neightbor);
//            e.setPrevious(ePrevious);
//            getPrevious().getEdges().add(ePrevious);
//        }
    }

    /**
     * Lie ce noeud à ses voisins avec des arêtes qui partent de ce noeud vers
     * ses voisins (dirigé)
     *
     * @param neightbors
     */
    public final void constructNeigthborhood(Node<P, E>... neightbors) {
        for (Node<P, E> n : neightbors) {
            link(n);
        }
//        if (getPrevious() != null) {
//            for (Node<P, E> n : neightbors) {
//                getPrevious().link(n.getPrevious());
//            }
//        }
    }

    public final boolean isNeightBorTo(Node<P, E> node) {
        return getNeighbors().contains(node);
    }

    /**
     * reçoit un paquet. Le rajoute à son buffer. Methode qu'on peut override si
     * d'autres traitements sont necessaire.
     *
     * @param packet
     */
    public void receive(P packet) {
        addToFIFO(packet);
    }

    /**
     * recupère le premier paquet dans le buffer. Retire ce paquet du buffer.
     *
     * @return
     */
    protected final P pollPacket() {
        return (P) getBufferPacket().poll();
    }

    /**
     * Rajoute un paquet à la file d'attente de traitement de ce noeud.
     *
     * @param packet
     */
    public final void addToFIFO(P packet) {
        getBufferPacket().add(packet);
    }

    
    /** *************************************** getter/setter *****************/
    
    public boolean isEnabled() {
        return enabled;
    }

    public LinkedList getBufferPacket() {
        return bufferPacket;
    }

    public List<E> getEdges() {
        return edges;
    }

    /**
     * @return the previous
     */
    public Node<P, E> getPrevious() {
        return previous;
    }

    /**
     * @param previous the previous to set
     */
    public void setPrevious(Node<P, E> previous) {
        this.previous = previous;
    }

    /**
     * @return the neighbors
     */
    public List<Node<P, E>> getNeighbors() {
        return neighbors;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param bufferPacket the bufferPacket to set
     */
    public void setBufferPacket(LinkedList<P> bufferPacket) {
        this.bufferPacket = bufferPacket;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(List<E> edges) {
        this.edges = edges;
    }

    /**
     * @param neighbors the neighbors to set
     */
    public void setNeighbors(List<Node<P, E>> neighbors) {
        this.neighbors = neighbors;
    }

}
