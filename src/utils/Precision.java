package utils;

public class Precision {
	
	public final static int FIXED_TRUNCATE = 0;
	public final static int FIXED_ROUND = 1;
	
	public static double to_real(int value,int fractional){
		return value / Math.pow(2, fractional);
	}
	
	public static int to_fp_integer(double value,int fractional){
		return round(value * Math.pow(2, fractional),FIXED_ROUND);
	}
	
	public static int round(double value,int method){
		switch (method) {
		case FIXED_TRUNCATE:
			return (int) value;
		case FIXED_ROUND:
			return (int) Math.round(value);
		default:
			return 0;
		}
		
	}

}
