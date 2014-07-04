package main.java.gui;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager;

import main.java.controler.Runner;
import main.java.statistics.Statistics;



public class Printer{
	//Command line parameters

	public static final String MODEL = "model";
	public static final String CONTEXT = "context";
	public static final String SCENARIO = "scenario";
	public static final String SHOW_GUI = "show";
	public static final String NB_CORE = "core";
	public static final String ITERATION = "it";
	public static final String INFO = "info";
	public static final String SAVE_MAP = "savemap"; //save map at every computation

	/**Nb iteration printed**/
	protected int nb = 0;
	/**Nb info character to print**/
	private  String infoString;
	
	
	public static final String CONTEXT_PATH = "file:./src/main/scripts/context/";



	public Printer(int nbInfo){
		infoString = new String();
		for(int i = 0 ; i < nbInfo ; i++){
			infoString += "#"+i+",";
		}


	}

	public synchronized void print(String txt){
		txt = txt.replace(new Double(Statistics.ERROR).toString(), "NA");
		System.out.println(infoString + nb+","+txt);
		nb ++;
	}





	/** Main function to start the program **/
	public static void main(String args[]) {

		try {

			System.setProperty("file.encoding", "UTF-8");


			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());

			// UIManager.setLookAndFeel(
			// UIManager.getSystemLookAndFeelClassName());

			argsToProperties(args);

			String context = System.getProperty(CONTEXT);
			String savemapStr = System.getProperty(SAVE_MAP);
			boolean savemap = false;
			if(savemapStr != null ){
				savemap = true;
			}
			
			
			
			String model = System.getProperty(MODEL);
			if(model == null){
				System.err.println("The main.java.model should be precised. For instance " +
						"add the parameters : main.java.model=CNFT show=true");
				System.exit(-1);
			}
			String showGUI = System.getProperty(SHOW_GUI);

			boolean showGui = false;
			if(showGUI != null && showGUI.equals("true")){
				showGui = true;
			}
			String scenario = System.getProperty(SCENARIO);

			String contextScript = System.getProperty(CONTEXT);


			String nbInfoS = System.getProperty(INFO);
			int nbInfo = 0;
			if(nbInfoS!=null)
				nbInfo = Integer.parseInt(nbInfoS);

			Printer printer = new Printer(nbInfo);

			String iterationS = System.getProperty(ITERATION);
			int iteration = 1;
			if(iterationS != null)
				iteration = Integer.parseInt(iterationS);



			String nbCoreS = System.getProperty(NB_CORE);
			int nbCore = 4;
			if(nbCoreS != null)
				nbCore = Integer.parseInt(nbCoreS);

			Runner[] runners = new Runner[nbCore];
			Thread[] threads = new Thread[nbCore];
			int[] iterations = computeNbThreadIteration(iteration, nbCore);

			for(int i = 0 ; i < nbCore ; i++){
				if(iterations[i] > 0){

					runners[i] = new Runner(printer,model,contextScript,scenario,showGui);
					runners[i].setIterationCore(iterations[i]);
//					runners[i].setSavemap(savemap,i); //TODO

					threads[i] = new Thread(runners[i]);
					threads[i].start();
				}
			}




		}catch(FileNotFoundException e1){
			System.err.println("You should add the main.java.model context config file " +  System.getProperty(MODEL) + ".dnf in the context/ folder. Or you type a wrong main.java.model name.");
			e1.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public static int GetScreenWorkingWidth() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int GetScreenWorkingHeight() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}



	/** Convert initScript line arguments into system properties */
	public static void argsToProperties(String[] args) {
		// Go through all the parameters
		for (int i=0; i<args.length; i++) {
			Pattern pattern = Pattern.compile("^([^=]*)=(.*)$");
			Matcher matcher = pattern.matcher(args[i]);
			// Does it have the parameter format : name=str
			if (matcher.find()) {
				// Set the property
				String val1 =matcher.group(1).trim();
				String val2 = matcher.group(2).trim();
				val2 =  val2.replace("'", "");
				val2 = val2.replace("\\", "");
				System.setProperty(val1,val2);
			}
		}
	}



	/**
	 * 
	 * @return a integer array with the number of iteration per core given the total
	 * number of iteration and the nb of available cores
	 */
	public static int[] computeNbThreadIteration(int nbIt,int nbCores){
		int[] res = new int[nbCores];

		int base = nbIt / nbCores;
		int rest = nbIt % nbCores;

		for(int i = 0 ; i < nbCores ; i++){
			res[i] = base;
		}

		for(int i = 0 ; i < rest ; i++){
			res[i] ++;
		}
		return res;

	}





}


