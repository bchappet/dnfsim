

package main.java.maps;

import java.math.BigDecimal;

import main.java.space.Coord;
import main.java.space.Space;
import main.resources.utils.ArrayUtils;

/**
 * TODO if the parameter is static the this should be static, and it should be updated on parameter change
 * TODO optimize the way to copy data
 */
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
       // System.out.println("wrapper compute " + getName());
        Map<Number,Integer> map = (Map<Number,Integer>) getParam(PARAMETER);
		//copy values in jama matrix
		//double[][] val = getMat().getArray();
		for(int i = 0 ; i < this.getSpace().getVolume() ; i++){
				this.setIndex(i, (Double) map.getIndex(i));
		}
        //System.out.println(ArrayUtils.toString(this.get2DArrayDouble()));
	}

}
