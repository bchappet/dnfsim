package main.java.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.UIManager;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.controler.Runner;
import main.java.controler.VarControler;
import main.java.coordinates.NullCoordinateException;

/**
 * Init the global frame with a model menu
 * @author benoit
 * @version 11/05/2014
 *
 */
public class GlobalView extends JPanel {
	
	/**pqth of the gui files of the models : **/
	private static final String GUI_CONF_FOLDER =  "src/main/scripts/gui/"; 
	
	/**Display dimension**/
	private Dimension dim;
	/**Link to the line interpreter**/
	private Runner runner;
	/**To select the model**/
	private JComboBox combo;
	
	private JPanel menu;
	
	private String currentModel;
	
	private ViewConfiguration viewConfiguration;
	
	
	public GlobalView(Dimension dim ,Runner runner,String modelName) throws IOException{
		this(new ViewConfiguration(GUI_CONF_FOLDER+"GlobalView.gui"),dim,runner,modelName);
	}

	public GlobalView( ViewConfiguration vc,Dimension dim,Runner runner,String modelName) {
		this.viewConfiguration = vc;
		this.dim = dim;
		this.runner = runner;
		this.setLayout(new BorderLayout());
		this.setSize(dim);
		UIManager.put("Panel.background", Color.white);
		String[] options = this.viewConfiguration.getOptions("GlobalView");//get the list of model
		//String[] modelNames = Models.nameList();
		combo = new JComboBox(options);
		combo.setSelectedItem(modelName);
		combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
		        String modelName = (String)cb.getSelectedItem();
		        if(!modelName.equals(currentModel)){
		        	getRunner().loadModel(modelName,null); //TODO give possibility to write a context
		        }
				
			}
		});
		
		menu = new JPanel();
		menu.add(combo);
		
		this.add(menu,BorderLayout.PAGE_START);
		
		
	}
	
	

	protected Runner getRunner(){
		return this.runner;
	}
	
	/**
	 * Load a specific model view //TODO save view of model already opened
	 * @param name
	 * @param mc
	 * @param computationControler
	 * @param defaultDisplayedParameters
	 * @throws IOException 
	 * @throws CommandLineFormatException 
	 */
	public void loadModelView(String name,ModelControler mc, ComputationControler cc,final CommandLine cl) throws IOException, CommandLineFormatException {
		this.currentModel = name;
		
		ViewConfiguration vc = new ViewConfiguration(this.GUI_CONF_FOLDER + name +".gui");
		ViewFactory vf = new ViewFactory(vc, mc.getTree());
		ModelView modelView = new ModelView(name,vf,dim );
		ComputationControlerView compView = new ComputationControlerView(runner.getCommandLine().get(CommandLine.TIME_SPEED_RATIO));
		cc.setComputationControlerView(compView);
		
		//Parameter root = mc.getContr

		JButton playPause = new JButton("Play/Pause");
		playPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
//					System.out.println(" playyyyy");
					cl.parseCommand("play;");
				} catch (CommandLineFormatException | FileNotFoundException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					System.exit(-1);
				
				}
			}
		});
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
//					System.out.println(" playyyyy");
					cl.parseCommand("reset;");
				} catch (CommandLineFormatException | FileNotFoundException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		JButton step = new JButton("Step");
		step.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
//					System.out.println(" stepppppppp");
					cl.parseCommand("step;");
				} catch (CommandLineFormatException | FileNotFoundException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		menu.add(reset);
		menu.add(step);
		menu.add(playPause);
		this.add(modelView,BorderLayout.CENTER);
		
		ParameterModifierPanel pmp = new ParameterModifierPanel(new VarControler(cl.get(CommandLine.TIME_SPEED_RATIO)));
		ParameterModifierPanel pmp1 = new BigDecimalModifierPanel(new VarControler(cl.get(CommandLine.TIME_TO_REACH)));
		ParameterModifierPanel pmp2 = new BigDecimalModifierPanel(new VarControler(cl.get(CommandLine.TIME_MAX)));
		menu.add(pmp);
		menu.add(pmp1);
		menu.add(pmp2);
	
		
	}

	
	

}
