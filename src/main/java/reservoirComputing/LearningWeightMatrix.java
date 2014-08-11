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
	
	protected StateSaver<Double> res_save;
	protected StateSaver<Double> target_save;
	


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
		res_save.compute();
		target_save.compute();
	}
	
	public void computeOutputWeights() throws CommandLineFormatException{
		//Stack the reservoir in an (t,res) matrix
		Matrix X = new Matrix(res_save.stackStates()).inverse().transpose();
//		X.print(3, 3);
		Matrix yTgt = new Matrix(target_save.stackStates()).transpose();
		
//		yTgt.print(3, 3);
		
		
		System.err.println("a : " + getParam(1).getName() + " " + yTgt.getRowDimension() + "," +yTgt.getColumnDimension());
		System.err.println("b : " + getParam(0).getName() + " " + X.getRowDimension() + "," +X.getColumnDimension());
		
		Matrix outputWeights = yTgt.times(X);
		this.setJamat(outputWeights);
		
		res_save.reset();
		target_save.reset();
	}

	

}
