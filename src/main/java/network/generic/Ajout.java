package main.java.network.generic;

import main.java.network.generic.packet.Packet;

public class Ajout<P extends Packet>{
	int indice;
	P packet;
	
	public Ajout(int indice, P packets){
		this.indice = indice;
		this.packet = packets;
	}
}
