package main.java.optimisation;

import java.util.concurrent.BlockingQueue;

import main.java.maps.Parameter;
import main.java.model.Model;
import main.java.statistics.CharacteristicsCNFT;
import main.java.statistics.StatisticsCNFT;

public class ConvergenceIndivEvaluator extends IndivEvaluator  {



	protected double fitness;
	protected GALauncher gaLauncher;

	protected BlockingQueue<Model> modelPool;
	protected String[] scenarios;
	protected int[] nbIteration;

	protected String[] parameterNames;

	public ConvergenceIndivEvaluator(BlockingQueue<Model> modelPool,GALauncher gaLauncher,String[] scenarios,String[] parameterNames,int[] nbIteration) {
		this.modelPool = modelPool;
		this.gaLauncher = gaLauncher;
		this.scenarios = scenarios;
		this.nbIteration = nbIteration;
		this.parameterNames = parameterNames;
	}

	@Override
	public void run() {
		fitness = 0;
		Thread[] threads = new Thread[scenarios.length];
		GAScenarioPrinter[] scenar = new GAScenarioPrinter[scenarios.length];
		//Launch simulation
		for(int i  =0 ; i < scenarios.length ; i++){
			scenar[i] = new GAScenarioPrinter(indivNb, modelPool,i ,this,
					doubleToString(parameter),getScenarios(scenarios[i]),nbIteration[i]);
			threads[i] = new Thread(scenar[i]);
			threads[i].start();
		}

		try {
			for(int i  =0 ; i < scenarios.length ; i++){
				threads[i].join();
				scenar[i] = null;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private String doubleToString(double[] parameter) {
		String ret = "";

		for(int i = 0 ; i < parameter.length ;i++){
			ret += parameterNames[i] + "=" + parameter[i] + ";";
		}
		return ret;
	}



	/**
	 * Return the scenario scripts
	 * @param scenarioNames2
	 * @return
	 */
	private String getScenarios(String scenarioNames) {
		String ret = new String();

		ret = "load="+scenarioNames+";";

		return ret;
	}



	public double getFitness(){
		return fitness/scenarios.length;
	}

	public synchronized void addScenarioFitness(String scenario, double scenarioFitness) {
		fitness += scenarioFitness;
	}


	protected  double  getScenarioFitness(int numInd,int numIt,int scenarioId,CharacteristicsCNFT charac){
		charac.compute();
		double conv =  charac.getParam(CharacteristicsCNFT.TEST_CONV).get();
		return conv;
	}

	

}
