package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import maps.Var;
import model.Model;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;


/**
 * This is the Runnable which will handle the model computation.
 * 
 * If a scenario is given at the construction of the printer, it will be executed on thread start, as many 
 * time as "iteration" attribute value.
 * Otherwise, if a gui is present, the thread wait for play to be true.
 * 
 * As several thread can be launched with the same model, the core used is given by the "core" attribute.
 * 
 * 
 * 
 * @author bchappet
 *
 */
public class Runner  implements Runnable{

	/**Speed ratio of the simulation if 1, simulation seconds = real seconds**/
	protected double speedRatio = 1;
	/**Is the simulation running?**/
	protected boolean play = false;
	/**If true, we exit applet**/
	protected boolean exit = false;

	protected RunnerGUI gui;
	protected Model model;
	/**If not null the scenario is lauched on thread start**/
	protected String scenario;
	/**Number of iteration of computation**/
	protected int iteration = 1;

	/**Safely (concurrent compatible) printing of the results */
	protected Printer printer; 

	/**share with the GUI if there is a gui**/
	protected Lock lock;
	/**save every step if true with format save_core_it_Map.csv**/
	protected boolean savemap;
	/**Core used #**/
	protected int core;
	
	

	
	/**
	 * Time of each step
	 * TODO why is it also the display dt now? (from dnf_som...)
	 */
	protected Var guiStep = null; //in s
	
	/**Current time of simulation**/
	protected BigDecimal time;
	
	


	public Runner(Model model,String scenario,Printer printer) throws CommandLineFormatException{
		this.model = model;
		this.scenario = scenario;
		this.gui = null;
		this.printer = printer;
		this.lock = new ReentrantLock(true);
		this.time = BigDecimal.ZERO;
	}


	public void setLock(Lock lock){
		this.lock = lock;
	}


	/**
	 * One update of the model**
	 * of time in seconds

	 */
	public void step()
	{
		try {
			BigDecimal gStep = new BigDecimal(guiStep.get());
			gStep = gStep.setScale(Model.SCALE_LIMIT,Model.ROUDING_MODE);
			time = time.add(gStep);
			update(time);

		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}
		if(play)
			play = false;

	}

	/**
	 * Simulate to time  timeToReach (s)
	 * @param time
	 * @throws CommandLineFormatException 
	 */
	public void simulate(BigDecimal timeToReach) throws CommandLineFormatException {
		//System.out.println("Simulate :  Current time = " + time + " time end = " +  timeToReach);
		
		BigDecimal gStep = new BigDecimal(guiStep.get());
		gStep = gStep.setScale(Model.SCALE_LIMIT,  Model.ROUDING_MODE);
		
		time = time.add(gStep);
		while( time.compareTo( timeToReach) <= 0){
			//System.out.println(" ==> update " + time);
			update(time);
			time = time.add(gStep);
		}

	}

	/**
	 * Update the model
	 * time in seconds
	 * @throws CommandLineFormatException
	 */
	protected void update(BigDecimal timeToReach) throws CommandLineFormatException
	{
		lock.lock(); //this lock ensure that we do not change the displayed model or change parameters during computation
		try{
			//System.out.println("Update ==> " + timeToReach);
			model.update(timeToReach);
			if(gui != null)
				gui.repaint();
		}finally{
			lock.unlock();
		}

	}

	@Override
	public void run(){
		
		//Lauch scenario if there is one
		try{
			if(scenario != null){ //as many time as iteration
				for(int i = 0 ; i< iteration ; i ++){
					//System.out.println("scenar : " + scenario);
					String ret = model.getCommandLine().parseCommand(scenario);

					printer.print(ret);//as several threads want to print their results, we ordonance the display with a printer

					reinitialize();
					reset();
				}
			}else{//there is no scenario, we init manually
				
				this.firstComputation();
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		if(gui != null){ //if there is a gui, we wait for play

			while( !exit )
			{
				try {
					while(!play && !exit)
						Thread.sleep(100);
					long time_start = System.currentTimeMillis();
					
					BigDecimal gStep = new BigDecimal(guiStep.get());
					gStep = gStep.setScale(Model.SCALE_LIMIT,  Model.ROUDING_MODE);
					
					time = time.add(gStep);
					
					update(time);
					long computationTime = System.currentTimeMillis()-time_start;
					// Remaining delay to wait after the computations
					long sleep = (long)(guiStep.get()*1000-computationTime);
					// Wait for the necessary amount of time
					if (sleep>0)
						Thread.sleep(sleep);

				}catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}



	public void setPrinter(Printer printer2) {
		this.printer = printer2;
	}
	
	/**
	 * Compute time = 0 state
	 * @throws CommandLineFormatException 
	 */
	public void firstComputation() throws CommandLineFormatException{
		update(BigDecimal.ZERO);
	}





	/**
	 * Reinitialize parameters with the  default script and the context command
	 */
	public void reinitialize()
	
	{
		try{
			model.getCommandLine().reinitialize();
			this.time = BigDecimal.ZERO;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reset time and memory
	 */
	public void reset() {
		model.reset();
		this.time = BigDecimal.ZERO;
	}

	public void setSpeedRatio(double v)
	{
		speedRatio = v;

	}

	public void play_pause()
	{
		play = !play;
	}

	public void play()
	{
		play = true;
	}

	public boolean pause()
	{
		boolean ret = play;
		play = false;
		return ret;
	}

	public double getSpeedRatio() {
		return speedRatio;
	}

	public void setGui(RunnerGUI gui) {
		this.gui = gui;
	}

	public void exit() {

		exit = true;

	}

	public boolean isPlay() {
		return play;
	}


	public void setIteration(int iteration2) {
		this.iteration = iteration2;

	}



	public double getTimeStep() {
		return guiStep.get();
	}


	public void setTimeStep(double timeStep) {
		this.guiStep.set(timeStep);
	}


	/**
	 * Save statistics in the given file
	 * @param filename
	 * @throws IOException
	 */
	public void saveStats(String filename) throws IOException {
		model.getStatistics().save(filename);
	}

	/**
	 * Save the displayed map in the filename
	 * @param filename
	 * @return the name of the saved files
	 * @throws NullCoordinateException
	 * @throws IOException
	 */
	public String saveMaps(String filename) throws NullCoordinateException, IOException {
		return model.save(filename,model.getDefaultDisplayedParameter());
	}

	/**
	 * This will be to change model resolution
	 * @deprecated do not work for now
	 * @throws Exception
	 */
	public void hardReset() throws Exception {
		if(gui != null){
			//System.out.println("Hard reset");
			gui.resetHard();
			this.time = BigDecimal.ZERO;
		}
	}

	public void setModel(Model model2) {
		this.model = model2;

	}

	/**
	 * If save map is true, each iteration will be saved
	 * @param savemap
	 * @param core
	 */
	public void setSavemap(boolean savemap, int core) {
		this.savemap = savemap;
		this.core = core;

	}


	public void test() {
		try {
			model.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void setSimulationStep(double d) {
		guiStep.set(d);

	}


	public double getSimulationStep() {
		return guiStep.get();
	}


	public void setSimulationStep(Var var) {
		guiStep = var;
	}


}


