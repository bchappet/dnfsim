package main.java.statistics;

import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.TrajectoryUnitMap;
import main.java.maps.Var;
import main.java.model.Model;

public abstract class Charac extends TrajectoryUnitMap {
	
	protected Statistics stats;
	protected Model model;

	public Charac(String name, Statistics stats, Space space,Model model, Parameter... params) {
		super(name, new Var("Dt", 0), space, params);//TODO shouldnot have dt
		this.stats =stats;
		this.model = model;
	}
	
	/**
	 * Return the given parameter value
	 * @param name of the parameter
	 * @return
	 */
	protected double get(String name)
	{
		double ret = 0;
		try {
			ret = getByName(name).get();
		}catch (NullCoordinateException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return ret;
	}
	
	/**
	 * Return the parameter with the given name (0) otherwhise TODO integrate in another class
	 * @param name
	 * @return
	 */
	protected Parameter getByName(String name)
	{	
		for(Parameter p : params)
		{
			if(p.getName().equals(name))
				return p;
		}
		return null;
	}
	
	
	

	


}
