package maps;

import unitModel.UnitModel;
import utils.ArrayUtils;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * 
 * Local values of the parameter are used here
 * The space of this map need to contains all parameters spaces dimensions!!!
 * 
 * 
 * 
 * Facility class to rapidly create trajectory
 * The refSpace is cloned and forced to be "dimenstionless" : 
 * this.space = refSpace.clone()
 * this.space.setDimension(0,0,...);
 * a TrajectoryUnitModel is constructed with evaluate method as computation.
 * @author bchappet
 *
 */
public abstract class TrajectoryUnitMap extends Map {
	
	public TrajectoryUnitMap(String theName,Var dt, Space space, Parameter... maps)
	{
		super(theName,null,dt,space,maps);
//		System.out.println("Nom : " + name + " space : " + space);
		this.unitModel = new UnitModel(this) {
			@Override
			public double compute(){
				
				return computeTrajectory(ArrayUtils.getValues(params,this.coord));
			}
		};
	}
	
	/**
	 * Compute activity knowing the values of parameters at specific coord
	 * @param param
	 * @return
	 * @throws NullCoordinateException
	 */
	public abstract double computeTrajectory(double... param);

}
