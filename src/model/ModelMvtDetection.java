package model;

import java.util.Arrays;
import java.util.List;

import com.sun.org.apache.xml.internal.security.encryption.AgreementMethod;

import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import maps.Var;
import statistics.StatCNFT;
import statistics.StatMapCNFT;
import statistics.StatisticsCNFT;
import unitModel.AggregationUM;
import unitModel.GaussianND;
import unitModel.LineTraj;
import unitModel.RandTrajUnitModel;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;
import gui.Suscriber;
/**
 * 
 * @author bchappet
 *
 */
public class ModelMvtDetection extends ModelGSpikeFFT {

	protected static final String FILTER = "Filter";
	protected static final String INPUTXFILTER= "InputXFilter";
	protected static final String AGGREGATION = "Aggregation";
	
	protected AbstractMap filter;
	protected AbstractMap inputXfilter;
	protected AbstractMap aggregation;
	
	public ModelMvtDetection(String name) {
		super(name);
	}
	
	protected Map getFilter(Var dt,Space space,Parameter ie, Parameter we,Parameter cxe,Parameter cye,
			Parameter ii, Parameter wi,Parameter cxi,Parameter cyi){
		
		UnitModel excUM = new GaussianND(dt, space, ie, we, cxe,cye);
		UnitModel inhUM = new GaussianND(dt, space, ii, wi, cxi,cyi);
		
		Map ret = new Map(FILTER,new Sum(dt, space, new Map("excUM",excUM),new Map("inhUM",inhUM)));
		return ret;
		
	}
	
	


	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		
		Var vdt = command.get(CNFTCommandLine.DT);


		Var ie = command.get(CNFTCommandLine.FILTRE_IE);
		Var we = command.get(CNFTCommandLine.FILTRE_WE);
		Var cxe = command.get(CNFTCommandLine.FILTRE_CXE);
		Var cye = command.get(CNFTCommandLine.FILTRE_CYE);
		
		Var ii = command.get(CNFTCommandLine.FILTRE_II);
		Var wi = command.get(CNFTCommandLine.FILTRE_WI);
		Var cxi = command.get(CNFTCommandLine.FILTRE_CXI);
		Var cyi = command.get(CNFTCommandLine.FILTRE_CYI);
		
		addParameters(ie,we,cxe,cye,ii,wi,cxi,cyi);
		
		filter = getFilter(vdt,space2d,ie,we,cxe,cye,ii,wi,cxi,cyi);
		
		inputXfilter = new FFTConvolutionMatrix2D(INPUTXFILTER,vdt,space2d);
		inputXfilter.addParameters(filter,input);
		
		aggregation = new Map(AGGREGATION,new AggregationUM(vdt,noDimSpace,inputXfilter));
		


		potential = new Map(POTENTIAL,new SpikingPotentialUM(),vdt,noDimSpace);


		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),vdt,noDimSpace);
		focus = new Map(FOCUS,new SpikingUM(),vdt,noDimSpace);

		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		cnft = new Var(CNFT,0);
		potential.addParameters(resetedPotential, pTau,aggregation,cnft,ph,pth,new Var("High",0));
		resetedPotential.addParameters(new Leaf(potential),pth,new Leaf(potential),new Var("High",0));
		focus.addParameters( new Leaf(potential), pth, new Var("Low",0),new Var("High",1));
		potential.constructMemory();
		this.root = potential;
	}
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret ={input,filter,inputXfilter,aggregation,potential};
		return Arrays.asList(ret);
	}

	protected void initDefaultInput() throws CommandLineFormatException, NullCoordinateException
	{
		Var tck_dt = command.get(CNFTCommandLine.TRACK_DT);
		Var tck_speed = command.get(CNFTCommandLine.TCK_SPEED);
		
		Var angle = command.get(CNFTCommandLine.ANGLE);
		addParameters(angle,tck_speed);
		
		Parameter stepX = new  TrajectoryUnitMap("StepX",tck_dt,noDimSpace,angle,tck_speed) {
			@Override
			public double computeTrajectory(double... param) {
				return Math.cos(Math.toRadians(param[0])) * param[1];
			}
		};
		Parameter stepY = new  TrajectoryUnitMap("StepY",tck_dt,noDimSpace,angle,tck_speed) {
			@Override
			public double computeTrajectory(double... param) {
				return Math.sin(Math.toRadians(param[0])) * param[1];
			}
		};
		
		

		Map cx = new Map("CenterX_"+0,new LineTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,stepX));

		Map cy = new Map("CenterY_"+0,new LineTraj(command.get(CNFTCommandLine.TRACK_DT),noDimSpace,stepY));


		UnitModel track = new GaussianND(command.get(CNFTCommandLine.TRACK_DT),extendedFramedSpace,
				command.get(CNFTCommandLine.TRACK_INTENSITY), 
				command.get(CNFTCommandLine.TRACK_WIDTH), 
				cx,cy);


		AbstractMap tck =  new Map(name,track);
		tck.constructMemory();//otherwise the position is changed at each computation step
		trackable.add((Map) tck);




		//Construct noise map
		UnitModel noise = new RandTrajUnitModel(command.get(CNFTCommandLine.NOISE_DT),extendedFramedSpace,
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
		Map mNoise = new Map("Noise",noise);
		mNoise.constructMemory(); //otherwise the noise is changed at each computation step
		//Construct the input as a sum of theses params
		UnitModel sum = new Sum(command.get(CNFTCommandLine.INPUT_DT),extendedFramedSpace, mNoise,tck);
		this.input = new Map(INPUT,sum);


	}
	
	public void modifyModel() throws CommandLineFormatException, NullCoordinateException {
		boolean changed = this.changeNbDistr() ;
		if(changed){
			for(Suscriber s : suscribers)
				s.signalTreeChanged();
		}

	}
	
	/**
	 * Here we are intrested in focus map and not the potential map
	 * @throws CommandLineFormatException 
	 */
	protected void initializeStatistics() throws CommandLineFormatException 
	{

		
		stats = new StatisticsCNFT("Stats",command.get(CNFTCommandLine.STAT_DT), 
				noDimSpace);
				
				

	}

}
