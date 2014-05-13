package main.java.model;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.gui.Suscriber;
import main.java.maps.AbstractMap;
import main.java.maps.HasChildren;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.statistics.Characteristics;
import main.java.statistics.Statistics;

public abstract class Model implements HasChildren<Parameter> {

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
	protected Map root;

	/** True when the main.java.model will be initilized **/
	protected boolean isInitilized = false;

	/** True when we are performing assynchronous computation **/
	protected boolean assynchronousComputation = false;

	/**This will be the computation time step for main.java.model update. It is the smallest
	 * dt in the parameter tree and the statsControler
	 */
	protected BigDecimal clockStep;

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
	 * construct the model specific command line
	 * @return
	 * @throws CommandLineFormatException
	 */
	public CommandLine constructCommandLine() throws CommandLineFormatException{
		return new CommandLine();
	}


	/**
	 * Initialize the main.java.model with the given script
	 * 
	 * @param contextScript
	 *            : URL of the script
	 * @throws CommandLineFormatException
	 * @throws FileNotFoundException
	 * @throws MalformedURLException
	 * @throws NullCoordinateException
	 * @throws CloneNotSupportedException
	 */
	public void initialize(CommandLine cl)
			throws CommandLineFormatException, FileNotFoundException,
			MalformedURLException, NullCoordinateException {
		if (!isInitilized) {
			this.command = cl;
			parameters = new LinkedList<Parameter>();
			initializeParameters();
			initializeStatistics();
			initializeCharacteristics();

			addParameters(this.root,stats,charac);
			this.isInitilized = true;
		}
	}
	@Override
	public Parameter getIndex(int index) {
		return parameters.get(index);
	}


	@Override
	public List<Parameter> getValues() {
		return parameters;
	}

	

	/**
	 * Construct main.java.model tree
	 * 
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException
	 * 
	 */
	protected abstract void initializeParameters()
			throws CommandLineFormatException, NullCoordinateException;

	/**
	 * Construct the main.java.model main.java.statistics
	 * 
	 * @throws CommandLineFormatException
	 */
	protected abstract void initializeStatistics()
			throws CommandLineFormatException;

	/**
	 * Construct the main.java.model characteristics
	 * 
	 * @throws CommandLineFormatException
	 */
	protected abstract void initializeCharacteristics()
			throws CommandLineFormatException;

	
	

	/**
	 * First displayed parameters when the main.java.model is selected
	 *TODO not very MVC init with a script
	 * 
	 * @return
	 */
	public abstract String[] getDefaultDisplayedParameter();

	


//	/**
//	 * Update the root which will update the rest of the tree
//	 * @param guiStep : time of update in seconds
//	 * 
//	 * @throws NullCoordinateException
//	 * @throws CommandLineFormatException
//	 */
//	public void update(BigDecimal timeToReach) throws NullCoordinateException,
//	CommandLineFormatException {
//		this.modifyModel();
//		clockStep = findSmallestDt();
////		System.out.println("clock step : " + clockStep);
//		
//
//		while(this.time.compareTo(timeToReach) <= 0){
//
//
//			for(Parameter p : parameters){
//				if(p instanceof Map){
//					((Map)p).update(time);
//				}
//			}
//			if (!assynchronousComputation) {//asynchrone non uniform
//				//TODO 
//				
//				root.update(time);
//			} else {
//				throw new Error("TODO assynchronous not handled here");
////				int size = refSpace.getDiscreteVolume();
////				for (int i = 0; i < size; i++) {
////					root.compute((int) (Math.random() * size));
////				}
//			}
//			//System.out.println("Update " + time + " (time to reach : ) " + timeToReach );
//			statsControler.update(time);
//
//			this.time = this.time.add(clockStep);
//		}
//	}

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

	

	public void reset() {
		this.time = BigDecimal.ZERO;
		stats.reset();
		charac.reset();
		root.reset(); //TODO
	}

	/**
	 * Return the root parameter of the main.java.model
	 * 
	 * @return
	 */
	public Parameter getRootParam() {
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
	 * Information about the main.java.model
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



	

	public void addSuscriber(Suscriber s) {
		this.suscribers.add(s);
	}

	public void setAssynchronousComputation(boolean assynchronousComputation) {
		this.assynchronousComputation = assynchronousComputation;
	}

	public boolean isInitilized() {
		return isInitilized;
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
	 * @return the defult displayed stat //TODO not very mvc
	 * @deprecated
	 */
	public  abstract String getDefaultDisplayedStatistic() ;
		
	


	public Model clone(){
		//TODO
		return null;
	}


}
