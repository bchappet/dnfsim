package main.java.unitModel;

import static java.lang.Math.abs;
import static java.lang.Math.exp;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import main.java.space.Space;
import main.java.space.WrappableDouble2DSpace;
import main.java.maps.Parameter;
import main.java.maps.Track;
import main.java.maps.Var;
import main.java.space.Coord;
import main.resources.utils.ArrayUtils;

/**
 * Use continuous main.java.coordinates
 * @author bchappet
 *
 */
public class GaussianND extends UnitModel<Double> implements Track{
	public static final int SPACE = 0;
	public static final int INTENSITY = 1;
	public static final int WIDTH = 2;
	public static final int COORDS = 3;
	

	public GaussianND(Double init) {
		super(init);
	}
	
	@Override
	protected Double compute(BigDecimal time, int index, List<Parameter> params) {
		
		
		Space space = (Space) params.get(SPACE);
		//Translate the coor in the center centered refSpace
		Double[] translation = new Double[params.size()-COORDS];//Translated main.java.coordinates
		Coord<Double> coord = ((WrappableDouble2DSpace) space).indexToCoordContinuous(index);//TODO more generic
		for(int i = 0 ; i < translation.length; i++){
//			System.out.println("coor : " + i + " = " + coord[i]);
			translation[i] = abs(coord.getIndex(i)-(Double)params.get(COORDS+i).getIndex(index));
		}
		
		//Wrap the coor if needed
		if(space.isWrap()) 
			translation = space.wrap(translation);
		
		//Compute sum of squares x²+y²+z²+...
		double sumOfSquare = 0;
		for(int i = 0 ; i < translation.length ; i++){
				sumOfSquare += translation[i]*translation[i];
		}

		double res = params.getIndex(INTENSITY).getIndex(coord)*exp(
				-(sumOfSquare)/(
						Math.pow(params.getIndex(WIDTH).getIndex(coord),2)));
		return res;
	}
	
	
	

	@Override
	public Double[] getCenter() throws NullCoordinateException {
		return saveCenter;
	}

	@Override
	public Double[] getDimension() throws NullCoordinateException {
		return saveDim;
	}


	

	


	
}