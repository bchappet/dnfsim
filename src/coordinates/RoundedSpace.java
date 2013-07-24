package coordinates;

import java.math.BigDecimal;

import maps.Var;


/**
 * The refSpace currently used : 
 * -0.5 -0.3 -0.1 0.1 0.3 0.5  : continous coord                                 
 *   [---------------------[
 *     0  |  1 | 2 | 3 | 4     : discretes coord
 * @author bchappet
 *
 */
public class RoundedSpace extends Space {

	public RoundedSpace(Double[] origin, Double[] size,
			Var resolution,boolean wrap) {
		super(origin, size, resolution,wrap);
	}

	public RoundedSpace clone()
	{
		return (RoundedSpace) super.clone();
	}

	@Override
	public void initDiscreteSpace(double resolution)
	{
		discreteSize = new Integer[dim];
		for(int i = 0 ; i < dim ; i++)
		{
			//discreteSize[i] = (int)Math.round(size[i] * resolution);
			discreteSize[i] = (int)Math.round(resolution);
		}

	}

	@Override
	public Double[] discreteProj(Double[] coor) {
		Double[] ret = new Double[dim];

		int i = 0;

		for( i = 0 ; i < dim ; i++)
		{
				if(dimension[i] == 1 && coor[i] != null){
					double fact = this.size[i]/res;
					//The problem is that without the rounding 
					//we have for instance 0.6/0.2 that gives 2.9999999999 which will be casted in 2
					//But we should have 3. => that is why we have to use a precision rounding
					ret[i] = (double)(int) round(((coor[i] - this.origin[i]) / fact),10);
				}else{
					ret[i] = null;
				}

		}
		return ret;
	}

	/**
	 * Use BigDecimal to round the double at the given precision
	 * @param d : the double to round 
	 * @param decimalPlace  : the precision 
	 * @return
	 */
	public static double round(double d, int decimalPlace){
		//source : http://www.rgagnon.com/javadetails/java-0016.html
		// see the Javadoc about why we use a String in the constructor
		// http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public double continuousProj(double val, int axis) {
		double fact = this.size[axis]/res;
		//System.out.println("fact : "+ fact);
		return this.origin[axis] + fact/2 + fact*val;
	}

	@Override
	public double distContinuousProj(int val, int axis) {
		double fact = this.size[axis]/res;
		return  fact*val;

	}

	@Override
	public Double[] continuousProj(Double[] coor) {
		int i= 0;
		Double[] ret = new Double[dim];
		for(i = 0 ; i < dim ; i++)
		{
			if(coor[i] != null){
				double fact = this.size[i]/res;
				ret[i] = this.origin[i] + fact/2 + fact*coor[i];
			}else{
				ret[i] = null;
			}
		}

		return ret;
	}

	@Override
	public Double continusousProj(double val,int axis) throws NullCoordinateException
	{
		Double ret = null;

		double fact = this.size[axis]/res;
		ret = this.origin[axis] + fact/2 + fact*val;
		return ret;
	}

	@Override
	public Space extend(double factor){
		Double[] newOrig = new Double[origin.length];
		Double[] newSize = new Double[size.length];

		for(int i = 0 ; i < newSize.length ; i++){
			newSize[i] = size[i]*factor;
			newOrig[i] = origin[i] - ((newSize[i] - size[i])/2.0);
		}

		//TODO : the new res should be a TrajectoryMap with the "main" resolution as parameter
		//=> when the "main" resolution changes, the new res will change automatically

		Space ret=  new RoundedSpace(newOrig, newSize, 
				new Var("res",this.res*factor), this.wrap);
		ret.setSimulationSpace(this); //keep the simulation refSpace for future usage
		return ret;
	}
	
	
	@Override
	public RoundedSpace transpose() {
		Double[] transOrigin = new Double[origin.length];
		for(int i = 0 ; i < origin.length ; i++)
			transOrigin[transOrigin.length-i-1] = origin[i];
		
		Double[] transSize = new Double[size.length];
		for(int i = 0 ; i < size.length ; i++)
			transSize[transSize.length-i-1] = size[i];
		
		return new RoundedSpace(transOrigin,	transSize, this.resolution,this.wrap);
		
		
	}




}
