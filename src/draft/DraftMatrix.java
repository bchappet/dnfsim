package draft;

import Jama.Matrix;

public class DraftMatrix {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[][] array = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.}};
		Matrix a = new Matrix(array);
		Matrix inv = a.inverse();
		inv.print(1,10);
		

	}

}
