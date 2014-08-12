package main.java.network.generic;

import java.math.BigDecimal;

import main.java.console.CommandLineFormatException;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.Space2D;

public class ConcentrationUnitMap extends UnitMap<Integer,Integer>{

	//String name,Var<BigDecimal> dt,Space<C> space,UnitModel<T> unitModel, Parameter... param
	public  ConcentrationUnitMap(String name,SpreadingGraph sg,Var<BigDecimal> dt,Var<Integer> size) throws CommandLineFormatException {
		super(
				name, 
				dt, 
				new Space2D(size,size), 
				new ConcentrationPacketUnitModel(/*sg*/), 
				sg);
		
	}

}
