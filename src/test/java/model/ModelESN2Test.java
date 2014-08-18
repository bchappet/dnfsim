package test.java.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.MatrixCSVFileReader;
import main.java.maps.MatrixDouble2D;
import main.java.maps.MatrixDouble2DWrapper;
import main.java.maps.MultiplicationMatrix;
import main.java.maps.TransposedMatrix;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.reservoirComputing.LearningWeightMatrix;
import main.java.reservoirComputing.TanHReservoirNeuronUM;
import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.java.unitModel.RandTrajUnitModel;
import main.resources.utils.ArrayUtils;

import org.junit.Before;
import org.junit.Test;

import Jama.Matrix;

public class ModelESN2Test {
	
	

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	public void testInputWeightsIR() {
		int lenght_reservoir = 35;
		Space1D spaceReservoir = new Space1D(lenght_reservoir);
		UnitMap<Double,Integer> weightsIR = 
				new UnitMap<Double,Integer>("WeightsIR",new InfiniteDt(), spaceReservoir,
						new RandTrajUnitModel(0d), new Var<Double>(0d),new Var<Double>(0.5d));
		weightsIR.compute();
		System.out.println(weightsIR.toString());
		
	}
	
	@Test
	public void testDotWeightRR_R(){
		int lenght_reservoir = 3;
		Space2D spaceWeightsReservoir = new Space2D(lenght_reservoir,lenght_reservoir);
		Var<String> sep = new Var<String>(",");
		Var<String> fileWRR = new Var<String>("src/test/data/weights/weights");
		Map weightsRR = new MatrixCSVFileReader("weights_RR", new InfiniteDt(), spaceWeightsReservoir, fileWRR,sep);
		weightsRR.compute();
		//System.out.println(weightsRR);
		
		Space1D spaceReservoir = new Space1D(lenght_reservoir);
		Var<BigDecimal> dtReservoir = new Var<BigDecimal>(BigDecimal.ONE);
		Map reservoir = new UnitMap("reservoir",dtReservoir,spaceReservoir,new TanHReservoirNeuronUM(0d));
		
		Map dot_WRR_R = new MultiplicationMatrix("dot_WRR_R", dtReservoir, spaceReservoir.transpose());
		MatrixDouble2D matReservoir = new MatrixDouble2DWrapper((Map) reservoir);
		MatrixDouble2D columnVectorMatReservoir = new TransposedMatrix(matReservoir);
		dot_WRR_R.addParameters(weightsRR,columnVectorMatReservoir);
		
		dot_WRR_R.compute();
		
		//System.out.println(dot_WRR_R);
		//System.err.println(dot_WRR_R.getIndex(1));
		
	}
	
	@Test
	public void testEye(){
		Matrix eye = Matrix.identity(20, 20);
		//eye.print(2, 10);
		double reg = 1E-8;
		eye.timesEquals(reg);
		//eye.print(2, 10);
	}
	
	@Test
	public void compareToPyhtonWeights(){
		double reg = 2;
		Matrix X = new Matrix(new double[][]{{1, 3, 3, 3},
											{2, 4, 4, 7},
											{3, 5, 3, 9}});
//		System.out.println(X.getRowDimension() + "," +X.getColumnDimension());
//		X.print(1,1);
		Matrix Ytgt = new Matrix(new double[][]{{1, 2, 3, 4}});
		
		
		Matrix weight = LearningWeightMatrix.computeWeightMethod2(Ytgt, X, reg);
//		weight.print(1, 8);
		
		assertTrue("Python find that:",
				ArrayUtils.equals2D(new double[][]{{0.21551724137931094,0.4482758620689662,0.00862068965517171}},weight.getArray())); 

	}

}
