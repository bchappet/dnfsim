package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
import maps.Parameter;
import maps.Var;
import unitModel.SpikingPotentialUM;
import unitModel.SpikingUM;
import unitModel.Sum;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;

public class TestSOM extends ModelGSpike {
	
	public static final String CUMUL_POT = "cumulPot";
	
	protected Map cumulatedPotentials;

	public TestSOM(String name) {
		super(name);
	}
	
	@Override
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT);

		

		cnft = new FFTConvolutionMatrix2D(CNFT,vdt,space2d);

		potential = new Map(POTENTIAL,new SpikingPotentialUM(),vdt,space2d);
		
		cumulatedPotentials = new Map(CUMUL_POT,new Sum(),vdt,space2d);

		
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
		cumulatedPotentials.addParameters(new Leaf(cumulatedPotentials),new Leaf(potential));
		//to reach it
		this.addParameters(cumulatedPotentials);
		this.root = potential;
	}
	
	public void update() throws NullCoordinateException,
	CommandLineFormatException {
		super.update();
		
		//elect the winer from inputs and cumul pot
		List<Double[]> winInput = winners(input);
		List<Double[]> winPot = winners(potential);
		List<Double[]> winCumul = winners(cumulatedPotentials);
		
		System.out.println("Winners input : " + dispWin(winInput));
		System.out.println("Winners potential : " + dispWin(winPot));
		System.out.println("Winners cumulatedPotential : " + dispWin(winCumul));
		
		//System.out.println(input.display2D());
		
	}
	
	private String dispWin(List<Double[]> win){
		String ret = "";
		for(Double[] vec : win){
			ret += Arrays.toString(vec) + ", ";
		}
		return ret;
	}
	
	private List<Double[]> winners(Parameter p){
		p.constructMemory();
		double[] values  = p.getValues();
		Space sp = p.getSpace();
		double max = Double.MIN_VALUE;
		List<Double[]> list = new ArrayList<Double[]>();
		for(int i = 0 ; i < values.length ; i++){
			if(values[i] > max){
				max = values[i];
				list.clear();
				list.add(sp.discreteProj(sp.indexToCoord(i)));
			}else if(values[i] == max){
				list.add(sp.discreteProj(sp.indexToCoord(i)));
			}
		}
		
		return list;
	}
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret ={input,cnftW,cnft,potential,focus,cumulatedPotentials};
		return Arrays.asList(ret);
	}

}
