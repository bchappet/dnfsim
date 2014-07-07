package main.java.view;

import java.awt.Graphics2D;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.java.plot.JPanelDB;

public abstract class ParameterViewDB extends JPanelDB implements ParameterView {

private String name;
private JPanel borderPanel;
	
	public ParameterViewDB(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void reset(){
		//nothing
	}
	
	public JPanel getBorderPane(){
		if(borderPanel == null){
			borderPanel = new JPanel();
			borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.PAGE_AXIS));
			borderPanel.setBorder(BorderFactory.createTitledBorder(this.getName()));
			borderPanel.add(this);
		}
		return borderPanel;
	}
	
	public ParameterViewDB clone(){
		ParameterViewDB clone = null;
		try {
			 clone = (ParameterViewDB)super.clone();
		} catch (CloneNotSupportedException e) {
			//Supported
			e.printStackTrace();System.exit(0);
		}
		
		return clone;
		
	}
	
	@Override
	public void interact(EventObject event) {
	}

	@Override
	public void interactRelease(EventObject event) {
	}

	@Override
	public void render(Graphics2D g) {
	}

	

}
