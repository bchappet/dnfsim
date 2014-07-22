package main.java.network.generic.packet;

/**
 * Tout les fils doivent implémenter le constructeur avec Object ... params
 * @author CARRARA Nicolas
 */
public class Packet {

    private int size;
    
    //private Object[] params;
    
    protected Packet(Object ... params){
    	//setSize(Integer.MAX_VALUE);
    	//this.params = params;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

}
