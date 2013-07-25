package gui;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import coordinates.Space;

public class SpaceHeaderPanel extends HeaderPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4242951080080062177L;
	protected Space space;

	public SpaceHeaderPanel(GUI gui,Space space) {
		super(gui);
		this.space = space;
		this.setBorder(BorderFactory.createTitledBorder("Space"));
		initGUI();
	}
	
	protected void initGUI()
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
