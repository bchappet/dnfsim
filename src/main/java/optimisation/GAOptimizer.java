package main.java.optimisation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.resources.utils.Triplet;

public class GAOptimizer {

	public static final int LOW = 0;
	public static final int STARTING_VALUE = 1;
	public static final int HIGH = 2;




	protected int popSize;
	protected CommandLine cl;

	protected double[][]  optimizationInterval;

	protected GALauncher gaLauncher;
	protected GAPrinter gaStats;


	protected IndivEvaluator[] indivEvaluator;


	protected Random rand = new Random();
	protected int numIndiv = 0;
	protected int numGen = 0;
	protected int parameterSize;
	protected int nbElite;
	protected int[] eliteIndex;
	protected double[] eliteFitness;
	protected List<double[]> population;
	protected  String file_save;


	public GAOptimizer(GALauncher gaLauncher,GAPrinter gaPrinter, String initScript) throws CommandLineFormatException, IOException{
		this.cl = new CommandLine(initScript);
		gaLauncher.setGAO(this);
		this.popSize = (int) cl.get(CommandLine.POP_SIZE).get();
		this.indivEvaluator = gaLauncher.getIndividualEvaluator(popSize);
		this.gaLauncher = gaLauncher;
		this.gaStats = gaPrinter;
		this.optimizationInterval = gaLauncher.getOptimizationInterval();
		parameterSize = gaLauncher.getOptimizedParameters().length;

		nbElite = (int) Math.round (popSize*cl.get(CommandLine.ELITE_RATIO).get());
		eliteIndex = new int[nbElite];
		eliteFitness = new double[nbElite];
		file_save = "saveGAO_"+Math.random();

		population = getPopulation();
	}

	public GAOptimizer(String fileName,GALauncher gaLauncher,Class<GAPrinter> gaPrinter,String initScript) throws CommandLineFormatException, IOException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		gaLauncher.setGAO(this);
		this.cl = new CommandLine(initScript);
		this.popSize = (int) cl.get(CommandLine.POP_SIZE).get();
		this.indivEvaluator = gaLauncher.getIndividualEvaluator(popSize);
		this.gaLauncher = gaLauncher;
		this.optimizationInterval = gaLauncher.getOptimizationInterval();
		parameterSize = optimizationInterval.length;

		Method m = gaPrinter.getMethod("load", String.class);
		this.gaStats = (GAPrinter) m.invoke(null,fileName+"_stats");
		file_save = fileName;
		load(fileName);
		
		System.out.println("load : " + numGen);
	}


	/**
	 * Start the optimization
	 * @throws CommandLineFormatException
	 * @throws IOException
	 */
	public void lauchGA() throws CommandLineFormatException, IOException {


		while(numGen < cl.get(CommandLine.GEN_MAX).get()){
			evolve();
			System.out.println(numGen + "/" + cl.get(CommandLine.GEN_MAX).get());
			numGen ++;
			save(file_save);
		}
	}

	public void deleteFile(){
		File file = new File(file_save);
		file.delete();
	}


	private void load(String fileName) throws IOException, ClassNotFoundException{

		//Read the population : 
		FileInputStream fos = new FileInputStream(fileName+"_pop");
		ObjectInputStream oos = new ObjectInputStream(fos);

		try{
			population = (List<double[]>) oos.readObject();
		}finally{
			oos.close();
		}



		//Read the other info
		fos = new FileInputStream(fileName+"_info");
		DataInputStream dos = new DataInputStream(fos);

		try{
			popSize = dos.readInt();
			numGen = dos.readInt();
			numIndiv = dos.readInt();
			nbElite = dos.readInt();
			eliteIndex = new int[nbElite];
			eliteFitness = new double[nbElite];
			for(int i = 0 ; i < nbElite ; i ++){
				eliteIndex[i] = dos.readInt();
				eliteFitness[i] = dos.readDouble();
			}
		}finally{
			dos.close();
		}



	}


	private void save(String fileSave) throws IOException {

		//Write the population : 
		FileOutputStream fos = new FileOutputStream(fileSave+"_pop");
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		try{
			oos.writeObject(population);
		}finally{
			oos.close();
		}

		//Write the GAPrinter
		gaStats.save(fileSave+"_stats");

		//Other info
		fos = new FileOutputStream(fileSave+"_info");
		DataOutputStream dos = new DataOutputStream(fos);

		try{
			dos.writeInt(popSize);
			dos.writeInt(numGen);
			dos.writeInt(numIndiv);
			dos.writeInt(nbElite);
			for(int i = 0 ; i < nbElite ; i ++){
				dos.writeInt(eliteIndex[i]);
				dos.writeDouble(eliteFitness[i]);
			}

		}finally{
			dos.close();
		}
	}

	private List<double[]> getPopulation() {

		List<double[]> population = new ArrayList<double[]>(popSize);

		double[] startIndiv = new double[parameterSize];
		for(int i = 0 ; i < parameterSize ; i++){
			startIndiv[i] = optimizationInterval[i][STARTING_VALUE];
		}

		population.add(startIndiv);

		for(int i = 1 ; i < popSize ; i++){
			population.add(generateRandomIndividual());
		}

		return population;
	}



	/**
	 * 
	 * @param population
	 * @return then new generation
	 * @throws CommandLineFormatException 
	 */
	private void evolve() throws CommandLineFormatException {



		Thread[] threads = new Thread[popSize];

		int[] indivNbs = new int[popSize];

		int offset = numIndiv;
		//Launch every indiv thread
		for(int i =0 ; i < popSize ; i++){
			double[] indiv = population.get(i);
			//The elite keeps the same id
			if(numGen != 0 && i < nbElite){
				indivNbs[i] = eliteIndex[i];
			}else{

				gaStats.addIndiv(numIndiv, indiv);
				indivNbs[i] = numIndiv;
				numIndiv++;
			}
			indivEvaluator[i].setParam(indivNbs[i],indiv);
			threads[i] = new Thread( indivEvaluator[i]);
			threads[i].start();

		}

		try{
			//Wait for all the thread to terminate
			for(int i =0 ; i < popSize ; i++){
				threads[i].join();
			}


		}catch (Exception e) {
			e.printStackTrace();
		}

		/*End of the generation**/

		//Get every individual fitness
		List<Triplet> lIndiv = new ArrayList<Triplet>(popSize);
		
		for(int i = 0 ; i <nbElite ; i++){
			double fitness;
			if(numGen == 0 || cl.getBool(CommandLine.REEVALUATE)){
				fitness = indivEvaluator[i].getFitness();
				gaStats.addFitness(indivNbs[i], fitness);
			}else{
				fitness = eliteFitness[i];
			}
			Triplet trip = new Triplet(i,indivNbs[i],fitness );
			lIndiv.add(trip);
		}
		for(int i = nbElite ; i < popSize ; i++){
			double fitness = indivEvaluator[i].getFitness();
			gaStats.addFitness(indivNbs[i], fitness);
			Triplet trip = new Triplet(i,indivNbs[i],fitness );
			lIndiv.add(trip);
		}


		//Sort the array to find the best results
		Collections.sort(lIndiv, new Comparator<Triplet>() {
			@Override
			public int compare(Triplet o1,	Triplet o2) {
				if (o1.getFitness() < o2.getFitness()) return -1;
				if (o1.getFitness() > o2.getFitness()) return 1;
				return 0;
			}
		});


		//Create the new gen

		int[][] breedingreport = new int[popSize-nbElite][3];

		List<double[]> newGen = new ArrayList<double[]>(popSize);

		//Add the elite
		for(int i = 0 ; i< nbElite ; i++){
			eliteIndex[i] = lIndiv.get(i).getIndivNum();
			eliteFitness[i] = lIndiv.get(i).getFitness();
			int popIndex =  lIndiv.get(i).getPopIndex();
			newGen.add(population.get((popIndex)));
		}
		//Then add the childrens
		for(int i = nbElite ; i < popSize ; i++){

			int childFutureIndNb= numIndiv + i-nbElite;
			int p1 = lIndiv.get(getParent()).getPopIndex();
			int p2 = lIndiv.get(getParent()).getPopIndex();
			double[] child = childIndividual(population.get(p1),population.get(p2));

			// Apply mutations
			applyMutation(childFutureIndNb,child);
			// Put the child in the new population
			newGen.add(child);

			breedingreport[i-nbElite] = new int[]{childFutureIndNb,p1 + offset,p2 + offset};
		}


		gaStats.endGeneration(numGen, lIndiv, nbElite);
		gaStats.addBreedingReport(numGen, breedingreport);


		population = newGen;

	}

	/** Apply mutations to an individual genotype 
	 * @throws CommandLineFormatException */
	protected void applyMutation(int indNb,double[] ind) throws CommandLineFormatException {
		// Apply mutations
		for (int i=0; i< parameterSize; i++) {
			if (rand.nextDouble()<cl.get(CommandLine.MUTATION_PROB).get()) {
				double nval;
				double oldVal = ind[i];
				do {

					nval = ind[i]+(2*rand.nextDouble()-1);//*mutation_ranges[i];
					//System.out.println("2 " + nval );
				} while(!gaLauncher.assertConstraints(ind,nval,i) || nval<optimizationInterval[i][LOW] || nval>optimizationInterval[i][HIGH]);
				ind[i] = nval;

				gaStats.addMutation(numGen,indNb,i,oldVal,nval);
			}
		}
	}

	/** Generate a child from two parent genotypes
	 * (crossing-over or equivalent operation mixing the parent genotypes) */
	protected double[] childIndividual(double[] p1, double[] p2) {
		double[] child = new double[p1.length];
		// For each coordinate choose an intermediate value
		// between the parents main.java.coordinates
		do{
			for (int i=0; i< parameterSize; i++) {
				double r = Math.random();
				child[i] = r*p1[i] + (1-r)*p2[i];
				//System.out.println("1 : " +indivNum Arrays.toString(p1) + " + "  +Arrays.toString(p2) + " => " + Arrays.toString(child) + " r= " + r + "");
			}

		}while(!gaLauncher.assertConstraints(child));
		return child;
	}

	/** Get a biased parent index (towards the best individuals) 
	 * @throws CommandLineFormatException */
	private int getParent() throws CommandLineFormatException {
		int parent;
		// Select more often the parents in the elite
		// and make sure it is in the range of existing individuals
		do {
			parent = (int)(Math.abs(rand.nextGaussian())*cl.get(CommandLine.PARENT_SIGMA).get()*popSize);
		} while (parent>=popSize);
		// Return the parent index
		return parent;
	}



	private double[] generateRandomIndividual() {

		double[] ret = new double[parameterSize];

		do{
			for(int i = 0 ; i < parameterSize ; i++){
				double diff = optimizationInterval[i][HIGH] - optimizationInterval[i][LOW];
				ret[i] = Math.random() * diff + optimizationInterval[i][LOW];
				//System.out.println("0");
			}
		}while(!gaLauncher.assertConstraints(ret));

		return ret;
	}

	public String getFileSave() {
		return file_save;
	}

	public GAPrinter getGaStats() {
		return gaStats;
	}

}


