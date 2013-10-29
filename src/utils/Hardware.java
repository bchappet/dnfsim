package utils;

public class Hardware {

	/**
	 * Rounding mode
	 */
	public static final int CAST = 0;
	public final static int ROUND = 1;

	/**
	 * 
	 * @param logic_vector LBV...HBV
	 * @return the integer equivalent of the logic vector
	 */
	public static int toInteger(int[] logic_vector) {
		int res = 0;
		for(int i = 0 ;i < logic_vector.length ; i++){
			res += Math.pow(2, i) * logic_vector[i];
		}
		return res;
	}

	/**
	 * Transform a integer into a 6 bit logic_vector LBV...HBV
	 *  
	 * @param rule
	 * @return
	 */
	public static int[] toVector(int val,int vec_size){
		int arraySize = vec_size;
		int rest = val;
		int[] logic_vector = new int[arraySize];
		int i = 0;
		while(rest > 0){
			logic_vector[i] = rest % 2;
			rest = rest / 2;
			i ++;
		}

		for(int j=i ; j < arraySize ; j++){
			logic_vector[i] = 0;
		}
		//ArrayUtils.reverse(logic_vector);
		return  logic_vector;
	}

	/**
	 * Transform a double into the fp double with the given fractional part
	 * @param val
	 * @param frac
	 * @return
	 */
	public static double toFPDouble(double val, int frac,int roundingMode) {
		double fact =  Math.pow(2,frac);
		double to_round =  val*fact;

		double rounded = 0;
		switch(roundingMode){
		case CAST : rounded = (int) to_round;break;
		case ROUND : rounded = Math.round(to_round);break;
		}

		return rounded/fact;

	}
	
	

	/**
	 * Simulate a shifting
	 * @param val : the fp val to shift (divide)
	 * @param divisor : should be 2^x
	 * @param frac : fractional part of fp encoding
	 * @return
	 */
	public static double shiftRight(double val, double div,int frac) {
		double tmp = val/div;
		double res = toFPDouble(tmp,frac,CAST);
		return res;

	}
	/**
	 * 
	 * Simulate a shifting. More slow version with a lot of checking
	 * @param val : the fp val to shift (divide)
	 * @param divisor : should be 2^x
	 * @param frac : fractional part of fp encoding
	 * @return
	 */
	public static double shiftRightComplex(double val, double div,int frac) {
		double fact =  Math.pow(2,frac);
		double  tmpa =  (val * fact);
		int entier = (int) tmpa;
		assert tmpa == entier : ""+ val + " was not  a fixed point value";
		
		int nbShift = findPow2((int)div);
		
		entier = entier >> nbShift;
		
		double res =  entier/fact;
		//System.out.println("res : " + res + " >= " + 1/fact + " compare : " + Double.compare(res, 1/fact));
		if( Double.compare(res, 1/fact) < 0){
			throw new AssertionError( "res is to small (" + res +")");
		}
		return res;
		
	}
	
	/**
	 * Return the corresponding power of 2 value
	 * @param div x²
	 * @return x
	 */
	private static int findPow2(int div) {
		int i = 0;
		int tmp = div;
		while(tmp >1)
		{
			tmp = tmp /2;
			i++;
		}
		if( div != Math.pow(2, i)){
			throw new AssertionError("Wrong result : 2^" +i + " != " + div); 
		}
		
		return i;
	}

}
