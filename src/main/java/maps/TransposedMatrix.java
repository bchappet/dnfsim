package main.java.maps;

import java.math.BigDecimal;

import Jama.Matrix;
import main.java.space.Space;

public class TransposedMatrix extends MatrixDouble2D {
	
	private static final int MATRIX = 0;
	private Matrix linkedJamat;

	public TransposedMatrix(String name, Var<BigDecimal> dt,
			Parameter<Double>... params) {	
		super(name, dt,((Map) params[MATRIX]).getSpace().transpose(), params);
		this.linkedJamat = ((MatrixDouble2D) params[MATRIX]).getJamat();
	}
	
	public TransposedMatrix(MatrixDouble2D mat) {
		this(mat.getName()+"_transposed", mat.getDt(), mat);
	}

	@Override
	public void compute() {
		this.setJamat(linkedJamat.transpose());
	}

}
