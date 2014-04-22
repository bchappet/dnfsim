package console;

import gui.Runner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
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

/**
 * TODO commandLine should not use runner
 * @author bchappet
 *
 */
public class CNFTCommandLine extends CommandLine{

	
	/**Acceptable error : error threshold to ensure tracking**/
	public final static String ACCERROR = "accError";
	/**Number of iteration to ensure convergence**/
	public final static String STAB_TIME = "stabIt";
	/**factor applied to the stimulu shape to infer minimum aera**/
	public final static String SHAPE_FACTOR = "shapeFactor";
	
	public static final String ACT_THRESHOLD = "activation_threshold";

	public static final String NB_DISTRACTERS = "distr_nb";
	public static final String NB_TRACKS = "tck_nb";
	public static final String NOISE_AMP = "noise_amp";
	public static final String TRACK_INTENSITY = "tck_i";
	public static final String TRACK_WIDTH = "tck_w";
	public static final String TRACK_CENTER = "tck_center";
	public static final String TRACK_RADIUS = "tck_radius";
	public static final String TRACK_PERIOD = "tck_period";

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
	
	public static final String BUFF_WIDTH = "buff_width";
	
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

	//Model som
	public static final String DT_DNF = "dt_dnf";
	public static final String LEARNING_RATE = "learn_rate";
	public static final String STAT_DT = "stat_dt";
	public static final String FILE_DT = "file_dt";

	//Mvt detection
	public static final String ANGLE = "angle";
	public static final String TCK_SPEED = "tck_speed";
	//Filter form mvt detection
	public static final String FILTRE_IE = "filtre_ie";
	public static final String FILTRE_WE = "filtre_we";
	public static final String FILTRE_CXE = "filtre_cxe";
	public static final String FILTRE_CYE = "filtre_cye";

	public static final String FILTRE_II = "filtre_ii";
	public static final String FILTRE_WI = "filtre_wi";
	public static final String FILTRE_CXI = "filtre_cxi";
	public static final String FILTRE_CYI = "filtre_cyi";




	


	



	protected Model model;
	
	/**URL of config file (optional)**/
	protected URL contextPath;

	public CNFTCommandLine(String command,Model model) throws CommandLineFormatException
	{
		super(command,model);
		this.contextPath = null;
	}

	protected  String defaultScript()
	{
		return ""
				+ACCERROR+"=0.12;"			+ALPHA+"=10,0,20,0.1;"
				+STAB_TIME+"=1;" 			+IA+"=1.25,-10,10,0.01;"
				+IA2+"=1;"
				+ACT_THRESHOLD+"=0.75;" 	+IB+"=-0.70,-10,10,0.01;"
				+SHAPE_FACTOR+"=2.0;" 		+WA+"=0.10,0,10,0.01;"
				+NB_DISTRACTERS+"=0,0,40,1;"		+WB+"=1.00,0,10,0.01;"
				+NB_TRACKS+"=2,0,40,1;"
				+NOISE_AMP+"=0.00,0,10,0.01;"			+TAU+"=0.75,0,10,0.01;"
				+TRACK_INTENSITY+"=1,0,10,0.1;"		+N+"=10,0,100,1;"
				+TRACK_WIDTH+"=0.1,0,1,0.1;"		+RESOLUTION+"=49,1,200,2;"
				+TRACK_DT+"=0.1,0,100,0.1;"  			+WRAP+"=T;"
				+DISTR_DT+"=0.1,0,10,0.1;"           +THRESHOLD+"=0.75,0,10,0.01;" 
				+THRESHOLD_INHIBITORY+"=0.74;"
				+DISTR_INTENSITY+"=1,0,10,0.1;"     +RESTING_POTENTIAL+"=0;"
				+DISTR_WIDTH+"=0.1,0,1,0.1;"		+DT+"=0.1,0,10,0.1;"
				+NOISE_DT+"=0.1,0,10,0.1;"			+HARD_DT+"=0.01,0,10,0.01;"
				+INPUT_DT+"=0.1,0,10,0.1;"			+DISPLAY_DT+"=0.1,0,10,0.1;"
				+BUFF_WIDTH+"=6;"			+PRECISION+"=8,1,30,1;"
				+COMPUTE_CLK+"=10;"			
				+INH_CST+"=-1;"				+TAU_DT+"=6.3999999999999995;"
				+INPUT_FILES+"=src/tests/files/input;"
				+PROBA_FRAC+"=7,1,30,1;"			+FRAC+"=8,1,30,1;"
				+DT_DNF+"=0.01,0,100,0.0001;"			+LEARNING_RATE+"=0.1,0,1,0.001;"
				+STAT_DT+"=0.1,0,100,0.0001;"	+FILE_DT+"=0.1,0,100,0.0001;"
				+ANGLE+"=0,-180,180,0.1;"		+TCK_SPEED+"=0.5,0,1,0.01;"
				+FILTRE_IE+"=1,0,10,0.01;"		+SIMULATION_STEP+"=0.1,0,100,0.0001;"
				+FILTRE_WE+"=0.1,0,10,0.01;"	+TRACK_CENTER+"=0.0,-10,10,0.001;"
				+FILTRE_CXE+"=0,0,10,0.01;"		+TRACK_RADIUS+"=0.3,0,20,0.01;"
				+FILTRE_CYE+"=0,0,10,0.01;"		+TRACK_PERIOD+"=36,0,10000,1;"
				+FILTRE_II+"=-1,0,10,0.01;"
				+FILTRE_WI+"=0.1,0,10,0.01;"
				+FILTRE_CXI+"=0,0,10,0.01;"
				+FILTRE_CYI+"=0,0,10,0.01;"
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
