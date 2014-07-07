package main.java.unitModel;

import static java.lang.Math.abs;
import static java.lang.Math.exp;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.maps.Track;
import main.java.neigborhood.WrappedSpace;
import main.java.space.Coord;
import main.java.space.Space;

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
		
		Coord<Double> coord = space.indexToCoord(index);//TODO more generic
		Coord<Double> translation = new Coord<Double>(coord,0d);//Translated coord
		
		for(int i = 0 ; i < translation.getSize(); i++){
//			System.out.println("coor : " + i + " = " + coord[i]);
			translation.set(i,abs(coord.getIndex(i)-((Number)params.get(COORDS+i).getIndex(index)).doubleValue()));
		}
		
		//Wrap the coor if needed
		if(space instanceof WrappedSpace) 
			translation = ((WrappedSpace)space).wrap(translation);
		
		//Compute sum of squares x²+y²+z²+...
		double sumOfSquare = 0;
		for(int i = 0 ; i < translation.getSize() ; i++){
				sumOfSquare += Math.pow(translation.getIndex(i),2);
		}
		double res = ((Number)params.get(INTENSITY).getIndex(index)).doubleValue()*exp(
				-(sumOfSquare)/(
						Math.pow((Double) params.get(WIDTH).getIndex(index),2)));
		return res;
	}
	
	
	

	@Override
	public Double[] getCenter() {
		return null;//TODO
	}

	@Override
	public Double[] getDimension() {
		return null; //TODO
	}


	

	


	
}