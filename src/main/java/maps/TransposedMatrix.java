package main.java.maps;

import java.math.BigDecimal;

public class TransposedMatrix extends MatrixDouble2D {
	
	private static final int MATRIX = 0;

	public TransposedMatrix(String name, Var<BigDecimal> dt,
			Parameter<Double>... params) {	
		super(name, dt,((Map) params[MATRIX]).getSpace().transpose(), params);
	}
	
	public TransposedMatrix(MatrixDouble2D mat) {
		this(mat.getName()+"_transposed", mat.getDt(), mat);
	}

	@Override
	public void compute() {
        this.setMat(((MatrixDouble2D) getParam(MATRIX)).getMat().transpose());
    }

}
