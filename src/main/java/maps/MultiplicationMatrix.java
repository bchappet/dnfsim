package main.java.maps;

import java.math.BigDecimal;

import main.java.space.Space2D;


public class MultiplicationMatrix extends MatrixDouble2D  {

	public MultiplicationMatrix(String name, Var<BigDecimal> dt, Space2D space,
			Parameter... params) {
		super(name, dt, space, params);
	}

	public MultiplicationMatrix(String name, Var<BigDecimal> dt, Space2D space,
			double[][] values, Parameter... params) {
		super(name, dt, space, values, params);
	}

	@Override
	public void compute()
	{
		Jama.Matrix a = ((MatrixDouble2D) getParam(0)).getJamat();
		Jama.Matrix b = ((MatrixDouble2D) getParam(1)).getJamat();
//		a.print(0, 0);
//		b.print(0, 0);
		//System.out.println("a : " + getParam(0).getName() + " " + a.getColumnDimension() + "," +a.getRowDimension());
		//System.out.println("b : " + getParam(1).getName() + " " + b.getColumnDimension() + "," +b.getRowDimension());
		Jama.Matrix res = null;
			res = a.times(b);
//		res.print(0, 0);
		this.setJamat(res);

	}


}
