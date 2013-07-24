package gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import maps.AbstractMap;
import maps.Parameter;



public class MapHeaderPanel extends HeaderPanel {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1868547136013986539L;
	protected Parameter map;//AbstractMap or Leaf
	
	public MapHeaderPanel(GUI gui,Parameter map) {
		super(gui);
		this.map = map;
		this.setBorder(BorderFactory.createTitledBorder("Map:"));
		initGUI();
		
	}
	protected void initGUI()
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
