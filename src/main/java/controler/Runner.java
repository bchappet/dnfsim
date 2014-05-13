package main.java.controler;

import java.awt.Dimension;
import java.net.URL;
import java.util.Scanner;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.gui.Printer;
import main.java.model.Model;
import main.java.model.Models;
import main.java.statistics.Characteristics;
import main.resources.utils.FluxUtils;

/**
 * Read the script and/or wait for dynamic command when there is a gui.
 * The gui send textual command to the Runner.
 * 
 * MultiTrhead commpatible : one Runner per thread, one GUI par thread (if needeed ) 
 * but a Printer to print them all.
 * Each runner output is preceded by the thread number and the model iteration number.
 * Because to avoid rebuilding the whole model tree at each iteration, we keep the Thread, reset the model and restart the computation
 *TODO check and double check that the first computation is the same that the second in every case!!!
 * @author benoit
 * @version 11/05/2014
 *
 */
public class Runner implements Runnable {

	private boolean gui;



	private CommandLine cl;

	private Printer printer;

	/**
	 * iteration of whole script computation
	 */
	private int iteration;

	private String runningScript;

	private GlobalView view;

	public Runner(Printer printer,String modelName,String initScript,String runningScript,boolean gui) throws Exception{
		this.gui = gui;
		this.printer = printer;
		this.runningScript = runningScript;



		if(gui){
			view = new GlobalView(new Dimension(GetScreenWorkingWidth(),GetScreenWorkingHeight()),this);
		}

		this.loadModel(modelName,initScript);

	}

	public GlobalView getGlobalView(){
		return this.view;
	}

	public CommandLine getCommandLine(){
		return this.cl;
	}

	/**
	 * 
	 * @param name model's name
	 * @param context (optional) if null will load the defaults context in context/name.dnfs file
	 */
	public void loadModel(String name,String context){
		try{
			String command;
			if(context == null){
				command = FluxUtils.readFile(new URL(Printer.CONTEXT_PATH+name+".dnfs"));
			}else{
				command = context;
			}

			Model model = Models.getModel(name).construct();
			this.cl = model.constructCommandLine();
			this.cl.setContext(command);
			model.initialize(cl);
			ModelControler mc = new ModelControler(model);
			cl.setCurentModelControler(mc);
			ComputationControler computationControler = new ComputationControler(mc.getTree());
			cl.setComputationControler(computationControler);

			CharacteristicsControler characContrl = (CharacteristicsControler) mc.getControler(Characteristics.NAME);
			cl.setCharacControler(characContrl);
			if(gui){
				this.view.loadModelView(name,mc,computationControler,model.getDefaultDisplayedParameter());
				this.view.setVisible(true);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}

	/**
	 * Command interpreter
	 * @param command
	 * @return
	 * @throws NumberFormatException
	 * @throws NullCoordinateException
	 * @throws CommandLineFormatException
	 */
	public String interpret(String command) throws NumberFormatException, NullCoordinateException, CommandLineFormatException{

		return cl.parseCommand(command);

	}

	@Override
	public void run() {

		try{
			if(runningScript != null){
				for(int i = 0 ; i < iteration ; i++){
					String[] commands = runningScript.split("[\n|;]+");
					for(int j = 0 ; j < commands.length ; j++){
						this.printer.print( this.interpret(commands[j]));
					}
				}
			}
			if(this.gui){
				this.printer.print(this.interpret("wait=10;"));
				Scanner sc = new Scanner(System.in);//TODO one input for each thread
				while(true){
					System.out.println("wait for command");
					String line = sc.nextLine();
					this.printer.print(this.interpret(line));
				}
			}
		}catch(Exception e){
			//TODO
			e.printStackTrace();
		}

	}


	/**
	 * Number of repetirion of the same scenario
	 * @param i
	 */
	public void setIteration(int i) {
		this.iteration = i;

	}


	public static int GetScreenWorkingWidth() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int GetScreenWorkingHeight() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}



}
