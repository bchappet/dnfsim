/*
 * This is a Dynamic Neural Field simulator which is extended to
 *     several other neural networks and extended to hardware simulation.
 *
 *     Copyright (C) 2014  Benoît Chappet de Vangel
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package main.java.reservoirComputing;

import main.java.console.CommandLineFormatException;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.space.Space;
import main.resources.utils.ArrayUtils;
import main.resources.utils.BadMatrixDimensionException;
import main.resources.utils.JamaMatrix;
import main.resources.utils.Matrix;

import java.math.BigDecimal;

/**
 * Perform operation regression when asked to learn
 * @author bchappet
 *
 */
public class PseudoInverseLearningWeightMatrix extends LearningWeightMatrix {



	private static final int RESERVOIR = 0;
	private static final int TARGET = 1;


	protected StateSaver<Double> res_save;
	protected StateSaver<Double> target_save;




	public PseudoInverseLearningWeightMatrix(String name, Var<BigDecimal> dt,
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
     * Pseudo inverse
     * @param m = X
     * @param r = yTeach column
     * @return
     * @throws CommandLineFormatException
     * @throws DeterminantErrror
     * @throws ComputationOutOfMemoryError
     */
    public Matrix computeOutputWeights(Matrix m,Matrix r) throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{

        Matrix inverse = null;
        try{
        inverse = m.inverse();
        }catch(RuntimeException e){
			throw new DeterminantErrror("The matrix is rank deficient. Wait for more data. ("+m.getNbColumns() + "," +m.getNbRows()+")",e);
		}catch(OutOfMemoryError e){
			throw new ComputationOutOfMemoryError("The inverse of matrix ("+m.getNbColumns() + "," +m.getNbRows()+") "
					+ "is a bit greedy for memory. You may want to reset reservoir states first.",e);
		}
        Matrix mult = null;
        try{
            mult = inverse.times(r);
        }catch(BadMatrixDimensionException e){
            throw new IllegalArgumentException("The dimension of the matrices are not good: you are trying to multiply a ("
                    + inverse.getNbColumns() + "," +inverse.getNbRows()+") by ("+r.getNbColumns() + "," +r.getNbRows()+")",e);
        }


        return mult.transpose();
    }

//    /**
//	 * Pseudo inverse regression
//	 */
//	public Matrix computeOutputWeights(Matrix yTgt,Matrix X) throws CommandLineFormatException, DeterminantErrror, ComputationOutOfMemoryError{
//		//Stack the reservoir in an (t,res) matrix
//		try{
//			X = X.inverse();
//		}catch(RuntimeException e){
//			throw new DeterminantErrror("The matrix is rank deficient. Wait for more data.",e);
//		}catch(OutOfMemoryError e){
//			throw new ComputationOutOfMemoryError("The inverse of matrix ("+X.getNbColumns() + "," +X.getNbRows()+") "
//					+ "is a bit greedy for memory. You may want to reset reservoir states first.",e);
//		}
////		X.print(3, 3);
//
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
//			throw new IllegalArgumentException("The dimension of the matrices are not good: you are trying to multiply a ("
//		+ yTgt.getNbColumns() + "," +yTgt.getNbRows()+") by ("+X.getNbColumns() + "," +X.getNbRows()+")",e);
//		}
//
//
//
//		return outputWeights;
//
//
//	}


	
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



        this.setMat(computeOutputWeights(X, Ytgt));
        //System.out.println("Weights size : " + getSpace());
		
	}
	
	public void resetStates(){
		res_save.reset();
		target_save.reset();
	}

	

}
