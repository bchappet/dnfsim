package draft;

import java.util.Arrays;

public class SplitRegexp {

	public SplitRegexp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		String equation = "$1/($2^2)*(40^2)/$3";
		String regex = "(?<=op)|(?=op)".replace("op", "[-+*^/()]");
	    System.out.println(java.util.Arrays.toString(
	        equation.split(regex)));
	    
	    String test = "8+$1/($2^2)*(40^2)/$3";
	    System.out.println(Arrays.toString(test.split("\\$[0-9]+")));
	    
	   
	    
	    
	    

	}

}
