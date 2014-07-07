package main.java.controler;

import java.math.BigDecimal;

import main.java.statistics.Characteristics;

public class CharacteristicsControler extends  ParameterControler {

	public CharacteristicsControler(Characteristics param) {
		super(param);
	}


	public void compute(BigDecimal currentTime) {
		
			
		
		
	}


	

	public String[] getTrajectoryUnitMapsName() {
		return ((Characteristics) getParam()).getTrajectoryUnitMapsName();
	}

}
