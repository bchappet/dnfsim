package test.java.network.probabilisticFlooding;

import static org.junit.Assert.*;
import main.java.console.CommandLineFormatException;
import main.java.network.probalisticFlooding.PFUtils;

import org.junit.Test;

public class PFUtilsTest {

	@Test
	public void test() {
		double[][] tab = {
				{0,1,1,1,0,0,1,0,0},
				{1,0,1,0,1,0,0,1,0},
				{1,1,0,0,0,1,0,0,1},
				{1,0,0,0,1,1,1,0,0},
				{0,1,0,1,0,1,0,1,0},
				{0,0,1,1,1,0,0,0,1},
				{1,0,0,1,0,0,0,1,1},
				{0,1,0,0,1,0,1,0,1},
				{0,0,1,0,0,1,1,1,0}
				};
				
		
		double[][] tab2 = PFUtils.generateToricAdjacentMatrix(3);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(tab[i][j],tab2[i][j],0.00000001);
			}
		}
	}

}
