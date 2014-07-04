package main.java.optimisation;

import main.java.gui.Printer;
import main.java.gui.Runner;
import main.java.model.Model;
import main.java.statistics.CharacteristicsCNFT;

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
