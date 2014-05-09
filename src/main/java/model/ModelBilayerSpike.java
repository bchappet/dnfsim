package main.java.model;


import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.ConvolutionMatrix2D;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Substract;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.Var;
import main.java.unitModel.SpikingPotentialUM;
import main.java.unitModel.SpikingUM;
import main.java.unitModel.Sum;

public class ModelBilayerSpike extends ModelGSpike {
	
	
	public static final String EXCITATIONMAP = "ExcitationMap";
	public static final String INHIBITIONMAP = "InhibitionMap";
	public static final String EXCITATIONSPIKE = "ExcitationSpike";
	public static final String INHIBITIONSPIKE = "InhibitionSpike";
	public static final String WEIGHTEE = "WEIGHT+ -> +";
	public static final String WEIGHTEI = "WEIGHT+ -> -";
	public static final String WEIGHTIE = "WEIGHT- -> +";
	public static final String WEIGHTII = "WEIGHT- -> -";
	public static final String INTERACTIONE = "INTERACTION+";
	public static final String INTERACTIONI = "INTERACTION-";
	public static final String TEMP = "TEMP";
	public static final String TEMP1 = "TEMP1";
	public static final String TEMP2 = "TEMP2";
	public static final String TEMP3 = "TEMP3";
	
	protected Parameter hpA2;
	protected Parameter pThE ;
	protected Parameter pThI;
	
	protected AbstractMap excitationSpike;
	protected AbstractMap inhibitionSpike;
	protected AbstractMap temp;
	protected AbstractMap temp1;
	protected AbstractMap temp2;
	protected AbstractMap temp3;
	
	protected AbstractMap weightee;
	protected AbstractMap transIE;
	protected AbstractMap transII;
	protected AbstractMap transEI;
	
	protected AbstractMap interactionE ;
	protected AbstractMap interactionI ;
	
	protected AbstractMap excitation ;
	protected AbstractMap inhibition;
	
	protected Parameter pA;
	protected Parameter pB;

	

	public ModelBilayerSpike(String name)  {
		super(name);
	}

	
	
	@Override
	protected void initLateralWeightParams(final Space extendedSpace) 
			throws CommandLineFormatException
	{
		
		super.initLateralWeightParams(extendedSpace);
		
		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);
		
		//Updatable pA2 = new StaticVar("A2",A2);
		Parameter pA2 = command.get(CNFTCommandLine.IA2);
		hpA2 = new TrajectoryUnitMap("A2_hidden",command.get(CNFTCommandLine.DT),extendedSpace,pA2,alphaP) {
			//A = A /(res*res)*(40*40)/alpha
			@Override
			public double computeTrajectory(double... param)  {
				return (params.getIndex(0)).get() / 
						(Math.pow(this.space.getResolution(),2)) *
						(40*40)/(params.getIndex(1)).get();
			}
		};
		
//		pThE = new StaticVar("Excitation_Threshold", threshold);
//		pThI = new StaticVar("Inhibition_Threshold", inhibitionThreshold);
		pThE = command.get(CNFTCommandLine.THRESHOLD);
		pThI = command.get(CNFTCommandLine.THRESHOLD_INHIBITORY);
		
	}
	
	protected void initModel() throws CommandLineFormatException
	{
	
	
		Var vdt = command.get(CNFTCommandLine.DT);
		weightee =  (Map) getLateralWeights(WEIGHTEE, vdt, refSpace, hpA, pa, hpB, pb);
		Map weightei =  (Map) getLateralWeights(WEIGHTEI, vdt, refSpace, hpA, pa, hpB, pb);
		Map weightie =  (Map) getLateralWeights(WEIGHTIE, vdt, refSpace, hpA2, pa, hpB, pb);
		Map weightii =  (Map) getLateralWeights(WEIGHTII, vdt, refSpace, hpA2, pa, hpB, pb);
		
		transIE =
				new TrajectoryUnitMap("transIE", vdt, refSpace,weightie) {
			@Override
			public double computeTrajectory(double... param)  {
				double activity = param[0];
				if (activity>0){
            		return  -activity;
            	}else{
            		return 0;
            	}
			}
		};
		 transIE.toStatic();
		transEI =
				new TrajectoryUnitMap("transEI", vdt, refSpace,weightei) {
			@Override
			public double computeTrajectory(double... param)  {
				return -param[0];
			}
		};
		transEI.toStatic();
		
		transII = 
				new TrajectoryUnitMap("transII", vdt, refSpace,weightii) {
			@Override
			public double computeTrajectory(double... param) {
				double activity = param[0];
				if (activity>0){
            		return  activity;
            	}else{
            		return 0;
            	}
			}
		};
		transII.toStatic();
		
		
		 excitationSpike = new Map(EXCITATIONSPIKE,new SpikingUM(), vdt, refSpace);
		 inhibitionSpike = new Map(INHIBITIONSPIKE,new SpikingUM(), vdt, refSpace);
		
		 temp = new ConvolutionMatrix2D("ExcSpikes*E->E",vdt,refSpace);
		 temp1 = new ConvolutionMatrix2D("InhSpikes*I->E",vdt,refSpace);
		 temp2 = new ConvolutionMatrix2D("ExcSpikes*E->I",vdt,refSpace);
		 temp3 = new ConvolutionMatrix2D("EInhSpikes*I->E",vdt,refSpace);
		
		temp.addParameters(weightee,new Leaf(excitationSpike));
		temp1.addParameters(transIE,new Leaf(inhibitionSpike));
		temp2.addParameters(transEI,new Leaf(excitationSpike));
		temp3.addParameters(transII,new Leaf(inhibitionSpike));
		
		 interactionE = new Map(INTERACTIONE,new Sum(),vdt,refSpace,temp,temp1);
		 interactionI = new Map(INTERACTIONI,new Sum(),vdt,refSpace,temp2,temp3);
		
		Var ph = command.get(CNFTCommandLine.RESTING_POTENTIAL);
		
		//Create potential,reset and spiking map for excitatory and inhibitory neurons
		Map resetExc = new Map("resetExc",new SpikingUM(), vdt, refSpace);
		Map resetInhi = new Map("resetInhi",new SpikingUM(), vdt, refSpace);
		
		
		
		
		
//	    excitation = new TrajectoryUnitMap(EXCITATIONMAP,vdt,refSpace,resetExc,pTau,main.java.input,interactionE,ph) {
//			@Override
//			public double computeActivity(double... param)  {
//				return computation2(params[RateCodedPotentialMap.POTENTIAL],
//						params[RateCodedPotentialMap.TAU],params[RateCodedPotentialMap.INPUT],
//						params[RateCodedPotentialMap.CNFT],params[RateCodedPotentialMap.H]);
//			}
//			private double computation2(double potential,double tau, double main.java.input,
//					double interactionE,double h  ) {
//					return Math.max(0, potential + dt.get()/tau*(-potential + main.java.input + h) + 1/tau*interactionE);
//			}
//		};
		
		excitation = new Map(EXCITATIONMAP,new SpikingPotentialUM(),vdt,refSpace,resetExc,pTau,input,interactionE,ph);
		
//		 inhibition = new TrajectoryUnitMap(INHIBITIONMAP,vdt,refSpace,resetInhi,pTau,main.java.input,interactionI,ph) {
//			@Override
//			public double computeActivity(double... param)  {
//				return computation2(param[RateCodedPotentialMap.POTENTIAL],
//						param[RateCodedPotentialMap.TAU],param[RateCodedPotentialMap.INPUT],
//						param[RateCodedPotentialMap.CNFT],param[RateCodedPotentialMap.H]);
//			}
//			private double computation2(double potential,double tau, double input_useless,
//					double interactionI,double h  ) {
//					return Math.max(0, potential + dt.get()/tau*(- potential + h) + 1/tau*interactionI);
//			}
//		};
		
		inhibition = new Map(INHIBITIONMAP,new SpikingPotentialUM(){
			@Override
			protected double computation2(double potential,double tau, double input_useless,
					double interactionI,double h  ) {
					return Math.max(0, potential + dt.get()/tau*(- potential + h) + 1/tau*interactionI);
			}
		},vdt,refSpace,resetInhi,pTau,input,interactionI,ph);
		
		Var low = new Var("Low",0);
		Var high = new Var("High",1);
		
		resetExc.addParameters(new Leaf(excitation),pThE,new Leaf(excitation),low);
		resetInhi.addParameters(new Leaf(inhibition),pThI,new Leaf(inhibition),low);
		excitationSpike.addParameters( excitation, pThE, low, high);
		inhibitionSpike.addParameters( inhibition, pThI, low, high);
		
		potential = new Map(POTENTIAL,new Substract(),vdt,refSpace,excitationSpike,inhibitionSpike);
		focus = new Map(FOCUS,new SpikingUM(), vdt, refSpace);
		focus.addParameters(potential,pThE,low,high);
		
		root = focus;
		
	}
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret ={input,focus,interactionE,interactionI,
						excitation,inhibition,excitationSpike,inhibitionSpike,
					temp,temp1,temp2,temp3,
					weightee,transIE,transEI,transII};
		return Arrays.asList(ret);
	}
	
	
	
	@Override
	public String getText() {
		return "Spiking version of \"Dynamic neural fields with local inhibition\" \n" +
				"inhibitory spike are propagated around the center of activity";
	}
	
	

}
