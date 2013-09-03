package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import model.Model;
import model.Root;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;


public class Runner  implements Runnable{

	/**Speed ratio of the simulation for 1, simulation seconds = real seconds**/
	protected double speedRatio = 1;
	/**Is the simulation running?**/
	protected boolean play = false;
	/**If true, we exit applet**/
	protected boolean exit = false;

	protected GUI gui;
	protected Model model;
	protected String scenario;
	/**Number of iteration of computation**/
	protected int iteration = 1;

	/**Safely (concurrent compatible) printing of the results */
	protected Printer printer; 

	/**share with the GUI if there is a gui**/
	protected Lock lock;
	/**save every step if true with format save_core_it_computation#_Map.csv**/
	protected boolean savemap;
	/**Core used #**/
	protected int core;
	
	
	protected int numComputation; //Computation # start with 0





	public Runner(Model model,String scenario,Printer printer){
		this.model = model;
		this.scenario = scenario;
		this.gui = null;
		this.printer = printer;
		this.lock = new ReentrantLock(true);
		this.numComputation = 0;
	}

	public void setLock(Lock lock){
		this.lock = lock;
	}



	public void step()
	{
		try {
			update();
			if(savemap){
				try {
					this.saveMaps("maps/save_"+core+"_"+iteration+"_"+numComputation);
				} catch (NullCoordinateException e) {
					e.printStackTrace();
					System.exit(-1);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
			numComputation ++;
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}
		if(gui != null)
			gui.repaint();

		if(play)
			play = false;

	}

	protected void update() throws CommandLineFormatException
	{
		lock.lock();
		try{
			model.update();
			if(gui!= null)
				gui.repaint();
		}finally{
			lock.unlock();
		}

	}

	@Override
	public void run(){




		//Lauch scenario
		try{
			if(scenario != null){
				for(int i = 0 ; i< iteration ; i ++){

					String ret = model.getCommandLine().parseCommand(scenario);
					printer.print(ret);

					reinitialize();
					reset();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		if(gui != null){

			while( !exit )
			{
				try {
					while(!play && !exit)
						Thread.sleep(100);
					long time_start = System.currentTimeMillis();
					update();
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
	 * Reinitialize every command line parameter to their initial value
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



	public void setGui(GUI gui) {
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

	public void hardReset() throws Exception {
		if(gui != null)
			gui.resetHard();
		
	}

	public void setModel(Model model2) {
		this.model = model2;
		
	}

	public void setSavemap(boolean savemap, int core) {
		this.savemap = savemap;
		this.core = core;
		
	}

	
	
	
}


