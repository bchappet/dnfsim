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
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IError;
import com.xuggle.xuggler.IMetaData;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoResampler;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import main.java.coordinates.Space;

/**
 * Permet de lire une vidéo étape par étape, ie computation par computation.
 * A chaque computation, le reader renvoit la frame correspondant à la computation
 * @author ncarrara
 */
public class ReaderWebcam extends AbstractReader{
    public static final int RANDOM = 0;
    public static final int INTERVAL_BASIC = 1;
    private double fps;
    private int outputFps;
    private IMediaReader mediaReader;
    private boolean  stop;

    
    private boolean pictureFound;
    private BufferedImage frameCourante;
    private int nbFramesCourantesLues;
    private int typeScaling;
    private int indexFrameRead;
    private Space space;
    private IContainer container;
    private IMetaData params;
    private IContainerFormat format;
    private IError error;
    private String deviceName;
    private String driverName;
    private int heigth;
    private int width;
    
    /**
     * Récupérer les frames d'une webcam
     * @param driverName
     * @param deviceName
     * @param h
     * @param w
     * @param fps
     * @param cps
     * @param typeScaling
     * @param main.java.space 
     */
    public ReaderWebcam(String driverName, String deviceName, int h, int w, int fps, int cps, int typeScaling,Space space) {
        nbFramesCourantesLues = 0;
        indexFrameRead = -9999;
        this.space = space;
        outputFps = cps;
        this.typeScaling =typeScaling;
        stop = false;
        this.fps = fps;
        this.driverName = driverName;
        this.deviceName = deviceName;
        heigth =h;
        width = w;
        initiation();
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
        initiation();
        
    }
    
    private void initiation(){
        if (!IVideoResampler.isSupported(IVideoResampler.Feature.FEATURE_COLORSPACECONVERSION))
            throw new RuntimeException("you must install the GPL version of Xuggler (with IVideoResampler support) for this demo to work");

        container = IContainer.make();

        System.out.println("driverName : "+driverName);
        
        // Tell Xuggler about the device format
        format = IContainerFormat.make();
        if (format.setInputFormat(driverName) < 0)
          throw new IllegalArgumentException("couldn't open webcam device: " + driverName);

        // devices, unlike most files, need to have parameters set in order
        // for Xuggler to know how to configure them, for a webcam, these
        // parameters make sense

        params = IMetaData.make();
        
        String size = width+"*"+heigth;
        String framerate =  (int)fps+"/1";
        System.out.println("size : "+size);
        System.out.println("framerate : "+framerate);
        params.setValue("framerate", framerate);
        params.setValue("video_size", size);   
        System.out.println("deviceName : "+deviceName);

        // Open up the container
        int retval = container.open(deviceName, IContainer.Type.READ, format,
            false, true, params, null);
        if (retval < 0)
        {
          // This little trick converts the non friendly integer return value into
          // a slightly more friendly object to get a human-readable error name
          error = IError.make(retval);
          throw new IllegalArgumentException("could not open file: " + deviceName + "; Error: " + error.getDescription());
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
    
    
}


