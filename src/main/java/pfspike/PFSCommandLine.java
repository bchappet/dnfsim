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
	public static final String A_E = "a_e";
	public static final String A_I = "a_i";
	public static final String B_E = "b_e";
	public static final String B_I = "b_i";
	public static final String TORIC = "toric";
	
	private static final int DEFAULT_SIZE = 9;
	private static final double DEFAULT_A_E = 1.2*(1./7.)/20.;
	private static final double DEFAULT_A_I = -0.3/20.;
	private static final double DEFAULT_B_E = -0.5;//-5.0;
	private static final double DEFAULT_B_I = 0.0;
	
	
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
				NB_SPIKE+"=20;"+
				THRESHOLD+"=bd1.0;"+
				TRANSITION_MATRIX_FILE+"=transitonMatrixFiles/PFTransitionMatrixFile"+DEFAULT_SIZE+";"+
				WRITE_TRANSITION_MATRIX_FILE + "=False;"+
				A_E+"=bd"+DEFAULT_A_E+";"+
				A_I+"=bd"+DEFAULT_A_I+";"+
				B_E+"=bd"+DEFAULT_B_E+";"+
				B_I+"=bd"+DEFAULT_B_I+";"+
				TORIC + "=F;";
				
	}
}
