package main.java.controler;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.swing.JPanel;

import main.java.maps.Computable;
import main.java.maps.InfiniteDt;
import main.java.maps.Parameter;
import main.java.view.View2D;

public abstract class ComputableControler extends ParameterControler {
	public ComputableControler(Parameter param) {
		super(param);
	}

	private final transient Logger LOGGER = Logger.getLogger(ComputableControler.class.getName());
	
	/**
	 * Compute the main.java.model and update the main.java.view
	 * @param currentTime
	 */
	public void compute(BigDecimal currentTime) {
		
		Computable map = ((Computable)getParam());
			map.setTime(currentTime);
			LOGGER.info("compute "+ this.getName() + " : current time = " + currentTime + " class : " + this.getClass());
			map.compute();
			
			
			if(this.getParamViewAdapter() != null){
				//System.out.println("*************Display " + map.toString());
//				if(this.getParamView() instanceof View2D)
//				System.out.println(map.toString());
//				
				//TODO also delay to reduce display frequency
				LOGGER.info("******************update view : " + this.getName());
				this.getParamViewAdapter().updateView(currentTime);
				((JPanel) this.getParamView()).repaint();
			}
	}

	/**
	 * Compute the next computation time
	 * @return
	 */
	public BigDecimal getNextTime() {
		Computable map = (Computable) this.getParam();
		BigDecimal nextTime = map.getTime().add((BigDecimal) map.getDt().get());
		return nextTime;
	}

	public boolean isStatic() {
		Computable map = (Computable) this.getParam();
		return map.getDt() instanceof InfiniteDt;
	}

}
