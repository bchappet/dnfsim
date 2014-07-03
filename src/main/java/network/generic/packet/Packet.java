package main.java.network.generic.packet;

/**
 * 
 * @author CARRARA Nicolas
 */
public abstract class Packet {

    private int size;

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
