package model;

import gui.Node;
import gui.Suscriber;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import maps.AbstractMap;
import maps.Map;
import maps.Parameter;
import maps.Var;
import statistics.Characteristics;
import statistics.Statistics;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;
import draft.RandomTestAbstractMap;

public abstract class Model implements Node {

	/** The set of parameter which are tuned during optimization **/
	protected List<Parameter> parameters;
	protected List<AbstractMap> trackable;
	/** List of every suscribers to all events **/
	protected List<Suscriber> suscribers;

	protected Statistics stats;
	protected Characteristics charac;
	protected double time;
	protected Space refSpace;
	protected CNFTCommandLine command;
	protected String name;

	/** The root map of the computation tree **/
	protected AbstractMap root;

	/** True when the model will be initilized **/
	protected boolean isInitilized = false;

	/** True when we are performing assynchronous computation **/
	protected boolean assynchronousComputation = false;

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
	 * Initialize a model with default parameters
	 * 
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException
	 * @throws CloneNotSupportedException
	 */
	public void initialize() throws CommandLineFormatException,
			NullCoordinateException, CloneNotSupportedException {
		if (!isInitilized) {
			command = new CNFTCommandLine(this);
			this.refSpace = new DefaultRoundedSpace(
					command.get(CNFTCommandLine.RESOLUTION), 2,
					command.getBool(CNFTCommandLine.WRAP));
			this.trackable = new LinkedList<AbstractMap>();
			parameters = new LinkedList<Parameter>();
			initializeParameters();
			initializeStatistics();
			initializeCharacteristics();

			addParameters(root);
			this.isInitilized = true;
		}
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

			command = new CNFTCommandLine(contextScript, this);
			this.refSpace = new DefaultRoundedSpace(
					
					command.get(CNFTCommandLine.RESOLUTION), 2,
					command.getBool(CNFTCommandLine.WRAP));
			//System.out.println("space res : " +command.get(CNFTCommandLine.RESOLUTION).get() );
//			System.err.println("dt : " + command.get(CNFTCommandLine.DISPLAY_DT).get());
			this.trackable = new LinkedList<AbstractMap>();
			parameters = new LinkedList<Parameter>();
			initializeParameters();
			initializeStatistics();
			initializeCharacteristics();

			addParameters(root);
			this.isInitilized = true;
		}
	}
	
	/**
	 * Initialize a new model from the parameter of the other
	 * @param other
	 */
	public void initialize(Model other)throws CommandLineFormatException, FileNotFoundException,
	MalformedURLException, NullCoordinateException {
		command = other.command;
		
		this.refSpace = new DefaultRoundedSpace(
				command.get(CNFTCommandLine.RESOLUTION), 2,
				command.getBool(CNFTCommandLine.WRAP));
		this.trackable = new LinkedList<AbstractMap>();
		parameters = new LinkedList<Parameter>();
		initializeParameters();
		initializeStatistics();
		initializeCharacteristics();

		addParameters(root);
		this.isInitilized = true;
		
		
	}

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
	 * Save the params
	 * 
	 * @param file
	 * @param parameters
	 *            : parameters to save
	 * @return the prameter name as a string
	 * @throws IOException
	 * @throws NullCoordinateException
	 */
	public abstract String save(String file, List<Parameter> parameters)
			throws IOException, NullCoordinateException;

	/**
	 * First displayed parameters when the model is selected
	 * 
	 * @return
	 */
	public abstract List<Parameter> getDefaultDisplayedParameter();

	/**
	 * Update the root which will update the rest of the tree
	 * @param stepTime : time of update in seconds
	 * 
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	public void update(double stepTime) throws NullCoordinateException,
			CommandLineFormatException {
		this.modifyModel();
//		System.out.println("time : " + time + " dt : " + command.get(CNFTCommandLine.DISPLAY_DT).get() );
		this.time += stepTime;
//		System.out.println("this.time : " + this.time);
		
		//System.out.println("Update");
		for(Parameter p : parameters){
//			System.out.println("time "+time);
			if(p instanceof Map){
				((Map)p).update(time);
			}
		}
		
		
		if (!assynchronousComputation) {
			
			root.update(time);
		} else {
			int size = refSpace.getDiscreteVolume();
			for (int i = 0; i < size; i++) {
				root.compute((int) (Math.random() * size));
			}
		}
		stats.update(time);
//		System.out.println(stats.getWtrace());
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
		this.time = 0;
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
		for (Parameter p : params) {
			parameters.add(p);
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

	public Space getRefSpace() {
		return refSpace;
	}

	public double getTime() {
		return time;
	}

	public double getDt() throws CommandLineFormatException {
		return command.get(CNFTCommandLine.DISPLAY_DT).get();
	}

	/**
	 * Return the list of trackable object
	 * 
	 * @return
	 */
	public List<AbstractMap> getTracks() {
		return trackable;
	}

	/**
	 * Return the trackable object with the given hashcode
	 * 
	 * @param trackedStimulis
	 * @return null if no trackable have the corresponding hashh code
	 */
	public AbstractMap getTracked(double hashcode) {
		AbstractMap ret = null;
		for (AbstractMap t : trackable) {
			if (t.hashCode() == hashcode)
				ret = t;
		}
		return ret;
	}

	public Characteristics getCharac() {
		return charac;
	}

	public CNFTCommandLine getCommandLine() {
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

	

	

}
