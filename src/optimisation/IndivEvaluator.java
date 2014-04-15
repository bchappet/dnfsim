package optimisation;

import statistics.CharacteristicsCNFT;
import model.Model;
import gui.Printer;
import gui.Runner;

public abstract class IndivEvaluator extends Printer implements Runnable {

	
	protected double[] parameter;
	protected int indivNb;
	
	public IndivEvaluator() {
		super(0);
	}

	@Override
	public abstract void run() ;
	
	public void setParam(int indivNb, double[] indiv) {
		this.indivNb = indivNb;
		this.parameter = indiv;
	}
	
	public abstract double getFitness();

	protected abstract void addScenarioFitness(String scenario, double scenarioFitness);

	protected abstract double getScenarioFitness(int individu, int iteration,
			int scenarioId, CharacteristicsCNFT charac);

}
