package main.java.console;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import main.java.controler.CharacteristicsControler;
import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.controler.ParameterControler;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.BadPathException;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.maps.Var;
import main.java.model.Model;
import main.java.plot.Trace;
import main.java.space.Coord;

public class CommandLine  {



	//public static final String DISPLAY_DT = "disp_dt"; //time refresh rate for running simu
	public static final String SIMULATION_STEP = "simu_dt";
	public static final String STAT_DT = "stat_dt";
	//TODO remove it and put it in OPTIMIZATION initScript line
	//	public static final String POP_SIZE = "pop_size";
	//	public static final String ELITE_RATIO = "elite_ratio";
	//	public static final String REEVALUATE = "reevaluate";
	//	public static final String PARENT_SIGMA = "parent_sigma";
	//	public static final String MUTATION_PROB = "mutation_prob";
	//	public static final String GEN_MAX = "gen_max";

	//http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
	static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";


	/**For simulation: simlation speed = real time * TIME_SPEED_RATIO**/
	public static final String TIME_SPEED_RATIO = "time_speed_ratio";


	protected String initScript;
	/**Associate a parameter name with its var**/
	protected Map<String, Var> map;
	/**Associate the parameter name with its definition set (min,max,step)**/
	protected Map<String, Coord<?>> definitionSet;
	/**With the main.java.model main.java.controler we will be able to control the main.java.model**/
	private ModelControler currentModelControler;
	private ComputationControler computationControler;
	private CharacteristicsControler characControler;

	/**
	 * Parse the default script and the initScript
	 * @throws CommandLineFormatException
	 */
	public CommandLine() {
		this.map = new HashMap<String, Var>();
		this.definitionSet = new HashMap<String,Coord<?>>();
	}
	/**
	 * Clear the map and Set a new context
	 * @param context
	 * @throws CommandLineFormatException
	 */
	public void setContext(String context) throws CommandLineFormatException{
		this.map.clear();
		this.initScript = context;
		parseInitialCommand(defaultScript());
		parseInitialCommand(context);
	}




	protected  String defaultScript()
	{
		return ""
				+STAT_DT+"=bd0.1,0.01,1,0.01;"	+SIMULATION_STEP+"=bd0.1,0.01,1,0.01;"
				+TIME_SPEED_RATIO+"=1.0,0.1,10.0,0.1;";
		//				+POP_SIZE+"=30;"	+ELITE_RATIO+"=0.4;"
		//				+REEVALUATE+"=T;"	+PARENT_SIGMA+"=0.5;"
		//				+MUTATION_PROB+"=0.1;"+GEN_MAX+"=30;"
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

	public void setCurentModelControler(ModelControler mc) {
		this.currentModelControler= mc;

	}



	/**
	 * Return the resulting string of the execution
	 * @param initScript
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
						//The initScript wa invalid
						//We display the value of the parameter in the return string
						Var var = map.get(key);
						if(var != null)
						{
							ret +=  var.get();
						}
						else
						{
							//We try to get the parameter in the main.java.model tree
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
						//It was a special initScript
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
							//TODO separate integer from double
							else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?,.*") )//Integer or double with definition set
							{
								String[] numbers = obj.split(",");
								var.set(Double.parseDouble(numbers[0]));
								Coord<Double> defSet = (Coord<Double>) this.definitionSet.get(key);
								defSet.set(Double.parseDouble(numbers[1]),Double.parseDouble(numbers[2]),Double.parseDouble(numbers[3]));

							}
							else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") )//Integer or double
							{
								var.set(Double.parseDouble(obj));
							}
							else if(obj.contains("+") || obj.contains("-") || obj.contains("*") ){ //TODO || obj.contains("/")){
								//http://stackoverflow.com/questions/5085524/regular-expression-for-simple-arithmetic-string
								//TODO make a real script main.resources.tool and expression parser in this case
								//TODO make Trajectory like that

								//No parenthesis

								String[] expr = obj.split(String.format(WITH_DELIMITER, "[+|-|*|/]"));
								//System.out.println(Arrays.toString(expr));
								double tmp = (Double) this.get(expr[0]).get();
								for(int i = 2 ; i < expr.length ; i+=2){
									tmp = computeOperator(tmp,expr[i-1],(Double) this.get(expr[i]).get());
								}

								var.set(tmp);
							}
							else//String by default
							{
								//System.out.println(obj);
								((Var<String>) var).set(obj);
							}
						}
						else
						{
							//We try to get the parameter in the main.java.model tree
							try{
								Var param = (Var) this.currentModelControler.getPath(key,0,obj,this);
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

		//main.java.model.modifyModel();
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
	 * Return null if nothing was executed (the initScript did not exist here)
	 * @param initScript
	 * @param value
	 * @return
	 * @throws CommandLineFormatException
	 * @throws NullCoordinateException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 * @throws BadPathException 
	 */
	private String execAffectation(String command, String value) throws  CommandLineFormatException, NumberFormatException, NullCoordinateException{
		String ret = "";
		if(command.equals("resolution"))
		{
			//			System.out.println("resolution =" + value);
			//			Var var = map.get(initScript);
			//			double newVal = Double.parseDouble(value);
			//			if(var.get() != newVal){
			//				var.set(newVal);
			//				try{
			//					currentModelControler.hardReset();
			//				}catch (Exception e) {
			//					throw new CommandLineFormatException("HardResetError",e);
			//				}
			//			}
			//TODO
			//throw new CommandLineFormatException("Resolution can only be set at the main.java.model initialization");

		}
		else if(command.equals("load"))
		{
			ret =  execScript(value);
		}

		else if(command.equals("wait"))
		{
			BigDecimal time =  new BigDecimal(value) ; //second 
			computationControler.compute(computationControler.getTime().add(time));
		}
		//		else if(command.equals("waitNsave"))
		//		{
		//			System.out.println("here");
		//			BigDecimal time =  new BigDecimal(value) ; //second 
		//			currentModelControler.simulateNSave(time);
		//		}
		else if(command.equals("print")){
			String split[] = value.split(",");
			this.characControler.compute(this.computationControler.getTime());

			for(String s : split){
				if(s.equals("all")){
					String[] all = this.characControler.getTrajectoryUnitMapsName();
					for(String s2 : all){
						ret += findParameterValue(s2) + ",";
					}

				}else{
					ret += findParameterValue(s) + ",";
				}
			}
		}

		//		else if(command.equals("trace")){//return a statistic trace
		//			String name = value;
		//
		//			Trace res = currentModelControler.getStatistics().getTrace(name);
		//			if(res == null){
		//				throw new CommandLineFormatException("The trace " + name + " does not exist.");
		//			}else{
		//				ret = ""+res.toString(true);
		//			}
		//
		//		}
		//		else if(command.equals("SaveStats")){
		//			try {
		//				currentModelControler.saveStats(value+".csv");
		//			} catch (IOException e) {
		//				throw new CommandLineFormatException("IO error",e);
		//			}
		//		}
		//		else if(command.equals("SaveMaps")){
		//			try {
		//				ret = currentModelControler.saveMaps(value);
		//			} catch (IOException e) {
		//				throw new CommandLineFormatException("IO error",e);
		//			}
		//		}

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
		ParameterControler param =currentModelControler.getTree().getControler(key);
		if(param == null){
			//param = currentModelControler.getPath(key,0,null,this); TODO
			return null;

		}

		if(param != null)
		{

			//Print the value of the given parameter or map
			if(param instanceof SingleValueParam){
				ret += ((SingleValueParam) param).get();
			}else{
				System.out.println("here");
				ret += param.displayText();
			}

		}
		else
		{
			throw new CommandLineFormatException("La variable " + key + " n'existe pas");
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
	 * Execute a initScript 
	 * @param initScript
	 * @return true if the initScript was correct
	 * @throws NullCoordinateException 
	 */
	private String execCommand(String command) throws CommandLineFormatException {
		String ret = "";
		//		if(command.equals("play"))
		//			currentModelControler.play();
		//		else if(command.equals("init")) //t = 0 computation
		//			currentModelControler.firstComputation();
		//		else if(command.equals("pause"))
		//			currentModelControler.pause();
		//		else if(command.equals("step"))
		//			currentModelControler.step();
		if(command.equals("reset"))
			currentModelControler.reset();
		//		else if(command.equals("compute"))
		//		{	
		//			currentModelControler.getCharac().compute();
		//			//ret = main.java.model.getCharac().toString();
		//		}
		else if(command.equals("args")){
			for(String k : map.keySet()){
				ret += k + "=";
				ret += map.get(k).get()+"; ";
			}
		}
		else if(command.equals("exit"))
			System.exit(0);

		else
		{
			throw new CommandLineFormatException("the initScript " + command + " is invalid");
		}

		return ret;

	}

	private void parseInitialCommand(String command) throws CommandLineFormatException
	{
		if(!command.isEmpty()){

			command = command.replaceAll("\\s+", "");
			String[] tab = command.split(";+");
			for(String s : tab)
			{
				String[] split = s.split("=");
				String key =  split[0];
				if(split.length == 1)
				{
					throw new CommandLineFormatException(key + " invalid" + " in " + s);
				}
				else
				{
					//We have to determine the type of the object
					String obj = split[1];
					if(obj.matches("[T|F]"))//Boolean
					{
						boolean val ;

						if( obj.equals("T"))
							val = true;
						else 
							val = false;

						map.put(key, new Var<Boolean>(key,val));
					}


					else if(obj.matches("[-+]?[0-9]*[0-9]+([eE][-+]?[0-9]+)?,.*") )//Integer with definition set
					{
						try{
							String[] numbers = obj.split(",");
//							System.out.println("map.add " + key + " val : " +Double.parseDouble(numbers[0])  + " reste : " +  Arrays.toString(numbers));
							Var<Integer> var = new Var<Integer>(key,Integer.parseInt(numbers[0]));
							Coord<Integer> defSet = new Coord<Integer>(Integer.parseInt(numbers[1]),Integer.parseInt(numbers[2]),Integer.parseInt(numbers[3]));
							map.put(key,var);
							this.definitionSet.put(key, defSet);
						}catch(NumberFormatException e){
							throw new CommandLineFormatException("There was an error trying to parse the var: " + key + " " + obj,e);
						}
					}
					else if(obj.matches("[-+]?[0-9]*[0-9]+([eE][-+]?[0-9]+)?") )//Integer 
					{
						//	System.out.println("map.add " + key + " val : " +Double.parseDouble(numbers[0])  + " reste : " +  Arrays.toString(numbers));
						Var<Integer> var = new Var<Integer>(key,Integer.parseInt(obj));
						map.put(key,var);
					}
					else if(obj.matches("bd[-+]?[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?,.*") )// Big Decimal with definition set
					{
						String[] numbers = obj.split(",");
						//	System.out.println("map.add " + key + " val : " +Double.parseDouble(numbers[0])  + " reste : " +  Arrays.toString(numbers));
						Var<BigDecimal> var = new Var<BigDecimal>(key,new BigDecimal(numbers[0].substring(2)));
						Coord<BigDecimal> defSet = new Coord<BigDecimal>(new BigDecimal(numbers[1]),new BigDecimal(numbers[2]),new BigDecimal(numbers[3]));
						map.put(key,var);
						this.definitionSet.put(key, defSet);

					}
					else if(obj.matches("bd[-+]?[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?") )//Big Decimal
					{
						//System.out.println("map.add " + key + " val : " +Double.parseDouble(obj) );
						Var<BigDecimal> var = new Var<BigDecimal>(key,new BigDecimal(obj.substring(2)));
						map.put(key,var);
					}
					else if(obj.matches("[-+]?[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?,.*") )// double with definition set
					{
						String[] numbers = obj.split(",");
						//	System.out.println("map.add " + key + " val : " +Double.parseDouble(numbers[0])  + " reste : " +  Arrays.toString(numbers));
						Var<Double> var = new Var<Double>(key,Double.parseDouble(numbers[0]));
						Coord<Double> defSet = new Coord<Double>(Double.parseDouble(numbers[1]),Double.parseDouble(numbers[2]),Double.parseDouble(numbers[3]));
						map.put(key,var);
						this.definitionSet.put(key, defSet);

					}
					else if(obj.matches("[-+]?[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?") )// double
					{
						//System.out.println("map.add " + key + " val : " +Double.parseDouble(obj) );
						Var<Double> var = new Var<Double>(key,Double.parseDouble(obj));
						map.put(key,var);
					}

					else//String by default
					{
						map.put(key, new Var<String>(key,obj));
					}
				}
			}
		}
	}

	/**
	 * Reinitialize parameters with the  default script and the context initScript
	 * @throws CommandLineFormatException 
	 * @throws FileNotFoundException 
	 * @throws NullCoordinateException 
	 * @throws NumberFormatException 
	 */
	public void reinitialize() throws FileNotFoundException, CommandLineFormatException, NumberFormatException, NullCoordinateException {
		//Reinitialize map
		parseCommand(defaultScript());
		parseCommand(initScript);
		//Reinitialize main.java.model
	}



	public Map<String, Var> getMap() {
		return map;
	}

	public String getScript() {
		return initScript;
	}




	public void setComputationControler(
			ComputationControler computationControler) {
		this.computationControler = computationControler;

	}




	public void setCharacControler(CharacteristicsControler characContrl) {
		this.characControler = characContrl;
	}




}
