package main.java.view;

import java.io.PrintStream;
import java.math.BigDecimal;

import main.java.maps.Map;

/**
 * View associated with the computation
 * @author benoit
 *
 */
public class ComputationView {
	
	/**Print strem for the output**/
	private PrintStream ps;
	
	public ComputationView(PrintStream ps){
		this.ps = ps;
	}

	
	public void onChange(Map m) {
		this.ps.println("compute"+m.getName());
		
	}

	public void setTime(BigDecimal time) {
		this.ps.println("time="+time);
		
	}

}
