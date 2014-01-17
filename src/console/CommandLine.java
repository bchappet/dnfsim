package console;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import maps.BadPathException;
import maps.Var;
import maps.VarBool;
import maps.VarString;
import precision.PrecisionVar;
import coordinates.NullCoordinateException;

public class CommandLine  {

	//	protected Var populationSize;
	//	protected Var nbElite;
	//	protected Var eliteRatio;
	//	protected Var reevaluate;
	//	protected Var parent_sigma;
	//	protected Var mutation_prob;
	//	protected Var gen_max;

	public static final String POP_SIZE = "pop_size";
	public static final String ELITE_RATIO = "elite_ratio";
	public static final String REEVALUATE = "reevaluate";
	public static final String PARENT_SIGMA = "parent_sigma";
	public static final String MUTATION_PROB = "mutation_prob";
	public static final String GEN_MAX = "gen_max";

	protected String command;
	protected Map<String, Var> map;

	public CommandLine(String command)
			throws CommandLineFormatException {
		this.command = command;
		this.map = new HashMap<String, Var>();
		parseInitialCommand(defaultScript());
		if(command != null && !command.isEmpty())
			parseInitialCommand(command);


	}

	public CommandLine() throws CommandLineFormatException {
		this((String)null);

	}

	public CommandLine(CNFTCommandLine commandLine) {

	}

	protected  String defaultScript()
	{
		return ""
				+POP_SIZE+"=30;"	+ELITE_RATIO+"=0.4;"
				+REEVALUATE+"=T;"	+PARENT_SIGMA+"=0.5;"
				+MUTATION_PROB+"=0.1;"+GEN_MAX+"=30;"
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
	 * @throws BadPathException 
	 */
	public String parseCommand(String command) throws CommandLineFormatException, NumberFormatException, NullCoordinateException, BadPathException
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
						throw new CommandLineFormatException("La variable " + key + " n'existe pas");
					}

				}
				else
				{
					String obj = split[1];
					String res;

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
						else//String by default
						{
							((VarString) var).setString(obj);
						}
					}
					else
					{
						throw new CommandLineFormatException("La variable " + key + " n'existe pas");
					}

				}
			}
			if(!ret.isEmpty() && ret.lastIndexOf("\n") != ret.length()-1)
				ret += "\n";
		}
		if(ret.endsWith("\n"))
			ret = ret.substring(0,ret.length()-1);
	
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
				throw new CommandLineFormatException(key + " invalid" );
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
				else if(obj.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?,.*") )//Integer or double with definition set
				{
					String[] numbers = obj.split(",");
				//	System.out.println("map.add " + key + " val : " +Double.parseDouble(numbers[0])  + " reste : " +  Arrays.toString(numbers));
					Var var = new Var(key,Double.parseDouble(numbers[0]),Double.parseDouble(numbers[1]),Double.parseDouble(numbers[2]),Double.parseDouble(numbers[3]));
					map.put(key,var);
					
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
	 * @throws BadPathException 
	 */
	public void reinitialize() throws FileNotFoundException, CommandLineFormatException, NumberFormatException, NullCoordinateException, BadPathException {
		parseCommand(defaultScript());
		parseCommand(command);
	}

	public Map<String, Var> getMap() {
		return map;
	}

	public String getScript() {
		return command;
	}



}
