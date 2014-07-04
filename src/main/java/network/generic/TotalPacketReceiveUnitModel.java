package main.java.network.generic;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Parameter;
import main.java.unitModel.UnitModel;


public class TotalPacketReceiveUnitModel extends UnitModel<Integer>{

	private SpreadingGraph sg;

	public TotalPacketReceiveUnitModel(SpreadingGraph sg) {
		super(0);
		this.sg = sg;
	}

	@Override
	protected Integer compute(BigDecimal time, int index,
			List<Parameter> params) {
		System.out.println("index : "+index+" last packet reçus : "+sg.getIndex(index).getTotalPacketReceived());
		return sg.getIndex(index).getTotalPacketReceived();
	}

}
