/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.input;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ncarrara
 */
public class TestAlgoInsertionSuppresionFrames {
    
    public static void main(String[] args){
        int outputFps = 7;
        int fps = 20;
        List<Integer>frames= new ArrayList<Integer>();
        for(int k =0 ; k< 200 ; k++)
            frames.add(k);
        System.out.println("frames : "+frames);
      
        List<Integer>framesTraitees = new ArrayList<Integer>();
        //pour chaque seconde
        //faire une projection de l'espace le plus petit vers le plus grand
        int x; // coordonée de la frame dans la nouvelle liste
        int y; // projection de x sur la liste d'entrée
        /*if( outputFps > fps ){*/
            for(x = 0 ; x < outputFps*10 ; x++){
                y = (int) Math.round(((double)x/(double)outputFps)*(double)fps);
                framesTraitees.add(frames.get(y));
            }
        /*}*/
        System.out.println("framesTraitess : "+framesTraitees);
        System.out.println("framesTraitees.size() : "+framesTraitees.size());
    }
}
