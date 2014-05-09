package main.java.optimisation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GACNFTPrinter extends GAPrinter {

	protected class Indiv extends GAPrinter.Indiv {


		/**For each fitness**/
		protected List<List<double[]>> scenarios;
		protected List<List<Double>> scenarioScore;
		/**Index of the current fitness computation**/
		protected int currentIndex = 0;
		/**List the added sceneario of the current index**/
		protected List<Integer> scenariosOfCurrentIndex;

		public Indiv(double[] params) {
			super(params);
			scenarios = new ArrayList<List<double[]>>();
			scenarioScore = new ArrayList<List<Double>>();
			scenariosOfCurrentIndex = new ArrayList<Integer>();
		}
		
		/**
		 * Final addition before another fitness
		 */
		public void addFitness(double fit) {
			fitness.add(currentIndex,fit);
			scenariosOfCurrentIndex.clear();
			currentIndex ++;
		}
		
		/**
		 * We set the scenario score before setting the final fitness
		 * @param scenario
		 * @param characs
		 * @param score
		 */
		public synchronized void addScenario(int scenario,double[] characs,double score){
			List<double[]> currentScenario;
			List<Double> currentScore;
			if(scenariosOfCurrentIndex.isEmpty()){
				currentScenario = new ArrayList<double[]>();
				currentScore = new ArrayList<Double>();
				scenarios.add(currentScenario);
				scenarioScore.add(currentScore);
			}else{
				currentScenario = scenarios.get(currentIndex);
				currentScore = scenarioScore.get(currentIndex);
			}
			
			
			while(scenario >= currentScenario.size() ){
				currentScenario.add(null);
				currentScore.add(null);
			}
			//size > scenario
			currentScenario.set(scenario, characs);
			currentScore.add(scenario,score);
			scenariosOfCurrentIndex.add(scenario);
			
			
			
		}
		
		/**
		 * Also print the scenario fitness and score for each characteristics
		 * |NumFitness||fitness||Scenario1  |Score||Scenario2   | score||...||
		 * |		  ||	   ||Char1,Char2|     ||Char1,Char2 |
		 * @return
		 */
		public String toStringFull(){
			String ret = "";
			//Iterate on fitnesses
			for(int i = 0 ; i < fitness.size() ; i++){
				ret += "||" +i+"||"+fitness.get(i)+"||";
				//Iterate on scenario
				for(int j = 0 ; j < scenarios.get(i).size() ; j++){
					//iterate on characteristics
					for(int k = 0 ; k < scenarios.get(i).get(j).length ; k++){
						ret += scenarios.get(i).get(j)[k] + "|";
					}
					ret += "||";
					ret += scenarioScore.get(i).get(j);
					ret += "||";
				}
				ret += "||\n";
			}
			return ret;
		}
	}


	public GACNFTPrinter() {
		super();
	}
	
	public synchronized void addIndiv(int numind,double[] params){
		GACNFTPrinter.Indiv ind = new GACNFTPrinter.Indiv(params);
		mapIndiv.put(numind, ind);
		
	}
	
	public void addScenario(int numInd, int scenario,
			 double[] val, double[] normVal, double fitness) {
			System.out.println("Evaluating scenario " + scenario + "::"  + Arrays.toString(val) + " ==> " + fitness + "::"+ getIndividualStats(numInd) );
			((GACNFTPrinter.Indiv)mapIndiv.get(numInd)).addScenario(scenario, val, fitness);
	}
	
	

}
