package main.java.network.generic.packet;


public class IPv4Datagramme extends Packet{
	
	String message;
	
	public IPv4Datagramme(String message) {
		super();
        setSize(16); // 16 bits : "La taille maximum supportée par IPv4 (car codée sur 16 bits)" est de 64 Ko max http://fr.wikipedia.org/wiki/IPv4
        this.message = message;
	}
	
	@Override
	public String toString(){
		return message;
	}
}
