package main.java.reservoirComputing;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.controler.ModelControler;
import main.java.model.Model;

public class ESNCommandLine extends CommandLine {

	
	public static final String SEP = "sep";//separator for csv files
	
	public static final String LENGTH_RESERVOIR = "length_reservoir";
	public static final String WRAP_RESERVOIR = "wrap_reservoir";
	public static final String DT_RESERVOIR = "dt_reservoir";
	public static final String WRR_FILE = "wrr_file";
	public static final String WRO_FILE = "wro_file";
	public static final String DT_LEARNING = "dt_learning";
	public static final String LENGHT_INPUT = "lenght_input";
	public static final String DT_INPUT = "dt_input";
	public static final String DT_OUTPUT = "dt_output";
	public static final String LEARNING_RATE = "alpha";
	public static final String LENGHT_OUTPUT = "res_output";
	//for targeted output
	public static final String OUTPUT_MEMORY = "output_memory";
	public static final String OUTPUT_POLYNOMIAL_DEGREE = "output_polynomial_degree";
	
	

	public ESNCommandLine() throws CommandLineFormatException {
		super();
	}


	


	public  String defaultScript()
	{
		System.out.println("here");
		return super.defaultScript() 
				
				+LENGTH_RESERVOIR+"=49,1,1000,1;"	+WRAP_RESERVOIR+"=T;"
				+DT_RESERVOIR+"=bd0.1,0,10,0.1;"	+WRR_FILE+"=files/weights;"
				+WRO_FILE+"=files/weightsOutput;"	
				+DT_LEARNING+"=bd0.1,0,10,0.1;"	+LENGHT_INPUT+"=1,1,1,1;"
				+DT_INPUT+"=bd0.1,0,10,0.1;"		+DT_OUTPUT+"=bd0.1,0,10,0.1;"
				+LEARNING_RATE+"=0.01,0,1,0.01;"+LENGHT_OUTPUT+"=1,1,1,1;"
				+OUTPUT_MEMORY+"=1,0,10,1;"		+OUTPUT_POLYNOMIAL_DEGREE+"=1,0,10,1;"
				+SEP+"=,;";
		
	}


	

}
