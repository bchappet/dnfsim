/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.input;

import java.awt.image.AffineTransformOp;

import main.java.input.ReaderStepVideo;

/**
 *
 * @author ncarrara
 */
public class TestReaderInputVideo {
    public static void main(String[] args){
       // ReaderStepInputVideo riv = new ReaderStepInputVideo("AFightD.mpg", 35,  AffineTransformOp.TYPE_BILINEAR);
        //System.out.println("riv.get(0, 30, 32) : "+riv.get(0, 30, 32));
        
        /*List<BufferedImage> frames = riv.getFrames();
        long microSec = riv.getVideoDurationMicroSeconds();
        long sec = microSec/1000000;
        System.out.println("riv.getVideoDurationMicroSeconds() : "+riv.getVideoDurationMicroSeconds());
        System.out.println("seconde : "+sec);
        System.out.println("riv.getNumberFrames() "+riv.getNumberFrames());
        System.out.println("fps : "+riv.getFps());
        System.out.println("riv.getFramesTraitees().size() : "+riv.getFramesTraitees().size());*/
        /*for(int i = 0; i < riv.getFramesTraitees().size();i++){
            int codeRGB = riv.get(i, 0, 0);
            int red = (codeRGB >> 16) & 0xFF;
            int green = (codeRGB >> 8) & 0xFF;
            int blue = codeRGB & 0xFF;
        
            System.out.println(""+ ((0.2126*red) + (0.7152*green) + (0.0722*blue)));
        }*/
        /*for(int i=0;i<10;i++){
            try {
                ImageIO.write(riv.getFramesTraitees().get(i), "png", new File("scaling/image"+i));
            } catch (IOException ex) {
                Logger.getLogger(TestReaderInputVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    
    }
}
