package main.java.optimisation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.resources.utils.Triplet;



/**
 * 
 * To display these informations :
 * 
 * NumGen|NumInd|ParamInd		|Iteration|Score Scenario	|Fitness
 *       |      |IA|IB|WA|WB|...|		  |A|B|C|D...		|
 *       								  |Charac1,Charac2...
 * ----------------------------------------------------------------------
 * Global indiv (mean of the iterations) : 
 * NumGen|NumInd|ParamInd		|Score Scenario	|Fitness
 *       |      |IA|IB|WA|WB|...|		  |A|B|C|D...		|
 *       								  |Charac1,Charac2...
 * 
 * 
 * Numgen summary
 * Top n best individuals (without the itearations) :
 * NumGen|NumInd|ParamInd		|Score Scenario	|Fitness
 *       |      |IA|IB|WA|WB|...|A|B|C|D...		|
 * 
 * Elite individuals (will (not) be reevaluated) :
 * 1,2,4,5
 * ----------------------------------------------------------------------
 * 
 * Breeding report :
 * 1*2 => 3
 * 2*5 => 6
 * 
 * And to restart (continue) the GA from any point
 * 
 * @author bchappet
 *
 */
public class GAPrinter implements Serializable {
	
	
	protected class Key implements Serializable{
		int numInd;
		int iteration;
		public Key(int numInd2, int it)  {
			numInd = numInd2;
			iteration = it;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + iteration;
			result = prime * result + numInd;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (iteration != other.iteration)
				return false;
			if (numInd != other.numInd)
				return false;
			return true;
		}
		private GAPrinter getOuterType() {
			return GAPrinter.this;
		}
	}
	
	protected class Indiv implements Serializable{
		List<Double> parameters;
		List<Double> fitness;
		
		
		
		public Indiv(double[] params){
			fitness = new ArrayList<Double>();
			parameters = new ArrayList<Double>();
			for(double d : params)
				parameters.add(d);
		}
		
		public String toString(){
			String ret = "";
			for(Double p : parameters)
				ret += p + ",";
			ret += "||";
			
			for(Double d : fitness)
				ret += d+",";
			ret += "||";
			
			return ret;
		}
		
		public void addParameters(double[] params){
			for(double d : params){
				parameters.add(d);
			}
		}
		
		
		
		

		public void setParameters(List<Double> parameters2) {
			this.parameters = parameters2;
			
		}

		public void addFitness(double fit) {
			fitness.add(fit);
			
		}
	}
	
	public class Generation implements Serializable{
		List<Triplet> orderedIndiv;
		int nbElite;
		
		public Generation(List<Triplet> orderedIndiv,int nbElite){
			this.orderedIndiv = orderedIndiv;
			this.nbElite = nbElite;
		}

		public String printOrderedPopulation() {
			String ret = "";
			
			return ret;
		}
	}
	
	protected class Mutation implements Serializable{
		int paramIndex; double oldVal; double newval;
		public Mutation(int paramIndex, double oldVal, double newval){
			this.paramIndex = paramIndex; this.oldVal = oldVal;this.newval = newval;
		}
	}
	
	/**Store each individual global results**/
	protected Map<Integer,Indiv> mapIndiv;
	/**Strore the generations reports**/
	protected Map<Integer,Generation> mapGeneration;
	/**Stor the breeding report for each generation**/
	protected Map<Integer,int[][]> breedMap;
	/**Store the Mutations**/
	protected Map<AbstractMap.SimpleEntry<Integer,Integer>,Mutation> mutationMap;
	
	
	public GAPrinter(){
		mapIndiv = new HashMap<Integer, Indiv >();
		mapGeneration = new HashMap<Integer, GAPrinter.Generation>();
		breedMap = new HashMap<Integer, int[][]>();
		mutationMap = new HashMap<AbstractMap.SimpleEntry<Integer,Integer>, GAPrinter.Mutation>();
	}
	
	public synchronized void addIndiv(int numind,double[] params){
		Indiv ind = new Indiv(params);
		mapIndiv.put(numind, ind);
		
	}
	
	/**
	 * Set the global parameters of an individual
	 * @param numInd
	 * @param parameters
	 */
	public void setParameters(int numInd,List<Double> parameters){
		mapIndiv.get(numInd).setParameters(parameters);
	}
	
	/**
	 * Called at the end of the generation
	 */
	public void endGeneration(int gen,List<Triplet> orderedIndiv,int nbElite){
		System.out.println("==========================================================");
		System.out.println("End of generation " + gen + " . List of the elite :");
		
		for(int i = 0 ; i < nbElite ; i++){
			Triplet indiv = orderedIndiv.get(i);
			System.out.println(indiv.getFitness() + " ==> " +   getIndividualStats(indiv.getIndivNum()));
		}
		mapGeneration.put(gen,new Generation(orderedIndiv, nbElite));
	}
	
	public void addBreedingReport(int gen,int[][] report){
		breedMap.put(gen, report);
	}


	/**
	 * 
	 * @param indNb num of the individual
	 * @param paramIndex
	 * @param oldVal
	 * @param newval
	 */
	public void addMutation(int gen,int indNb, int paramIndex, double oldVal, double newval) {
		mutationMap.put(new AbstractMap.SimpleEntry<Integer,Integer>(gen,indNb),new Mutation(paramIndex,oldVal,newval));
		
	}


	public String getIndividualStats(int numInd){
		String ret = "";
		
		Indiv ind = mapIndiv.get(numInd);
		ret = "#" + numInd + "[" + ind.toString() + "]";
		
		return ret;
	}
	
	public String getBestIndividualStat(int numGen){
		int ref = mapGeneration.get(numGen).orderedIndiv.get(0).getIndivNum();
		return getIndividualStats(ref);
	}

	public String printOrderedPopulation(int genNb) {
		String ret = "";
		Generation gen = mapGeneration.get(genNb);
		for(Triplet tr : gen.orderedIndiv){
			ret += getIndividualStats(tr.getIndivNum()) + "\n";
		}
		return ret;
	}

	/**
	 * Add a fitness to an individual
	 * @param indivNb
	 * @param fit
	 */
	public void addFitness(int indivNb, double fit) {
		mapIndiv.get(indivNb).addFitness(fit);
		
	}

	/**
	 * Load statsControler from the file
	 * @param string
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static GAPrinter load(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fos = new FileInputStream(fileName);
		ObjectInputStream oos = new ObjectInputStream(fos);
		GAPrinter gaStats = null;
		try{
			gaStats = (GAPrinter) oos.readObject();
		}finally{
			oos.close();
		}
		return gaStats;
	}
	
	public void save(String fileName) throws IOException{
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		try{
			oos.writeObject(this);
		}finally{
			oos.close();
		}
	}


}
