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
	
	
	public static String toVectorString(int val,int vec_size) throws  BitOverflowException{
		int[] vec = toVector(val,vec_size);
		String ret = "\"";
		try{
			for(int i = vec_size -1;i  >=0 ; i --){
				ret += "" + vec[i] ;
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			throw  new BitOverflowException("The vector width is to small " + vec_size + " to encode the integer " + val,e);
		}
		
		ret += "\"";
		
		return ret;
		
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
	 * Return the nb bit necessar to encode this int
	 * @param val /TODO can be optimized
	 * @return
	 */
	public static int necessaryNbBit(int val){
		String bin = Integer.toBinaryString(val);
		return bin.length();
	}
	
	/**
	 * Return the corresponding power of 2 value
	 * @param div x²
	 * @return x
	 */
	public static int findPow2(int div) {
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
	
	/**
	 * Return "0000010" with n=2 and width = 7
	 * @param n position of activated bit started from the end
	 * @param width
	 * @return
	 */
	public static String nThBit(int n, int width) {
		String logic_vector = "\"";
		for(int i = 1 ; i <= width ; i++){
			if(i == width - n + 1)
				logic_vector += "1";
			else
				logic_vector += "0";
		}
		logic_vector += "\"";
		return logic_vector;
	}
	
	/**
	 * Return a FP string logic_vector(INT-1,-FRAC) or (INT,-FRAC) if signed 
	 * @param val value to transform
	 * @param INT integer part size
	 * @param FRAC fractional part size
	 * @param signed true if we want a signed number (TODO not ready!!)
	 * @return a FP string logic_vector(INT-1,-FRAC) or (INT,-FRAC) if signed
	 *TODO not signed compatible
	 */
	public static String toFPVectorString(double val,int INT,int FRAC,boolean signed){
		double unsigned_val;
		boolean negative;
		String ret;
		
		if(signed){
			unsigned_val = Math.abs(val);
			negative = (val  < 0);
		}else{
			unsigned_val = val;
		}
		
		int int_part = (int) unsigned_val;
		double frac_part = unsigned_val - int_part;
		
		
		int frac_part_to_int = (int)Math.round(frac_part * Math.pow(2, FRAC));
		String bit_frac_part = Integer.toBinaryString(frac_part_to_int);
		bit_frac_part = fill(bit_frac_part,FRAC,false);
		String bit_int_part = Integer.toBinaryString(int_part);
		bit_int_part = fill(bit_int_part,INT,false);
		
		if(signed){
			ret = bit_int_part + bit_frac_part;
		}else{
			ret = bit_int_part + bit_frac_part;
		} 
		
		//finaly cut if to large
		if(ret.length() > INT + FRAC){
			ret = ret.substring((ret.length() - ( INT + FRAC)), ret.length());
		}
		
		
		return "\"" + ret + "\"";

		
	}
	
	public static String fill(String vector,int width,boolean signed){
		String ret = vector;
		char fillWith = '0';
		if(signed){
			fillWith = vector.charAt(0); //TODO
		}else{
			fillWith = '0';
		}
		
		while(ret.length() < width){
			ret = fillWith + ret; 
		}
		
		return ret;
		
	}
	
	

}
