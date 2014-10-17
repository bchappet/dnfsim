package main.java.maps;


import main.resources.utils.Matrix;

/**
 * Created by bchappet on 24/09/14.
 */
public class ToSquareMatrix extends MatrixDouble2D{



    private static final int MAP = 0;


    public ToSquareMatrix(Map param) {
        super(param.getName()+"_squared", param.getDt(), param.getSpace(), param);
    }


    @Override
    public void compute()
    {

        Matrix a = ((MatrixDouble2D) getParam(MAP)).getMat();
        Matrix res = a.arrayTimes(a);
        this.setMat(res);

//		a.print(0, 0);
//		b.print(0, 0);
//		a.print(1, 1);
//		b.print(1,1);
//		System.err.println("a : " + getParam(0).getName() + " " + a.getRowDimension() + "," +a.getColumnDimension());
//		System.err.println("b : " + getParam(1).getName() + " " + b.getRowDimension() + "," +b.getColumnDimension());

//		a.print(2, 2);
//		b.transpose().print(2, 2);
//        Jama.Matrix res = new Matrix(a.getRowDimension(),a.getColumnDimension());
//
//        double[][] result = ArrayUtils.dotPower2(a.getArray());
//
//
//
////		res.print(0, 0);
//        this.set2DArrayDouble(result);

    }
}
