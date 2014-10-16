package main.java.maps;

import main.java.space.Space;
import main.resources.utils.Matrix;

import java.math.BigDecimal;


/**
 * Show the result of A * B
 * @author bchappet
 *
 */
public class AdditionMatrix extends MatrixDouble2D  {

	private static final int A = 0;
	private static final int B = 1;

	public AdditionMatrix(String name, Var<BigDecimal> dt, Space space,
                          Parameter... params) {
		super(name, dt, space, params);
	}

	public AdditionMatrix(String name, Var<BigDecimal> dt, Space space,
                          double[][] values, Parameter... params) {
		super(name, dt, space, values, params);
	}

	@Override
	public void compute()
	{

		Matrix a = ((MatrixDouble2D) getParam(A)).getMat();
		Matrix b = ((MatrixDouble2D) getParam(B)).getMat();
//		a.print(0, 0);
//		b.print(0, 0);
//		a.print(1, 1);
//		b.print(1,1);
//		System.err.println("a : " + getParam(0).getName() + " " + a.getRowDimension() + "," +a.getColumnDimension());
//		System.err.println("b : " + getParam(1).getName() + " " + b.getRowDimension() + "," +b.getColumnDimension());
		
//		a.print(2, 2);
//		b.transpose().print(2, 2);
		Matrix res = a.plus(b);
		
//		res.print(0, 0);
		this.setMat(res);

	}


}
