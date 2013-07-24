package optimisation;

import gui.Printer;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import model.Model;

public class GAScenarioPrinter extends Printer implements Runnable{

	protected int individu;
	protected int iteration = 0;
	protected BlockingQueue<Model> modelPool;
	protected int scenarioId;
	protected Model currentModel;
	protected CNFTIndivEvaluator  indivEvaluator;

	protected double scenarioFitness = 0;

	protected String parameters;
	protected String scenario;
	protected int nbIterations;


	public GAScenarioPrinter(int individu,BlockingQueue<Model> modelPool,int scenarioID,CNFTIndivEvaluator gaLauncher,
			String parameters,String scenario,int nbIterations) {
		super(0);
		this.individu = individu;
		this.modelPool = modelPool;
		this.scenarioId = scenarioID;
		this.indivEvaluator = gaLauncher;

		this.parameters = parameters;
		this.scenario = scenario;
		this.nbIterations = nbIterations;

	}

	@Override
	public void run() {
		try{
			Thread thread = null;
			for(int i = 0 ; i < nbIterations ; i++){
				currentModel = modelPool.take();
				GARunner runner = new GARunner(currentModel, parameters, scenario,this);
				thread = new Thread(runner);
				thread.start();
			}

			thread.join();
			assert(iteration == nbIterations);
			indivEvaluator.addScenarioFitness(scenario,scenarioFitness);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void computeScenarioFitness() throws InterruptedException{
		
		double res = indivEvaluator.getScenarioFitness(individu,iteration, scenarioId,currentModel.getCharac());
		scenarioFitness += res;
		iteration ++;
		modelPool.put(currentModel);

	}


}
