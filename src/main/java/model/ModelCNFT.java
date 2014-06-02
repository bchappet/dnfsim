package main.java.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.gui.Suscriber;
import main.java.maps.AbstractMap;
import main.java.maps.ConvolutionMatrix2D;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.DoubleSpace2D;
import main.java.space.Space;
import main.java.space.Space2D;
import main.java.statistics.Charac;
import main.java.statistics.CharacAccError;
import main.java.statistics.CharacClosestTrack;
import main.java.statistics.CharacConvergence;
import main.java.statistics.CharacMaxMax;
import main.java.statistics.CharacMaxSum;
import main.java.statistics.CharacMeanCompTime;
import main.java.statistics.CharacMeanError;
import main.java.statistics.CharacNoFocus;
import main.java.statistics.CharacObstinacy;
import main.java.statistics.CharacTestConvergence;
import main.java.statistics.CharacteristicsCNFT;
import main.java.statistics.StatCNFT;
import main.java.statistics.StatMapCNFT;
import main.java.statistics.StatisticsCNFT;
import main.java.unitModel.ComputeUM;
import main.java.unitModel.CosTraj;
import main.java.unitModel.GaussianND;
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.RateCodedUnitModel;
import main.java.unitModel.Sum;
import main.java.unitModel.UnitModel;
import draft.RandomTestAbstractMap;


/**
 * Standard CNFT equation main.java.model
 * @author bchappet
 * @version 12/05/2014
 *
 */
public class ModelCNFT extends Model{





	public static final String INPUT = "Inputs";
	public static final String CNFTW = "CNFT_Weights";
	public static final String CNFT = "CNFT";
	public static final String POTENTIAL = "Potential";
	/**Distracters name  DISTR_i **/
	protected static final String DISTR = "distr";
	/**Tracks name  TRACK_i **/
	public static final String TRACK = "track";



	protected Map cnft;
	protected Map cnftW;
	protected Map input;
	protected Map potential;



	/**Updatable accessed by the main.java.model**/
	protected Parameter pa;
	protected Parameter pb;
	protected Parameter pTau;
	protected Parameter hpA;
	protected Parameter hpB;
	
	private DoubleSpace2D space;
	private Var<Integer> res;

	protected List<AbstractMap> trackable;





	public ModelCNFT(String name) {
		super(name);
		this.trackable = new LinkedList<AbstractMap>();
	}
	
	public CommandLine constructCommandLine() throws CommandLineFormatException{
		return new CNFTCommandLine();
	}


	@Override
	protected void initializeParameters() throws CommandLineFormatException{

		/*Here we initialize some main.java.space which will be used later**/
//		noDimSpace = refSpace.clone(); //Space with "0" dimension for single value map
//		noDimSpace.setDimension(new int[]{0,0});
//
//		space2d = refSpace.clone();//Standard 2D main.java.space for the 2D map 
//		space2d.setDimension(new int[]{1,1});
//
//
//		double extensionConvolution = 1; //extension for the weight map if we don't wrap
//		double extensionComputation = 1; //More computation main.java.space if we wrap
//		if(!refSpace.isWrap()){
//			extensionConvolution = 2; //we need a kernel of 2 time the map
//			extensionComputation = 1.2; //enough to diminish border effects
//
//		}
//
//
//		//If no wrap we may want to compute outside the borders
//		//-0.6,0.6 (instead of -0.5,0.5) main.java.space
//		extendedComputationSpace = space2d.extend(extensionComputation,false);
//		//System.out.println(extendedComputationSpace.getResolution());
//		//Extended main.java.space is a -1.2,1.2 (instead of -0.5,0.5) main.java.space
//		// used for lateral weights map in case of non wrapped convolution
//		extendedConvSpace = extendedComputationSpace.extend(extensionConvolution,false);
//		//System.out.println(extendedConvSpace.getResolution());
//
//		//If no wrap, we are using this main.java.space to have value outside the frame
//		//but we do not compute outside the frame
//		//the frame state that we don't want to compute outside the frame, for the main.java.input for instance
//		extendedFramedSpace = space2d.extend(extensionComputation,true);

		
		res = command.get(DMADSomCommandLine.RESOLUTION);
		space = new DoubleSpace2D(new Var<Double>("OriX",-0.5),new Var<Double>("OriY",-0.5),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),res);

		//Displayed parameter
		addParameters(command.get(CNFTCommandLine.DT));
		addParameters(command.get(CNFTCommandLine.NB_TRACKS));
		addParameters(command.get(CNFTCommandLine.TRACK_DT));
		addParameters(command.get(CNFTCommandLine.NB_DISTRACTERS));
		addParameters(command.get(CNFTCommandLine.DISTR_DT));
		addParameters(command.get(CNFTCommandLine.NOISE_AMP));
		addParameters(command.get(CNFTCommandLine.NOISE_DT));
		//addParamaters(inputs.getDefaultDisplayedParameters(RandomTestAbstractMap.class,initScript));


		initDefaultInput();
		initLateralWeightParams();
		initModel();
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
	public Map getLateralWeights(String name,Var<BigDecimal> dt,Space space,
			Parameter ia,Parameter wa,Parameter ib,Parameter wb) throws CommandLineFormatException 
			{
		Map cnfta = new UnitMap(name + "_A",dt, space,new GaussianND(0d), ia, wa, new Var(0),new Var(0));
		Map cnftb = new UnitMap(name + "_B",dt, space,new GaussianND(0d), ib, wb, new Var(0),new Var(0));
		Map sum = new UnitMap(name,new InfiniteDt(),space,new Sum(0d),cnfta,cnftb);
		return sum;
			}


	/**
	 * Initialize somme Parameter used in lateral weights
	 * the objective here is to normalize some parameters
	 * Warning : use "this.space.getSimulationSpace().getResolution()" to acces the
	 * 	original resolution of the main.java.model and not the extended main.java.space resolution
	 * @throws CommandLineFormatException
	 * @throws CloneNotSupportedException
	 * @throws NullCoordinateException 
	 */
	protected void initLateralWeightParams() 
			throws CommandLineFormatException
			{
		
		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);
		//wa = wa_/(res^2)*40^2/alpha 
		Var<String> equationWeights = new Var<String>("$1/$2**2*40**2/$3");
		hpA = new Trajectory<Double>("A_hidden",new InfiniteDt(),new ComputeUM(0d),
				equationWeights,pa,space.getResolution(),alphaP);
		hpB = new Trajectory<Double>("B_hidden",new InfiniteDt(),new ComputeUM(0d),
				equationWeights,pb,space.getResolution(),alphaP);
			}







	/**
	 * Construct the main.java.model architecture
	 * @throws CommandLineFormatException if the given parameter was not initialized in
	 * the default script or main.java.model script
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	protected void initModel() throws CommandLineFormatException
	{
		Var<BigDecimal> dt = command.get(CNFTCommandLine.DT); //default dt
		initLateralWeights();
		cnft = new ConvolutionMatrix2D(CNFT,dt,space);
		potential = new UnitMap<Double, Double>(POTENTIAL,dt,space,new RateCodedUnitModel(0.));

		potential.addParameters(potential,command.get(CNFTCommandLine.TAU),
				input,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));
		cnft.addParameters(cnftW,potential);
		this.root = potential;
	}

	protected void initLateralWeights() throws  CommandLineFormatException {
		this.cnftW =  getLateralWeights(CNFTW, command.get(CNFTCommandLine.DT), space, hpA, pa, hpB, pb);
	}
	/**
	 * init the default main.java.input 
	 * @param width : width of the tracks and distracters gaussian
	 * @param intensity : intensity of the tracks and distracters gaussian
	 * @param nbDistr : number of distracters
	 * @param noiseAmpl : amplitude of noise
	 * @throws CommandLineFormatException 
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	protected void initDefaultInput() throws CommandLineFormatException
	{
		Var<BigDecimal> dtNoise = command.get(CNFTCommandLine.NOISE_DT);
		Var<BigDecimal> dtInput = command.get(CNFTCommandLine.INPUT_DT);

		//Construct noise map
		Map mNoise = new UnitMap("Noise",dtNoise,space,new RandTrajUnitModel(0.),
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
		//Construct the input as a sum of theses params
		this.input = new UnitMap(INPUT,dtInput,space,new Sum(0.),mNoise);
	}

	/**
	 * Construct one distrcater map
	 * @param name
	 * @return
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	protected Map constructDistracter(String name) throws NullCoordinateException, CommandLineFormatException{
		//Construct distracters map
		Map cx2 = new UnitMap("CenterX",command.get(CNFTCommandLine.DT),space,new RandTrajUnitModel(0.),
				new Var(0),new Var(0.5));
		Map cy2 = new UnitMap("CenterY",command.get(CNFTCommandLine.DT),space,new RandTrajUnitModel(0.),
				new Var(0),new Var(0.5));

		Map mDistr = new UnitMap(name,command.get(CNFTCommandLine.DISTR_DT),space,new GaussianND(0.),
				command.get(CNFTCommandLine.DISTR_INTENSITY), 
				command.get(CNFTCommandLine.DISTR_WIDTH), 
				cx2,cy2);
		return mDistr;
	}
	/**
	 * TODO : not very nice addition of tracks eg. from 2 to 3. But removing and reconstructing
	 * every tracks would change the main.java.statistics and characteristics results 
	 * Construct a trck with specific name
	 * @param name
	 * @param nbTrack 
	 * @param num 
	 * @return
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	protected Map constructTrack(String name, int num, int nbTrack) throws NullCoordinateException, CommandLineFormatException{
		Map cx = new UnitMap("CenterX_"+num,new CosTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,
				command.get(CNFTCommandLine.TRACK_CENTER),
				command.get(CNFTCommandLine.TRACK_RADIUS),
				command.get(CNFTCommandLine.TRACK_PERIOD),
				new Var("tck_phase",num/(double)nbTrack+0)));
		//new Var("center",0),new Var("radius",0.3),new Var("period",36),new Var("tck_phase",num/(double)nbTrack+0)){
		//			@Override
		//			public double compute() throws NullCoordinateException {
		//				double ret =  params.get(CENTER).get()+params.get(RADIUS).get()*
		//						cos(2*PI*(time.get()/params.get(PERIOD).get()-params.get(PHASE).get()));
		//				System.out.println(time.get() + "==>" + ret+ "@"+time.hashCode());
		//				return ret;
		//			}

		Map cy = new UnitMap("CenterY_"+num,new CosTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,
				command.get(CNFTCommandLine.TRACK_CENTER),
				command.get(CNFTCommandLine.TRACK_RADIUS),
				command.get(CNFTCommandLine.TRACK_PERIOD),
				new Var("tck_phase",num/(double)nbTrack + 0.25)));
		//new Var("center",0),new Var("radius",0.3),new Var("period",36),new Var("tck_phase",num/(double)nbTrack + 0.25)));


		UnitModel track = new GaussianND(command.get(CNFTCommandLine.TRACK_DT),extendedFramedSpace,
				command.get(CNFTCommandLine.TRACK_INTENSITY), 
				command.get(CNFTCommandLine.TRACK_WIDTH), 
				cx,cy);
		AbstractMap ret =  new Map(name,track);
		//{
		//			public void update(BigDecimal timeLimit) throws NullCoordinateException{
		//				System.out.println("Update Input === : " +  name + ": time : " + time.get() + "@"+time.hashCode() + Arrays.toString(Thread.currentThread().getStackTrace()));
		//				//System.out.println(this.display2D());
		//				super.update(timeLimit);
		//				
		//			}
		//		};
		ret.constructMemory();//otherwise the position is changed at each computation step
		trackable.add((Map) ret);
		return ret;
	}


	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {input,cnftW,cnft,potential};
		return Arrays.asList(ret);
	}



	/**
	 * Initialize the main.java.statistics
	 * @throws CommandLineFormatException 
	 */
	protected void initializeStatistics() throws CommandLineFormatException 
	{

		Var stat_dt = command.get(CNFTCommandLine.STAT_DT);

		StatCNFT stat = new StatCNFT(stat_dt,this);

		List<StatMapCNFT> statMaps = stat.getDefaultStatistics(new Leaf(potential), trackable);
		statMaps.add(stat.getTestConvergence(new Leaf(potential)));
		statMaps.add(stat.getLyapunov(new Leaf(potential), new Leaf(cnft), new Leaf(input)));
		statMaps.add(stat.getMax(new Leaf(potential)));
		StatMapCNFT[] array = statMaps.toArray(new StatMapCNFT[]{});
		stats = new StatisticsCNFT("Stats",stat_dt,noDimSpace,array);

	}

	protected  void initializeCharacteristics() throws CommandLineFormatException
	{
		Charac conv = new CharacConvergence(CharacteristicsCNFT.CONVERGENCE,stats, noDimSpace, this);
		Charac meanError = new CharacMeanError(CharacteristicsCNFT.MEAN_ERROR,stats, noDimSpace, this,conv);
		Charac obstinacy = new CharacObstinacy(CharacteristicsCNFT.OBSTINACY,stats, noDimSpace, this, conv);
		Charac noFocus = new CharacNoFocus(CharacteristicsCNFT.NO_FOCUS, stats, noDimSpace, this, conv);
		Charac maxSum = new CharacMaxSum(CharacteristicsCNFT.MAX_SUM, stats, noDimSpace, this);
		Charac meanCompTime = new CharacMeanCompTime(CharacteristicsCNFT.MEAN_COMP_TIME, stats, noDimSpace, this, conv);
		Charac accError = new CharacAccError(CharacteristicsCNFT.ACC_ERROR,stats,noDimSpace,this,conv,command.get(CNFTCommandLine.STAB_TIME));
		Charac maxMax = new CharacMaxMax(CharacteristicsCNFT.MAX_MAX,stats,noDimSpace,this);
		Charac testConv = new CharacTestConvergence(CharacteristicsCNFT.TEST_CONV, stats, noDimSpace, this,
				command.get(CNFTCommandLine.WA),command.get(CNFTCommandLine.SHAPE_FACTOR),command.get(CNFTCommandLine.STAB_TIME));
		Charac closestTrack = new CharacClosestTrack(CharacteristicsCNFT.CLOSEST_TRACK, stats, noDimSpace, this,conv);

		charac = new CharacteristicsCNFT(noDimSpace, stats, conv,meanError,obstinacy,noFocus,maxSum,meanCompTime,accError,maxMax,testConv,closestTrack);

	}


	/**
	 * Change the main.java.model environement if needed
	 * Every scenario update which are not automaticaly computed by
	 * the main.java.model params architecture should be implemented here :
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
	 * Change the number of distractors according to the initScript line parameters
	 * @return true if the main.java.model tree changed
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
	 * Change the number of track according to the initScript line parameters
	 * @return true if the main.java.model tree changed
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
		return "Basic CNFT main.java.model : potential =  potential + dt/tau*(-potential + main.java.input +cnft + h  )";
	}



	public void test() throws Exception{
		// change the main.java.input
		Map map = new RandomTestAbstractMap(INPUT,
				command.get(CNFTCommandLine.NOISE_DT),space2d,
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));

		for(AbstractMap p : input.getParents()){
			p.replaceParameter(map);
		}

		removeParameters(command.get(CNFTCommandLine.NB_TRACKS),
				command.get(CNFTCommandLine.TRACK_DT),
				command.get(CNFTCommandLine.NB_DISTRACTERS),
				command.get(CNFTCommandLine.DISTR_DT),
				command.get(CNFTCommandLine.NOISE_AMP),
				command.get(CNFTCommandLine.NOISE_DT));


		addParameters(command.get(CNFTCommandLine.NOISE_AMP));
		addParameters(command.get(CNFTCommandLine.NOISE_DT));

		this.input = map;
	}

	public Space getRefSpace() {
		return refSpace;
	}


	/**
	 * Return the list of trackable object
	 * 
	 * @return
	 */
	public List<AbstractMap> getTracks() {
		return trackable;
	}

	/**
	 * Return the trackable object with the given hashcode
	 * 
	 * @param trackedStimulis
	 * @return null if no trackable have the corresponding hashh code
	 */
	public AbstractMap getTracked(double hashcode) {
		AbstractMap ret = null;
		for (AbstractMap t : trackable) {
			if (t.hashCode() == hashcode)
				ret = t;
		}
		return ret;
	}

	@Override
	public String getDefaultDisplayedStatistic() {
		return StatisticsCNFT.ERROR_DIST;
	}













}
