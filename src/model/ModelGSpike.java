package model;

import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.ConvolutionMatrix2D;
import maps.Leaf;
import maps.Map;
import maps.Parameter;
import maps.Var;
import statistics.Stat;
import statistics.StatMapCNFT;
import statistics.StatisticsCNFT;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

/**
 * Spiking neurons with a differential of gaussian as lateral weights
 * @author bchappet
 *
 */
public class ModelGSpike extends ModelCNFT {

	/** The map focus will contains the **/
	public static final String FOCUS = "Focus";
	protected AbstractMap focus;




	public ModelGSpike(String name)  {
		super(name);
	}


	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT);

		cnft = new ConvolutionMatrix2D(CNFT,vdt,extendedComputationSpace);

		potential = new Map(POTENTIAL,new SpikingPotentialUM(),vdt,extendedComputationSpace);

		
		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),
				vdt,extendedComputationSpace);
		focus = new Map(FOCUS,new SpikingUM(),vdt,extendedComputationSpace);
		
		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		//Init cnftW
		initLateralWeights();
		
		Var high = new Var("high",0);
		cnft.addParameters(cnftW,focus);
		potential.addParameters(resetedPotential, pTau,input,cnft,ph,pth, high);
		resetedPotential.addParameters(new Leaf(potential),pth,new Leaf(potential), high);
		focus.addParameters( new Leaf(potential), pth, new Var("Low",0),new Var("High",1));
		potential.constructMemory();

		this.root = potential;
	}

	



	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret ={input,cnftW,cnft,potential,focus};
		return Arrays.asList(ret);
	}



	/**
	 * Here we are intrested in focus map and not the potential map
	 * @throws CommandLineFormatException 
	 */
	protected void initializeStatistics() throws CommandLineFormatException 
	{

		Stat stat = new Stat(command.get(CNFTCommandLine.STAT_DT),this);
		
		List<StatMapCNFT> statMaps = stat.getDefaultStatistics(new Leaf(focus), trackable);
		statMaps.add(stat.getMax(new Leaf(potential)));
		statMaps.add(stat.getTestConvergence(new Leaf(potential)));
		statMaps.add(stat.getLyapunov(new Leaf(potential), new Leaf(cnft), new Leaf(input)));
		StatMapCNFT[] array = statMaps.toArray(new StatMapCNFT[]{});
		stats = new StatisticsCNFT("Stats",command.get(CNFTCommandLine.STAT_DT), 
				noDimSpace,array);
				
				

	}
	@Override
	public String getText() {
		return "Gaussian spiking model : " +
				"The lateral weight are modelised with a difference of gaussian " +
				"Spikes are transmitted if the potential is above a threshold " +
				"potential =  potential + dt/tau*(-potential + input + h) + 1/tau*cnft ";
	}







}
