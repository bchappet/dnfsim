package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.Arrays;


import main.java.console.CommandLineFormatException;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.space.Space;
import main.resources.utils.ArrayUtils;
import main.resources.utils.BadMatrixDimensionException;
import main.resources.utils.OpenCVMatrix;
import main.resources.utils.Matrix;

/**
 * Perform operation regression when asked to learn
 * @author bchappet
 *
 */
public class PseudoInverseLearningWeightMatrixWithInput extends PseudoInverseLearningWeightMatrix {




    private static final int RESERVOIR = 0;
    private static final int TARGET = 1;
    private static final int INPUT = 2;


	protected StateSaver<Double> res_save;
	protected StateSaver<Double> target_save;
	protected StateSaver<Double> input_save;



	public PseudoInverseLearningWeightMatrixWithInput(String name, Var<BigDecimal> dt,
                                                      Space space, Parameter... params) {
		super(name,dt,space,params);
	}

	@Override
	public void compute(){
		if(res_save == null) {
            res_save = new StateSaver(getParam(RESERVOIR));
        }
		if(target_save == null)
			target_save = new StateSaver(getParam(TARGET));
		if(input_save == null)
			input_save = new StateSaver(getParam(INPUT));
		res_save.compute();
		target_save.compute();
		input_save.compute();
	}

	/**
	 * Pseudo inverse regression
	 * Xsquares = column vector 2N + 2 (u,x1...,xN,u²,x1²,...,xN²) TODO
	 * for now just X
	 * r = column vector training pre signal : (fout-1)yteach
	 * M = row-wise Xsquares
	 *
	 *
	 */
	public Matrix computeOutputWeights(Matrix m,Matrix r) throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{

        System.out.println("computeOutputWeights m,r : (" + m.getNbColumns() + "," +m.getNbRows()+") by ("+r.getNbColumns() + "," +r.getNbRows()+")");
        Matrix inverse = null;
        inverse = m.inverse();
        Matrix mult = null;
        System.out.println("computeOutputWeights inverse : (" + inverse.getNbColumns() + "," +inverse.getNbRows()+") by ("+r.getNbColumns() + "," +r.getNbRows()+")");
        try{
			mult = inverse.times(r);
		}catch(BadMatrixDimensionException e){
					throw new IllegalArgumentException("The dimension of the matrices are not good: you are trying to multiply a ("
							+ inverse.getNbColumns() + "," +inverse.getNbRows()+") by ("+r.getNbColumns() + "," +r.getNbRows()+")",e);
		}


		return mult.transpose();
	}


	public  void computeOutputWeights() throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{
		Matrix X = new OpenCVMatrix(res_save.stackStates());
		Matrix Ytgt = new OpenCVMatrix(target_save.stackStates());
		Matrix input = new OpenCVMatrix(input_save.stackStates());
        System.out.println("X " + Arrays.toString(X.shape()));
       // System.out.println(X);
        System.out.println("Ytgt " +  Arrays.toString(Ytgt.shape()));
        //System.out.println(Ytgt);
        System.out.println("input " + Arrays.toString(input.shape()));
        //System.out.println(input);

        Matrix M = null;
        //Not using Xsquared
        double[][] part1 = ArrayUtils.horizontalConcatenation(input.getArray(), X.getArray());
        if(this.isSquare()) {
            //Using Xsquared:
            double[][] part2 = ArrayUtils.horizontalConcatenation(
                    ArrayUtils.dotPower2(input.getArray()), ArrayUtils.dotPower2(X.getArray()));
            double[][] tmp = ArrayUtils.horizontalConcatenation(part1, part2);
         //   System.out.println(ArrayUtils.toString(tmp));
           M = new OpenCVMatrix(tmp);
            System.out.println("computeOutputWeights m,r : (" + M.getNbColumns() + "," +M.getNbRows()+")");
        }else{
           M = new OpenCVMatrix(part1);
        }


		Matrix r = Ytgt;

        Matrix res = computeOutputWeights(M,r);
		this.setMat(res);
        System.out.println("Weights size : " + getSpace());
        System.out.println("Weights : " + res);

    }
	
	public void resetStates(){
		res_save.reset();
		target_save.reset();
		input_save.reset();
	}

	

}
