package main.java.controler;

import java.math.BigDecimal;

import main.java.maps.Map;
import main.java.view.MapViewAdapter;
import main.java.view.ParamViewAdapter;

public class MapControler extends ParameterControler implements ComputableControler {

	public MapControler(Map param) {
		super(param, new MapViewAdapter(param));
	}
	
	public MapControler(Map param,ParamViewAdapter adaptater) {
		super(param, adaptater);
	}
	
	

	public void compute(BigDecimal currentTime){
		Map map = (Map) this.getParam();
		if(map.getTime().add((BigDecimal) map.getDt().get()).compareTo(currentTime) == 0){
			map.setTime(currentTime);
			map.compute();
			this.getParaView().updateView(currentTime);
		}
	}
	
	public BigDecimal getNextTime(){
		Map map = (Map) this.getParam();
		BigDecimal nextTime = map.getTime().add((BigDecimal) map.getDt().get());
		return nextTime;
	}

	@Override
	public String toString() {
		return ((Map)getParam()).getName();
	}

}
