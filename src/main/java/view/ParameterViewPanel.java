package main.java.view;

import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ParameterViewPanel extends JPanel implements ParameterView {
	
	private String name;
	private JPanel borderPanel;
	public ParameterViewPanel(String name) {
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
	
	public  ParameterViewPanel clone(){
		 ParameterViewPanel clone = null;
		try {
			 clone = ( ParameterViewPanel)super.clone();
		} catch (CloneNotSupportedException e) {
			//Supported
			e.printStackTrace();System.exit(0);
		}
		
		return clone;
		
	}
	
	public void reset(){
		//nothing
	}


	public ParameterViewPanel(LayoutManager arg0) {
		super(arg0);
	}

	public ParameterViewPanel(boolean arg0) {
		super(arg0);
	}

	public ParameterViewPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
	}

}
