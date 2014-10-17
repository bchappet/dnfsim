package main.java.controler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.model.Model;
import main.java.model.Models;
import main.java.statistics.Characteristics;
import main.java.view.GlobalView;
import main.resources.utils.FluxUtils;


/**
 * Read the script and/or wait for dynamic command when there is a gui.
 * The gui send textual command to the Runner.
 *
 * MultiTrhead compatible : one Runner per thread, one GUI par thread (if needeed ) 
 * but a Printer to print them all.
 * Each runner output is preceded by the thread number and the model iterationCore number.
 * Because to avoid rebuilding the whole model tree at each iterationCore, we keep the Thread, reset the model and restart the computation
 * TODO check and double check that the first computation is the same that the second in every case!!!
 * @author benoit
 * @version 11/05/2014
 *
 */
public class Runner extends JFrame implements Runnable {

    private final String[] mapsToSave;
    private String savePath;
    private boolean gui;

    private CommandLine cl;

    private Printer printer;

    /**
     * iterationCore of whole script computation
     */
    private int iterationCore;

    private List<Integer> iterationIds;

    private String runningScript;

    private GlobalView view;

    private ComputationControler computationControler;



    public  Runner(Printer printer,String modelName,String initScript,String runningScript,
                   boolean gui,String[] mapsToSave,String savePath,List<Integer> iterationIds) throws Exception{
        this.gui = gui;
//		System.out.println(" construct Runner" + iterationIds);
        this.savePath = savePath;
        this.printer = printer;
        this.runningScript = runningScript;
        this.iterationIds = iterationIds;
        this.mapsToSave = mapsToSave;
        if(this.iterationIds.isEmpty()){
            this.iterationIds.add(0);
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(gui){
            Dimension dim = new Dimension(GetScreenWorkingWidth(),GetScreenWorkingHeight());
            view = new GlobalView(dim,this,modelName);

        }
        this.loadModel(modelName,initScript);

        if(gui){
            this.getContentPane().setLayout(new BorderLayout());
            this.setSize(view.getSize());
            this.add(view);
            this.setVisible(true);
        }


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
                command = FluxUtils.readFile(new URL(Printer.CONTEXT_PATH + name + ".dnfs"));
                //System.out.println("context : "+Printer.CONTEXT_PATH+name+".dnfs");
            }else{
                command = context;
            }

            Model model = Models.getModel(name).construct();
            this.cl = model.constructCommandLine();
            this.cl.setContext(command);
            model.initialize(cl);
            ModelControler mc = new ModelControler(model);
            cl.setCurentModelControler(mc);
            computationControler = new ComputationControler(mc.getTree());
            cl.setComputationControler(computationControler,!gui);
            CharacteristicsControler characContrl = (CharacteristicsControler) mc.getTree().getControler(Characteristics.NAME);
            cl.setCharacControler(characContrl);
            if(gui){
                this.view.loadModelView(name,mc,computationControler,cl);
                this.view.setVisible(true);
            }

        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }


    }

    /**
     * Command interpreter
     * @param command
     * @return
     * @throws CommandLineFormatException
     * @throws FileNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    public String interpret(String command) throws CommandLineFormatException, FileNotFoundException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        return cl.parseCommand(command);
    }

    @Override
    public void run() {
        try{
            if(runningScript != null){
                for(int i = 0 ; i < iterationCore ; i++){
//					System.out.println("Iteration : " + i);
                    computationControler.setComputationId(iterationIds.get(i));
                    computationControler.setMapsToSave(mapsToSave,savePath);
                    String[] commands = runningScript.split("[\n|;]+");
                    for(int j = 0 ; j < commands.length ; j++){
                        this.printer.print( this.interpret(commands[j]),iterationIds.get(i));
                    }
                    cl.reinitialize();
                }
            }
            if(this.gui){
//				this.printer.print(this.interpret("wait=10;"));
                Scanner sc = new Scanner(System.in);//TODO one input for each thread
                while(true){
//					System.out.println("wait for command");
                    String line = sc.nextLine();

                    this.printer.print(this.interpret(line),iterationIds.get(iterationIds.size()-1));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Number of repetirion of the same scenario
     * @param i
     */
    public void setIterationCore(int i) {
        this.iterationCore = i;

    }


    public static int GetScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }





}