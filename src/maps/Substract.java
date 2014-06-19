package maps;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;

/**
 * Generic function to substract each map to the first one
 * 
 * @author Benoit
 *
 */
public class Substract extends UnitModel {
	
	




	@Override
	public double compute() throws NullCoordinateException {
		double ret = params.get(0).get(coord);
		//System.out.print("Substract : ("+name+") "  );
		for(int i = 1 ; i < params.size() ; i++)
		{
			//System.out.print( "; " + ret +" -= " +((Updatable) p).getDouble(coor));
			ret -= params.get(i).get(coord);
		}
		//System.out.println("");
		return ret;
	}

	

	


	



	

}
