package main.java.maps;

import java.math.BigDecimal;

import main.java.space.Space;
import main.java.space.SpaceFactory;

/**
 * Concatenate A(na,ma) with B(nb,mb) (A,B)(na=nb,ma+mb)
 * @author bchappet
 *
 */
public class HorizontalConcatenationMatrix extends MatrixDouble2D {

    /**
     * Two MatrixDouble2D
     */
	protected final static int A = 0;
	protected final static int B = 1;

	public HorizontalConcatenationMatrix(String name, Var<BigDecimal> dt,Space space,Parameter<Array2DDouble>... params) {
		super(name,dt,space,params);
	}
	
	public HorizontalConcatenationMatrix(Map a,Map b) {
		this(a.getName()+"_"+ ".hconc."+b.getName(), a.getDt(),SpaceFactory.horizontalConcatenationSpace(a.getSpace(),b.getSpace()), a,b);
	}
	
	@Override
	public void compute(){
//		double[][] a = ((Array2DDouble) getParam(A)).get2DArrayDouble();
//		double[][] b = ((Array2DDouble) getParam(B)).get2DArrayDouble();
//
////		System.out.println("a " + ((Map) getParam(A)).getSpace());
////		System.out.println(ArrayUtils.toString(a));
////		System.out.println("b " + ((Map) getParam(B)).getSpace());
////		System.out.println(ArrayUtils.toString(b));
//
//
//		double[][] res = ArrayUtils.horizontalConcatenation(a, b);
//
//        this.set2DArrayDouble(res);

//        System.out.println("res " + this.getValues());

        this.setMat(((MatrixDouble2D) getParam(A)).getMat().hconcat(((MatrixDouble2D) getParam(B)).getMat()));
		
	}

	
	
}
