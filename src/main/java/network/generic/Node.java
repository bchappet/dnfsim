package main.java.network.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.network.rsdnf.RSDNFTransmitter;
import main.java.network.rsdnf.Spike;

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
public class Node<P extends Packet, E extends DirectedEdge> {

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
	 * decharger d'un ou de plusieur paquet quoi qu'il arrive.
	 * 
	 * Comportement par defaut :  envoie le premier paquet de sa liste FIFO à tous
	 * ses voisins. Se decharge de ce paquet
	 */
	public /* abstract */void send(){
		if (isEnabled()) {
			P p = pollPacket();
			if (p != null) {
				for (DirectedEdge e : getEdges()) {
					e.transfer(p);
				}
			}
		}
	}

	/**
	 * Fait un envoi de manière parallele.prepareBeforeSendParrallele doit être appellé
	 * avant de faire sendParallele.
	 */
	public /*abstract*/final void sendParallele(){
		getTemporary().send();
	}

	/**
	 * Creer une copy de ce noeud. Si une classe implemente Node, si le
	 * comportement de ce noeud change lors de send, alors constructTemporary
	 * doit etre override( cf RSDNF). en effet constructTemporary n'existe
	 * que pour simuler un calcul parrallèle en copiant le comportement
	 * du noeud courant.
	 * @return
	 */
	protected Node<P,E> constructTemporary(){
		return new Node<P, E>();
	}
	
	/**
	 * Fait un ensemble d'opérations qui prépare le noeud a
	 * appeller sendParallele(). Peut etre override à condition de faire 
	 * un super.prepareBeforeSendParalle() avant toutes choses.
	 */
	protected void prepareBeforeSendParallele(){
//		RSDNFTransmitter tmp = new RSDNFTransmitter(this.weight);
		Node<P, E> tmp = constructTemporary();
		tmp.setBufferPacket((LinkedList<P>) getBufferPacket().clone());
		tmp.setEdges(this.getEdges());
		tmp.setNeighbors(this.getNeighbors());
		tmp.setEnabled(this.isEnabled());
		setTemporary(tmp);
		/**
		 * on retire un packet de la liste (celui envoyé) car c'est temporary
		 * qui va send et pas this.
		 */
		pollPacket();
	}

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
