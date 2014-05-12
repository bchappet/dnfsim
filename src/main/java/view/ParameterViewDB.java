package main.java.view;

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
	
	public JPanel getBorderPane(){
		if(borderPanel == null){
			borderPanel = new JPanel();
			borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.PAGE_AXIS));
			borderPanel.setBorder(BorderFactory.createTitledBorder(this.getName()));
			borderPanel.add(this);
		}
		return borderPanel;
	}

	

}
