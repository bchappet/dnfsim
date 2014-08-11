package test.java.model;

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
import main.java.reservoirComputing.TanHReservoirNeuronUM;
import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.java.unitModel.RandTrajUnitModel;

import org.junit.Before;
import org.junit.Test;

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
		System.out.println(weightsRR);
		
		Space1D spaceReservoir = new Space1D(lenght_reservoir);
		Var<BigDecimal> dtReservoir = new Var<BigDecimal>(BigDecimal.ONE);
		Map reservoir = new UnitMap("reservoir",dtReservoir,spaceReservoir,new TanHReservoirNeuronUM(0d));
		
		Map dot_WRR_R = new MultiplicationMatrix("dot_WRR_R", dtReservoir, spaceReservoir.transpose());
		MatrixDouble2D matReservoir = new MatrixDouble2DWrapper((Map) reservoir);
		MatrixDouble2D columnVectorMatReservoir = new TransposedMatrix(matReservoir);
		dot_WRR_R.addParameters(weightsRR,columnVectorMatReservoir);
		
		dot_WRR_R.compute();
		
		System.out.println(dot_WRR_R);
		System.err.println(dot_WRR_R.getIndex(1));
		
	}

}
