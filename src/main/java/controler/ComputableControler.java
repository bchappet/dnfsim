package main.java.controler;

import java.math.BigDecimal;

public interface ComputableControler {
	
	/**
	 * Compute the main.java.model and update the main.java.view
	 * @param currentTime
	 */
	public void compute(BigDecimal currentTime);

	/**
	 * Compute the next computation time
	 * @return
	 */
	public BigDecimal getNextTime();

}
