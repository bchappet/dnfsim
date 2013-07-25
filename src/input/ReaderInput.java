/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import coordinates.Space;
import java.awt.image.AffineTransformOp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permet de lire une vidéo de différente manière.
 * 
 * @author ncarrara
 */
public class ReaderInput implements InterfaceReader {
    private InterfaceReader reader;
    /* type Reader */
    public static final int FULL = 0;
    public static final int STEP = 1;
    public static final int WEBCAM = 2;
    public static final int IMAGES = 3;
    
    /* type Traitement (uniquement pour reader de type FULL */
    public static final int RANDOM = 0;
    public static final int INTERVAL_BASIC = 1;
    public static final int INTERVAL_AVANCE = 2;
    
    /* type scaling (interpolation) */
    public static int TYPE_BICUBIC = AffineTransformOp.TYPE_BICUBIC;
    public static int TYPE_BILINEAR = AffineTransformOp.TYPE_BILINEAR;
    public static int TYPE_NEAREST_NEIGHBOR = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
    
    protected boolean reseted;
    
    /**
     * Crée une instance Reader sur laquelle on va pouvoir récupéré la valeur d'un pixel à un temps donné.
     * @param inputVideo la vidéo d'entrée
     * @param cps fps de sortie, ie le nombre de computation par seconde effectué par le model qui utilise l'unit model lié à ce reader
     * @param typeTraitement manière dont on choisit les frames à éliminer/rajouter en fonction du fps d'entrée (fps de la vidéo) et fps de sortie (cps : computation par seconde)
     * @param typeScaling type de l'algorithme de mise à l'échelle de l'image
     * @param typeReader si on lis la vidéo d'une traite, ou étape par étape
     * @param space sur quel espace on travail (on récupère la résolution)
     */
    public ReaderInput(ConfigurationInput configurationInput, Integer typeScaling, Integer typeReader,Space space){
        reseted = false;
        if(typeScaling != TYPE_BICUBIC && typeScaling != TYPE_BILINEAR && typeScaling != TYPE_NEAREST_NEIGHBOR){
            Logger.getLogger(ReaderInput.class.getName()).log(Level.SEVERE, null, new Exception("Type de scaling (interpolation) inconnu"));
        }else{
            switch(typeReader){
                case FULL:
                    reader = new ReaderFullVideo(configurationInput.pathVideo, configurationInput.cps, configurationInput.typeTraitement, typeScaling, space);
                    break;
                case STEP :
                    reader = new ReaderStepVideo(configurationInput.pathVideo, configurationInput.cps, typeScaling, space);
                    break;
                case WEBCAM:
                    reader = new ReaderWebcam(configurationInput.driverName, configurationInput.deviceName, configurationInput.heigth,configurationInput.width, configurationInput.fps, configurationInput.cps, typeScaling, space);
                    break;
                case IMAGES:
                    reader = new ReaderImages(configurationInput.pathImages, configurationInput.nameImage, configurationInput.cpf, configurationInput.cps, typeScaling, space);
                    break;
                    
                default :
                    Logger.getLogger(ReaderInput.class.getName()).log(Level.SEVERE, null, new Exception("Type de reader inconnu"));
                    break;
            }
        }
        
    }

    @Override
    public int get(double time, int x, int y) {
        reseted = false;
        return reader.get(time, x, y);
    }

    @Override
    public void reset() {
        if(!reseted){
            reader.reset();
            reseted = true;
        }
    }
}
