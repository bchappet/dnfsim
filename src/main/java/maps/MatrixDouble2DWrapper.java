package main.java.maps;

import java.math.BigDecimal;

import main.java.space.Coord;
import main.java.space.Space2D;

public class MatrixDouble2DWrapper extends MatrixDouble2D {
	
	/**Parameter to copy**/
	private static final int PARAMETER = 0;


	public MatrixDouble2DWrapper(String name, Var<BigDecimal> dt,
			Space2D space, Parameter<Double>... params) {
		super(name, dt, space, params);
	}
	
	
	public MatrixDouble2DWrapper(Map param) {
		this(param.getName()+"_MatrixWrapper", param.getDt(), (Space2D)param.getSpace(), param);
	}
	
	@Override
	public void compute() {
		Map<Number,Integer> map = (Map<Number,Integer>) getParam(PARAMETER);
		//copy values in jama matrix
		double[][] val = getJamat().getArray();
		for(int i = 0 ; i < val[0].length ; i++){
			for(int j = 0 ; j < val.length ; j++){
				val[j][i] = map.getIndex(map.getSpace().coordToIndex(new Coord<Integer>(i,j))).doubleValue();
			}
		}
	}

}
