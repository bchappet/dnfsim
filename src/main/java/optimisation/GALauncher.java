package main.java.optimisation;

import main.java.console.CommandLineFormatException;

public abstract class GALauncher {
	
	protected GAOptimizer gao;
	
	

	
	
	public abstract IndivEvaluator[] getIndividualEvaluator(int popSize);

	public abstract double[][] getOptimizationInterval();

	public abstract boolean assertConstraints(double[] ind);
		
	protected abstract String[] getOptimizedParameters();



	

	/**
	 * Assert constraintsfor a new parameter value ; nval the n^th parameter
	 * @param ind
	 * @param nval
	 * @param i
	 * @return
	 */
	public boolean assertConstraints(double[] ind, double nval, int i) {
		double[] newInd = new double[ind.length];
		System.arraycopy(ind, 0, newInd, 0, newInd.length);
		
		newInd[i] = nval;
		return assertConstraints(newInd);
	}

	public void setGAO(GAOptimizer gaOptimizer) {
		this.gao = gaOptimizer;
		
	}

	public GAOptimizer getGao() {
		return gao;
	}
	
	

}
