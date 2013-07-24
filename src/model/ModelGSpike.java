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
import statistics.Statistics;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import fft.FFTConvolutionMatrix2D;

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

		

		cnft = new ConvolutionMatrix2D(CNFT,vdt,space2d);

		potential = new Map(POTENTIAL,new SpikingPotentialUM(),vdt,space2d);

		
		AbstractMap resetedPotential = new Map("resetedPotential",new SpikingUM(),
				vdt,space2d);
		focus = new Map(FOCUS,new SpikingUM(),vdt,space2d);
		
		Var pth = command.get(CNFTCommandLine.THRESHOLD);
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		//Init cnftW
		initLateralWeights();
		
		cnft.addParameters(cnftW,focus);
		potential.addParameters(resetedPotential, pTau,input,cnft,ph,pth,new Var("High",0));
		resetedPotential.addParameters(new Leaf(potential),pth,new Leaf(potential),new Var("High",0));
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

		Stat stat = new Stat(command.get(CNFTCommandLine.DT),noDimSpace,this);
		stats = new Statistics("Stats",command.get(CNFTCommandLine.DT), 
				noDimSpace,stat.getDefaultStatistics(new Leaf(focus), trackable));

	}
	@Override
	public String getText() {
		return "Gaussian spiking model : " +
				"The lateral weight are modelised with a difference of gaussian " +
				"Spikes are transmitted if the potential is above a threshold " +
				"potential =  potential + dt/tau*(-potential + input + h) + 1/tau*cnft ";
	}







}
