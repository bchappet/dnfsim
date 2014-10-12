package main.java.maps;

import java.math.BigDecimal;


import main.java.space.Space;
import main.java.space.SpaceFactory;
import main.resources.utils.BadMatrixDimensionException;
import main.resources.utils.Matrix;


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

    public MultiplicationMatrix(MatrixDouble2D a, MatrixDouble2D b) {
        super(a.getName()+"*"+b.getName(),a.getDt(), SpaceFactory.matrixProductSpace(a.getSpace(),b.getSpace()),a,b);
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
        Matrix res = null;
        try {
           res = a.times(b);
//            System.out.println(  "In " + this.getName() +  ". Dimension of matrices does not agree: trying to multiply " +
//                    "a : " + getParam(0).getName() + " " + a.getNbColumns() + "," +a.getNbRows() + " by " +
//                    "b : " + getParam(1).getName() + " " + b.getNbColumns() + "," +b.getNbRows() );
        }catch (BadMatrixDimensionException e){
            throw new IllegalArgumentException("In " + this.getName() +  ". Dimension of matrices does not agree: trying to multiply " +
                    "a : " + getParam(0).getName() + " " + a.getNbColumns() + "," +a.getNbRows() + " by " +
                            "b : " + getParam(1).getName() + " " + b.getNbColumns() + "," +b.getNbRows() ,e);
        }
		
//		res.print(0, 0);

        this.setMat(res);

	}


}
