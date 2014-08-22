package main.java.maps;

import java.math.BigDecimal;

import main.java.space.Coord;
import main.java.space.Space;

public class MatrixDouble2DWrapper extends MatrixDouble2D {
	
	/**Parameter to copy**/
	private static final int PARAMETER = 0;


	public MatrixDouble2DWrapper(String name, Var<BigDecimal> dt,
			Space space, Parameter<Double>... params) {
		super(name, dt, space, params);
	}
	
	
	public MatrixDouble2DWrapper(Map param) {
		this(param.getName()+"_MatrixWrapper", param.getDt(), (Space)param.getSpace(), param);
	}
	
	public MatrixDouble2DWrapper(String name, Trajectory param) {
		this(name, param.getDt(), (Space)param.getSpace(), param);
	}


	@Override
	public void compute() {
		Map<Number,Integer> map = (Map<Number,Integer>) getParam(PARAMETER);
		//copy values in jama matrix
		double[][] val = getJamat().getArray();
		for(int i = 0 ; i < val[0].length ; i++){
			for(int j = 0 ; j < val.length ; j++){
				val[j][i] = map.getIndex(map.getSpace().coordIntToIndex(new Coord<Integer>(i,j))).doubleValue();
			}
		}
	}

}
