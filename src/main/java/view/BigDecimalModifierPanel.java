package main.java.view;

import java.math.BigDecimal;

import main.java.controler.VarControler;

public class BigDecimalModifierPanel extends ParameterModifierPanel {

	public BigDecimalModifierPanel(VarControler var) {
		super(var);
	}
	
	protected void addToValue() {
		var.set(((BigDecimal)getValue(var)).add(new BigDecimal(""+amount)));
	}
	
	protected void subToValue() {
		var.set(((BigDecimal)getValue(var)).subtract(new BigDecimal(""+amount)));
	}
	

	

}
