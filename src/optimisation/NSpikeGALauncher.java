package optimisation;

import gui.Printer;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import model.Model;
import model.Models;
import console.CommandLineFormatException;

public class NSpikeGALauncher extends GALauncher {

	public static final int IA = 0;
	public static final int IB = 1;
	public static final int PA = 2;
	public static final int PB = 3;
	public static final int TAU = 4;	
	public static final int ALPHA = 5;


	protected String[] scenarioNames;
	/**Nb iterations per scenario**/
	protected int[] nbIterations;

	protected BlockingQueue<Model> modelPool;

	protected URL contextPath;



	/** Random number generator */

	IndivEvaluator[] indivEvaluator;
	protected String contextScript;
	protected String modelName;

	protected int nbThread;


	public NSpikeGALauncher(String modelName,String context,int nbThread) throws CommandLineFormatException{
		super();
		System.out.println("MEM:"+"construct:"+this.getClass());

		this.nbThread = nbThread;
		try{

			contextPath = new URL("file:./context/");
			if(context == null || context.isEmpty()){
				//Default behaviour : read the file in default context path with the same name as the model
				contextScript = Printer.readFile(new URL("file:"+contextPath.getPath()+modelName+".dnfs"));
			}else{
				//The context is given by the string 
				contextScript = context;
			}

			this.modelName = modelName;
			scenarioNames = getScenariosNames();
			this.nbIterations = getNbIterations(scenarioNames);



		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int GetScreenWorkingWidth() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int GetScreenWorkingHeight() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}

	public IndivEvaluator[] getIndividualEvaluator(int popSize) {

		String[] paramNames = getOptimizedParameters();
		ArrayBlockingQueue<Model> modelPool = new ArrayBlockingQueue<Model>(nbThread,true);
		Model model;
		try {
			model = Models.getModel(modelName).construct();



			model.initialize(contextScript);
			modelPool.add(model);
			for(int i = 1 ; i < nbThread ; i++){
				Model tmp = Models.getModel(modelName).construct();
				tmp.initialize(contextScript);
				modelPool.add(tmp);
			}



		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		IndivEvaluator[] ret = new IndivEvaluator[popSize];

		for(int i  = 0 ; i < popSize ; i++){
			ret[i] = new ConvergenceIndivEvaluator(modelPool, this, scenarioNames,paramNames, nbIterations);

			
		}

		return ret;

	}
	/**
	 * Return the number of iteration per scenario
	 * @return
	 */
	private int[] getNbIterations(String[] scenarioNames) {
		int[] ret = new int[scenarioNames.length];
		Arrays.fill(ret, 1); //TODO
		return ret;
	}



	/**
	 * 
	 * @param parameters
	 * @return true if the constrainst are respected
	 * @throws CommandLineFormatException
	 */
	public boolean assertConstraints(double[] parameters) {
		return parameters[PA] <= parameters[PB]
				&& parameters[IA] >= -parameters[IB];
	}

	protected String[] getScenariosNames() {
		//return new String[]{"emergence.dnfs","tracking.dnfs","competition.dnfs","noise.dnfs","distracters.dnfs","switching.dnfs"};
		//return new String[]{"competition.dnfs",};//"emergence.dnfs","tracking.dnfs","competition.dnfs","noise.dnfs","distracters.dnfs","switching.dnfs"};
		return new String[]{"noiseAlone.dnfs",};
	}



	public double[][] getOptimizationInterval() {
		double[][] ret = {	
				{0,2.33,10}, //IA
				{-10,-1.59,0}, //IB
				{0,0.0043,1}, //PA
				{0,0.17,1}, //PB
				{0,0.64,5}, //Tau
				{0,10,50} //alpha
		};

		return ret;
	}



	protected String[] getOptimizedParameters() {
		return new String[]{"ia","ib","wa","wb","tau","alpha"};
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int popSize = 50;
			int genMax = 50;
			NSpikeGALauncher ga = new NSpikeGALauncher("NSpikeFileReader",null ,1);

			GAOptimizer gao = new GAOptimizer( ga,new GACNFTPrinter(), "pop_size="+popSize+";"+"gen_max="+genMax+";");
			gao.lauchGA();
			System.out.println(gao.getGaStats().printOrderedPopulation(genMax-1));
			System.out.println(gao.getGaStats().getBestIndividualStat(genMax-1));

		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}






}
