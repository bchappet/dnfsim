package unitModel;

import static java.lang.Math.abs;
import static java.lang.Math.exp;

import java.util.Arrays;

import maps.Parameter;
import maps.Track;
import maps.Var;
import utils.ArrayUtils;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Use continuous coordinates
 * @author bchappet
 *
 */
public class GaussianND extends UnitModel implements Track{
	public static final int INTENSITY = 0;
	public static final int WIDTH = 1;
	public static final int COORDS = 2;

	public GaussianND(Var dt, Space space,Parameter intensity,Parameter width,
			Parameter ... center) {
		super(dt, space,ArrayUtils.concat(new Parameter[]{intensity,width},center));
	}


	@Override
	public double compute() throws NullCoordinateException {
		//Translate the coor in the center centered refSpace
		Double[] translation = new Double[params.size()-COORDS];//Translated coordinates
		for(int i = 0 ; i < translation.length; i++){
			//System.out.println("coor : " + i + " = " + coord[i]);
			translation[i] = abs(coord[i]-params.get(COORDS+i).get(coord));
		}
		
		//Wrap the coor if needed
		if(space.isWrap()) 
			translation = space.wrap(translation);
		
		//Compute sum of squares x²+y²+z²+...
		double sumOfSquare = 0;
		for(int i = 0 ; i < translation.length ; i++){
				sumOfSquare += translation[i]*translation[i];
		}

		double res = params.get(INTENSITY).get(coord)*exp(
				-(sumOfSquare)/(
						Math.pow(params.get(WIDTH).get(coord),2)));
		return res;
	}
	
	
	

	@Override
	public Double[] getCenter() throws NullCoordinateException {
		Double[] ret = new Double[space.getDim()];
		for(int i = 0 ; i < ret.length ; i++)
			ret[i] = params.get(COORDS+i).get();
		return ret;
	}

	@Override
	public Double[] getDimension() throws NullCoordinateException {
		double width = params.get(WIDTH).get();
		Double[] ret = new Double[space.getDim()];
		for(int i = 0 ; i < ret.length ; i++)
			ret[i]	=  width;
		
		return ret;
	}

	


	
}