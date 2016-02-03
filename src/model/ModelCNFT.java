package model;

import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;
import gui.Suscriber;
import maps.*;
import statistics.*;
import unitModel.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Standard CNFT equation model
 * @author bchappet
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



	protected Parameter cnft;
	protected AbstractMap cnftW;
	protected AbstractMap input;
	protected AbstractMap potential;



	/**Updatable accessed by the model**/
	protected Parameter pa;
	protected Parameter pb;
	protected Parameter pTau;
	protected Parameter hpA;
	protected Parameter hpB;

	protected List<AbstractMap> trackable;

	/**The two type of refSpace used in the unitModel**/
	protected Space refSpace;
	protected Space space2d;//2 dim refSpace
	protected Space noDimSpace;//"no dim" refSpace : only one value
	protected Space extendedConvSpace; //For the lateral weight (if no wrap : res*=2) (convolution kernel)
	protected Space extendedComputationSpace;//Frame of computation if no wrap
	protected Space extendedFramedSpace; //For no wrap => no computation outside the frame




	public ModelCNFT(String name) {
		super(name);
		this.trackable = new LinkedList<AbstractMap>();
	}
	
	protected void initializeCommandLine(String contextScript) throws CommandLineFormatException {
		command = new CNFTCommandLine(contextScript, this);
		this.refSpace = new DefaultRoundedSpace(
				command.get(CNFTCommandLine.RESOLUTION), 2,
				command.getBool(CNFTCommandLine.WRAP));
		
	}

	@Override
	protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException {

		/*Here we initialize some space which will be used later**/
		noDimSpace = refSpace.clone(); //Space with "0" dimension for single value map
		noDimSpace.setDimension(new int[]{0,0});

		space2d = refSpace.clone();//Standard 2D space for the 2D map 
		space2d.setDimension(new int[]{1,1});


		double extensionConvolution = 1; //extension for the weight map if we don't wrap
		double extensionComputation = 1; //More computation space if we wrap
		if(!refSpace.isWrap()){
			extensionConvolution = 2; //we need a kernel of 2 time the map
			extensionComputation = 1.2; //enough to diminish border effects

		}


		//If no wrap we may want to compute outside the borders
		//-0.6,0.6 (instead of -0.5,0.5) space
		extendedComputationSpace = space2d.extend(extensionComputation,false);
		//System.out.println(extendedComputationSpace.getResolution());
		//Extended space is a -1.2,1.2 (instead of -0.5,0.5) space
		// used for lateral weights map in case of non wrapped convolution
		extendedConvSpace = extendedComputationSpace.extend(extensionConvolution,false);
		//System.out.println(extendedConvSpace.getResolution());

		//If no wrap, we are using this space to have value outside the frame
		//but we do not compute outside the frame
		//the frame state that we don't want to compute outside the frame, for the input for instance
		extendedFramedSpace = space2d.extend(extensionComputation,true);


		//Displayed parameter
		addParameters(command.get(CNFTCommandLine.DT));
		addParameters(command.get(CNFTCommandLine.NB_TRACKS));
		addParameters(command.get(CNFTCommandLine.TRACK_DT));
		addParameters(command.get(CNFTCommandLine.NB_DISTRACTERS));
		addParameters(command.get(CNFTCommandLine.DISTR_DT));
		addParameters(command.get(CNFTCommandLine.NOISE_AMP));
		addParameters(command.get(CNFTCommandLine.NOISE_DT));
		//addParamaters(inputs.getDefaultDisplayedParameters(RandomTestAbstractMap.class,command));


		initDefaultInput();
		initLateralWeightParams(extendedConvSpace);
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
	 * Initialize somme Parameter used in lateral weights
	 * the objective here is to normalize some parameters
	 * Warning : use "this.space.getSimulationSpace().getResolution()" to acces the
	 * 	original resolution of the model and not the extended space resolution
	 * @throws CommandLineFormatException
	 * @throws CloneNotSupportedException
	 * @throws NullCoordinateException 
	 */
	protected void initLateralWeightParams(final Space extendedSpace) 
			throws CommandLineFormatException
			{

		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);

		Parameter pA = command.get(CNFTCommandLine.IA);
		hpA = new TrajectoryUnitMap("A_hidden",command.get(CNFTCommandLine.DT),
				extendedSpace,pA,alphaP) {

			@Override
			public double computeTrajectory(double... param) {
				//System.out.println("Res : " + extendedSpace.getSimulationSpace().getResolution());

				double ret = param[0] / 
						(extendedSpace.getSimulationSpace().getResolution()*
								extendedSpace.getSimulationSpace().getResolution()) *
								(40*40)/(param[1]);
				//System.out.println("Param : " + ret);
				return ret;
			}
		}; 
		hpA.toStatic();


		Parameter pB = command.get(CNFTCommandLine.IB);
		hpB = new TrajectoryUnitMap("B_hidden",command.get(CNFTCommandLine.DT),
				extendedSpace,pB,alphaP) {

			@Override
			public double computeTrajectory(double... param)  {
				return param[0] / 
						(extendedSpace.getSimulationSpace().getResolution()*
								extendedSpace.getSimulationSpace().getResolution()) *
								(40*40)/param[1];
			}
		}; 
		hpB.toStatic();

		pa = command.get(CNFTCommandLine.WA);
		pb = command.get(CNFTCommandLine.WB);
		pTau = command.get(CNFTCommandLine.TAU);

		addParameters(pa,pb,pA,pB,pTau,alphaP);


		//TODO will be usefull for optimization
		//		optimizedMaps.add(pA);
		//		optimizedMaps.add(pB);
		//		optimizedMaps.add(pa);
		//		optimizedMaps.add(pb);
		//		optimizedMaps.add(pTau);
		//		optimizedMaps.add(alphaP);
			}







	/**
	 * Construct the model architecture
	 * @throws CommandLineFormatException if the given parameter was not initialized in
	 * the default script or model script
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT); //default dt
		initLateralWeights();
		cnft = new ConvolutionMatrix2D(CNFT,vdt,extendedComputationSpace);
		potential = new Map(POTENTIAL,new RateCodedUnitModel(),vdt,extendedComputationSpace);

		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				input,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));
		cnft.addParameters(cnftW,new Leaf(potential));
		cnft.constructMemory();
		potential.constructMemory();
		this.root = potential;
	}

	protected void initLateralWeights() throws NullCoordinateException, CommandLineFormatException {
		this.cnftW = (AbstractMap) getLateralWeights(CNFTW, command.get(CNFTCommandLine.DT), extendedConvSpace, hpA, pa, hpB, pb);
	}
	/**
	 * init the default input 
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	protected void initDefaultInput() throws CommandLineFormatException, NullCoordinateException
	{

		//Construct noise map
		UnitModel noise = new RandTrajUnitModel(command.get(CNFTCommandLine.NOISE_DT),extendedFramedSpace,
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
		Map mNoise = new Map("Noise",noise);
		mNoise.constructMemory(); //otherwise the noise is changed at each computation step
		//Construct the input as a sum of theses params
		UnitModel sum = new Sum(command.get(CNFTCommandLine.INPUT_DT),extendedFramedSpace, mNoise);
		this.input = new Map(INPUT,sum);
		this.input.constructMemory();
		modifyModel();

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

		UnitModel distr = new GaussianND(command.get(CNFTCommandLine.DISTR_DT),extendedFramedSpace,
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
				
		Map cy = new Map("CenterY_"+num,new CosTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,
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
	 * Initialize the statistics
	 * @throws CommandLineFormatException 
	 */
	protected void initializeStatistics() throws CommandLineFormatException 
	{

		Var stat_dt = command.get(CNFTCommandLine.STAT_DT);
		
		Stat stat = new Stat(stat_dt,this);

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

		charac = new CharacteristicsCNFT(noDimSpace, stats, conv,meanError,obstinacy,noFocus,maxSum,meanCompTime,accError,maxMax,testConv);

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
		return "Basic CNFT model : potential =  potential + dt/tau*(-potential + input +cnft + h  )";
	}

	

//	public void test() throws Exception{
//		// change the input
//		Map map = new RandomTestAbstractMap(INPUT,
//				command.get(CNFTCommandLine.NOISE_DT),space2d,
//				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
//
//		for(AbstractMap p : input.getParents()){
//			p.replaceParameter(map);
//		}
//
//		removeParameters(command.get(CNFTCommandLine.NB_TRACKS),
//				command.get(CNFTCommandLine.TRACK_DT),
//				command.get(CNFTCommandLine.NB_DISTRACTERS),
//				command.get(CNFTCommandLine.DISTR_DT),
//				command.get(CNFTCommandLine.NOISE_AMP),
//				command.get(CNFTCommandLine.NOISE_DT));
//
//
//		addParameters(command.get(CNFTCommandLine.NOISE_AMP));
//		addParameters(command.get(CNFTCommandLine.NOISE_DT));
//
//		this.input = map;
//	}
	
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
	 * @param hashcode
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
