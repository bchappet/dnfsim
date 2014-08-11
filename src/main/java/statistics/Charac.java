package main.java.statistics;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.InfiniteDt;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.model.Model;
import main.java.unitModel.UnitModel;

public abstract class Charac extends Trajectory<Double> {
	
	protected Statistics stats;
	protected Model model;
	protected int index = 0;

	public Charac(String name, Statistics stats,Model model, Parameter... params) {
		super(name, new InfiniteDt(),null, params);

			this.initMemory(new UnitModel<Double>(0.){
			@Override
			protected Double compute(BigDecimal time, int index,
					List<Parameter> params) {
				return computeTrajectory(params);
			}
		});
		this.stats =stats;
		this.model = model;
	}
	
	
	protected abstract Double computeTrajectory(List<Parameter> params);


	/**
	 * Return the parameter with the given name (0) otherwhise TODO integrate in another class
	 * @param name
	 * @return
	 */
	protected Parameter getByName(String name)
	{
		for(Parameter p : getParameters())
		{
			if(p.getName().equals(name))
				return p;
		}
		return null;
	}

}
