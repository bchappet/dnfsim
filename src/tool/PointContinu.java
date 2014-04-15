/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

/**
 * Point dans un répère continu.
 * @author ncarrara
 */
public class PointContinu {
    public double x;
    public double y;
    
    public PointContinu(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public PointContinu(PointContinu p){
        this.x = p.x;
        this.y = p.y;
    }
    
    public PointContinu(){
        
    }
    
    public PointContinu(java.awt.Point p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    public String toString(){
        return "("+x+","+y+")";
    }
    
}
