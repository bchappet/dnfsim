package main.java.network.generic;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.unitModel.UnitModel;


public class ConcentrationPacketUnitModel extends UnitModel<Integer>{

	private static final int SPREADING_GRAPH = 0;

	public ConcentrationPacketUnitModel(/*SpreadingGraph sg*/) {
		super(0);
		//this.sg = sg;
	}

	@Override
	protected Integer compute(BigDecimal time, int index,
			List<Parameter> params) {
		SpreadingGraph sg = (SpreadingGraph) params.get(SPREADING_GRAPH);
		return sg.getIndex(index).getLastPacketReceiveNumber();
		
	}

}