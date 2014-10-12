package main.java.console;

import java.math.BigDecimal;

import main.java.controler.ComputationControler;
import main.java.maps.Var;


public  class WaitRunner implements Runnable{
	
	private BigDecimal lastSimulationTime = new BigDecimal(0);
	private long lastRealTime = 0;
	private Var<Boolean> play;
	private Var<BigDecimal> timeToReach;
	private Var<BigDecimal> timeMax; //max of the simulation
	private ComputationControler computationControler;
	private Var<Double> timeSpeedRatio;
	
	private BigDecimal time; //simulation time
	private Var<String> mapToSave;
	private Var<String> pathToSave;
	private boolean maxSpeed;
	
	
	
	public WaitRunner(ComputationControler cc,boolean maxSpeed,Var<Boolean> play,Var<BigDecimal> timeToReach,Var<BigDecimal> timeMax,
			Var<Double> timeSpeedRatio,Var<String> mapToSave,Var<String> pathToSave){
		this.play = play;
		this.timeToReach = timeToReach;
		this.timeMax = timeMax;
		this.computationControler = cc;
		this.timeSpeedRatio = timeSpeedRatio;
		this.time = BigDecimal.ZERO;
		this.mapToSave = mapToSave;
		this.pathToSave = pathToSave;
		this.maxSpeed = maxSpeed;
				
	}
	
	public void reset(){
		this.time = BigDecimal.ZERO;
	}
	
	
	
	
	public void execute() {
//		System.out.println("run wait runner");
			try {
				if(time.equals(BigDecimal.ZERO)){
//					System.out.println("time zero");
					computationControler.checkFirstComputation();
				}
				//while((this.time.compareTo(timeMax.get()) < 0)){
					
					while((this.time.compareTo(timeToReach.get()) < 0)){
//						System.out.println(" current time : " + this.time + " timetoReach " + timeToReach.get());
						if((Boolean) play.get()){
							String maps = this.mapToSave.get();
							if(!maps.isEmpty()){
								
								String[] mapsToSaveArray = maps.split(",");
								computationControler.saveMap(mapsToSaveArray,pathToSave.get());
							}
							
							computationControler.compute();
							time = computationControler.getTime();
							if(!maxSpeed)
								this.delayComputationSpeed(time);
						}else{
//							System.out.println(" sleep1");
							Thread.sleep(100);
						}
					}
					this.play.set(false);
			//	}
				//System.out.println("print end of run");
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
	}
	
	@Override
	public void run() {
		while((Boolean) play.get() && (this.time.compareTo(timeMax.get()) < 0)){
			this.execute();
		}
		
	}
	
	
	

//	@Override
//	public void run() {
//		System.out.println("run wait runner");
//			try {
//				if(time.equals(BigDecimal.ZERO)){
//					System.out.println("time zero");
//					computationControler.checkFirstComputation();
//				}
//				while((this.time.compareTo(timeMax.get()) < 0)){
//					
//					while((this.time.compareTo(timeToReach.get()) < 0)){
//						System.out.println(" current time : " + this.time + " timetoReach " + timeToReach.get());
//						if((Boolean) play.get()){
//							String maps = this.mapToSave.get();
//							if(!maps.isEmpty()){
//								
//								String[] mapsToSaveArray = maps.split(",");
//								computationControler.saveMap(mapsToSaveArray,pathToSave.get());
//							}
//							
//							computationControler.compute();
//							time = computationControler.getTime();
//							this.delayComputationSpeed(time);
//						}else{
//							System.out.println(" sleep1");
//							Thread.sleep(100);
//						}
//					}
//					this.play.set(false);
//					System.out.println(" sleep2");
//					Thread.sleep(100);
//				}
//				System.out.println("print end of run");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				System.exit(-1);
//			}
//			
//			
//	}
	

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
//			System.out.println("Sleep : " + (wantedSimulationTime-realTimeGapSec)*1000);
			Thread.sleep((long) ((wantedSimulationTime-realTimeGapSec)*1000));
		}
		lastRealTime = currentRealTime;
		lastSimulationTime = new BigDecimal(""+currentSimulationTime.doubleValue());

	}


    public BigDecimal getCurrentTime() {
        return time;
    }
}
