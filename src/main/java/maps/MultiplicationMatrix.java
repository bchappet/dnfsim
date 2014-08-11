package main.java.maps;

import java.math.BigDecimal;

import main.java.space.Space;


/**
 * Show the result of A * B
 * @author bchappet
 *
 */
public class MultiplicationMatrix extends MatrixDouble2D  {
	
	private static final int A = 0;
	private static final int B = 1;

	public MultiplicationMatrix(String name, Var<BigDecimal> dt, Space space,
			Parameter... params) {
		super(name, dt, space, params);
	}

	public MultiplicationMatrix(String name, Var<BigDecimal> dt, Space space,
			double[][] values, Parameter... params) {
		super(name, dt, space, values, params);
	}

	@Override
	public void compute()
	{

		Jama.Matrix a = ((MatrixDouble2D) getParam(A)).getJamat();
		Jama.Matrix b = ((MatrixDouble2D) getParam(B)).getJamat();
//		a.print(0, 0);
//		b.print(0, 0);
//		a.print(1, 1);
//		b.print(1,1);
//		System.err.println("a : " + getParam(0).getName() + " " + a.getRowDimension() + "," +a.getColumnDimension());
//		System.err.println("b : " + getParam(1).getName() + " " + b.getRowDimension() + "," +b.getColumnDimension());
//		Jama.Matrix am = new Jama.Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
//		Jama.Matrix bm = new Jama.Matrix(new double[][]{{1,2,3}});
		
//		System.err.println("am : " + am.getRowDimension() + "," +am.getColumnDimension());
//		System.err.println("bm : "+ bm.getRowDimension() + "," +bm.getColumnDimension());
		
//		Jama.Matrix resm = am.times(bm);
//		a.print(2, 2);
//		b.transpose().print(2, 2);
		Jama.Matrix res = a.times(b);
		
//		res.print(0, 0);
		this.setJamat(res);

	}


}
