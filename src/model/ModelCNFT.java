package model;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import gui.Suscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.ConvolutionMatrix2D;
import maps.Leaf;
import maps.Map;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import maps.Var;
import statistics.Charac;
import statistics.CharacAccError;
import statistics.CharacMaxMax;
import statistics.CharacMeanCompTime;
import statistics.CharacConvergence;
import statistics.CharacMeanError;
import statistics.CharacMaxSum;
import statistics.CharacNoFocus;
import statistics.CharacObstinacy;
import statistics.Characteristics;
import statistics.Stat;
import statistics.Statistics;
import unitModel.CosTraj;
import unitModel.GaussianND;
import unitModel.RandTrajUnitModel;
import unitModel.RateCodedUnitModel;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;
import draft.RandomTestAbstractMap;


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
	/**Distractors name  DISTR_i **/
	protected static final String DISTR = "distr";
	/**Tracks name  TRACK_i **/
	public static final String TRACK = "track";



	protected AbstractMap cnft;
	protected AbstractMap cnftW;
	protected AbstractMap input;
	protected AbstractMap potential;



	/**Updatable accessed by the model**/
	protected Parameter pa;
	protected Parameter pb;
	protected Parameter pTau;
	protected Parameter hpA;
	protected Parameter hpB;



	/**The two type of refSpace used in the unitModel**/
	protected Space space2d;//2 dim refSpace
	protected Space noDimSpace;//"no dim" refSpace : only one value
	protected Space extendedSpace; //For the lateral weight (if no wrap res*=2)




	public ModelCNFT(String name) {
		super(name);
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
		//addParamaters(inputs.getDefaultDisplayedParameters(RandomTestAbstractMap.class,command));


		initDefaultInput();
		initLateralWeightParams(extendedSpace);
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

		cnft = new ConvolutionMatrix2D(CNFT,vdt,space2d);

		potential = new Map(POTENTIAL,new RateCodedUnitModel(),vdt,space2d);


		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				input,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));

		cnft.addParameters(cnftW,new Leaf(potential));
		cnft.constructMemory();

		this.root = potential;
		
		



	}

	protected void initLateralWeights() throws NullCoordinateException, CommandLineFormatException {
		this.cnftW = (AbstractMap) getLateralWeights(CNFTW, command.get(CNFTCommandLine.DT), extendedSpace, hpA, pa, hpB, pb);
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

		Stat stat = new Stat(command.get(CNFTCommandLine.DT),noDimSpace,this);
		stats = new Statistics("Stats",command.get(CNFTCommandLine.DT), 
				noDimSpace,stat.getDefaultStatistics(new Leaf(potential), trackable));

	}

	protected  void initializeCharacteristics() throws CommandLineFormatException
	{
		Charac conv = new CharacConvergence(Characteristics.CONVERGENCE,stats, noDimSpace, this);
		Charac meanError = new CharacMeanError(Characteristics.MEAN_ERROR,stats, noDimSpace, this,conv);
		Charac obstinacy = new CharacObstinacy(Characteristics.OBSTINACY,stats, noDimSpace, this, conv);
		Charac noFocus = new CharacNoFocus(Characteristics.NO_FOCUS, stats, noDimSpace, this, conv);
		Charac maxSum = new CharacMaxSum(Characteristics.MAX_SUM, stats, noDimSpace, this, conv);
		Charac meanCompTime = new CharacMeanCompTime(Characteristics.MEAN_COMP_TIME, stats, noDimSpace, this, conv);
		Charac accError = new CharacAccError(Characteristics.ACC_ERROR,stats,noDimSpace,this,conv,command.get(CNFTCommandLine.STABIT));
		Charac maxMax = new CharacMaxMax(Characteristics.MAX_MAX,stats,noDimSpace,this,conv);

		charac = new Characteristics(noDimSpace, stats, conv,meanError,obstinacy,noFocus,maxSum,meanCompTime,accError,maxMax);

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

	@Override
	public  String save(String file,List<Parameter> toSave) throws IOException, NullCoordinateException
	{

		FileWriter fw = null;
		String ret = "[";

		for(Parameter p : toSave)
		{
			String fileName = file+"_"+p.getName()+".csv";
			ret += fileName + ",";
			fw= new FileWriter(file+"_"+p.getName()+".csv",false);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(((AbstractMap)p).displayMemory());
			out.close();
		}
		return ret.subSequence(0, ret.length()-1)+"]";
	}
	
	public void test() throws Exception{
		// change the input
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














}
