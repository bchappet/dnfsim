package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import model.Model;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;


/**
 * This is the Runnable which will handle the model computation.
 * 
 * If a scenario is given at the construction of the printer, it will be executed on thread start, as many 
 * time as "iteration" attribute value.
 * Otherwise, if a gui is present, the thread wait for play to be true.
 * 
 * As several thread can be lauched with the same model, the core used is given by the "core" attribute.
 * 
 * 
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
	 * TODO why is it also the diplay dt now? (from dnf_som...)
	 */
	protected double timeStep = 0.01; //in s





	public Runner(Model model,String scenario,Printer printer){
		this.model = model;
		this.scenario = scenario;
		this.gui = null;
		this.printer = printer;
		this.lock = new ReentrantLock(true);
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
			update(timeStep);
			
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}
		

		if(play)
			play = false;

	}
	
	/**
	 * Update the model
	 * time in seconds
	 * @throws CommandLineFormatException
	 */
	protected void update(double time) throws CommandLineFormatException
	{
		lock.lock(); //this lock ensure that we do not change the displayed model or change parameters during computation
		try{
			model.update(time);
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
					
					printer.print(ret);//as sevaral thread want to print their results, we ordonance the display with a printer

					reinitialize();
					reset();
				}
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
					update(timeStep);
					// Remaining delay to wait after the computations
					long d = (long)(speedRatio * model.getDt() * 1000)-(System.currentTimeMillis()-time_start);
					// Wait for the necessary amount of time

					if (d>0)
						Thread.sleep(d);

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
	 * Reinitialize parameters with the  default script and the context command
	 */
	public void reinitialize()
	{
		try{
			model.getCommandLine().reinitialize();
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
		if(gui != null)
			gui.exit();

	}



	public boolean isPlay() {
		return play;
	}


	public void setIteration(int iteration2) {
		this.iteration = iteration2;

	}
	
	

	public double getTimeStep() {
		return timeStep;
	}


	public void setTimeStep(double timeStep) {
		this.timeStep = timeStep;
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



	
	
	
}


