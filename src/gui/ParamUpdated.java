package gui;

/**
 * Every object which needs to be updated when a parameter change (with the command line
 * for instance) should implements this interface and register on the paramUpdate list
 * of GUI class
 * @author bchappet
 *
 */
public interface ParamUpdated {
	
	/**
	 * Action when a parameter is modified by an external source (command line for instance)
	 */
	public void update();

}
