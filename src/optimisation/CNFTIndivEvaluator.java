package optimisation;

import java.util.concurrent.BlockingQueue;

import maps.Parameter;
import model.Model;
import statistics.CharacteristicsCNFT;
import statistics.StatisticsCNFT;

public class CNFTIndivEvaluator extends IndivEvaluator  {

	public final static int SWITCHING = 5;
	
	public final static int MAX_FITNESS = 50;

	protected double fitness;
	protected GALauncher gaLauncher;

	protected BlockingQueue<Model> modelPool;
	protected String[] scenarios;
	protected int[] nbIteration;

	protected String[] parameterNames;

	public CNFTIndivEvaluator(BlockingQueue<Model> modelPool,GALauncher gaLauncher,String[] scenarios,String[] parameterNames,int[] nbIteration) {
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
		double conv =  charac.getParam(CharacteristicsCNFT.CONVERGENCE).get();
		double noFocus = charac.getParam(CharacteristicsCNFT.NO_FOCUS).get();
		double obstinacy = charac.getParam(CharacteristicsCNFT.OBSTINACY).get();
		double meanError  = charac.getParam(CharacteristicsCNFT.MEAN_ERROR).get();
		double accError = charac.getParam(CharacteristicsCNFT.ACC_ERROR).get();
		double fitness = 0;
		if(scenarioId != SWITCHING){

			double[] val = {conv,noFocus,obstinacy,meanError,accError};
			double[] normVal = new double[val.length];
			double sum = 0;

			for(int i  = 0 ; i < 5 ; i++){
				normVal[i] = normalize(val[i], i);
				sum += normVal[i];
			}
			fitness = sum;
			GACNFTPrinter gaStats = (GACNFTPrinter) gaLauncher.getGao().getGaStats();
			gaStats.addScenario(numInd,scenarioId,val,normVal,fitness);
		}else{
			if(obstinacy == 1)//We are expecting a switch of target
				fitness = 0;
			else
				fitness = MAX_FITNESS;
		}

		return fitness;


	}

	protected  double normalize(double val,int paramIndex)
	{
		//To normalize the weight of each factor, we choose an interval :
		//The weight of each factor will be equivalent on this interval
		//					cov,nofoc,Obs,Err,nbNonAccError
		double[] wanteds = {20d,10d,  5d, 0.1,1d};
		double[] minimums = {0d,0d,   0d, 0d, 0d};
		double[] error = {MAX_FITNESS,MAX_FITNESS,MAX_FITNESS,MAX_FITNESS,MAX_FITNESS};
		//Do we want to minimize or maximize
		boolean[] minimize = {true,true,true,true,true};


		double ret;
		if(val == StatisticsCNFT.ERROR){
			ret = error[paramIndex];
		}else{
			ret =  (val - minimums[paramIndex])/(wanteds[paramIndex] - minimums[paramIndex]);
			if(!minimize[paramIndex])
			{
				ret = 1 - ret;
			}
		}

		return ret;
	}

}
