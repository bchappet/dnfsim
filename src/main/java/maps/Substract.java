package main.java.maps;

import main.java.coordinates.NullCoordinateException;
import main.java.unitModel.UnitModel;

/**
 * Generic function to substract each map to the first one
 * 
 * @author Benoit
 *
 */
public class Substract extends UnitModel {
	
	




	@Override
	public double compute() throws NullCoordinateException {
		double ret = params.getIndex(0).getIndex(coord);
		//System.out.print("Substract : ("+name+") "  );
		for(int i = 1 ; i < params.size() ; i++)
		{
			//System.out.print( "; " + ret +" -= " +((Updatable) p).getDouble(coor));
			ret -= params.getIndex(i).getIndex(coord);
		}
		//System.out.println("");
		return ret;
	}

	

	


	



	

}
