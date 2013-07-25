/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelCMSVA;

import java.io.IOException;
import java.util.List;

import maps.AbstractMap;
import maps.ConvolutionMatrix2D;
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
import gui.Suscriber;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import model.Model;
import static model.ModelCNFT.TRACK;
import statistics.Charac;
import statistics.CharacAccError;
import statistics.CharacConvergence;
import statistics.CharacMaxSum;
import statistics.CharacMeanCompTime;
import statistics.CharacMeanError;
import statistics.CharacNoFocus;
import statistics.CharacObstinacy;
import statistics.Characteristics;
import statistics.Stat;
import statistics.Statistics;
import unitModel.CosTraj;

/**
 *
 * @author john
 */
public class ModelTestFocusCMSVA extends Model{
    
    /* constante pour désigner chaque map */
    public static final String INPUT = "input";
    public static final String FOCUS = "focus";
    public static final String CONV_AFF_FOCUS_TO_INPUT = "cafti";
    public static final String CONV_LAT_FOCUS_TO_FOCUS = "clftf";
    public static final String WEIGHT_AFF_FOCUS_INPUT = "wafi";
    public static final String WEIGHT_LAT_FOCUS_FOCUS = "wlff";
    /**Distractors name  DISTR_i **/
    protected static final String DISTR = "distr";
    /**Tracks name  TRACK_i **/
    public static final String TRACK = "track";



    protected AbstractMap input;
    protected AbstractMap focus;
    protected AbstractMap cafti;
    protected AbstractMap clftf;
    protected AbstractMap wafi;
    protected AbstractMap wlff;
    
    /**Updatable accessed by the model**/
    protected Parameter pa_lat;
    protected Parameter pb_lat;
    protected Parameter pa_aff;
    protected Parameter pTau;// =1 for all (except for REWARD but this map is not used)
    protected Parameter pA_lat;
    protected Parameter pB_lat;
    protected Parameter pA_aff;
    protected Parameter palpha; // = 13
    protected Parameter pBaseline;
    
    protected Var i_v_a;
    protected Var i_v_A;
    protected Var v_v_a,v_v_A,v_v_b,v_v_B;
    protected AbstractMap W_VV,W_IV,cnftVV,cnftIV;


    /**The two type of refSpace used in the unitModel**/
    protected Space space2d;//2 dim refSpace
    protected Space noDimSpace;//"no dim" refSpace : only one value
    protected Space extendedSpace; //For the lateral weight (if no wrap res*=2)
    
    
    public ModelTestFocusCMSVA(String name){
        super(name);
        double alpha = 13;
        double n = 39;
        
        i_v_a = new Var("i_v_a",2/n);
        i_v_A = new Var("i_v_A",2/alpha);
        
        v_v_a = new Var("v_v_a",2/n);
        v_v_A = new Var("v_v_A",2.5/alpha);
        v_v_b = new Var("v_v_b",4/n);
        v_v_B = new Var("v_v_B",-1/alpha);
      
       
        pa_aff = new Var("pa_aff",2/n);
        pA_aff = new Var("pA_aff",0.25/alpha);
        
       double fact = 10;
        
        pa_lat = new Var("pa_lat",4/n);
        pA_lat = new Var("pA_latt",fact*1.7/alpha);
        
        pb_lat =  new Var("pb_lat",17/n);
        pB_lat = new Var("pB_latt",fact*-0.65/alpha);
       
        pTau = new Var("tau",1);
        palpha = new Var("alpha",alpha);
        pBaseline = new Var("baseLine",0);
        
       
        
    }
    
     @Override
    public List<Parameter> getDefaultDisplayedParameter() {
                Parameter[] ret = {input,focus,cafti,clftf,wafi,wlff};
		return Arrays.asList(ret);    
     }

    @Override
    protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException {
        /*Here we initialize some space which will be used later**/
        noDimSpace = refSpace.clone(); //Space with "0" dimension for single value map
        noDimSpace.setDimension(new int[]{0,0});

        space2d = refSpace.clone();//Standard 2D space for the 2D map 
        space2d.setDimension(new int[]{1,1});

        double extension = 1; //extension for the weight map if we don't wrap
        if(!refSpace.isWrap())
                extension = 2;
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
        addParameters(pa_lat,pb_lat,pA_lat,pB_lat);
        
        
        initDefaultInput();
        initModel();
    }
    
    private void initModel() throws CommandLineFormatException{
        Var vdt = command.get(CNFTCommandLine.DT); //default dt
        initWLFF(); // initialize the Weight latteral focus map
        initWAFI(); // initialize the weight afferante focus-input map
        
        W_VV =  (AbstractMap) getLateralWeights("WcnftVV",
        		command.get(CNFTCommandLine.DT), extendedSpace, v_v_A, v_v_a, v_v_B, v_v_b);
        W_IV = (AbstractMap) getAfferantWeights("WcnftIV", command.get(CNFTCommandLine.DT),
        		extendedSpace, i_v_A, i_v_a);
        cnftVV = new ConvolutionMatrix2D("cnftVV",vdt,space2d);
        cnftIV = new ConvolutionMatrix2D("cnftIV",vdt,space2d);
        
        
        

        clftf = new ConvolutionMatrix2D(CONV_LAT_FOCUS_TO_FOCUS,vdt,space2d);
        
        cafti = new ConvolutionMatrix2D(CONV_AFF_FOCUS_TO_INPUT,vdt,space2d);
        
        focus = new Map(FOCUS,new FocusUnitModelCMSVA(),vdt,space2d); 
        
        focus.addParameters(new Leaf(focus), pTau, pBaseline, palpha, cafti, clftf);

        clftf.addParameters(wlff,new Leaf(focus));
        clftf.constructMemory();
        
       
        cafti.addParameters(wafi,input);
        cafti.constructMemory();
        
        
        
        root = focus;
    }
    
    /**
	 * init the default input 
	 * @param width : width of the tracks and distracters gaussian
	 * @param intensity : intensity of the tracks and distracters gaussian
	 * @param nbDistr : number of distracters
	 * @param noiseAmpl : amplitude of noise
	 * @throws CommandLineFormatException 
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	protected void initDefaultInput() throws CommandLineFormatException, NullCoordinateException
	{
		//Construct noise map
		UnitModel noise = new RandTrajUnitModel(command.get(CNFTCommandLine.NOISE_DT),space2d,
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
		Map mNoise = new Map("Noise",noise);
		mNoise.constructMemory(); //otherwise the noise is changed at each computation step
		//Construct the input as a sum of theses params
		UnitModel sum = new Sum(command.get(CNFTCommandLine.INPUT_DT),space2d, mNoise);
		this.input = new Map(INPUT,sum);
		modifyModel();
	}
    
    /**
     * initialize the weight lateral focus map
     * @throws CommandLineFormatException 
     */
    private void initWLFF() throws CommandLineFormatException{
        wlff = (AbstractMap) getLateralWeights(WEIGHT_LAT_FOCUS_FOCUS, command.get(CNFTCommandLine.DT), extendedSpace, pA_lat, pa_lat, pB_lat, pb_lat);
        
    }
    
    /**
     * initialize the weight afferante focus-input map
     * @throws CommandLineFormatException 
     */
    private void initWAFI() throws CommandLineFormatException{
        wafi = (AbstractMap) getAfferantWeights(WEIGHT_AFF_FOCUS_INPUT, command.get(CNFTCommandLine.DT), extendedSpace, pA_aff, pa_aff);
    }
    
    public Parameter getAfferantWeights(String name,Var dt,Space extendedSpace,
			Parameter ia,Parameter wa) throws CommandLineFormatException {
        //pas sur que ce soit guassian ND vu que c'est aff et non lat , mais je pense que c'est la meme, 
                    //c'est la convulution avec une map différente qui fera le changement
        //System.out.println("ia : "+ia.get());
        //System.out.println("wa : "+wa.get());
         UnitModel a = new GaussianND(dt, extendedSpace, ia, wa, new Var(0),new Var(0));
        Map cnfta = new Map(name + "_A",a);
        cnfta.constructMemory();

        cnfta.toStatic();
      //  System.out.println("cnfta : "+cnfta.display2D());
        return cnfta;
    }

   
    /**
	 * Generate a centered lateral weight map
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
	public Parameter getLateralWeights(String name,Var dt,Space extendedSpace,
			Parameter ia,Parameter wa,Parameter ib,Parameter wb) throws CommandLineFormatException 
			{

		UnitModel a = new GaussianND(dt, extendedSpace, ia, wa, new Var(0),new Var(0));
		Map cnfta = new Map(name + "_A",a);
		//cnfta.constructMemory();

		UnitModel b = new GaussianND(dt, extendedSpace, ib, wb, new Var(0),new Var(0));
		Map cnftb = new Map(name + "_B",b);
		//cnftb.constructMemory();

		Map sum = new Map(name,new Sum(),dt,extendedSpace,cnfta,cnftb);
		sum.toStatic();

		return sum;
			}
        
    /**
	 * Initialize the statistics
	 * @throws CommandLineFormatException 
	 */
	protected void initializeStatistics() throws CommandLineFormatException 
	{

		Stat stat = new Stat(command.get(CNFTCommandLine.DT),noDimSpace,this);
		stats = new Statistics("Stats",command.get(CNFTCommandLine.DT), 
				noDimSpace,stat.getDefaultStatistics(new Leaf(focus), trackable));

	}

	protected  void initializeCharacteristics() throws CommandLineFormatException
	{
		Charac conv = new CharacConvergence(Characteristics.CONVERGENCE,stats, noDimSpace, this);
		Charac meanError = new CharacMeanError(Characteristics.MEAN_ERROR,stats, noDimSpace, this,conv);
		Charac obstinacy = new CharacObstinacy(Characteristics.OBSTINACY,stats, noDimSpace, this, conv);
		Charac noFocus = new CharacNoFocus(Characteristics.NO_FOCUS, stats, noDimSpace, this, conv);
		Charac maxSum = new CharacMaxSum(Characteristics.MAX_SUM, stats, noDimSpace, this, conv);
		Charac meanCompTime = new CharacMeanCompTime(Characteristics.MEAN_COMP_TIME, stats, noDimSpace, this, conv);
		Charac accError = new CharacAccError(Characteristics.ACC_ERROR,stats,noDimSpace,this,conv);

		charac = new Characteristics(noDimSpace, stats, conv,meanError,obstinacy,noFocus,maxSum,meanCompTime,accError);

	}


	/**
	 * Change the model environement if needed
	 * Every scenario update which are not automaticaly computed by
	 * the model params architecture should be implemented here :
	 * exemple : create more distracter params
	 * @throws CommandLineFormatException 
	 * @throws NullCoordinateException 
	 */
	public void modifyModel() throws CommandLineFormatException, NullCoordinateException {
		boolean changed = this.changeNbDistr() || this.changeNbTrack();
		if(changed){
			for(Suscriber s : suscribers)
				s.signalTreeChanged();
		}

	}

	/**Previous Nb distractor (do not use outside changeNbDistr())**/
	private int nbDistrPrec = 0; 

	/**
	 * Change the number of distractors according to the command line parameters
	 * @return true if the model tree changed
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException
	 */
	protected boolean changeNbDistr() throws CommandLineFormatException, NullCoordinateException{

		int nbDistr = (int) command.get(CNFTCommandLine.NB_DISTRACTERS).get() ;
		boolean changed = true;
		nbDistr = nbDistr >= 0 ? nbDistr : 0;
		if(nbDistr > nbDistrPrec)
		{
			for(int i = nbDistrPrec ; i < nbDistr   ; i ++){
				Parameter distr = constructDistracter(DISTR+"_"+i);
				addParameter(distr, input);
			}

		}else if(nbDistr < nbDistrPrec){

			for(int i = nbDistrPrec-1 ; i >= nbDistr ; i --)
			{	
				Parameter p = removeParameter(DISTR+"_"+i);
				p = null;
			}

		}else{
			changed = false;
		}
		nbDistrPrec = nbDistr;
		return changed;
	}

	/**Previous Nb distractor (do not use outside changeNbDistr())**/
	private int nbTrackPrec = 0; 

	/**
	 * Change the number of track according to the command line parameters
	 * @return true if the model tree changed
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException
	 */
	protected boolean changeNbTrack() throws CommandLineFormatException, NullCoordinateException{
		int nbTrack = (int) command.get(CNFTCommandLine.NB_TRACKS).get() ;
		boolean changed = true;
		nbTrack = nbTrack >= 0 ? nbTrack : 0;
		if(nbTrack > nbTrackPrec)
		{
			for(int i = nbTrackPrec ; i < nbTrack   ; i ++){
				Parameter track = constructTrack(TRACK+"_"+i,i,nbTrack);
				addParameter(track, input);
			}

		}else if(nbTrack < nbTrackPrec){

			for(int i = nbTrackPrec-1 ; i >= nbTrack ; i --)
			{	
				Parameter p = removeParameter(TRACK+"_"+i);
				trackable.remove(p);
				p = null;
			}

		}else{
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
	public  String save(String file,List<Parameter> toSave) throws IOException, NullCoordinateException
	{

		FileWriter fw = null;

		for(Parameter p : toSave)
		{
			fw= new FileWriter(file+"_"+p.getName()+".csv",false);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(((AbstractMap)p).displayMemory());
			out.close();
		}
		return null;
	}
        
        
	/**
	 * Construct one distrcater map
	 * @param name
	 * @return
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	protected AbstractMap constructDistracter(String name) throws NullCoordinateException, CommandLineFormatException{
		//Construct distracters map
		Map cx2 = new Map("CenterX",new RandTrajUnitModel(command.get(CNFTCommandLine.DT),noDimSpace,
				new Var(0),new Var(0.5)));
		Map cy2 = new Map("CenterY",new RandTrajUnitModel(command.get(CNFTCommandLine.DT),noDimSpace,
				new Var(0),new Var(0.5)));
		cx2.constructMemory();
		cy2.constructMemory();

		UnitModel distr = new GaussianND(command.get(CNFTCommandLine.DISTR_DT),space2d,
				command.get(CNFTCommandLine.DISTR_INTENSITY), 
				command.get(CNFTCommandLine.DISTR_WIDTH), 
				cx2,cy2
				);
		Map mDistr = new Map(name,distr);
		mDistr.constructMemory(); //otherwise the position is changed at each computation step
		return mDistr;
	}
	/**
	 * TODO : not very nice addition of tracks eg. from 2 to 3. But removing and reconstructing
	 * every tracks would change the statistics and characteristics results 
	 * Construct a trck with specific name
	 * @param name
	 * @param nbTrack 
	 * @param num 
	 * @return
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	protected AbstractMap constructTrack(String name, int num, int nbTrack) throws NullCoordinateException, CommandLineFormatException{
		Map cx = new Map("CenterX_"+num,new CosTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,
				new Var("center",0),new Var("radius",0.3),new Var("period",36),new Var("phase",num/(double)nbTrack+0)){
//			@Override
//			public double compute() throws NullCoordinateException {
//				double ret =  params.get(CENTER).get()+params.get(RADIUS).get()*
//						cos(2*PI*(time.get()/params.get(PERIOD).get()-params.get(PHASE).get()));
//				System.out.println(time.get() + "==>" + ret+ "@"+time.hashCode());
//				return ret;
//			}
		}){
//			public void update(double timeLimit) throws NullCoordinateException{
//				super.update(timeLimit);
//				System.out.println(name + ": time : " + time.get() + "@"+time.hashCode());
//			}
		};
		Map cy = new Map("CenterY_"+num,new CosTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,
				new Var("center",0),new Var("radius",0.3),new Var("period",36),new Var("phase",num/(double)nbTrack + 0.25)));


		UnitModel track = new GaussianND(command.get(CNFTCommandLine.TRACK_DT),space2d,
				command.get(CNFTCommandLine.TRACK_INTENSITY), 
				command.get(CNFTCommandLine.TRACK_WIDTH), 
				cx,cy);
		AbstractMap ret =  new Map(name,track);
		ret.constructMemory();//otherwise the position is changed at each computation step
		trackable.add((Map) ret);
		return ret;
	}

   
    
                       
                      
    
    
}
