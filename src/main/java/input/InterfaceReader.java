/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.input;

/**
 * Interface pour les classes qui lisent des vidéos en entrée
 * @author ncarrara
 */
interface InterfaceReader {
    /**
     * 
     * @param time
     * @param x
     * @param y
     * @return la valeur du pixel (x,y) à un moment time
     */
    public int get(double time, int x, int y);
    
    /**
     * reset le reader
     */
    public void reset();
}
