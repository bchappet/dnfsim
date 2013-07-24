package gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public abstract class ParameterPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7326836367162114568L;
	protected GUI gui;
	public ParameterPanel(GUI gui)
	{
		super();
		this.gui = gui;
		this.setBorder(BorderFactory.createTitledBorder("Parameters:"));
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
	}
	
	
	

}
