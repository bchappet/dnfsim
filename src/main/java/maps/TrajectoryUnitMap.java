package main.java.maps;

import java.util.ArrayList;
import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.unitModel.UnitModel;
import main.resources.utils.ArrayUtils;

/**
 * 
 * Local values of the parameter are used here
 * The main.java.space of this map need to contains all parameters spaces dimensions!!!
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
		this.isTrajectory = true;
//		System.out.println("Nom : " + name + " main.java.space : " + main.java.space);
		this.unitModel = new UnitModel(this) {
			@Override
			public double compute(){
				return computeTrajectory(ArrayUtils.getValues(params,this.coord));
			}
		};
	}
	
	@Override //because we don't want to set the value
	public  void constructMemory() 
	{

		if(!isMemory)
		{
			
			//System.out.println("Construct memory of " + name + Arrays.toString(Thread.currentThread().getStackTrace()));
			//Init the coordinate array of this unit

			//Create the generic collection
			this.units =  new ArrayList<Unit>();
			//Construct as much main.java.unitModel as necessary
			//with the correct coordinate and the right value...
			//System.out.println(this.name + " construct memory vol = " + main.java.space.getDiscreteVolume());
			for(int i = 0 ; i < space.getDiscreteVolume() ; i++)
			{
				UnitModel u = (UnitModel)unitModel.clone();
				u.setCoord(space.indexToCoord(i));
				
				this.units.add(new Unit(u));
			}
			this.isMemory = true;
		}
	}
	
	/**
	 * Compute activity knowing the values of parameters at specific coord
	 * @param param
	 * @return
	 * @throws NullCoordinateException
	 */
	public abstract double computeTrajectory(double... param);

}
