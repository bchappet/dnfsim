package gui;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager;

import model.Model;
import model.Root;
import statistics.Statistics;
import applet.AppletStub;



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


	public static String readFile(URL url) throws FileNotFoundException{
		File file = new File(url.getPath());
		StringBuilder fileContents = new StringBuilder((int)file.length());
		Scanner scanner = new Scanner(file);
		String command = null;

		try {
			while(scanner.hasNextLine()) {        
				fileContents.append(scanner.nextLine() );
			}
			command = fileContents.toString();
		} finally {
			scanner.close();
		}

		return command;

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
			if(savemapStr != null && savemapStr.equals("true")){
				savemap = true;
			}
			String model = System.getProperty(MODEL);
			if(model == null){
				System.err.println("The model should be precised. For instance " +
						"add the parameter : model=CNFT show=true");
				System.exit(-1);
			}
			String showGUI = System.getProperty(SHOW_GUI);

			boolean showGui = false;
			if(showGUI != null && showGUI.equals("true")){
				showGui = true;
			}
			String scenario = System.getProperty(SCENARIO);

			String contextScript;
			URL contextPath;

			contextPath = new URL("file:./context/");


			if(context == null || context.isEmpty()){

				//Default behaviour : read the file in default context path with the same name as the model
				contextScript = readFile(new URL("file:"+contextPath.getPath()+model+".dnfs"));
			}else{

				//The context is given by the string 
				contextScript = context;
			}

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
					Root root = new Root();
					Model modelM = Root.constructModel(model);
					root.addModel(modelM);
					root.setActiveModel(model);

					runners[i] = new Runner(modelM,scenario,printer);
					runners[i].setIteration(iterations[i]);
					runners[i].setSavemap(savemap,i);

					root.getActiveModel().initialize(contextScript);
					root.getActiveModel().getCommandLine().setRunner(runners[i]);

					if(showGui){
						RunnerGUI applet =  new RunnerGUI(runners[i],root,contextPath,new Dimension(GetScreenWorkingWidth(),GetScreenWorkingHeight()-50));
						// Configure the frame to display the Applet
						applet.setStub(new AppletStub(applet, "CNFT simulation"));
						runners[i].setLock(applet.getLock());
					}

					threads[i] = new Thread(runners[i]);
					threads[i].start();
				}
			}




		}catch(FileNotFoundException e1){
			System.err.println("You should add the model context config file " +  System.getProperty(MODEL) + ".dnf in the context/ folder. Or you type a wrong model name.");
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



	/** Convert command line arguments into system properties */
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


