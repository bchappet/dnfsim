/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author nicolas
 */
public abstract class AbstractReader implements InterfaceReader{
//    int i = 0;

    @Override
    public abstract int get(double time, int x, int y);

    @Override
    public abstract void reset();
    
    /**
     * réduit l'échelle d'une image
     * @param w
     * @param h
     * @param type
     * @param imageToScale
     * @return 
     */
    protected BufferedImage scaling(int w,int h, int type, BufferedImage imageToScale){
        BufferedImage before = imageToScale;
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        double sx = ((double)w)/((double)before.getWidth());
        double sy = ((double)h)/((double)before.getHeight());
        at.scale(sx, sy);
        AffineTransformOp scaleOp = new AffineTransformOp(at, type);
        after = scaleOp.filter(before, after);
//        try {
//                ImageIO.write(after, "png", new File("tmp/framesTraiteesScaled_"+i));
//            } catch (IOException ex) {
//                Logger.getLogger(AbstractReader.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        i++;
        return after;
    } 
}
