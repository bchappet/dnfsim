package main.java.network.generic;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.unitModel.UnitModel;


public class AccumulationUnitModel extends UnitModel<Integer>{

	//private SpreadingGraph sg;

	private static final int SPREADING_GRAPH = 0;

	public AccumulationUnitModel(/*SpreadingGraph sg*/) {
		super(0);
		//this.sg = sg;
	}

	@Override
	protected Integer compute(BigDecimal time, int index,
			List<Parameter> params) {
		SpreadingGraph sg = (SpreadingGraph) params.get(SPREADING_GRAPH);
//		System.out.println("index : "+index+" last packet reçus : "+sg.getIndex(index).getTotalPacketReceived());
		return sg.getIndex(index).getTotalPacketReceived();
	}

}
