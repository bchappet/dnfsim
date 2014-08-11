package main.java.reservoirComputing;

import main.java.console.CommandLine;

public class ESNCommandLine extends CommandLine {

	
	public static final String SEP = "sep";//separator for csv files
	
	public static final String LENGTH_RESERVOIR = "length_reservoir";
	public static final String WRAP_RESERVOIR = "wrap_reservoir";
	public static final String WRR_FILE = "wrr_file";
	public static final String WRO_FILE = "wro_file";
	public static final String LENGHT_INPUT = "lenght_input";
	public static final String LEARNING_RATE = "alpha";
	public static final String LENGHT_OUTPUT = "res_output";
	//for targeted output
	public static final String OUTPUT_MEMORY = "output_memory";
	public static final String OUTPUT_POLYNOMIAL_DEGREE = "output_polynomial_degree";

	public static final String INPUT_FILE = "input_file";

	public static final String TGT_OUTPUT_FILE = "tgtOutputFile";

	public static final String LEAK = "leak";
	public static final String INPUT_SCALE = "input_scale";

	public static final String DT = "dt";
	
	

	public ESNCommandLine()  {
		super();
	}


	


	public  String defaultScript()
	{
		//System.out.println("here");
		return super.defaultScript() 
				
				+LENGTH_RESERVOIR+"=49,1,1000,1;"	+WRAP_RESERVOIR+"=T;"
				+DT+"=bd0.1,0,10,0.1;"	+WRR_FILE+"=src/main/java/reservoirComputing/weights/weights;"
				+WRO_FILE+"=src/main/java/reservoirComputing/weights/weightsOutput;"	
				+LENGHT_INPUT+"=1,1,1,1;"
				+LEARNING_RATE+"=0.01,0,1,0.01;"+LENGHT_OUTPUT+"=1,1,1,1;"
				+OUTPUT_MEMORY+"=1,0,10,1;"		+OUTPUT_POLYNOMIAL_DEGREE+"=1,0,10,1;" 
				+SEP+"=,;" 						+ INPUT_FILE+"=src/main/java/reservoirComputing/data/input.dat"
				+ TGT_OUTPUT_FILE+"=src/main/java/reservoirComputing/data/input.dat;" + LEAK+"=0.1;"
				+ INPUT_FILE+"=1.0,0.0,1.0,0.01;"
				
				;
		
	}


	

}
