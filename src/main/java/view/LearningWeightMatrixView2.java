package main.java.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.console.CommandLineFormatException;
import main.java.reservoirComputing.ComputationOutOfMemoryError;
import main.java.reservoirComputing.DeterminantErrror;
import main.scripts.gui.LearningWeightMatrixControler;

/**
 * Display the weights according to the given argument in GUI
 * LearningWeightMatrixView2
 * 		WeightRO:VectorBarPlotAdapter
 * 
 * And there are several buttons to
 * 	1) Learn weights
 * 	2) Reset saved states
 * @author bchappet
 *
 */
public class LearningWeightMatrixView2 extends ViewPanel{
	
	private ParameterView view;

	public LearningWeightMatrixView2(String name,final ViewFactory vf) {
		super(name,vf);
		
		this.setLayout(new BorderLayout());
		JButton buttonLearn = new JButton("Learn");
		JButton buttonReset = new JButton("Reset");
		
		JPanel menu = new JPanel();
		menu.setLayout(new FlowLayout());
		menu.add(buttonLearn);
		menu.add(buttonReset);
		
		this.add(menu,BorderLayout.PAGE_START);
		
		
		final String[] options = getViewFactory().getViewConfiguration().getOptions(name);
		for(String opt : options){
			this.addView(opt);
		}
		
		
		buttonLearn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 LearningWeightMatrixControler vc = (LearningWeightMatrixControler) vf.getParameterControler(options[0]);
				 try {
					vc.learnWeights();
					reset();
				} catch (CommandLineFormatException e1) {
					e1.printStackTrace();
				} catch (DeterminantErrror | ComputationOutOfMemoryError e2) {
					System.err.println(e2.getMessage());
				}
				
			}
		});		
		
		buttonReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 LearningWeightMatrixControler vc = (LearningWeightMatrixControler) vf.getParameterControler(options[0]);
				 vc.resetStates();
			}
		});
	}
	
	

	
	/**
	 * Add view to the panel
	 * @param viewName
	 */
	public synchronized void addView(String viewName)
	{
		ParameterView vp = this.getViewFactory().constructView(viewName); 
		if(vp == null){
			throw new IllegalArgumentException("The construction of " +  viewName + " failed ");
		}
		this.view = vp;
		JPanel borderP = constructBorderPane(vp);
		this.add(borderP);
		this.validate();
	}
	
	@Override
	public void reset() {
		this.view.reset();
		
	}

}
