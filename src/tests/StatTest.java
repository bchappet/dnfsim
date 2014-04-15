package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import statistics.StatCNFT;
import statistics.StatisticsCNFT;

public class StatTest {

	@Test
	public void testisError() {
		Double[] vect = {(double) StatisticsCNFT.ERROR,(double) StatisticsCNFT.ERROR};
		
		assertTrue(StatCNFT.isError(vect));
		
		Double[] vect2 = {-1.0,(double) StatisticsCNFT.ERROR};
		assertTrue(StatCNFT.isError(vect2));
		
		Double[] vect3 = {-1.00000000001,(double) StatisticsCNFT.ERROR};
		assertFalse(StatCNFT.isError(vect3));
	}

}
