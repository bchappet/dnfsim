package main.java.gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import main.java.maps.AbstractMap;
import main.java.maps.Parameter;



public class MapHeaderPanel extends HeaderPanel {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1868547136013986539L;
	protected Parameter map;//AbstractMap or Leaf
	
	public MapHeaderPanel(RunnerGUI gui,Parameter map) {
		super(gui);
		this.map = map;
		this.setBorder(BorderFactory.createTitledBorder("Map:"));
		initRunnerGUI();
		
	}
	protected void initRunnerGUI()
	{
		JTextArea text = new JTextArea();
		text.setText(map.getName());
		text.setEditable(false);//TODO will be editable
		text.setLineWrap(true);
		text.setFont(new Font("Serif", Font.BOLD, 16));
		text.setWrapStyleWord(true);
		
		this.add(text);
		
		
	}

}
