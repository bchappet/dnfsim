package main.java.pfspike;

import main.java.console.CNFTCommandLine;

public class PFSCommandLine extends CNFTCommandLine{
	
	public static final String I_WEIGTH = "iweigth";
	public static final String E_WEIGTH = "eweigth";
	public static final String I_NBCOMPUTATION = "inbcomputation";
	public static final String E_NBCOMPUTATION = "enbcomputation";
	public static final String NB_SPIKE = "nbspike";
	public static final String THRESHOLD = "threshold";
	public static final String MAIN_DT = "main_dt";
	public static final String TRANSITION_MATRIX_FILE = "transition_matrix_file";
	public static final String SIZE = RESOLUTION;
	public static final String STIMULIS_FILE = "stimulis_file";
	public static final String STIMULIS_DT = "stimulis_dt";
	public static final String WRITE_TRANSITION_MATRIX_FILE = "write_transition_matrix_file";
	public static final String COEFF_E = "coeff_e";
	public static final String COEFF_I = "coeff_i";
	
	private static final int DEFAULT_SIZE = 19;
	
	@Override
	protected String defaultScript() {
		
		return	super.defaultScript()+
//				I_WEIGTH+"=bd0.5;"+
//				E_WEIGTH+"=bd0.7;"+
//				I_NBCOMPUTATION+"=30;"+
//				E_NBCOMPUTATION+"=50;"+
//				SIZE+"=9;"+
//				MAIN_DT+ "=bd0.1;"+
//				NB_SPIKE+"=1;"+
//				THRESHOLD+"=bd1.0;"+
//				TRANSITION_MATRIX_FILE+"=PFTransitionMatrixFile9;"+
//				WRITE_TRANSITION_MATRIX_FILE + "=False;"+
//				COEFF_E+"=bd4.0;"+
//				COEFF_I+"=bd-1.0;";
				I_WEIGTH+"=bd0.5;"+
				E_WEIGTH+"=bd0.7;"+
				I_NBCOMPUTATION+"=30;"+
				E_NBCOMPUTATION+"=50;"+
				SIZE+"="+DEFAULT_SIZE+";"+
				//RESOLUTION+"=19;"+
				MAIN_DT+ "=bd0.1;"+
				NB_SPIKE+"=1;"+
				THRESHOLD+"=bd1.0;"+
				TRANSITION_MATRIX_FILE+"=PFTransitionMatrixFile"+DEFAULT_SIZE+";"+
				WRITE_TRANSITION_MATRIX_FILE + "=False;"+
				COEFF_E+"=bd4.0;"+
				COEFF_I+"=bd-1.0;";
				
	}
}
