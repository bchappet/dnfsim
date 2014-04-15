package input;

import java.awt.image.BufferedImage;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import coordinates.Space;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permet de lire une vidéo en entier puis garder en mémoire ses frames. Ainsi
 * un model peut exploité la vidéo seulement en parcourant les frames.
 *
 * @author ncarrara
 */
public class ReaderFullVideo extends AbstractReader {

    public static final int RANDOM = 0;
    public static final int INTERVAL_BASIC = 1;
    public static final int INTERVAL_AVANCE = 2;
    private double fps;
    private int numberFrames;
    private long videoDurationMicroSeconds;
    private String inputFilename;
    private List<BufferedImage> frames;
    private List<BufferedImage> framesTraitees;
    private List<Image> framesTraiteesScaled;
    private int outputFps;
    private int typeTraitement;

    /**
     * lit le fichier video ( récupère frames et temps de la vidéo )
     *
     * @param inputVideo video d'entrée
     * @param cps computation par seconde demandé
     * @param typeTraitement la manière dont on veut add/remove les frames selon
     * le cps
     */
    public ReaderFullVideo(String inputVideo, int cps, int typeTraitement, int typeScaling, Space space) {

        inputFilename = inputVideo;
        outputFps = cps;
        frames = new ArrayList<BufferedImage>();

        IContainer container = IContainer.make();
        if (container.open(inputFilename, IContainer.Type.READ, null) < 0) {
            System.out.println("Could not open file: " + inputFilename);
        }

        videoDurationMicroSeconds = container.getDuration();

        System.out.println("videoDuration : "+videoDurationMicroSeconds+" ms");
        
        IMediaReader mediaReader = ToolFactory.makeReader(container);

        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

        mediaReader.addListener(new ImageSnapListener());

        ToolFactory.makeReader(container);

        while (mediaReader.readPacket() == null) ;
        System.out.println("frames.size() : "+frames.size());
        /* impossible de récupérer la première frame 
         * à priori donc on duplique la dernière 
         * pour garder un nombre de fps correct
         */
        frames.add(frames.get(frames.size() - 1));

        numberFrames = frames.size();

        //fps = (numberFrames/(videoDurationMicroSeconds/1000000));
        IStream stream = container.getStream(0);

        IStreamCoder coder = stream.getStreamCoder();

        fps = coder.getFrameRate().getDouble();

        System.out.println("fps : "+fps);
        
        switch (typeTraitement) {
            case RANDOM:
                traitementFramesRandom();
                break;
            case INTERVAL_BASIC:
                //traitementFramesIntervalBasic();
                Logger.getLogger(ReaderFullVideo.class.getName()).log(Level.SEVERE, null, new Exception("Type de traitement buggé"));
                break;
            case INTERVAL_AVANCE:
                traitementFramesIntervalAvance();
                break;
            default:
                Logger.getLogger(ReaderFullVideo.class.getName()).log(Level.SEVERE, null, new Exception("Type de traitement fps to cps incorrect"));
                break;
        }

        scaling((int) space.getResolution(), (int) space.getResolution(), typeScaling);

        mediaReader.close();
        container.close();
    }

    @Override
    public void reset() {
        // nothing to do
    }

    /**
     * à chaque frame lue, rajoute une frame dans la liste
     */
    private class ImageSnapListener extends MediaListenerAdapter {

        @Override
        public void onVideoPicture(IVideoPictureEvent event) {
            frames.add(event.getImage());
        }
    }

    /**
     *
     * @param time
     * @param x
     * @param y
     * @return code RGB au point x y de la frame time
     */
    @Override
    public int get(double time, int x, int y) {
        int code;
        int a = (int) (time * outputFps);
//        if (a > 118) {
//            System.out.println("time and a : " + time + " & " + a);
//        }
        if(a < framesTraitees.size()){
            code = this.framesTraitees.get(a).getRGB(x, y);
//            System.out.println("this.framesTraitees.get(a)"+this.framesTraitees.get(a));
        }else{
            code = 0;
        }
        return code;
    }

    /**
     * Rajoute ou supprime des frames de manière aléatoire pour correspondre au
     * fps de sortie
     */
    private void traitementFramesRandom() {
        framesTraitees = new ArrayList(frames);
        Random r = new Random();
        int index;
        if (outputFps > fps) {
            for (int i = 0; i < (outputFps - fps) * (videoDurationMicroSeconds / 1000000); i++) {
                index = r.nextInt(framesTraitees.size());
                framesTraitees.add(index, framesTraitees.get(index));
            }
        }
        if (outputFps < fps) {
            // on supprime des frames de manière aléatoire
            // on doit rajouter des frames (on duplique aléatoirement ce qu'il manque)
            for (int i = 0; i < (fps - outputFps) * (videoDurationMicroSeconds / 1000000); i++) {
                index = r.nextInt(framesTraitees.size());
                framesTraitees.remove(index);
            }
        }
    }

    /**
     * BUG à resoudre , cf TestAlgoInsertionSuppresionFrame Trouve le nombre de
     * frame à add/enlever par seconde. Supprime/ajoute une frame à intervalle
     * régulier Basic : car il est possible que l'intervalle ne soit pas entier.
     * Donc on arrondit à l'inférieur l'interval. ceci implique qu'on
     * rajoute/supprime de trop, donc on fixe une limite de rajout/suppression
     * par seconde. ça implique que pour chaque seconde on fait une modification
     * à intervalle régulière pendant X intervals et on ne fait aucune
     * modification pendant Y interval (jusqu'a la prochaine seconde)
     */
    private void traitementFramesIntervalBasic() {
        framesTraitees = new ArrayList(frames);
        int diffFps = (int) Math.abs(outputFps - fps);
        int interval = (int) fps / diffFps;
        int nbFrameModifiedTotal = 0;
        int nbFrameModifiedSec = 0;
        for (int i = 0; i < (videoDurationMicroSeconds / 1000000); i++) {
            nbFrameModifiedSec = 0;
            for (int frame = i * (int) fps; frame < (i + 1) * (int) fps; frame = frame + interval) {
                if (nbFrameModifiedSec < diffFps) {
                    nbFrameModifiedSec++;
                    if (outputFps > fps) {
                        framesTraitees.add(frame + nbFrameModifiedTotal, framesTraitees.get(frame + nbFrameModifiedTotal));
                    }
                    if (outputFps < fps) {
                        framesTraitees.remove(frame - nbFrameModifiedTotal);
                    }
                    nbFrameModifiedTotal++;
                }
            }
        }
    }

    /**
     * choisit les frames à afficher en faisant une projection d'un interval sur
     * un autre. (cps vers fps ou fps vers cps)
     */
    private void traitementFramesIntervalAvance() {
        framesTraitees = new ArrayList<BufferedImage>();
        int x; // coordonée de la frame dans la nouvelle liste
        int y; // projection de x sur la liste d'entrée
        for (x = 0; x < outputFps * (videoDurationMicroSeconds / 1000000); x++) {
            y = (int) Math.round(((double) x / (double) outputFps) * (double) fps);
            framesTraitees.add(frames.get(y));
        }
    }

    /**
     * reduit toutes les images traitée
     */
    private void scaling(int w, int h, int type) {
        for (int i = 0; i < framesTraitees.size(); i++) {
            BufferedImage before = framesTraitees.get(i);
            BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = new AffineTransform();
            double sx = ((double) w) / ((double) before.getWidth());
            double sy = ((double) h) / ((double) before.getHeight());
            at.scale(sx, sy);
            AffineTransformOp scaleOp = new AffineTransformOp(at, type);
            after = scaleOp.filter(before, after);
            framesTraitees.set(i, after);
//            try {
//                ImageIO.write(framesTraitees.get(i), "png", new File("tmp/framesTraiteesScaled_"+i));
//            } catch (IOException ex) {
//                Logger.getLogger(ReaderFullInputVideo.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    /**
     * ****************** getter/setter ****************************
     */
    /**
     * @return the fps
     */
    public double getFps() {
        return fps;
    }

    /**
     * @param fps the fps to set
     */
    public void setFps(double fps) {
        this.fps = fps;
    }

    /**
     * @return the inputFilename
     */
    public String getInputFilename() {
        return inputFilename;
    }

    /**
     * @param inputFilename the inputFilename to set
     */
    public void setInputFilename(String inputFilename) {
        this.inputFilename = inputFilename;
    }

    /**
     * @return the videoDurationMicroSeconds
     */
    public long getVideoDurationMicroSeconds() {
        return videoDurationMicroSeconds;
    }

    /**
     * @param videoDurationMicroSeconds the videoDurationMicroSeconds to set
     */
    public void setVideoDurationMicroSeconds(long videoDurationMicroSeconds) {
        this.videoDurationMicroSeconds = videoDurationMicroSeconds;
    }

    /**
     * @return the numberFrames
     */
    public int getNumberFrames() {
        return numberFrames;
    }

    /**
     * @param numberFrames the numberFrames to set
     */
    public void setNumberFrames(int numberFrames) {
        this.numberFrames = numberFrames;
    }

    /**
     * @return the frames
     */
    public List<BufferedImage> getFrames() {
        return frames;
    }

    /**
     * @param frames the frames to set
     */
    public void setFrames(List<BufferedImage> frames) {
        this.frames = frames;
    }

    /**
     * @return the framesTraitees
     */
    public List<BufferedImage> getFramesTraitees() {
        return framesTraitees;
    }

    /**
     * @param framesTraitees the framesTraitees to set
     */
    public void setFramesTraitees(List<BufferedImage> framesTraitees) {
        this.framesTraitees = framesTraitees;
    }

    /**
     * @return the framesTraiteesScaled
     */
    public List<Image> getFramesTraiteesScaled() {
        return framesTraiteesScaled;
    }

    /**
     * @param framesTraiteesScaled the framesTraiteesScaled to set
     */
    public void setFramesTraiteesScaled(List<Image> framesTraiteesScaled) {
        this.framesTraiteesScaled = framesTraiteesScaled;
    }
}
