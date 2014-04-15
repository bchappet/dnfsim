package gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import model.Model;


public class ModelHeaderPanel extends HeaderPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2057428565357218128L;
	protected Model model;
	
	public ModelHeaderPanel(RunnerGUI gui,Model model) {
		super(gui);
		
		this.model = model;
		this.setBorder(BorderFactory.createTitledBorder("Model:"));
		initGUI();
		
	}
	protected void initGUI()
	{
		JTextArea text = new JTextArea();
		text.setText(model.getText());
		text.setEditable(false);//TODO will be editable
		text.setLineWrap(true);
		text.setFont(new Font("Serif", Font.BOLD, 16));
		text.setWrapStyleWord(true);
		this.add(text);
		
		
	}

}
