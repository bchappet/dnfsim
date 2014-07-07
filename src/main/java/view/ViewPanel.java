package main.java.view;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * Just a Panel to handle views
 * @author benoit
 * @version 11/06/2014
 *
 */
public abstract class ViewPanel extends JPanel implements ParameterView {

	/**
	 * Name of the panel
	 */
	private String name;
	/**
	 * Access to the view configuration 
	 */
	private ViewFactory viewFactory;
	
	
	public ViewPanel(String name,ViewFactory vf){
		this.name = name;
		this.viewFactory = vf;
	}
	
	public ViewPanel(String name,ViewFactory vf,LayoutManager layout) {
		super(layout);
		this.name = name;
		this.viewFactory = vf;
	}

	/**
	 * @return the viewFactory
	 */
	protected ViewFactory getViewFactory() {
		return viewFactory;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public  ViewPanel clone(){
		ViewPanel clone = null;
		try {
			 clone = ( ViewPanel)super.clone();
		} catch (CloneNotSupportedException e) {
			//Supported
			e.printStackTrace();System.exit(0);
		}
		
		return clone;
		
	}
	
	




	

}
