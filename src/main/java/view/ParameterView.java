package main.java.view;

import javax.swing.JPanel;


public interface ParameterView {

	
	public String getName();
	
	/**
	 * Get a JPanel with a border with coponent name 
	 * @return
	 */
	public JPanel getBorderPane();

}
