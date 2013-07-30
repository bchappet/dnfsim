/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelCMSVA;

import java.io.IOException;
import java.util.List;

import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
import maps.Parameter;
import maps.Var;
import unitModel.GaussianND;
import unitModel.RandTrajUnitModel;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;
import gui.Suscriber;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import lucasKanade.UnitModelLucasKanade;
import model.Model;
import statistics.Charac;
import statistics.CharacConvergence;
import statistics.CharacMaxSum;
import statistics.CharacMeanCompTime;
import statistics.CharacMeanError;
import statistics.CharacNoFocus;
import statistics.CharacObstinacy;
import statistics.Characteristics;
import statistics.Stat;
import statistics.StatMap;
import statistics.Statistics;
import unitModel.CosTraj;
import input.ConfigurationInput;
import input.ReaderInput;
import input.UnitModelInput;
import statistics.CharacAccError;

/**
 *
 * @author john
 */
public class ModelCMSVA extends Model {

    /* constante pour désigner chaque map */
    public static final String INPUT = "input";
    public static final String FOCUS = "focus";
    public static final String FEF = "fef";
    public static final String WM = "working memory";
    public static final String CONV_AFF_FOCUS_TO_INPUT = "convolution focus to input";
    public static final String CONV_LAT_FOCUS_TO_FOCUS = "convolution focus to focus";
    public static final String WEIGHT_AFF_FOCUS_INPUT = "weight focus from input";
    public static final String WEIGHT_LAT_FOCUS_FOCUS = "weight focus from focus";
    public static final String CONV_AFF_FEF_TO_FOCUS = "convolution FEF to focus";
    public static final String CONV_AFF_FEF_TO_WM = "convolution  FEF to working memory";
    public static final String CONV_LAT_FEF_TO_FEF = "convolution FEF to FEF";
    public static final String CONV_AFF_FEF_TO_INPUT = "convlution FEF TO INPUT";
    public static final String CONV_AFF_WM_TO_FEF = "convolution working memory to FEF";
    public static final String WEIGHT_AFF_FEF_WM = "weight FEF from working memory";
    public static final String WEIGHT_AFF_FEF_FOCUS = "weight FEF from focus";
    public static final String WEIGHT_AFF_FEF_INPUT = "weight FEF from input";
    public static final String WEIGHT_LAT_FEF_FEF = "weight FEF from FEF";
    public static final String WEIGHT_AFF_WM_FEF = "weight working memory from FEF";
    /**
     * Distractors name DISTR_i *
     */
    protected static final String DISTR = "distr";
    /**
     * Tracks name TRACK_i *
     */
    public static final String TRACK = "track";
    /**
     * For understand the name of the following maps : c = convolution l =
     * lateral f = focus fef = fef vm = working memory t = to w = weigth i =
     * input a = afferant
     */
    /* input */
    protected AbstractMap input;
    /* focus */
    protected AbstractMap focus;
    protected AbstractMap cafti;
    protected AbstractMap clftf;
    protected AbstractMap wafi;
    protected AbstractMap wlff;
    /* fef */
    protected AbstractMap fef;
    protected AbstractMap cafeftf;
    protected AbstractMap cafeftwm;
    protected AbstractMap clfeftfef;
    protected AbstractMap cafefti;
    protected AbstractMap wafefwm;
    protected AbstractMap wlfeffef;
    protected AbstractMap wafeff;
    protected AbstractMap wafefi;
    /* vm */
    protected AbstractMap wm;
    protected AbstractMap cawmtfef;
    protected AbstractMap wawmfef;
    protected Map inputData;
    protected Map inputTest;
    /**
     * Updatable accessed by the model*
     */
    protected Parameter pa_lat;
    protected Parameter pb_lat;
    protected Parameter pa_aff;
    protected Parameter pTau;// =1 for all (except for REWARD but this map is not used)
    protected Parameter pA_lat;
    protected Parameter pB_lat;
    protected Parameter pA_aff;
    protected Parameter palpha; // = 13
    protected Parameter pBaseline;
    protected Var vdt;
    private double fact = 40 * 40;
    private double res2 = 49 * 49;
    private double diviseur = 40.0;
    private double ratio = 1.0;
    private final static int TRUE = 1;
    private final static int FALSE = 0;
    /**
     * The two type of refSpace used in the unitModel*
     */
    protected Space space2d;//2 dim refSpace
    protected Space noDimSpace;//"no dim" refSpace : only one value
    protected Space extendedSpace; //For the lateral weight (if no wrap res*=2)

    public ModelCMSVA(String name) {
        super(name);




    }

    @Override
    public List<Parameter> getDefaultDisplayedParameter() {
        Parameter[] ret = {inputData/*,inputTest*/, input, focus, fef, wm/*,
         cafti,clftf,wafi,wlff,cafeftf,
         cafeftwm,clfeftfef,cafefti,wafefwm,
         wlfeffef,wafeff,wafefi,cawmtfef,wawmfef*/

        };
        return Arrays.asList(ret);
    }

    @Override
    protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException {
        /*Here we initialize some space which will be used later**/
        noDimSpace = refSpace.clone(); //Space with "0" dimension for single value map
        noDimSpace.setDimension(new int[]{0, 0});

        space2d = refSpace.clone();//Standard 2D space for the 2D map 
        space2d.setDimension(new int[]{1, 1});

        double extension = 1; //extension for the weight map if we don't wrap
        if (!refSpace.isWrap()) {
            extension = 2;
        }
        //Extended space is a -1,1 (instead of -0.5,0.5) space
        // used for lateral weights map in case of non wrapped convolution
        extendedSpace = space2d.extend(extension);

        //Displayed parameter
        addParameters(command.get(CNFTCommandLine.NB_TRACKS));
        addParameters(command.get(CNFTCommandLine.TRACK_DT));
        addParameters(command.get(CNFTCommandLine.NB_DISTRACTERS));
        addParameters(command.get(CNFTCommandLine.DISTR_DT));
        addParameters(command.get(CNFTCommandLine.NOISE_AMP));
        addParameters(command.get(CNFTCommandLine.NOISE_DT));

        initModel();
    }

    private void initModel() throws CommandLineFormatException {
        vdt = command.get(CNFTCommandLine.DT); //default dt
        initInput();
        initFocus();
        fef = new Map(FEF, new UnitModelCMSVA(), vdt, space2d);
        initVM();
        initFEF();
        root = fef;
    }

    /**
     * init the default input
     *
     * @param width : width of the tracks and distracters gaussian
     * @param intensity : intensity of the tracks and distracters gaussian
     * @param nbDistr : number of distracters
     * @param noiseAmpl : amplitude of noise
     * @throws CommandLineFormatException
     * @throws NullCoordinateException
     * @throws CloneNotSupportedException
     */
    protected void initInput() throws CommandLineFormatException, NullCoordinateException {
        //Construct noise map
        UnitModel noise = new RandTrajUnitModel(command.get(CNFTCommandLine.NOISE_DT), space2d,
                new Var(0), command.get(CNFTCommandLine.NOISE_AMP));
        Map mNoise = new Map("Noise", noise);
        mNoise.constructMemory(); //otherwise the noise is changed at each computation step

        //Construct the input as a sum of theses params
        UnitModel inputTestUM = new Sum(command.get(CNFTCommandLine.INPUT_DT), space2d, mNoise);
        inputData = new Map("inputData", inputTestUM);
        modifyModel();

        inputData.toParallel();

//        ConfigurationInput metaData = new ConfigurationInput();
//        metaData.deviceName = "/dev/video0";
//        metaData.driverName = "video4linux2";
//        metaData.width = 320 ;
//        metaData.heigth = 240;
//        metaData.fps = 30;
//        metaData.nameImage = "framesTraiteesScaled_";
//        metaData.pathImages = "tmp";
//        metaData.pathVideo = "AFightD.mpg";
//        metaData.cpf = 1; // => 10 frames par secondes vu que le model est a 10 cps
//        UnitModel inputVideoUM = new UnitModelInput( command.get(CNFTCommandLine.INPUT_DT), 
//                                        space2d,
//                                        metaData, 
//                                        ReaderInput.TYPE_BILINEAR,
//                                        ReaderInput.STEP); 
//        inputData = new Map("inputData", inputVideoUM);
//        inputData.constructMemory();
//        inputData.toParallel();

        UnitModel lucasKanadeUM = new UnitModelLucasKanade(command.get(CNFTCommandLine.INPUT_DT),
                space2d,
                inputData/*inputTest*/,
                new Var(1), // épaisseur de la fenêtre de voisinage 
                new Var(FALSE), // on veut des poid ou pas
                new Var(10), // intensité du poid de la gausienne
                new Var(100), // largeur du poid de la gausienne
                new Var(FALSE)); // la map est wrapped ou pas 
        input = new Map("lucasKanade", lucasKanadeUM);
        input.constructMemory();

    }

    private void initFocus() throws CommandLineFormatException {
        initWLFF();
        initWAFI();

        focus = new Map(FOCUS, new UnitModelCMSVA(), vdt, space2d);
        clftf = new FFTConvolutionMatrix2D(CONV_LAT_FOCUS_TO_FOCUS, vdt, space2d);
        cafti = new FFTConvolutionMatrix2D(CONV_AFF_FOCUS_TO_INPUT, vdt, space2d);
        palpha = new Var(13);
        pBaseline = new Var(-0.05);
        pTau = new Var(1);
        clftf.addParameters(wlff, new Leaf(focus));
        clftf.constructMemory();
        cafti.addParameters(wafi, input);
        cafti.constructMemory();
        focus.addParameters(new Leaf(focus), pTau, pBaseline, palpha, cafti, clftf);
    }

    private void initFEF() throws CommandLineFormatException {
        initWAFEFWM();
        initWLFEFFEF();
        initWAFEFF();
        initWAFEFI();

        cafeftwm = new FFTConvolutionMatrix2D(CONV_AFF_FEF_TO_WM, vdt, space2d);
        clfeftfef = new FFTConvolutionMatrix2D(CONV_LAT_FEF_TO_FEF, vdt, space2d);
        cafeftf = new FFTConvolutionMatrix2D(CONV_AFF_FEF_TO_FOCUS, vdt, space2d);
        cafefti = new FFTConvolutionMatrix2D(CONV_AFF_FEF_TO_INPUT, vdt, space2d);
        palpha = new Var(13);
        pBaseline = new Var(/*-0.2*/-0.25);
        pTau = new Var(1);
        cafeftwm.addParameters(wafefwm, wm);
        cafeftf.addParameters(wafeff, focus);
        cafefti.addParameters(wafefi, input);
        clfeftfef.addParameters(wlfeffef, new Leaf(fef));
        fef.addParameters(new Leaf(fef), pTau, pBaseline, palpha, cafeftwm, clfeftfef, cafeftf, cafefti);
    }

    private void initVM() throws CommandLineFormatException {
        initWAWMFEF();

        wm = new Map(WM, new UnitModelCMSVA(), vdt, space2d);
        cawmtfef = new FFTConvolutionMatrix2D(CONV_AFF_WM_TO_FEF, vdt, space2d);
        palpha = new Var(13);
        pBaseline = new Var(0.0);
        pTau = new Var(1);
        cawmtfef.addParameters(wawmfef, new Leaf(fef));
        wm.addParameters(new Leaf(wm), pTau, pBaseline, palpha, cawmtfef);
    }

    /*public ModelTestFocusCMSVA(String name){
     super(name);
     double fact = 40*40;
     double res2 = 49*49;
     pa_lat = new Var("pa_lat",0.1);
     pb_lat =  new Var("pb_lat",1);
     pa_aff = new Var("pa_aff",0.04);
     pTau = new Var(1);
     pA_lat = new Var("pA_latt",1.25*fact/(res2));
     pB_lat = new Var("pB_latt",-0.70*fact/(res2));
     pA_aff = new Var("pA_aff",0.25*fact/(res2));
     palpha = new Var(13);
     pBaseline = new Var(-0.05);*/
    private void initWAWMFEF() throws CommandLineFormatException {
        pA_aff = new Var("pA_aff_WAFI", /*2.35*/ 2.5);//ok
        pa_aff = new Var("pa_aff_WAFI", ratio * 1.5 / diviseur);//ok
        wawmfef = (AbstractMap) getAfferantWeights(WEIGHT_AFF_WM_FEF, command.get(CNFTCommandLine.DT), extendedSpace, pA_aff, pa_aff);
    }

    private void initWLFF() throws CommandLineFormatException {
        pa_lat = new Var("pa_lat_WLFF", /*ratio*4/diviseur*/ 0.1);//ok
        pb_lat = new Var("pb_lat_WLFF", /*ratio*17/diviseur*/ 1);//ok
        pA_lat = new Var("pA_lat_WLFF", /*1.7*/ 1.25);//ok 
        pB_lat = new Var("pB_lat_WLFF", /*-0.65*/ -0.70);//ok 
        wlff = (AbstractMap) getLateralWeights(WEIGHT_LAT_FOCUS_FOCUS, command.get(CNFTCommandLine.DT), extendedSpace, pA_lat, pa_lat, pB_lat, pb_lat);
    }

    private void initWAFI() throws CommandLineFormatException {
        pA_aff = new Var("pA_aff_WAFI", /*0.25*//*0.35*//*0.30*/ 0.25);//ok
        pa_aff = new Var("pa_aff_WAFI", /*ratio*2/diviseur*/ 0.04);//ok
        wafi = (AbstractMap) getAfferantWeights(WEIGHT_AFF_FOCUS_INPUT, command.get(CNFTCommandLine.DT), extendedSpace, pA_aff, pa_aff);
    }

    private void initWAFEFWM() throws CommandLineFormatException {
        pA_aff = new Var("pA_aff_WAFEFWM",/*2.4*/ 2.4); //ok
        pa_aff = new Var("pa_aff_WAFEFWM", ratio * 1.5 / diviseur);//ok
        wafefwm = (AbstractMap) getAfferantWeights(WEIGHT_AFF_FEF_WM, command.get(CNFTCommandLine.DT), extendedSpace, pA_aff, pa_aff);
    }

    private void initWAFEFF() throws CommandLineFormatException {
        pA_aff = new Var("pA_aff_WAFEFF",/* initial :*/ /*0.2*//*0.7*/ 1.2);//ok
        pa_aff = new Var("pa_aff_WAFEFF", ratio * 2 / diviseur);
        wafeff = (AbstractMap) getAfferantWeights(WEIGHT_AFF_FEF_FOCUS, command.get(CNFTCommandLine.DT), extendedSpace, pA_aff, pa_aff);

    }

    private void initWAFEFI() throws CommandLineFormatException {
        pA_aff = new Var("pA_aff_WAFEFI",/*0.25*/ 0.255);//ok
        pa_aff = new Var("pa_aff_WAFEFI", ratio * 2 / diviseur);//ok
        wafefi = (AbstractMap) getAfferantWeights(WEIGHT_AFF_FEF_INPUT, command.get(CNFTCommandLine.DT), extendedSpace, pA_aff, pa_aff);
    }

    private void initWLFEFFEF() throws CommandLineFormatException {
        pa_lat = new Var("pa_lat_WLFEFFEF", ratio * 2 / diviseur);//ok
        pb_lat = new Var("pb_lat_WLFEFFEF", ratio * 4 / diviseur);//ok
        pA_lat = new Var("pA_lat_WLFEFFEF", 2.5);//ok
        pB_lat = new Var("pB_lat_WLFEFFEF", -1);//ok
        wlfeffef = (AbstractMap) getLateralWeights(WEIGHT_LAT_FEF_FEF, command.get(CNFTCommandLine.DT), extendedSpace, pA_lat, pa_lat, pB_lat, pb_lat);

    }

    public Parameter getAfferantWeights(String name, Var dt, Space extendedSpace,
            Parameter ia, Parameter wa) throws CommandLineFormatException {
        UnitModel a = new GaussianND(dt, extendedSpace, ia, wa, new Var(0), new Var(0));
        Map cnfta = new Map(name + "_A", a);
        cnfta.constructMemory();
        cnfta.toStatic();
        return cnfta;
    }

    /**
     * Generate a centered lateral weight map
     *
     * @param name
     * @param dt
     * @param extendedSpace
     * @param ia intensity of excitatory map
     * @param wa width of excitatory map
     * @param ib intensity of inhibitory map
     * @param wb width of inhibitory map
     * @return
     * @throws CommandLineFormatException
     * @throws NullCoordinateException
     * @throws CloneNotSupportedException
     */
    public Parameter getLateralWeights(String name, Var dt, Space extendedSpace,
            Parameter ia, Parameter wa, Parameter ib, Parameter wb) throws CommandLineFormatException {

        UnitModel a = new GaussianND(dt, extendedSpace, ia, wa, new Var(0), new Var(0));
        Map cnfta = new Map(name + "_A", a);
        //cnfta.constructMemory();

        UnitModel b = new GaussianND(dt, extendedSpace, ib, wb, new Var(0), new Var(0));
        Map cnftb = new Map(name + "_B", b);
        //cnftb.constructMemory();

        Map sum = new Map(name, new Sum(), dt, extendedSpace, cnfta, cnftb);
        sum.toStatic();

        return sum;
    }

    /**
     * Initialize the statistics
     *
     * @throws CommandLineFormatException
     */
    @Override
    protected void initializeStatistics() throws CommandLineFormatException {

//        AbstractMap[] tracks = trackable.toArray(new AbstractMap[]{trackable.get(0)});
        Stat stat = new Stat(command.get(CNFTCommandLine.DT), noDimSpace, this);
        stats = new Statistics("Stats", command.get(CNFTCommandLine.DT),
                noDimSpace, stat.getDefaultStatistics(new Leaf(focus), trackable/*tracks*/));

        /*(String theName, Var dt, Space space,Statistics stats,
         List<AbstractMap> tracks, Parameter... maps) {
         super(theName, dt, space,tracks, maps);*/



        StatMap fefstatmap = new FEFStatMap("test", command.get(CNFTCommandLine.DT), noDimSpace, stats, trackable, new Leaf(fef));
        stats.addStatisticMap(fefstatmap);

    }

    @Override
    protected void initializeCharacteristics() throws CommandLineFormatException {
        Charac conv = new CharacConvergence(Characteristics.CONVERGENCE, stats, noDimSpace, this);
        Charac meanError = new CharacMeanError(Characteristics.MEAN_ERROR, stats, noDimSpace, this, conv);
        Charac obstinacy = new CharacObstinacy(Characteristics.OBSTINACY, stats, noDimSpace, this, conv);
        Charac noFocus = new CharacNoFocus(Characteristics.NO_FOCUS, stats, noDimSpace, this, conv);
        Charac maxSum = new CharacMaxSum(Characteristics.MAX_SUM, stats, noDimSpace, this, conv);
        Charac meanCompTime = new CharacMeanCompTime(Characteristics.MEAN_COMP_TIME, stats, noDimSpace, this, conv);
        Charac accError = new CharacAccError(Characteristics.ACC_ERROR, stats, noDimSpace, this, conv);

        charac = new Characteristics(noDimSpace, stats, conv, meanError, obstinacy, noFocus, maxSum, meanCompTime, accError);

    }

    /**
     * Change the model environement if needed Every scenario update which are
     * not automaticaly computed by the model params architecture should be
     * implemented here : exemple : create more distracter params
     *
     * @throws CommandLineFormatException
     * @throws NullCoordinateException
     */
    @Override
    public void modifyModel() throws CommandLineFormatException, NullCoordinateException {
        boolean changed = this.changeNbDistr() || this.changeNbTrack();
        if (changed) {
            for (Suscriber s : suscribers) {
                s.signalTreeChanged();
            }
        }
    }
    /**
     * Previous Nb distractor (do not use outside changeNbDistr())*
     */
    private int nbDistrPrec = 0;

    /**
     * Change the number of distractors according to the command line parameters
     *
     * @return true if the model tree changed
     * @throws CommandLineFormatException
     * @throws NullCoordinateException
     */
    protected boolean changeNbDistr() throws CommandLineFormatException, NullCoordinateException {

        int nbDistr = (int) command.get(CNFTCommandLine.NB_DISTRACTERS).get();
        boolean changed = true;
        nbDistr = nbDistr >= 0 ? nbDistr : 0;
        if (nbDistr > nbDistrPrec) {
            for (int i = nbDistrPrec; i < nbDistr; i++) {
                Parameter distr = constructDistracter(DISTR + "_" + i);
                addParameter(distr, inputData);
            }
        } else if (nbDistr < nbDistrPrec) {
            for (int i = nbDistrPrec - 1; i >= nbDistr; i--) {
                Parameter p = removeParameter(DISTR + "_" + i);
                p = null;
            }
        } else {
            changed = false;
        }
        nbDistrPrec = nbDistr;
        return changed;
    }
    /**
     * Previous Nb distractor (do not use outside changeNbDistr())*
     */
    private int nbTrackPrec = 0;

    /**
     * Change the number of track according to the command line parameters
     *
     * @return true if the model tree changed
     * @throws CommandLineFormatException
     * @throws NullCoordinateException
     */
    protected boolean changeNbTrack() throws CommandLineFormatException, NullCoordinateException {
        int nbTrack = (int) command.get(CNFTCommandLine.NB_TRACKS).get();
        boolean changed = true;
        nbTrack = nbTrack >= 0 ? nbTrack : 0;
        if (nbTrack > nbTrackPrec) {
            for (int i = nbTrackPrec; i < nbTrack; i++) {
                Parameter track = constructTrack(TRACK + "_" + i, i, nbTrack);
                addParameter(track, inputData);
            }

        } else if (nbTrack < nbTrackPrec) {

            for (int i = nbTrackPrec - 1; i >= nbTrack; i--) {
                Parameter p = removeParameter(TRACK + "_" + i);
                trackable.remove(p);
                p = null;
            }
        } else {
            changed = false;
        }
        nbTrackPrec = nbTrack;
        return changed;
    }

    @Override
    public String getText() {
        return "CMSVA test FOCUS map  : ...";
    }

    @Override
    public String save(String file, List<Parameter> toSave) throws IOException, NullCoordinateException {

        FileWriter fw = null;

        for (Parameter p : toSave) {
            fw = new FileWriter(file + "_" + p.getName() + ".csv", false);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(((AbstractMap) p).displayMemory());
            out.close();
        }

        return toSave.toString();
    }

    /**
     * Construct one distrcater map
     *
     * @param name
     * @return
     * @throws NullCoordinateException
     * @throws CommandLineFormatException
     */
    protected AbstractMap constructDistracter(String name) throws NullCoordinateException, CommandLineFormatException {
        //Construct distracters map
        Map cx2 = new Map("CenterX", new RandTrajUnitModel(command.get(CNFTCommandLine.DT), noDimSpace,
                new Var(0), new Var(0.5)));
        Map cy2 = new Map("CenterY", new RandTrajUnitModel(command.get(CNFTCommandLine.DT), noDimSpace,
                new Var(0), new Var(0.5)));
        cx2.constructMemory();
        cy2.constructMemory();

        UnitModel distr = new GaussianND(command.get(CNFTCommandLine.DISTR_DT), space2d,
                command.get(CNFTCommandLine.DISTR_INTENSITY),
                command.get(CNFTCommandLine.DISTR_WIDTH),
                cx2, cy2);
        Map mDistr = new Map(name, distr);
        mDistr.constructMemory(); //otherwise the position is changed at each computation step
        return mDistr;
    }

    /**
     * TODO : not very nice addition of tracks eg. from 2 to 3. But removing and
     * reconstructing every tracks would change the statistics and
     * characteristics results Construct a trck with specific name
     *
     * @param name
     * @param nbTrack
     * @param num
     * @return
     * @throws NullCoordinateException
     * @throws CommandLineFormatException
     */
    protected AbstractMap constructTrack(String name, int num, int nbTrack) throws NullCoordinateException, CommandLineFormatException {
        Map cx = new Map("CenterX_" + num, new CosTraj(command.get(CNFTCommandLine.TRACK_DT), noDimSpace,
                new Var("center", 0), new Var("radius", 0.3), new Var("period", 36/*3.6*/), new Var("phase", num / (double) nbTrack + 0)) {
//			@Override
//			public double compute() throws NullCoordinateException {
//				double ret =  params.get(CENTER).get()+params.get(RADIUS).get()*
//						cos(2*PI*(time.get()/params.get(PERIOD).get()-params.get(PHASE).get()));
//				System.out.println(time.get() + "==>" + ret+ "@"+time.hashCode());
//				return ret;
//			}
        }) {
//			public void update(double timeLimit) throws NullCoordinateException{
//				super.update(timeLimit);
//				System.out.println(name + ": time : " + time.get() + "@"+time.hashCode());
//			}
        };
        Map cy = new Map("CenterY_" + num, new CosTraj(command.get(CNFTCommandLine.TRACK_DT), noDimSpace,
                new Var("center", 0), new Var("radius", 0.3), new Var("period", 36/*3.6*/), new Var("phase", num / (double) nbTrack + 0.25)));


        UnitModel track = new GaussianND(command.get(CNFTCommandLine.TRACK_DT), space2d,
                command.get(CNFTCommandLine.TRACK_INTENSITY),
                command.get(CNFTCommandLine.TRACK_WIDTH),
                cx, cy);
        AbstractMap ret = new Map(name, track);
        ret.constructMemory();//otherwise the position is changed at each computation step
        trackable.add((Map) ret);
        return ret;
    }
}
