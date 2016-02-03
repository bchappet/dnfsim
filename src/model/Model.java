package model;

import gui.Node;
import gui.Suscriber;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import maps.AbstractMap;
import maps.Map;
import maps.Parameter;
import statistics.Characteristics;
import statistics.CharacteristicsCNFT;
import statistics.Statistics;
import statistics.StatisticsCNFT;
import console.CNFTCommandLine;
import console.CommandLine;
import console.CommandLineFormatException;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public abstract class Model implements Node {

	/**
	 * Scale limit for dt (used in big decimal)
	 */
	public static final int SCALE_LIMIT = 10;

	public static final int ROUDING_MODE = BigDecimal.ROUND_HALF_EVEN;

	/** The set of parameter which are tuned during optimization **/
	protected List<Parameter> parameters;
	
	/** List of every suscribers to all events **/
	protected List<Suscriber> suscribers;

	protected Statistics stats;
	protected Characteristics charac;
	protected BigDecimal time = BigDecimal.ZERO;
	
	protected CommandLine command;
	protected String name;

	/** The root map of the computation tree **/
	protected AbstractMap root;

	/** True when the model will be initilized **/
	protected boolean isInitilized = false;

	/** True when we are performing assynchronous computation **/
	protected boolean assynchronousComputation = false;

	/**This will be the computation time step for model update. It is the smallest
	 * dt in the parameter tree and the stats
	 */
	protected BigDecimal clockStep;
	private List<AbstractMap> tracks;

	/**
	 * Dafault constructor
	 * 
	 * @param name
	 * @param dt
	 */
	public Model(String name) {
		this.name = name;
		this.suscribers = new LinkedList<Suscriber>();
		parameters = new LinkedList<Parameter>();
	}

	



	/**
	 * Initialize the model with the given script
	 * 
	 * @param contextScript
	 *            : URL of the script
	 * @throws CommandLineFormatException
	 * @throws FileNotFoundException
	 * @throws MalformedURLException
	 * @throws NullCoordinateException
	 * @throws CloneNotSupportedException
	 */
	public void initialize(String contextScript)
			throws CommandLineFormatException, FileNotFoundException,
			MalformedURLException, NullCoordinateException {
		if (!isInitilized) {
			initializeCommandLine(contextScript);
			//System.out.println("space res : " +command.get(CNFTCommandLine.RESOLUTION).get() );
			//			System.err.println("dt : " + command.get(CNFTCommandLine.DISPLAY_DT).get());
			
			parameters = new LinkedList<Parameter>();
			initializeParameters();
			initializeStatistics();
			initializeCharacteristics();

			//addParameters(root); TODO why? I thinkj its a mistake
			root.constructAllMemories();
			this.isInitilized = true;
		}
	}
	protected abstract void initializeCommandLine(String contextScript) throws CommandLineFormatException;
		//command = new CommandLineWHAT(contextScript, this);
	//this.refSpace = new DefaultRoundedSpace(
		//	command.get(CNFTCommandLine.RESOLUTION), 2,
		//	command.getBool(CNFTCommandLine.WRAP));
	

	

	/**
	 * Construct model tree
	 * 
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException
	 * 
	 */
	protected abstract void initializeParameters()
			throws CommandLineFormatException, NullCoordinateException;

	/**
	 * Construct the model statistics
	 * 
	 * @throws CommandLineFormatException
	 */
	protected abstract void initializeStatistics()
			throws CommandLineFormatException;

	/**
	 * Construct the model characteristics
	 * 
	 * @throws CommandLineFormatException
	 */
	protected abstract void initializeCharacteristics()
			throws CommandLineFormatException;

	
	

	/**
	 * First displayed parameters when the model is selected
	 * 
	 * @return
	 */
	public abstract List<Parameter> getDefaultDisplayedParameter();

	


	/**
	 * Update the root which will update the rest of the tree
	 * @param guiStep : time of update in seconds
	 * 
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	public void update(BigDecimal timeToReach) throws NullCoordinateException,
	CommandLineFormatException {
		this.modifyModel();
		clockStep = findSmallestDt();
		//System.out.println("clock step : " + clockStep);

		while(this.time.compareTo(timeToReach) <= 0){


			for(Parameter p : parameters){
				if(p instanceof Map){
					((Map)p).update(time);
				}
			}
			if (!assynchronousComputation) {//asynchrone non uniform
				//TODO 
				
				root.update(time);
			} else {
				throw new Error("TODO assynchronous not handled here");
//				int size = refSpace.getDiscreteVolume();
//				for (int i = 0; i < size; i++) {
//					root.compute((int) (Math.random() * size));
//				}
			}
			//System.out.println("Update " + time + " (time to reach : ) " + timeToReach );
			stats.update(time);

			this.time = this.time.add(clockStep);
		}
	}

	public abstract void modifyModel() throws CommandLineFormatException,
	NullCoordinateException;

	/**
	 * Duplicate a map within the tree
	 * 
	 * @param map
	 */
	public void duplicateMap(AbstractMap map) {

	}

	/**
	 * Add a map within the tree
	 * 
	 * @param map
	 */
	public void addParameter(Parameter p, AbstractMap parent) {

		parent.addParameters(p);

	}

	/**
	 * Delete a map within the tree
	 * 
	 * @param map
	 * @return
	 */
	public Parameter removeParameter(String paramName) {
		Parameter p = root.getParameter(paramName);
		p.delete();
		return p;
	}

	public void reset() {
		this.time = BigDecimal.ZERO;
		stats.reset();
		charac.reset();
		root.reset();
	}

	/**
	 * Return the root parameter of the model
	 * 
	 * @return
	 */
	public AbstractMap getRootParam() {
		return root;
	}

	/**
	 * Add several parameters to the parameters list
	 * 
	 * @param params
	 */
	public void addParameters(Parameter... params) {
		//	System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
		for (Parameter p : params) {
			parameters.add(p);
			//	System.out.println("add param: " + p);
		}
	}

	public void removeParameters(Parameter... params) {
		for (Parameter p : params) {
			parameters.remove(p);
		}
	}

	/**
	 * Information about the model
	 * 
	 * @return
	 */
	public String getText() {
		return "You should override the getText() method";
	}

	public Statistics getStatistics() {
		return stats;
	}

	

	public BigDecimal getTime() {
		return time;
	}

	public double getDt() throws CommandLineFormatException {
		return command.get(CommandLine.DISPLAY_DT).get();
	}

	

	

	public Characteristics getCharac() {
		return charac;
	}

	public CommandLine getCommandLine() {
		return command;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}



	/**
	 * Recursively look for a parameter
	 * 
	 * @param keyName
	 * @return
	 */
	public Parameter getParameter(String keyName) {
		Parameter ret = null;
		int i = 0;
		// Explore map tree

		ret = root.getParameter(keyName);
		if(ret == null){
			while (ret == null && i < parameters.size()) {
				Parameter p = parameters.get(i);
				if (p instanceof AbstractMap)
					ret = ((AbstractMap) p).getParameter(keyName);
				i++;
			}
			if (ret == null) {
				// explore stat params
				ret = stats.getParam(keyName);
				if (ret == null) {
					// Explore charac params
					ret = charac.getParam(keyName);
				}
			}
		}
		return ret;
	}

	public void addSuscriber(Suscriber s) {
		this.suscribers.add(s);
	}

	public void setAssynchronousComputation(boolean assynchronousComputation) {
		this.assynchronousComputation = assynchronousComputation;
	}

	public boolean isInitilized() {
		return isInitilized;
	}

	public void test() throws Exception {


	}

	protected BigDecimal findSmallestDt() {
		double min = root.findSmallestDt();
		double sDt = stats.getDt().get();
		if(sDt < min)
			min = sDt;
		BigDecimal ret = new BigDecimal(min);
		ret = ret.setScale(Model.SCALE_LIMIT,  Model.ROUDING_MODE);

		return ret;
	}
	
	/**
	 * Save the params
	 * 
	 * @param file
	 * @param parameters
	 *            : parameters to save
	 * @return the prameter name as a string
	 * @throws IOException
	 * @throws NullCoordinateException
	 */
	public  String save(String file,List<Parameter> toSave) throws IOException, NullCoordinateException
	{

		FileWriter fw = null;
		String ret = "[";

		for(Parameter p : toSave)
		{
			String fileName = file+"_"+p.getName()+".csv";
			ret += fileName + ",";
			fw= new FileWriter(file+"_"+p.getName()+".csv",false);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(((AbstractMap)p).displayMemory());
			out.close();
		}
		return ret.subSequence(0, ret.length()-1)+"]";
	}





	/**
	 * 
	 * @return the defult displayed stat
	 */
	public  abstract String getDefaultDisplayedStatistic() ;


	public List<AbstractMap> getTracks() {
		return tracks;
	}
}
