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
 * noeuds d'un graphe, puis mettre à jour avec update temporary.
 *
 * @author CARRARA Nicolas
 * @param <P>
 * @param <E>
 */
public abstract class Node<P extends Packet, E extends DirectedEdge> {

    private Node<P, E> temporary;

    private boolean enabled = true;

    private LinkedList<P> bufferPacket;

    private List<E> edges;

    private List<Node<P, E>> neighbors;

    private int maxSize = Integer.MAX_VALUE;

    public Node(Node<P, E>... neigthbours) {
        bufferPacket = new LinkedList();
        edges = new ArrayList<>();
        neighbors = new ArrayList<>();
        constructNeigthborhood(neigthbours);
    }

    public Node() {
        bufferPacket = new LinkedList();
        edges = new ArrayList<>();
        neighbors = new ArrayList<>();
    }

    public Node(int maxSize) {
        this();
        this.maxSize = maxSize;
    }

    public Node(int maxSize, Node<P, E>... neigthbours) {
        this(neigthbours);
        this.maxSize = maxSize;
    }

    @Override
    public String toString() {
        return bufferPacket.size() + "";
    }

    /**
     * Doit passer un ou plusieurs paquets à un ou plusieurs voisins. Doit se
     * decharger d'un ou de plusieur paquet quoi qu'il arrive
     */
    public abstract void send();

    /**
     * Doit faire un envoi de manière parallele.
     */
    public abstract void sendParallele();

    /**
     * Doit faire un ensemble d'opérations qui doivent préparer le noeud a
     * appeller sendParallele().
     */
    protected abstract void prepareBeforeSendParallele();

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
     *
     * @return the load amount of the buffer
     */
    public final int getLoad() {
        return getBufferPacket().size();
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
    }

    /**
     * 
     * @param node
     * @return vrai si node est voisin de/link à this
     */
    public final boolean isNeightBorTo(Node<P, E> node) {
        return getNeighbors().contains(node);
    }

    /**
     * reçoit un paquet(peut etre appeler pendant une computation du spreading
     * graph associé). Le rajoute à son buffer. Methode qu'on peut override si
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
     * Rajoute un paquet à la file d'attente de traitement de ce noeud, si la 
     * file n'est pas pleine, jète le paquet sinon.
     *
     * @param packet
     * @return vrai si le packet a bien été ajouté
     */
    public boolean addToFIFO(P packet) {
        boolean added;
        if (added = getLoad() < getMaxSize()) {
            getBufferPacket().add(packet);
        }
        return added;
    }

    /**
     * *************************************** getter/setter ****************
     */
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
     * @return the temporary
     */
    public Node<P, E> getTemporary() {
        return temporary;
    }

    /**
     * @param temporary the temporary to set
     */
    public void setTemporary(Node<P, E> temporary) {
        this.temporary = temporary;
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

    /**
     * @return the maxSize
     */
    public int getMaxSize() {
        return maxSize;
    }

}
