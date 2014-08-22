package main.java.maps;

import java.math.BigDecimal;

import main.java.space.Space;
import main.resources.utils.ArrayUtils;
import Jama.Matrix;

/**
 * Concatenate A(na,ma) with B(nb,mb) (A,B)(na=nb,ma+mb)
 * @author bchappet
 *
 */
public class HorizontalConcatenationMatrix extends MatrixDouble2D {
	
	protected final static int A = 0;
	protected final static int B = 1;

	public HorizontalConcatenationMatrix(String name, Var<BigDecimal> dt,Space space,Parameter<Array2DDouble>... params) {
		super(name,dt,space,params);
	}
	
	@Override
	public void compute(){
		double[][] a = ((Array2DDouble) getParam(A)).get2DArrayDouble();
		double[][] b = ((Array2DDouble) getParam(B)).get2DArrayDouble();
		
		double[][] res = ArrayUtils.horizontalConcatenation(a, b);
		
		//this.set
		
	}

	
	
}
