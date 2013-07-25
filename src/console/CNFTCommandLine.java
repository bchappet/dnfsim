package console;

import gui.Runner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import plot.Trace;
import precision.PrecisionVar;

import maps.BadPathException;
import maps.Parameter;
import maps.Var;
import maps.VarBool;
import maps.VarString;
import model.Model;
import statistics.CharacConvergence;
import statistics.Statistics;
import coordinates.NullCoordinateException;

/**
 * TODO commandLine should not use runner
 * @author bchappet
 *
 */
public class CNFTCommandLine extends CommandLine{

	public static final String ACCERROR = CharacConvergence.ACCERROR;
	public static final String STABIT = CharacConvergence.STABIT;
	public static final String SHAPE_FACTOR = CharacConvergence.SHAPE_FACTOR;
	public static final String ACT_THRESHOLD = "activation_threshold";

	public static final String NB_DISTRACTERS = "distr_nb";
	public static final String NB_TRACKS = "tck_nb";
	public static final String NOISE_AMP = "noise_amp";
	public static final String TRACK_INTENSITY = "tck_i";
	public static final String TRACK_WIDTH = "tck_w";
	public static final String TRACK_DT = "tck_dt";
	public static final String DISTR_DT = "distr_dt";
	public static final String DISTR_INTENSITY = "distr_i";
	public static final String DISTR_WIDTH = "distr_w";
	public static final String NOISE_DT = "noise_dt";
	public static final String INPUT_DT = "input_dt";

	public static final String ALPHA = "alpha";
	public static final String IA = "ia";
	public static final String IA2 = "ia2";//Intensity of lateral weight for inhibitory spike (bilayer spike model) 
	public static final String IB = "ib";
	public static final String WA = "wa";
	public static final String WB = "wb";
	public static final String TAU = "tau";
	public static final String N = "n";
	public static final String RESOLUTION = "resolution";

	public static final String THRESHOLD = "threshold";
	public static final String RESTING_POTENTIAL = "resting_potential";
	public static final String THRESHOLD_INHIBITORY = "inhibitory_threshold";
	public static final String WRAP = "wrap";

	public static final String HARD_DT = "hard_dt";//hard ware map refreshing rate
	public static final String DT = "dt";
	public static final String DISPLAY_DT = "disp_dt"; //time refresh rate for running simu
	public static final String BUFF_CAP = "buff_cap";
	public static final String PRECISION = "precision";
	public static final String COMPUTE_CLK = "compute_clk";//frequecy of computation for har model

	
	//Espike 2 inhibitory weight constant
	public static final String INH_CST = "inh_cst";
	
	//NSpike real param tau/dt
	public static final String TAU_DT = "tau_dt";
	
	//Input files for generated input
	public static final String INPUT_FILES = "input_files";
	//Probability generation fractional part size in bit
	public static final String PROBA_FRAC = "proba_frac";
	public static final String FRAC = "frac";
	





	protected Model model;
	protected Runner runner;
	/**URL of config file (optional)**/
	protected URL contextPath;

	public CNFTCommandLine(String command,Model model) throws CommandLineFormatException
	{
		super(command);
		this.model = model;
		this.contextPath = null;
	}

	protected  String defaultScript()
	{
		return ""
				+ACCERROR+"=0.1;"			+ALPHA+"=10.0;"
				+STABIT+"=10;" 				+IA+"=1.25;"
				+IA2+"=1;"
				+ACT_THRESHOLD+"=0.75;" 	+IB+"=-0.70;"
				+SHAPE_FACTOR+"=1.5;" 		+WA+"=0.10;"
				+NB_DISTRACTERS+"=0;"		+WB+"=1.00;"
				+NB_TRACKS+"=2;"
				+NOISE_AMP+"=0.00;"			+TAU+"=0.75;"
				+TRACK_INTENSITY+"=1;"		+N+"=10;"
				+TRACK_WIDTH+"=0.1;"		+RESOLUTION+"=49;"
				+TRACK_DT+"=0.1;"  			+WRAP+"=T;"
				+DISTR_DT+"=0.1;"           +THRESHOLD+"=0.75;" 
				+THRESHOLD_INHIBITORY+"=0.74;"
				+DISTR_INTENSITY+"=1;"      +RESTING_POTENTIAL+"=0;"
				+DISTR_WIDTH+"=0.1;"		+DT+"=0.1;"
				+NOISE_DT+"=0.1;"			+HARD_DT+"=0.01;"
				+INPUT_DT+"=0.1;"			+DISPLAY_DT+"=0.1;"
				+BUFF_CAP+"=50;"			+PRECISION+"=8;"
				+COMPUTE_CLK+"=10;"			
				+INH_CST+"=-1;"				+TAU_DT+"=6.3999999999999995;"
				+INPUT_FILES+"=src/tests/files/input;"
				+PROBA_FRAC+"=7;"			+FRAC+"=8;"
				;
	}

	/**
	 * Construct a new command line with default parameters
	 * @throws CommandLineFormatException
	 */
	public CNFTCommandLine(Model model) throws CommandLineFormatException
	{
		this("",model);
	}

	//	/**
	//	 * Construct a commanLine object with a specific script file as parameters
	//	 * the parameter hich are not described in this script are initilized with the default 
	//	 * constructor
	//	 * @param url
	//	 * @throws FileNotFoundException
	//	 * @throws CommandLineFormatException
	//	 */
	//	public CNFTCommandLine(URL url,Model model) throws FileNotFoundException, CommandLineFormatException {
	//		this(model);
	//		this.contextPath = url;
	//		try{
	//			this.initWithURL(contextPath);
	//		}catch (FileNotFoundException e) {
	//			System.out.println(e.getMessage());
	//			System.out.println("Launching with defaults parameters");
	//		}
	//		
	//	}

	//	/**
	//	 * Parse the file and execut it as initial command 
	//	 * @param url
	//	 * @throws FileNotFoundException
	//	 * @throws CommandLineFormatException
	//	 */
	//	private void initWithURL(URL url) throws FileNotFoundException, CommandLineFormatException {
	//		File file = new File(url.getPath());
	//		StringBuilder fileContents = new StringBuilder((int)file.length());
	//		Scanner scanner = new Scanner(file);
	//		String command = null;
	//
	//		try {
	//			while(scanner.hasNextLine()) {        
	//				fileContents.append(scanner.nextLine() );
	//			}
	//			command = fileContents.toString();
	//			parseInitialCommand(command);
	//		} finally {
	//			scanner.close();
	//		}
	//		
	//	}

	//	/**
	//	 * Parse the file and execut it as a command 
	//	 * @param url
	//	 * @throws FileNotFoundException
	//	 * @throws CommandLineFormatException
	//	 *TODO code duplication with initWithURL
	//	 * @throws NullCoordinateException 
	//	 * @throws NumberFormatException 
	//	 */
	//	private void execURL(URL url) throws FileNotFoundException, CommandLineFormatException, NumberFormatException, NullCoordinateException {
	//		File file = new File(url.getPath());
	//		StringBuilder fileContents = new StringBuilder((int)file.length());
	//		Scanner scanner = new Scanner(file);
	//		String command = null;
	//
	//		try {
	//			while(scanner.hasNextLine()) {        
	//				fileContents.append(scanner.nextLine() );
	//			}
	//			command = fileContents.toString();
	//			parseCommand(command);
	//		} finally {
	//			scanner.close();
	//		}
	//		
	//	}

	public CNFTCommandLine(CNFTCommandLine commandLine) {
		super(commandLine);
		this.model = commandLine.model;
		this.runner = commandLine.runner;
	}



	/**
	 * Return the resulting string of the execution
	 * @param command
	 * @return
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException 
	 * @throws NumberFormatException 
	 */
	public String parseCommand(String command) throws CommandLineFormatException, NumberFormatException, NullCoordinateException
	{

		command = command.replaceAll("\\s+", "");
		String ret =""; //Output of the execution
		String[] tab = command.split(";+");

		for(String s : tab)
		{
			if(!s.isEmpty())
			{
				String[] split = s.split("=");
				String key =  split[0];
				if(split.length == 1)
				{//There is no "="
					try{
						ret += execCommand(key);
					}
					catch (CommandLineFormatException e) 
					{
						//The command wa invalid
						//We display the value of the parameter in the return string
						Var var = map.get(key);
						if(var != null)
						{
							if(var instanceof VarString)
							{
								ret += ((VarString) var).getString();
							}
							else 
							{
								ret +=  var.get();
							}
						}
						else
						{
							//We try to get the parameter in the model tree
							try{
								Parameter res = model.getRootParam().getPath(key,0,null,this);
								if(res != null)
								{
									//Print the value of the given parameter or map
									if(res instanceof Var)
										ret += res.get();
								}
								else
								{
									throw new CommandLineFormatException("La variable " + key + " n'existe pas");
								}
							}catch (BadPathException e2) {
								throw new CommandLineFormatException(e2);
							}
							

						}
					}
				}
				else //there is an "="
				{
					String obj = split[1];
					String res;
					if((res = execAffectation(key,obj)) != null)
					{
						//It was a special command
						ret += res;
					}
					else
					{
						Var var = map.get(key);
						if(var != null)
						{
							//We have to determine the type of the object
							if(obj.matches("[T|F]"))//Boolean
							{
								int val;

								if( obj.equals("T"))
									val = 1;
								else 
									val = 0;

								var.set(val);
							}
							else if(obj.matches("0b[01]+")){
								try{
									((PrecisionVar)var).set(Integer.parseInt(obj.substring(2), 2));
								}catch(NumberFormatException e){
									throw new CommandLineFormatException("Bad binary String format : " + obj);

								}
							}
							else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") )//Integer or double
							{
								var.set(Double.parseDouble(obj));
							}
							else//String by default
							{
								((VarString) var).setString(obj);
							}
						}
						else
						{
							//We try to get the parameter in the model tree
							try{
								Var param = (Var) model.getRootParam().getPath(key,0,obj,this);
								if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") ){//Integer or double
									( param).set(Double.parseDouble(obj));
								}
								else if(key.endsWith("clone")){
									map.put(obj, param);
								}
								else if(key.endsWith("share")){
									//remove the parameter from the list
									map.remove(param);
								}
								//TODO
							}catch (BadPathException e) {
								throw new CommandLineFormatException(e);
							}
						}
					}
				}
			}
			if(!ret.isEmpty() && ret.lastIndexOf("\n") != ret.length()-1)
				ret += "\n";
		}
		if(ret.endsWith("\n"))
			ret = ret.substring(0,ret.length()-1);

		model.modifyModel();
		return ret;
	}

	/**
	 * Return null if nothing was executed (the command did not exist here)
	 * @param command
	 * @param value
	 * @return
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException 
	 * @throws NumberFormatException 
	 */
	private String execAffectation(String command, String value) throws  CommandLineFormatException, NumberFormatException, NullCoordinateException {
		String ret = "";
		if(command.equals("resolution"))
		{
			Var var = map.get(command);
			double newVal = Double.parseDouble(value);
			if(var.get() != newVal){
				var.set(newVal);
				try{
					runner.hardReset();
				}catch (Exception e) {
					throw new CommandLineFormatException("HardResetError",e);
				}
			}

		}
		else if(command.equals("load"))
		{
			ret =  execScript(value);
		}
		else if(command.equals("wait"))
		{
			for(int i = 0 ; i < Integer.parseInt(value) ; i++)
			{
				runner.step();
			}
		}
		else if(command.equals("print")){
			String split[] = value.split(",");
			model.getCharac().compute();
			if(split[0].equals("all")){
				//print all characteristics
				split = model.getCharac().getTrajectoryUnitMapsName();
			}

			for(String s : split){
				Parameter p = model.getParameter(s);
				if(p == null)
					throw new CommandLineFormatException("The parameter "+s+" does not exist");

				String res = ""+p.get();
				if(ret.isEmpty()){
					ret += res;
				}else{
					ret += ","+res;
				}
			}
		}
		else if(command.equals("trace")){//return a statistic trace
			String name = value;
			
				Trace res = model.getStatistics().getTrace(name);
				if(res == null){
					throw new CommandLineFormatException("The trace " + name + " does not exist.");
				}else{
					ret = ""+res.toString(true);
				}
			
		}
		else if(command.equals("SaveStats")){
			try {
				runner.saveStats(value+".csv");
			} catch (IOException e) {
				throw new CommandLineFormatException("IO error",e);
			}
		}
		else if(command.equals("SaveMaps")){
			try {
				ret = runner.saveMaps(value);
			} catch (IOException e) {
				throw new CommandLineFormatException("IO error",e);
			}
		}

		else
			ret = null;

		return ret;
	}

	/**
	 * Exec a file in scenario line by line
	 * @param value name of the file
	 * @throws IOException 
	 * @throws CommandLineFormatException 
	 * @return the resulting output
	 * @throws NullCoordinateException 
	 * @throws NumberFormatException 
	 */
	private String execScript(String value) throws  CommandLineFormatException, NumberFormatException, NullCoordinateException {
		BufferedReader br;
		String output = "";
		try {
			br = new BufferedReader(new FileReader("./scenario/"+value));//TODO out of here!!
		} catch (FileNotFoundException e) {
			throw new CommandLineFormatException("The file " + value + " was not found");
		}
		String line = null;
		try {
			while ((line = br.readLine() )!= null)
			{
				output += this.parseCommand(line);
			}
			br.close();
		} catch (IOException e) {
			throw new CommandLineFormatException("IO error in the file reading : " + e.getMessage());
		}
		return output;

	}

	/**
	 * Execute a command 
	 * @param command
	 * @return true if the command was correct
	 * @throws NullCoordinateException 
	 */
	private String execCommand(String command) throws CommandLineFormatException {
		String ret = "";
		if(command.equals("play"))
			runner.play();
		else if(command.equals("pause"))
			runner.pause();
		else if(command.equals("step"))
			runner.step();
		else if(command.equals("reset"))
			runner.reset();
		else if(command.equals("compute"))
		{	
			model.getCharac().compute();
			//ret = model.getCharac().toString();
		}
		else if(command.equals("exit"))
			runner.exit();

		else
		{
			throw new CommandLineFormatException("the command " + command + " is invalid");
		}

		return ret;

	}

	public void parseInitialCommand(String command) throws CommandLineFormatException
	{
		command = command.replaceAll("\\s+", "");
		String[] tab = command.split(";+");
		for(String s : tab)
		{
			String[] split = s.split("=");
			String key =  split[0];
			if(split.length == 1)
			{
				throw new CommandLineFormatException(key + " invalid");
			}
			else
			{
				//We have to determine the type of the object
				String obj = split[1];
				if(obj.matches("[T|F]"))//Boolean
				{
					int val ;

					if( obj.equals("T"))
						val = 1;
					else 
						val = 0;

					map.put(key, new VarBool(key,val));
				}
				else if(obj.matches("0b[01]+")){
					Var precision = get(PRECISION);//assumes that the precision is already set
					try{
						map.put(key,new PrecisionVar(key,Integer.parseInt(obj.substring(2), 2), precision));
					}catch(NumberFormatException e){
						throw new CommandLineFormatException("Bad binary String format : " + obj);

					}


				}
				else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") )//Integer or double
				{
					//System.out.println("map.add" + key);
					map.put(key,new Var(key,Double.parseDouble(obj)));
				}
				else//String by default
				{
					map.put(key, new VarString(key,obj));
				}
			}
		}
	}

	/**
	 * Reinitilize parameters with the 
	 * @throws CommandLineFormatException 
	 * @throws FileNotFoundException 
	 * @throws NullCoordinateException 
	 * @throws NumberFormatException 
	 */
	public void reinitialize() throws FileNotFoundException, CommandLineFormatException, NumberFormatException, NullCoordinateException {
		//Reinitilize map
		parseCommand(defaultScript());
		parseCommand(command);
		//reinitilize model
	}

	public Map<String, Var> getMap() {
		return map;
	}



	public void setRunner(Runner runner) {
		this.runner = runner;
	

	}

	public CNFTCommandLine clone()
	{
		return new CNFTCommandLine(this);
	}

	//	public static void main(String[] args){
	//		String test = "0b02001";
	//		int i = Integer.parseInt(test.substring(2),2);
	//		System.out.println(i);
	//	}
	//
	//	






}
