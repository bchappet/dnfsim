package main.java.space;

import main.java.maps.Var;
import main.java.neigborhood.WrappedSpace;

/**
 * Standard space for DNF
 * Continuous space defined with double coord origin and length of each axis
 * And a resolution to discretize it
 * @author benoit
 * @version 12/05/2014
 *
 */
public class WrappableDouble2DSpace extends DoubleSpace2D implements WrappedSpace<Double>{

	public WrappableDouble2DSpace(Var<Double> originX, Var<Double> originY,
			Var<Double> lengthX, Var<Double> lengthY, Var<Integer> resolution) {
		super(originX, originY, lengthX, lengthY, resolution);
	}

	@Override
	public Coord<Double> wrap(Coord<Double> coord) {
		Coord<Double> res = new Coord<Double>(coord,0d);

		for(int i = 0 ; i < res.getSize() ; i++)
		{
			Double x = coord.getIndex(i);
			double oriX = this.getOrigin(i);
			double sizeX =  this.getLenght(i);
			double endX = oriX + sizeX;

			//System.out.println(" avant " + Arrays.toString(coord));

			if( x < oriX || x > endX )
			{
				//We are outside the frame bounds
					x = x - oriX;
					x = x % sizeX;
					if(x < 0){
						x += sizeX;
					}
					x = x + oriX;
			}
			res.set(i, x);
		}
		//System.out.println(" apres " + Arrays.toString(res));
		return res;
	}











}
