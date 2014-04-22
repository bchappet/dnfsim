package console;

import gui.Runner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import maps.BadPathException;
import maps.Parameter;
import maps.Var;
import maps.VarBool;
import maps.VarString;
import model.Model;
import plot.Trace;
import precision.PrecisionVar;
import coordinates.NullCoordinateException;

public class CommandLine  {

	
	
	public static final String DISPLAY_DT = "disp_dt"; //time refresh rate for running simu
	public static final String SIMULATION_STEP = "simu_dt";
	public static final String STAT_DT = "stat_dt";
	//TODO remove it and put it in OPTIMIZATION command line
	public static final String POP_SIZE = "pop_size";
	public static final String ELITE_RATIO = "elite_ratio";
	public static final String REEVALUATE = "reevaluate";
	public static final String PARENT_SIGMA = "parent_sigma";
	public static final String MUTATION_PROB = "mutation_prob";
	public static final String GEN_MAX = "gen_max";
	
	//http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
	static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
	
	
	public static final String PRECISION = "precision";
	

	protected String command;
	protected Map<String, Var> map;
	protected Runner runner;
	protected Model model;

	public CommandLine(String command,Model model)
			throws CommandLineFormatException {
		this.command = command;
		this.model = model;
		this.map = new HashMap<String, Var>();
		parseInitialCommand(defaultScript());
		if(command != null && !command.isEmpty())
			parseInitialCommand(command);


	}

	public CommandLine(Model model) throws CommandLineFormatException {
		this((String)null,model);

	}

	public CommandLine(CommandLine commandLine) {

	}

	protected  String defaultScript()
	{
		return ""
				+STAT_DT+"=0.1,0.01,1,0.01;"	+SIMULATION_STEP+"=0.1,0.01,1,0.01;"
				+POP_SIZE+"=30;"	+ELITE_RATIO+"=0.4;"
				+REEVALUATE+"=T;"	+PARENT_SIGMA+"=0.5;"
				+MUTATION_PROB+"=0.1;"+GEN_MAX+"=30;"
				+PRECISION+"=8,1,30,1;"
				;
	}


	/**
	 * Return the given variable
	 * @param key
	 * @return
	 * @throws CommandLineFormatExceptionAt if the key is not found
	 */
	public Var get(String key) throws CommandLineFormatException
	{
		Var ret = map.get(key);
		if(ret == null)
			throw new CommandLineFormatException(key + " is invalid");

		return ret;
	}

	/**
	 * Return the cureent value of the given variable formatted in a boolean :
	 *  ret = (val != 0)
	 * @param key
	 * @return
	 * @throws CommandLineFormatExceptionAt if the key is not found
	 */
	public boolean getBool(String key) throws CommandLineFormatException
	{
		Var var = map.get(key);
		if(var == null)
			throw new CommandLineFormatException(key + " invalid");
		return (var.get() != 0);
	}
	/**
	 * 
	 * @param key
	 * @return
	 * @throws CommandLineFormatException if the key does not correspond to a string or 
	 * the key is not found
	 */
	public String getString(String key) throws CommandLineFormatException
	{
		Var var = map.get(key);
		if(var == null)
			throw new CommandLineFormatException(key + " invalid");
		try
		{
			return ((VarString) var).getString();
		}catch (ClassCastException e) {
			throw new CommandLineFormatException("The key " + key + " does not correspond to a string parameter");
		}
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
							ret += findParameterValue(key);
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
							else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?,.*") )//Integer or double with definition set
							{
								String[] numbers = obj.split(",");
								var.set(Double.parseDouble(numbers[0]));
								var.setDefinitionSet(Double.parseDouble(numbers[1]),Double.parseDouble(numbers[2]),Double.parseDouble(numbers[3]));

							}
							else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") )//Integer or double
							{
								var.set(Double.parseDouble(obj));
							}
							else if(obj.contains("+") || obj.contains("-") || obj.contains("*") ){ //TODO || obj.contains("/")){
								//http://stackoverflow.com/questions/5085524/regular-expression-for-simple-arithmetic-string
								//TODO make a real script tool and expression parser in this case
								//TODO make Trajectory like that

								//No parenthesis

								String[] expr = obj.split(String.format(WITH_DELIMITER, "[+|-|*|/]"));
								//System.out.println(Arrays.toString(expr));
								double tmp = this.get(expr[0]).get();
								for(int i = 2 ; i < expr.length ; i+=2){
									tmp = computeOperator(tmp,expr[i-1],this.get(expr[i]).get());
								}

								var.set(tmp);
							}
							else//String by default
							{
								//System.out.println(obj);
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

								if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") ){//Integer or double
									map.put(key, new Var(key,Double.parseDouble(obj)));
								}else{
									throw new CommandLineFormatException(e);
								}
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

		//model.modifyModel();
		return ret;
	}

	/**
	 * TODO dirty
	 * @param old
	 * @param string
	 * @param string2
	 * @return
	 */
	private double computeOperator(double old, String operator, double var) {
		if(operator.equals("+")){
			return old + var;
		}else if(operator.equals("-")){
			return old - var;
		}else if(operator.equals("*")){
			return old * var;
		}else if(operator.equals("/")){
			return old / var;
		}else{
			return old;
		}
	}

	/**
	 * Return null if nothing was executed (the command did not exist here)
	 * @param command
	 * @param value
	 * @return
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException 
	 * @throws NumberFormatException 
	 * @throws BadPathException 
	 */
	private String execAffectation(String command, String value) throws  CommandLineFormatException, NumberFormatException, NullCoordinateException{
		String ret = "";
		if(command.equals("resolution"))
		{
			//			System.out.println("resolution =" + value);
			//			Var var = map.get(command);
			//			double newVal = Double.parseDouble(value);
			//			if(var.get() != newVal){
			//				var.set(newVal);
			//				try{
			//					runner.hardReset();
			//				}catch (Exception e) {
			//					throw new CommandLineFormatException("HardResetError",e);
			//				}
			//			}
			//TODO
			//throw new CommandLineFormatException("Resolution can only be set at the model initialization");

		}
		else if(command.equals("load"))
		{
			ret =  execScript(value);
		}
		
		else if(command.equals("wait"))
		{
			BigDecimal time =  new BigDecimal(value) ; //second 
			runner.simulate(time);
		}
		else if(command.equals("waitNsave"))
		{
			for(int i = 0 ; i < Integer.parseInt(value) ; i++)
			{

				runner.step();
				try {
					ret = runner.saveMaps("save/save_"+i);
				} catch (IOException e) {
					throw new CommandLineFormatException("IO error",e);
				}
			}
		}
		else if(command.equals("print")){
			String split[] = value.split(",");
			model.getCharac().compute();

			for(String s : split){
				if(s.equals("all")){
					String[] all = model.getCharac().getTrajectoryUnitMapsName();
					for(String s2 : all){
						ret += findParameterValue(s2) + ",";
					}

				}else{
					ret += findParameterValue(s) + ",";
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

		if(ret != null && ret.endsWith(",")){

			ret = ret.substring(0, ret.length()-1);
		}

		return ret;
	}

	/**
	 * Find the parameter (path compliant) and add its value to the result string "ret"
	 * @param key 
	 * @return  resulting string with the addition of the key value
	 * @throws CommandLineFormatException
	 */
	protected String findParameterValue(String key) throws CommandLineFormatException{
		String ret = "";
		//System.out.println("Find param : " + key);
		try{
			Parameter param =model.getParameter(key);
			if(param == null){
				param = model.getRootParam().getPath(key,0,null,this);
			}

			if(param != null)
			{

				//Print the value of the given parameter or map
				if(param.getSpace().isNoDim()){
					ret += param.get();
				}else{
					ret += param.hashCode();
				}

			}
			else
			{
				throw new CommandLineFormatException("La variable " + key + " n'existe pas");
			}
		}catch (BadPathException e2) {
			throw new CommandLineFormatException(e2);
		}


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
		else if(command.equals("init")) //t = 0 computation
			runner.firstComputation();
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
		else if(command.equals("args")){
			for(String k : map.keySet()){
				ret += k + "=";
				ret += map.get(k).get()+"; ";
			}
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
				else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?,.*") )//Integer or double with definition set
				{
					String[] numbers = obj.split(",");
					//	System.out.println("map.add " + key + " val : " +Double.parseDouble(numbers[0])  + " reste : " +  Arrays.toString(numbers));
					Var var = new Var(key,Double.parseDouble(numbers[0]),Double.parseDouble(numbers[1]),Double.parseDouble(numbers[2]),Double.parseDouble(numbers[3]));
					map.put(key,var);

				}
				else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") )//Integer or double
				{
					//System.out.println("map.add " + key + " val : " +Double.parseDouble(obj) );
					Var var = new Var(key,Double.parseDouble(obj));
					map.put(key,var);
				}

				else//String by default
				{
					map.put(key, new VarString(key,obj));
				}
			}
		}
	}

	/**
	 * Reinitialize parameters with the  default script and the context command
	 * @throws CommandLineFormatException 
	 * @throws FileNotFoundException 
	 * @throws NullCoordinateException 
	 * @throws NumberFormatException 
	 */
	public void reinitialize() throws FileNotFoundException, CommandLineFormatException, NumberFormatException, NullCoordinateException {
		//Reinitialize map
		parseCommand(defaultScript());
		parseCommand(command);
		//Reinitialize model
	}



	public Map<String, Var> getMap() {
		return map;
	}

	public String getScript() {
		return command;
	}
	
	public void setRunner(Runner runner) throws CommandLineFormatException {
		this.runner = runner;
		runner.setSimulationStep(get(CommandLine.SIMULATION_STEP));


	}



}
