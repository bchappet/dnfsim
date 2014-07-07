package main.java.maps;

import java.math.BigDecimal;

/**
 * Computable object can be updated with the compute method
 * 
 * @author bchappet
 *
 */
public interface Computable {
	
	public void compute();

	public BigDecimal getTime();

	public Var<BigDecimal> getDt();

	public void setTime(BigDecimal currentTime);

}
