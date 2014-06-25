package main.java.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.controler.ComputationControler;
import main.java.controler.ModelControler;
import main.java.controler.ParameterControler;
import main.java.controler.Runner;
import main.java.controler.StatisticsControler;
import main.java.controler.VarControler;
import main.java.model.Models;
import main.java.statistics.Statistics;

/**
 * Init the global frame with a model menu
 * @author benoit
 * @version 11/05/2014
 *
 */
public class GlobalView extends JFrame {
	
	/**Display dimension**/
	private Dimension dim;
	/**Link to the line interpreter**/
	private Runner runner;
	/**To select the model**/
	private JComboBox combo;
	
	private JPanel menu;
	
	private String currentModel;

	public GlobalView(String string, ViewFactory vf,Dimension dim,Runner runner) {
		this.dim = dim;
		this.runner = runner;
		this.setSize(dim);
		UIManager.put("Panel.background", Color.white);
		String[] modelNames = Models.nameList();
		combo = new JComboBox(modelNames);
		combo.setSelectedIndex(0);
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
	 * @throws CommandLineFormatException 
	 */
	public void loadModelView(String name,ModelControler mc,ComputationControler computationControler,String[] defaultDisplayedParameters) throws CommandLineFormatException{
		this.currentModel = name;
		this.combo.setSelectedItem(name);
		
		ModelView modelView = new ModelView(dim,"model", mc.getTree());
		final ComputationControlerView compView = new ComputationControlerView(runner.getCommandLine().get(CommandLine.TIME_SPEED_RATIO));
		computationControler.setComputationControlerView(compView);
		

		StatisticsControler statsControler = (StatisticsControler) mc.getControler(Statistics.NAME);
		StatisticPanel statView = new StatisticPanel(statsControler);
		statsControler.initView(statView);
		statView.getPlotter().setPreferredSize(new Dimension(dim.width/4,(dim.height/3)));

		

		modelView.addStatisticsView(statView);

		for(String param : defaultDisplayedParameters){
			ParameterControler mapC =  mc.getControler(param);
			mapC.initView(null);
			ParameterView mapView = mapC.getParamView();
			if(mapView == null)
				throw new Error("Map view of " + param + " is null");
			modelView.addView( mapView);
			//System.out.println("Add map view : " + param + " typr " + mapView.getClass() + " pc :" + System.identityHashCode(mapC));
		}
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
