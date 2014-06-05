package main.java.view;

import java.math.BigDecimal;
import java.util.logging.Logger;

import main.java.maps.Var;

public class ComputationControlerView {
	
	private final transient Logger LOGGER = Logger.getLogger(ComputationControlerView.class.getName());


	/**True if the simulation gui is playing**/
	private boolean play;

	/**Simulation time = realTime * timeSpeedRatio**/
	private Var<Double> timeSpeedRatio;

	public ComputationControlerView(Var<Double> timeSpeedRatio){
		this.play = false;
		this.timeSpeedRatio = timeSpeedRatio;
	}
	

	/**
	 * While pause thread if
	 * 	-play = false
	 * 	-we are simulating to fast in respect of theoretical simulation speed
	 * @param currentTime
	 */
	public void delayComputation(BigDecimal currentSimulationTime){
		try{
			while(!play){
				//LOGGER.info("Pause");
				Thread.sleep(100);
			}
			delayComputationSpeed(currentSimulationTime);
		}catch(InterruptedException e){
			e.printStackTrace();
		}

	}



	private BigDecimal lastSimulationTime = new BigDecimal(0);
	private long lastRealTime = 0;
	/**
	 * Delay computation according to the timeSpeedRatio
	 * @param currentSimulationTime
	 * @throws InterruptedException
	 */
	private void delayComputationSpeed(BigDecimal currentSimulationTime) throws InterruptedException{
		long currentRealTime = System.currentTimeMillis();
		
		double simulationTimeGap = currentSimulationTime.subtract(lastSimulationTime).doubleValue(); //seconds
		long realTimeGap = currentRealTime - lastRealTime;//milisec
		double realTimeGapSec = realTimeGap/1000d; //second
		//System.out.println("time speed ratio " +this.timeSpeedRatio.get());
		double wantedSimulationTime =  (simulationTimeGap * this.timeSpeedRatio.get());
		//System.out.println("Obtained simulation time " + wantedSimulationTime);
		
		if(realTimeGapSec < wantedSimulationTime){
			Thread.sleep((long) ((wantedSimulationTime-realTimeGapSec)*1000));
		}
		lastRealTime = currentRealTime;
		lastSimulationTime = new BigDecimal(""+currentSimulationTime.doubleValue());

	}

	/**
	 * Play pause switcher
	 */
	public void playPause(){
		this.play = !this.play;
	}
	



}
