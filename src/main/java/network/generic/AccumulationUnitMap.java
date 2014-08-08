package main.java.network.generic;

import java.math.BigDecimal;

import main.java.console.CommandLineFormatException;
import main.java.maps.Parameter;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.Space;
import main.java.space.Space2D;
import main.java.unitModel.UnitModel;

public class AccumulationUnitMap extends UnitMap<Integer,Integer>{

	//String name,Var<BigDecimal> dt,Space<C> space,UnitModel<T> unitModel, Parameter... param
	public AccumulationUnitMap(SpreadingGraph sg,Var<BigDecimal> dt,Var<Integer> size) throws CommandLineFormatException {
		super(
				"ReceiveMap", 
				dt, 
				new Space2D(size,size), 
				new AccumulationUnitModel(/*sg*/), 
				sg);
		
	}

}
