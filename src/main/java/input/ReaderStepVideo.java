/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.input;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IError;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import main.java.coordinates.Space;

/**
 * Permet de lire une vidéo étape par étape, ie computation par computation.
 * A chaque computation, le reader renvoit la frame correspondant à la computation
 * @author ncarrara
 */
public class ReaderStepVideo extends AbstractReader{
    public static final int RANDOM = 0;
    public static final int INTERVAL_BASIC = 1;
    private double fps;
    private String inputFilename ;
    private int outputFps;
    private IMediaReader mediaReader;
    private boolean  stop;

    
    private boolean pictureFound;
    private BufferedImage frameCourante;
    private int nbFramesCourantesLues;
    private int typeScaling;
    private long videoDurationMicroSeconds;
    private int indexFrameRead;
    private Space space;
    private IContainer container;
    
    /**
     * Permet de lire la video étape par étape
     * @param inputVideo
     * @param cps computation par seconde (fps output)
     * @param typeScaling 
     */
    public ReaderStepVideo(String inputVideo, int cps, int typeScaling,Space space) {
        nbFramesCourantesLues = 0;
        indexFrameRead = -9999;
        inputFilename = inputVideo;
        this.space = space;
        outputFps = cps;
        this.typeScaling =typeScaling;
        stop = false;

        container = IContainer.make();
        
        if (container.open(inputFilename, IContainer.Type.READ, null) < 0) {
            System.out.println("Could not open file: " + inputFilename);
        }

        videoDurationMicroSeconds = container.getDuration();
        
        IStream stream = container.getStream(0);

        IStreamCoder coder = stream.getStreamCoder();
        
        fps = coder.getFrameRate().getDouble();
        
        mediaReader = ToolFactory.makeReader(container);

        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

        mediaReader.addListener(new ImageSnapListener());

    }
    
    @Override
    public void reset() {
        stop = false;
        mediaReader.close();
        container.close();
        pictureFound = false;
        frameCourante = null;
        nbFramesCourantesLues = 0;
        indexFrameRead = -9999;
        container = IContainer.make();
        if (container.open(inputFilename, IContainer.Type.READ, null) < 0) {
            System.out.println("Could not open file: " + inputFilename);
        }
        mediaReader = ToolFactory.makeReader(container);

        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

        mediaReader.addListener(new ImageSnapListener());
    }

    /**
     *  
     * @param computationTime (en secondes)
     * @param x
     * @param y
     * @return la valeur RGB en x y sur la frame de la video, correspondante au temps de computation courant
     */
    @Override
    public int get(double computationTime, int x, int y) {
        BufferedImage picture = getPicture(computationTime);
        int rgb = 0 ;
        if(picture != null)
            rgb = picture.getRGB(x, y);
        return rgb;
    }
    
    /**
     * 
     * @param computationTime
     * @return la frame scalé de la video correpondante au temps de computation courant, null si on atteint le EOF
     */
    private BufferedImage getPicture(double computationTime){
        int indexFrameToRead = computationTimeToVideoFrame(computationTime);
        if( indexFrameToRead > indexFrameRead ){
            pictureFound = false;
            // on lit les frames tant qu'on a pas atteint la bonne frame à renvoyer (en fonction de cps/fps)
            while(!stop && nbFramesCourantesLues < (indexFrameToRead + 1) ){
                // on lit les paquets jusqu'a ce qu'on trouve une nouvelle frame (ie que le listener proc)
                while(!pictureFound && !stop){
                    IError err = mediaReader.readPacket(); 
                    stop = err!=null;
                }
                pictureFound = false;
            }
            if(!stop){
                frameCourante = scaling((int)space.getResolution(), (int)space.getResolution(), typeScaling, frameCourante);
                indexFrameRead = indexFrameToRead;
            }
        }
        if(stop){
            frameCourante = null; // EOF
        }
        return frameCourante;
    }

    /**
     * 
     * @param computationTime
     * @return le numéro de la frame correspondant au temps de computation
     */
    private int computationTimeToVideoFrame(double computationTime) {
        double x = computationTime * outputFps;
        int y = (int) Math.round(((double)x/(double)outputFps)*(double)fps);
        return y;
    }

    
    
    /**
     * trigger dès qu'un paquet "fin de frame" est lu. 
     * Positionne la frame courante avec la frame lu gràce aux paquets.
     */
    private class ImageSnapListener extends MediaListenerAdapter {
        
        @Override
        public void onVideoPicture(IVideoPictureEvent event) {
            //System.out.println("onVideoPicture ! ");
            frameCourante = event.getImage();
            nbFramesCourantesLues++;
            pictureFound = true;
        }
        
    }
    
//    /**
//     * réduit l'échelle d'une image
//     * @param w
//     * @param h
//     * @param type
//     * @param imageToScale
//     * @return 
//     */
//    private BufferedImage scaling(int w,int h, int type, BufferedImage imageToScale){
//        BufferedImage before = imageToScale;
//        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        AffineTransform at = new AffineTransform();
//        double sx = ((double)w)/((double)before.getWidth());
//        double sy = ((double)h)/((double)before.getHeight());
//        at.scale(sx, sy);
//        AffineTransformOp scaleOp = new AffineTransformOp(at, type);
//        after = scaleOp.filter(before, after);
//        return after;
//    }
}

