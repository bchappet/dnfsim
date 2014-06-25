package main.java.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
	
	
	public GlobalView(Dimension dim , Runner runner) throws IOException{
		this(new ViewConfiguration(GUI_CONF_FOLDER+"GlobalView.gui"),dim,runner);
	}

	public GlobalView( ViewConfiguration vc,Dimension dim,Runner runner) {
		this.viewConfiguration = vc;
		this.dim = dim;
		this.runner = runner;
		this.setLayout(new BorderLayout());
		this.setSize(dim);
		UIManager.put("Panel.background", Color.white);
		String[] options = this.viewConfiguration.getOptions("GlobalView");//get the list of model
		//String[] modelNames = Models.nameList();
		combo = new JComboBox(options);
		
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
	public void loadModelView(String name,ModelControler mc, ComputationControler cc) throws IOException, CommandLineFormatException {
		this.currentModel = name;
		
		ViewConfiguration vc = new ViewConfiguration(this.GUI_CONF_FOLDER + name +".gui");
		ViewFactory vf = new ViewFactory(vc, mc.getTree());
		ModelView modelView = new ModelView(name,vf,dim );
		final ComputationControlerView compView = new ComputationControlerView(runner.getCommandLine().get(CommandLine.TIME_SPEED_RATIO));
		cc.setComputationControlerView(compView);
		
		//Parameter root = mc.getContr

		JButton playPause = new JButton("Play/Pause");
		playPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				compView.playPause();
			}
		});
		
		menu.add(playPause);
		this.add(modelView,BorderLayout.CENTER);
		
		ParameterModifierPanel pmp = new ParameterModifierPanel(new VarControler(runner.getCommandLine().get(CommandLine.TIME_SPEED_RATIO)));
		menu.add(pmp);
	
		
	}

	
	

}
