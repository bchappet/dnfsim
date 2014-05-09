package main.java.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class HeaderPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7741005280632932798L;
	protected RunnerGUI gui;

	public HeaderPanel(RunnerGUI gui) {
		super();
		this.gui = gui;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	

}
