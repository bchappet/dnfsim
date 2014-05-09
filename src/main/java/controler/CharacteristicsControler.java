package main.java.controler;

import java.math.BigDecimal;

import main.java.statistics.Characteristics;
import main.java.view.CharacteristicsViewAdapter;

public class CharacteristicsControler extends ParameterControler implements ComputableControler {

	public CharacteristicsControler(Characteristics param) {
		super(param, new CharacteristicsViewAdapter(param));
	}


	@Override
	public void compute(BigDecimal currentTime) {
		Characteristics map = ((Characteristics)getParam());
		
		if(map.getTime().add((BigDecimal) map.getDt().get()).compareTo(currentTime) == 0){
			map.setTime(currentTime);
			map.compute();
			this.getParaView().updateView(currentTime);
		}
		
	}

	@Override
	public BigDecimal getNextTime() {
		Characteristics map = (Characteristics) this.getParam();
		BigDecimal nextTime = map.getTime().add((BigDecimal) map.getDt().get());
		return nextTime;
	}

}
