package main.java.network.generic.packet;


public final class IPv4Datagramme extends Packet{
	
	private String message;
	
	public static final int MESSAGE_INDEX = 0;
	
	public IPv4Datagramme(Object ... params) {
		//super(params);
        setSize(16); // 16 bits : "La taille maximum supportée par IPv4 (car codée sur 16 bits)" est de 64 Ko max http://fr.wikipedia.org/wiki/IPv4
        setMessage((String)params[MESSAGE_INDEX]);
	}
	
	@Override
	public String toString(){
		return getMessage();
	}

	private String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
}
