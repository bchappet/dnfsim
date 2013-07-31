package unitModel;

import coordinates.NullCoordinateException;

/**
 * Updatable potential, Updatable threshold,Updatable low,Updatable high
 * @author bchappet
 * 
 *  public final static int POTENTIAL =0;
	public final static int THRESHOLD = 1;
	public final static int LOW = 2; value affected if the potential is lower than the threshold
	public final static int HIGH = 3; value affected if the potential is higher than the threshold
 *
 */
public class SpikingUM extends UnitModel {
	public final static int POTENTIAL =0;
	public final static int THRESHOLD = 1;
	public final static int LOW = 2;
	public final static int HIGH = 3;


	@Override
	public double compute() throws NullCoordinateException {
		//System.out.println(this);
		//System.out.println(params + " @"+params.hashCode());
		return computation2(params.get(POTENTIAL).get(coord),
				params.get(THRESHOLD).get(coord),
				params.get(LOW).get(coord),params.get(HIGH).get(coord));
	}

	/**
	 * 
	 * @param potential
	 * @param th : threshold
	 * @param low : value affected if the potential is lower than the threshold
	 * @param high : value affected if the potential is higher than the threshold
	 * @return
	 */
	protected double computation2(double potential, double th, double low, double high) {
		double ret;
		if(potential > th)
			ret = high;
		else 
			ret = low;
		
		return ret;
	}


}
