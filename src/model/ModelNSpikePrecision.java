package model;

import java.util.Arrays;
import java.util.List;

import precision.NSpikeUMPrecision;
import precision.PrecisionVar;
import precision.SpikingPotentialUMPrecision;
import precision.SpikingUMPrecision;

import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.V4Neighborhood2D;
import routing.Assymetric2DRouting;
import statistics.PreciseStat;
import statistics.Statistics;
import unitModel.CosTraj;
import unitModel.GaussianND;
import unitModel.RandTrajUnitModel;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;


/**
 * RSDNF validation model : spike are truly transmitted with a given probability
 * 
 * @author bchappet
 * @deprecated
 */
public class ModelNSpikePrecision extends ModelNSpike{

	protected Parameter pTh; //threshold
	protected Parameter ptck_i;//track intensity
	

	protected NeighborhoodMap cnfta;
	protected NeighborhoodMap cnftb;



	public ModelNSpikePrecision(String name) {
		super(name);
		
		
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
		
		Parameter precision = command.get(CNFTCommandLine.PRECISION);
		ptck_i = new TrajectoryUnitMap("tck_i_hidden",command.get(CNFTCommandLine.DT),extendedSpace,command.get(CNFTCommandLine.TRACK_INTENSITY),precision){
			//tck_i = tck_i * 2^precision
			public double computeTrajectory(double... param) {
				return param[0] * Math.pow(2, param[1]);
			}
		};
		//Construct noise map
		UnitModel noise = new RandTrajUnitModel(command.get(CNFTCommandLine.NOISE_DT),space2d,
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
		Map mNoise = new Map("Noise",noise);
		//Construct the input as a sum of theses params
		UnitModel sum = new Sum(command.get(CNFTCommandLine.INPUT_DT),space2d, mNoise);
		this.input = new Map(INPUT,sum);

		modifyModel();

	}
	
	protected AbstractMap constructTrack(String name, int num, int nbTrack) throws NullCoordinateException, CommandLineFormatException{
		Map cx = new Map("CenterX2",new CosTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,
				new Var(0),new Var(0.3),new Var(36),new Var(num/(double)nbTrack+0)));
		Map cy = new Map("CenterY2",new CosTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,
				new Var(0),new Var(0.3),new Var(36),new Var(num/(double)nbTrack + 0.25)));

		UnitModel track = new GaussianND(command.get(CNFTCommandLine.TRACK_DT),space2d,
				ptck_i, 
				command.get(CNFTCommandLine.TRACK_WIDTH), 
				cx,cy);
		AbstractMap ret =  new Map(name,track);
		trackable.add((Map) ret);
		return ret;
	}
	
	@Override
	protected void initLateralWeightParams(final Space extendedSpace) throws CommandLineFormatException
	{

		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);

		pn = command.get(CNFTCommandLine.N);
		pTau = command.get(CNFTCommandLine.TAU);

		Parameter ppa = command.get(CNFTCommandLine.WA);
		Parameter precision = command.get(CNFTCommandLine.PRECISION);
		hppa = new TrajectoryUnitMap("pa_hidden",command.get(CNFTCommandLine.DT),extendedSpace,ppa,precision) {
			@Override
			public double computeTrajectory(double... param)   {
				double proba =Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
				return  proba*Math.pow(2, param[1]);
			}
		};

		Parameter ppb = command.get(CNFTCommandLine.WB);
		hppb = new TrajectoryUnitMap("pb_hidden",command.get(CNFTCommandLine.DT),extendedSpace,ppb,precision) {
			@Override
			public double computeTrajectory(double... param) {
				double proba =Math.pow(param[0],
						1/this.space.getSimulationSpace().getResolution());
				return  proba*Math.pow(2, param[1]);
			}
		};

		Parameter pA =  command.get(CNFTCommandLine.IA);
		hpA = new TrajectoryUnitMap("A_hidden",command.get(CNFTCommandLine.DT),extendedSpace,pA,alphaP,pn,precision,pTau) {
			//A = A /(res*res*n)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param) {
				double res =  this.space.getSimulationSpace().getResolution();
				int n = (int) param[2];
				double result =  param[0]/ 
						(res*res*n)*
						(40*40)/param[1];
				//* 2^precision * 1/tau
				return result * Math.pow(2, param[3]) * 1/param[4];
			}
		};
		Parameter pB =  command.get(CNFTCommandLine.IB);
		hpB  = new TrajectoryUnitMap("B_hidden",command.get(CNFTCommandLine.DT),extendedSpace, pB,alphaP,pn,precision,pTau) {
			//B = B /(res*res*n)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param) {
				double res =  this.space.getSimulationSpace().getResolution();
				int n = (int) param[2];
				double result =  param[0]/ 
						(res*res*n)*
						(40*40)/param[1];
				//* 2^precision * 1/tau
				return result * Math.pow(2, param[3]) * 1/param[4];
			}
		};
		
		pTh = new TrajectoryUnitMap("th_hidden",command.get(CNFTCommandLine.DT),extendedSpace,command.get(CNFTCommandLine.THRESHOLD),precision){
			//th = th * 2^precision
			public double computeTrajectory(double... param) {
				return param[0] * Math.pow(2, param[1]);
			}
		};
		
		
		

		addParameters(ppa,ppb,pA,pB,pTau,pn,alphaP,precision);
	}

	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var precision = command.get(CNFTCommandLine.PRECISION);
		Var vdt = command.get(CNFTCommandLine.DT);

		potential = new Map(POTENTIAL,new SpikingPotentialUMPrecision(precision),vdt,space2d);

		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUMPrecision(precision),
				vdt,space2d);
		focus = new Map(FOCUS,new SpikingUMPrecision(precision),vdt,space2d);

		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		//Init cnft
		initLateralWeights();

		potential.addParameters(resetedPotential, pTau,input,cnft,ph,pTh,new PrecisionVar(0,precision), precision);
		resetedPotential.addParameters(new Leaf(potential),pTh,new Leaf(potential),new PrecisionVar(0,precision), precision);
		focus.addParameters( new Leaf(potential), pTh, new PrecisionVar(0,precision),new PrecisionVar(1,precision), precision);

		this.root = potential;
	}





	

	/**
	 * 
	 * @param name
	 * @param dt
	 * @param space2D
	 * @param na
	 * @param ia
	 * @param pa
	 * @param nb
	 * @param ib
	 * @param pb
	 * @param focus
	 * @return
	 * @throws CommandLineFormatException 
	 */
	protected  Parameter getLateralWeights(String name,Var dt,Space space2D,
			Parameter na,Parameter ia,Parameter pa,Parameter nb,Parameter ib,Parameter pb,
			Parameter focus,Parameter threshold) throws CommandLineFormatException
	{
		Parameter precision = command.get(CNFTCommandLine.PRECISION); 
		 cnfta = new NeighborhoodMap(name+"_A",
				new NSpikeUMPrecision(precision,new Assymetric2DRouting())
		,dt,space2D,na,pa,ia,focus,new Var(1),precision);// not threshold but var(1) same as focus... TODO change in other spiking map

		cnfta.addNeighboors(new V4Neighborhood2D(space2D, new UnitLeaf(cnfta)));
		cnfta.constructMemory();

		 cnftb = new NeighborhoodMap(name+"_B",
				new NSpikeUMPrecision(precision,new Assymetric2DRouting())
		,dt,space2D,nb,pb,ib,focus,new Var(1),precision);

		cnftb.addNeighboors(new V4Neighborhood2D(space2D, new UnitLeaf(cnftb)));
		cnftb.constructMemory();

		Map sum = new Map(CNFT,new Sum(),dt,space2D, cnfta, cnftb);
		sum.constructMemory();
		return sum;
	}
	
	/**
	 * Initialize the statistics
	 * @throws CommandLineFormatException 
	 */
	protected void initializeStatistics() throws CommandLineFormatException 
	{
		AbstractMap[] tracks = trackable.toArray(new AbstractMap[]{trackable.get(0)});
		PreciseStat stat = new PreciseStat(command.get(CNFTCommandLine.DT),noDimSpace,this);
		stats = new Statistics("Stats",command.get(CNFTCommandLine.DT), 
				noDimSpace,stat.getDefaultStatistics(new Leaf(potential),new Leaf(cnfta),new Leaf(cnftb),tracks));

	}
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret ={input,cnfta,cnftb,cnft,potential,focus};
		return Arrays.asList(ret);
	}


	@Override
	public String getText() {
		return "NSpike with hardware like computation. The precision is the number of bit used for computation." ;
	}



	







}
