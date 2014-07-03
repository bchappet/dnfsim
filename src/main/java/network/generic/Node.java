package main.java.network.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.network.generic.packet.Packet;

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
public class Node<P extends Packet, E extends DirectedEdge<P, ?>> {

	private Node<P, E> temporary;

	private boolean enabled = true;

	private LinkedList<P> bufferPacket;

	private List<E> edges;

	private List<Node<P, E>> neighbors;

	private Class clazzOfEdges;

	private int maxSize = Integer.MAX_VALUE;
	
	private int lastPacketReceivedCounter = 0;

	public Node(Class clazzOfEdge,Node<P, E>... neigthbours) {
		this.clazzOfEdges = clazzOfEdge;
		bufferPacket = new LinkedList<P>();
		edges = new ArrayList<>();
		neighbors = new ArrayList<>();
		constructNeigthborhood(neigthbours);
	}

	public Node(Class clazzOfEdge) {
		this.clazzOfEdges = clazzOfEdge;
		bufferPacket = new LinkedList<P>();
		edges = new ArrayList<>();
		neighbors = new ArrayList<>();
	}

	public Node(Class clazzOfEdge,int maxSize) {
		this(clazzOfEdge);
		this.maxSize = maxSize;
	}

	public Node(Class clazzOfEdge,int maxSize, Node<P, E>... neigthbours) {
		this(clazzOfEdge,neigthbours);
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
		System.out.println("send de node");
		if (isEnabled()) {
			P p = pollPacket();
			if (p != null) {
				for (DirectedEdge<P, ?> e : getEdges()) {
					e.transfer(p);
				}
			}
		}
	}

	/**
	 * Fait un envoi de manière parallele.prepareBeforeSendParrallele doit être appellé
	 * avant de faire sendParallele.
	 */
	public /*abstract*//*final*/ void sendParallele(){
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
		return new Node<P, E>(clazzOfEdges);
	}

	/**
	 * Fait un ensemble d'opérations qui prépare le noeud a
	 * appeller sendParallele(). Peut etre override à condition de faire 
	 * un super.prepareBeforeSendParalle() avant toutes choses.
	 */
	protected void prepareBeforeSendParallele(){
		// on met à zero le compteur du nombre de packet recu
		setLastPacketReceivedCounter(0);
		// on reconstruit le noeud temporaire
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
	public  E getInstance(Node<P, E> n, Node<P, E> neightbor){
		Class/*<? extends Node>*/ clazzOfThis = getClass();
		//		if(clazzOfThis.equals(Node.class)){
		//			return (E) new DirectedEdge(n,neightbor);
		//		}else{
		try {
			//				ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			//				Type ts[] = type.getActualTypeArguments();
			/* malheuresement impossible d'utiliser le workaround du dessus pour récuperer au runtime la classe
			 * des arêtes. Ce workaround ne fonctionne que pour les sous class de Node mais ne fonctionne pas
			 * pour Node. Dans la mesure où Node est instanciable on doit trouver une autre solution. Ici
			 * on opte pour le passage en paramètre de la class des arêtes. Seul truc bancale ici, il faut
			 * que la class passé en paramètre correspondent à la class générique.
			 */
			Class<E> clazzOfE = clazzOfEdges;//(Class<E>) ts[1];
//			System.out.println(clazzOfE);
//			System.out.println(clazzOfThis);
//			Constructor c = clazzOfE.getConstructor(clazzOfThis, clazzOfThis);
//			clazzOfE.getConstructors()
//			Constructor c = clazzOfE.getDeclaredConstructor(clazzOfThis, clazzOfThis);
			Constructor c = clazzOfE.getConstructors()[0];//getDeclaredConstructor(clazzOfThis, clazzOfThis); 
			/*
			 * TODO : faire en sorte de récupérer le bon constructeur avec les bon arguments
			 * et pas le 1er de la liste.
			 * 
			 * En spécifiant les arguments du constructeurs ça fonctionne seulement si l'edge possède 
			 * une classe codé en dure. Ca pose cela dit problème si par exemple on veut recupérer
			 * le constructeur de DirectedEdge en stipulant que les arguments sont de type PFNode => run time 
			 * error NoSuchMethodeFound
			 */
			
			return (E) c.newInstance(n, neightbor);
		} catch (InstantiationException | IllegalAccessException |
				/*NoSuchMethodException | */SecurityException |
				IllegalArgumentException | InvocationTargetException ex) {
			Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
		}
		//		}
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
		setLastPacketReceivedCounter(getLastPacketReceivedCounter() + 1);
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

	public int getLastPacketReceivedCounter() {
		return lastPacketReceivedCounter;
	}

	public void setLastPacketReceivedCounter(int lastPacketReceivedCounter) {
		this.lastPacketReceivedCounter = lastPacketReceivedCounter;
	}

}
