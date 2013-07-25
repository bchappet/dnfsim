package optimisation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import console.CommandLineFormatException;


public class GATestLauncher extends GALauncher {
	
	/**Parameter names**/
	public static final int X = 0;
	public static final int Y = 1;
	
	public class TestIndivEvaluator extends IndivEvaluator{

		public TestIndivEvaluator() {
			super();
		}

		@Override
		public double getFitness() {
			
			double fit =  0.5 * (Math.pow(parameter[X], 2) + Math.pow(parameter[Y], 2));
			
			
		//	gaStats.addScenario(indivNb, 0, new double[]{fit},  new double[]{fit}, fit);
			
			return fit;
		}

		@Override
		public void run() {
			// nothing
			
		}
		
	}
	
	
	
	public static void main(String[] args) throws CommandLineFormatException, IOException, ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException{
		
		GATestLauncher ga = new GATestLauncher();
		int genMax1 = 20;
		int genMax2 = 4;
		
		GAOptimizer gao = new GAOptimizer(ga,new GAPrinter() ,"gen_max="+genMax1+";reevaluate=F;");
		gao.lauchGA();
		System.out.println(gao.getGaStats().printOrderedPopulation(genMax1-1));
		System.out.println(gao.getGaStats().getBestIndividualStat(genMax1-1));
		
		String fileSave = gao.getFileSave();
		System.err.println("crash");
		Thread.sleep(1000);
		
		gao = new GAOptimizer(fileSave,ga,GAPrinter.class ,"gen_max="+genMax2+";reevaluate=F;");
		
		gao.lauchGA();
		System.out.println(gao.getGaStats().printOrderedPopulation(genMax2-1));
		System.out.println(gao.getGaStats().getBestIndividualStat(genMax2-1));
		
		
	}



	@Override
	public IndivEvaluator[] getIndividualEvaluator(int popSize){
		IndivEvaluator[] ret = new IndivEvaluator[popSize];
		for(int i = 0 ; i < popSize ; i ++){
			ret[i] = new TestIndivEvaluator();
		}
		System.out.println(ret[0]);
		return ret;
	}



	@Override
	public double[][] getOptimizationInterval() {
		return new double[][]{{-10,1,10},{-10,1,10}};
	}



	@Override
	public boolean assertConstraints(double[] ind) {
		return ind[X] >= ind[Y];
	}
	
	

}
