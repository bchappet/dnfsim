package main.java.maps;

import java.math.BigDecimal;

import main.java.space.Space;

/**
 * Just multiplie MAP per INPUT_scale
 * @author bchappet
 *
 */
public class NormalisationMatrix extends MatrixDouble2D {

	private static final int MAP =0;
	private static final int INPUT_SCALE =1;

	public NormalisationMatrix(Parameter...params ){
		super(params[MAP].getName()+"_normalised",((MatrixDouble2D) params[MAP]).getDt(),((MatrixDouble2D) params[MAP]).getSpace(),params);
	}
	
	
	@Override
	public void compute(){
		MatrixDouble2D map = (MatrixDouble2D) this.getParam(MAP);
		this . setJamat(map.getJamat().times((double) this.getParam(INPUT_SCALE).getIndex(0)));
		
	}
	

}
