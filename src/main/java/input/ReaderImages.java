/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.input;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.java.coordinates.Space;

/**
 * Lit les images d'un repertoire
 * Une image doit avoir un nom de type nameX où X est un entier de 0 à N (le nombre d'image totale)
 * Par exemple : image_0, image_1, image_2 ... avec name = "image_"
 * ou  9877970 9877971 9877972 9877973 9877974 9877975 ... 9877918 98779719 98779720 ... avec name = "987797"
 * ou test0 test1 test2 ... avec name = "test"
 * @author nicolas
 */
public class ReaderImages extends AbstractReader{
    private int cps;
    private int cpf;
    private String pathImages;
    private String name;
    private int typeScaling;
    private Space space;
    private ArrayList<BufferedImage> frames;
    private int numberFramesRead;
    
    /**
     * 
     * @param pathImages
     * @param name
     * @param cpf computation par frame
     * @param cps
     * @param typeScaling
     * @param main.java.space 
     */
    public ReaderImages(String pathImages, String name,int cpf, int cps, int typeScaling, Space space){
        this.cpf = cpf;
        this.cps = cps;
        this.pathImages = pathImages;
        this.name = name;
        this.typeScaling = typeScaling;
        this.space = space;
        frames = new ArrayList<BufferedImage>();
        boolean stop = false;
        int i = 0;
        // récupération et traitement des images
        while(!stop){
//            System.out.println("pathImages+\"/\"+name+i : "+pathImages+"/"+name+i);
            ImageIcon icon = new ImageIcon(pathImages+"/"+name+i);
//            System.out.println("icon : "+icon.getIconHeight());
            if(icon.getIconHeight() > 0){
                BufferedImage bi = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.createGraphics();
                // paint the Icon to the BufferedImage.
                icon.paintIcon(null, g, 0,0);
                g.dispose();
                frames.add(scaling((int)space.getResolution(), (int)space.getResolution(), typeScaling, bi));
            }else{
                stop = true;
            }
            i++;
        }
    }

    @Override
    public int get(double time, int x, int y) {
        int computation = (int) (time * cps);
        System.out.println("computation : "+computation);
        System.out.println("time : "+time);
        System.out.println("Math.floor(computation/cpf) : "+Math.floor(computation/cpf));
        return frames.get((int)Math.floor(computation/cpf)).getRGB(x, y);
    }

    @Override
    public void reset() {
        // nothing to do
    }
    
}
