package main.java.console;

import java.math.BigDecimal;

import main.java.controler.ComputationControler;
import main.java.maps.Var;


public  class WaitRunner  implements Runnable{
	
	private BigDecimal lastSimulationTime = new BigDecimal(0);
	private long lastRealTime = 0;
	private Var<Boolean> play;
	private Var<BigDecimal> timeToReach;
	private Var<BigDecimal> timeMax; //max of the simulation
	private ComputationControler computationControler;
	private Var<Double> timeSpeedRatio;
	
	private BigDecimal time; //simulation time
	
	
	
	public WaitRunner(ComputationControler cc,Var<Boolean> play,Var<BigDecimal> timeToReach,Var<BigDecimal> timeMax,
			Var<Double> timeSpeedRatio){
		this.play = play;
		this.timeToReach = timeToReach;
		this.timeMax = timeMax;
		this.computationControler = cc;
		this.timeSpeedRatio = timeSpeedRatio;
		this.time = BigDecimal.ZERO;
	}

	@Override
	public void run() {
			try {
				
				if(time.equals(BigDecimal.ZERO)){
					computationControler.checkFirstComputation();
				}
				
				while((this.time.compareTo(timeToReach.get()) < 0) && (this.time.compareTo(timeMax.get()) < 0)){
					if((Boolean) play.get()){
						computationControler.compute();
						time = computationControler.getTime();
						this.delayComputationSpeed(time);
					}else{
						Thread.sleep(100);
					}
				}
				this.play.set(false);
				System.out.println("print end of run");
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			
	}
	

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

}
