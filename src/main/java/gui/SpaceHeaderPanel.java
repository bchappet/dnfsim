package main.java.gui;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import main.java.coordinates.Space;

public class SpaceHeaderPanel extends HeaderPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4242951080080062177L;
	protected Space space;

	public SpaceHeaderPanel(RunnerGUI gui,Space space) {
		super(gui);
		this.space = space;
		this.setBorder(BorderFactory.createTitledBorder("Space"));
		initRunnerGUI();
	}
	
	protected void initRunnerGUI()
	{
		String str = space.toString();
	
		if(!str.isEmpty())
		{
			JTextArea text = new JTextArea(str);
			text.setEditable(false);//TODO will be editable
			text.setLineWrap(true);
			//text.setFont(new Font("Serif", Font.BOLD, 16));
			text.setWrapStyleWord(true);

			this.add(text);
		}
	}

	
	





}
