/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.input;

import java.awt.Color;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.unitModel.UnitModel;
import main.resources.tool.ToolsMAP;

/**
 * Unit main.java.model pour la lecture vidéo. 
 * Transforme un code RGB en luminosité.
 * @author ncarrara
 */
public class UnitModelInputHSB extends UnitModel{
    private ReaderInput riv;
    private int borneInfCouleur;
    private int borneSupCouleur;
    
    
    public UnitModelInputHSB(Var dt,Space space,ConfigurationInput dataVideo, Integer typeScaling, Integer typeReader,int borneInfCouleur, int borneSupCouleur){
        super(dt, space, new Parameter[]{});
        dataVideo.cps = (int)(1/dt.get());
        riv = new ReaderInput(dataVideo, typeScaling, typeReader, space);
        this.borneInfCouleur = borneInfCouleur;
        this.borneSupCouleur = borneSupCouleur;
    }

    /**
     * 
     * @return le pourcentage de la saturation du pixel  si la teinte de ce pixel appartient à l'intervale de couleur donnée en paramètre 
     * @throws NullCoordinateException 
     */
    @Override
    public double compute() throws NullCoordinateException {
        Double[] tab = space.discreteProj(coord);
//        System.out.println("compute UMInputVideo time.get: "+time.get());
        int codeRGB = riv.get(time.get(), (int)Math.round(tab[main.java.space.X]), (int)Math.round(tab[main.java.space.Y]));
        int red = (codeRGB >> 16) & 0xFF;
        int green = (codeRGB >> 8) & 0xFF;
        int blue = codeRGB & 0xFF;
        
        float[] HSB = new float[3];
        Color.RGBtoHSB(red, green, blue, HSB);
        float hue = HSB[0]*360;
        float saturation = HSB[1];
        
        Double res = null;
        
//        System.out.println("HSB : ("+HSB[0]+","+HSB[1]+";"+HSB[2]+")");
        
        
        if(borneSupCouleur >= borneInfCouleur){
            if(hue > borneSupCouleur || hue < borneInfCouleur){
                res = 0.0;
            }else{
                res = (double)saturation;
            }
        }else{
            if((hue>=0 && hue <= borneSupCouleur) || (hue<=360 && hue>= borneInfCouleur)){
                res = (double)saturation;
            }else{
                res = 0.0;
            }
        }
        
        
        
        return res;
    }
    
    @Override
    public void reset(){
        super.reset();
        riv.reset();
    }
    
    
    
}
