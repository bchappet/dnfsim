package main.java.reservoirComputing;

import java.math.BigDecimal;

import Jama.Matrix;
import main.java.console.CommandLineFormatException;
import main.java.maps.MatrixDouble2D;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.space.Space;

/**
 * Perform operation Ytgt * X -1 when asked to learn
 * @author bchappet
 *
 */
public class LearningWeightMatrix extends MatrixDouble2D {
	
	
	private static final int RESERVOIR = 0;
	private static final int TARGET = 1;
	private static final int INPUT = 2;
	private static final int REGULARIZATION_FACTOR = 3;
	
	protected StateSaver<Double> res_save;
	protected StateSaver<Double> target_save;
	protected StateSaver<Double> input_save;
	


	public LearningWeightMatrix(String name, Var<BigDecimal> dt,
			Space space,Parameter...params) {
		super(name,dt,space,params);
	}
	
	@Override
	public void compute(){
		if(res_save == null)
			res_save = new StateSaver<>(getParam(RESERVOIR));
		if(target_save == null)
			target_save = new StateSaver<>(getParam(TARGET));
		if(in)
		res_save.compute();
		target_save.compute();
	}
	
//	public void computeOutputWeights() throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{
//		//Stack the reservoir in an (t,res) matrix
//		Matrix X = null;
//		try{
//			X = new Matrix(res_save.stackStates());
//			X = X.inverse().transpose();
//		}catch(RuntimeException e){
//			throw new DeterminantErrror("The matrix is rank deficient. Wait for more data.",e);
//		}catch(OutOfMemoryError e){
//			throw new ComputationOutOfMemoryError("The inverse of matrix ("+X.getRowDimension() + "," +X.getColumnDimension()+") "
//					+ "is a bit greedy for memory. You may want to reset reservoir states first.",e);
//		}
////		X.print(3, 3);
//		Matrix yTgt = new Matrix(target_save.stackStates()).transpose();
//		
////		yTgt.print(3, 3);
//		
//		
//		System.err.println("a : " + getParam(1).getName() + " " + yTgt.getRowDimension() + "," +yTgt.getColumnDimension());
//		System.err.println("b : " + getParam(0).getName() + " " + X.getRowDimension() + "," +X.getColumnDimension());
//		Matrix outputWeights =null;
//		try{
//			outputWeights = yTgt.times(X);
//		}catch(IllegalArgumentException e){
//			throw new IllegalArgumentException("The dimension of the mattrices are not good: you are trying to multiply a (" 
//		+ yTgt.getRowDimension() + "," +yTgt.getColumnDimension()+") by ("+X.getRowDimension() + "," +X.getColumnDimension()+")",e);
//		}
//		this.setJamat(outputWeights);
//		
//		
//	}
	
	/**
	 * 	(Ytgt*X_T) * ( inv(X*X_t) + reg *eye );
	 * @param Ytgt
	 * @param X
	 * @param reg
	 * @return
	 */
	public static Matrix computeWeightMethod2(Matrix Ytgt,Matrix X,double reg){
		Matrix X_t = X.transpose();
		
	
		int res = X.getRowDimension();
		Matrix eye = Matrix.identity(res, res);
		eye.timesEquals(reg);
		
		Matrix outputWeights =null;
		Matrix target = null;
		Matrix X2 = null;
		Matrix inverse = null;
		try{
			target = Ytgt.times(X_t);
			
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException("The dimension of the mattrices are not good: you are trying to multiply a (" 
					+ Ytgt.getRowDimension() + "," +Ytgt.getColumnDimension()+") by ("+X_t.getRowDimension() + "," +X_t.getColumnDimension()+")",e);
		}
		
		try{
			X2 = (X.times(X_t));
//			System.out.println("X2");
//			X2.print(1, 8);
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException("The dimension of the mattrices are not good: you are trying to multiply a (" 
					+ X.getRowDimension() + "," +X.getColumnDimension()+") by ("+X_t.getRowDimension() + "," +X_t.getColumnDimension()+")",e);
		}
		try{
			inverse = (X2.plus(eye)).inverse();
//			System.out.println("inverse");
//			inverse.print(1, 8);
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException("The dimension of the mattrices are not good: you are trying to multiply a (" 
					+ X.getRowDimension() + "," +X.getColumnDimension()+") by ("+X_t.getRowDimension() + "," +X_t.getColumnDimension()+")",e);
		}
		
		try{
			outputWeights = target.times(( inverse  ));
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException("The dimension of the mattrices are not good: you are trying to multiply a (" 
		+ target.getRowDimension() + "," +target.getColumnDimension()+") by ("+inverse.getRowDimension() + "," +inverse.getColumnDimension()+")",e);
		}
		return outputWeights;
	}
	
	public void computeOutputWeights() throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{
		Matrix X = new Matrix(res_save.stackStates()).transpose();
		Matrix Ytgt = new Matrix(target_save.stackStates()).transpose();
		double reg = (double) getParam(REGULARIZATION_FACTOR).getIndex(0);
	
		
		this.setJamat(computeWeightMethod2(Ytgt, X, reg));
		
	}
	
	public void resetStates(){
		res_save.reset();
		target_save.reset();
	}

	

}
