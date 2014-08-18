package main.java.pfspike;

import java.math.BigDecimal;
import java.util.Arrays;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.packet.Spike;
import main.java.network.probalisticFlooding.PFSpreadingGraph;

public class PFSSpreadingGraph extends PFSpreadingGraph<Spike> {

	public static final int NAME = 7;
	public static final int FOCUS = 6;
	public static final int NB_SPIKE = 5;
	public static final int TRESHOLD = 4;	
	public static final int NB_COMPUTATION = 3;
	public static final int MAIN_DT = 2;

	/*private Var<Integer> nbSpike;
	private Var<BigDecimal> treshold;
	private Var<BigDecimal> maindt;*/

	public PFSSpreadingGraph(/*Var<BigDecimal> dt, Var<Integer> nbcomputation,
			Var<BigDecimal> threshold, Var<Integer> nbSpike,*/
			Parameter ... params) {
		super(params);
		
		double maindt = ((Var<BigDecimal>) getParameters().get(MAIN_DT)).get().doubleValue();
		double nbComputation = ((Var<Integer>) getParameters().get(NB_COMPUTATION)).get().doubleValue();
		Var<BigDecimal> newdt = new Var<BigDecimal>("spreading_graph_dt",new BigDecimal(maindt/nbComputation));
		System.out.println("newdt : "+newdt.get().doubleValue());
		this.getParameters().set(DT,newdt);
	}

	public void compute() {
		
		// on récupère les paramètres
		Var<BigDecimal> maindt = (Var<BigDecimal>) getParameters().get(MAIN_DT);
		Var<BigDecimal> treshold = (Var<BigDecimal>) getParameters().get(TRESHOLD);
		Var<Integer> nbSpike = (Var<Integer>) getParameters().get(NB_SPIKE);
		Map focus = (Map) getParameters().get(FOCUS);
		
		double div = getTime().doubleValue()/maindt.get().doubleValue();
		double divEntiere = Math.round((getTime().doubleValue()/maindt.get().doubleValue()));
		boolean itsTime = (Math.abs(div-divEntiere))< 0.000000001;
		
		if (itsTime) {
			// alors on est sur une des computation principale donc on vérifie
			// le focus et on vide le spreading graphe
			//System.out.println("trolololololo");
			BigDecimal lastTime = getTime();
			this.reset();
			setTime(lastTime);
			//System.out.println("trolololo");
			int size = this.getValues().size(); // TODO a recup des parametre
			// plutot non ?
			for (int i = 0; i < size; i++) {
				if (((Double) focus.getIndex(i))
						.doubleValue() >= treshold.get().doubleValue()) {
					for (int j = 0; j < nbSpike.get(); j++) {
						addToFIFO(i, new Spike());
					}					
				}
			}
		}
		

		super.compute();
	}
	
	@Override
	public String getName(){
		return ((Var<String>)getParameters().get(NAME)).get();
	}
}
