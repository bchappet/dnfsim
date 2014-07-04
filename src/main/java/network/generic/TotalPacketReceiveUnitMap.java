package main.java.network.generic;

import java.math.BigDecimal;

import main.java.console.CommandLineFormatException;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.Space2D;

public class TotalPacketReceiveUnitMap extends UnitMap<Integer,Integer>{

	public TotalPacketReceiveUnitMap(SpreadingGraph sg,Var<BigDecimal> dt,Var<Integer> size) throws CommandLineFormatException {
		super(
				"ReceiveMap", 
				dt, 
				new Space2D(size,size), 
				new TotalPacketReceiveUnitModel(sg), 
				sg);
	}

}
