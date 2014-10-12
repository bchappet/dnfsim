package main.java.reservoirComputing;

import java.math.BigDecimal;


import main.java.console.CommandLineFormatException;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.space.Space;
import main.resources.utils.ArrayUtils;
import main.resources.utils.BadMatrixDimensionException;
import main.resources.utils.JamaMatrix;
import main.resources.utils.Matrix;

/**
 * Perform operation regression when asked to learn
 * @author bchappet
 *
 */
public class RidgeRegressionLearningWeightMatrix extends LearningWeightMatrix {
	
	
	private static final int REGULARIZATION_FACTOR = 0;
	private static final int RESERVOIR = 1;
	private static final int TARGET = 2;
	
	
	protected StateSaver<Double> res_save;
	protected StateSaver<Double> target_save;


	


	public RidgeRegressionLearningWeightMatrix(String name, Var<BigDecimal> dt,
                                               Space space, Parameter... params) {
		super(name,dt,space,params);
	}
	
	@Override
	public void compute(){
		if(res_save == null)
			res_save = new StateSaver(getParam(RESERVOIR));
		if(target_save == null)
			target_save = new StateSaver(getParam(TARGET));
	
		res_save.compute();
		target_save.compute();
	}


	
	/**
	 * Pseudo inverse regression
	 */
//	public void computeOutputWeights() throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{
//		//Stack the reservoir in an (t,res) matrix
//		Matrix X = null;
//		try{
//			X = new Matrix(res_save.stackStates());
//			X = X.inverse().transpose();
//		}catch(RuntimeException e){
//			throw new DeterminantErrror("The matrix is rank deficient. Wait for more data.",e);
//		}catch(OutOfMemoryError e){
//			throw new ComputationOutOfMemoryError("The inverse of matrix ("+X.getNbColumns() + "," +X.getNbRows()+") "
//					+ "is a bit greedy for memory. You may want to reset reservoir states first.",e);
//		}
////		X.print(3, 3);
//		Matrix yTgt = new Matrix(target_save.stackStates()).transpose();
//		
////		yTgt.print(3, 3);
//		
//		
//		System.err.println("a : " + getParam(1).getName() + " " + yTgt.getNbColumns() + "," +yTgt.getNbRows());
//		System.err.println("b : " + getParam(0).getName() + " " + X.getNbColumns() + "," +X.getNbRows());
//		Matrix outputWeights =null;
//		try{
//			outputWeights = yTgt.times(X);
//		}catch(IllegalArgumentException e){
//			throw new IllegalArgumentException("The dimension of the mattrices are not good: you are trying to multiply a (" 
//		+ yTgt.getNbColumns() + "," +yTgt.getNbRows()+") by ("+X.getNbColumns() + "," +X.getNbRows()+")",e);
//		}
//		this.setMat(outputWeights);
//		
//		
//	}
	
	/**
	 * Ridge regression
	 * 	(Ytgt*X_T) * ( inv(X*X_t) + reg *eye );
	 * @param Ytgt
	 * @param X
	 * @param reg
	 * @return
	 */
	public static Matrix ridgeRegression(Matrix Ytgt, Matrix X, double reg){
		Matrix X_t = X.transpose();
		
	
		int res = X.getNbColumns();
		Matrix eye = JamaMatrix.identity(res, res);
		eye.timesEquals(reg);
		
		Matrix outputWeights =null;
		Matrix target = null;
		Matrix X2 = null;
		Matrix inverse = null;
		try{
			target = Ytgt.times(X_t);
			
		}catch(BadMatrixDimensionException e){
			throw new IllegalArgumentException("The dimension of the matrices are not good: you are trying to multiply a ("
					+ Ytgt.getNbColumns() + "," +Ytgt.getNbRows()+") by ("+X_t.getNbColumns() + "," +X_t.getNbRows()+")",e);
		}
		
		try{
			X2 = (X.times(X_t));
//			System.out.println("X2");
//			X2.print(1, 8);
		}catch(BadMatrixDimensionException e){
			throw new IllegalArgumentException("The dimension of the matrices are not good: you are trying to multiply a ("
					+ X.getNbColumns() + "," +X.getNbRows()+") by ("+X_t.getNbColumns() + "," +X_t.getNbRows()+")",e);
		}
		try{
			inverse = (X2.plus(eye)).inverse();
//			System.out.println("inverse");
//			inverse.print(1, 8);
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException("The dimension of the matrices are not good: you are trying to multiply a ("
					+ X.getNbColumns() + "," +X.getNbRows()+") by ("+X_t.getNbColumns() + "," +X_t.getNbRows()+")",e);
		}
		
		try{
			outputWeights = target.times(( inverse  ));
		}catch(BadMatrixDimensionException e){
			throw new IllegalArgumentException("The dimension of the matrices are not good: you are trying to multiply a ("
		+ target.getNbColumns() + "," +target.getNbRows()+") by ("+inverse.getNbColumns() + "," +inverse.getNbRows()+")",e);
		}
		return outputWeights;
	}
	
	public void computeOutputWeights() throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{


        Matrix X;
        if(isSquare()){
            double[][] part1 = res_save.stackStates();
            double[][] part2 =  ArrayUtils.dotPower2(part1);
            X = new JamaMatrix(ArrayUtils.horizontalConcatenation(part1,part2));
        }else {
            X = new JamaMatrix(res_save.stackStates());
        }

        X = X.transpose();



		Matrix Ytgt = new JamaMatrix(target_save.stackStates()).transpose();
		double reg = (double) getParam(REGULARIZATION_FACTOR).getIndex(0);
	
		
		this.setMat(ridgeRegression(Ytgt, X, reg));
		
	}
	
	public void resetStates(){
		res_save.reset();
		target_save.reset();
	}

	

}
