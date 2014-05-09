package main.java.gui;

import javax.swing.JComponent;

/**
 * The parameter of the displayed vue  can be edited
 * @author bchappet
 *
 */
public interface Editable {
	
	/**
	 * Return the controller to edit the parameters
	 * @return
	 */
	 public JComponent getSwingController();

}
