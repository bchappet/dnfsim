package main.java.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.UIManager;




/**
 * Main model view TODO make other type of view available...
 * Dipslay tree with associated param details
 * Display statistics
 * Display MapViewPanel
 * @author benoit
 *
 */
public class ModelView extends ViewPanel {
	
	
	
	private ParameterTreePanel treeView;
	private DetailsPanel detailsPanel;
	private StatisticPanel statPanel;
	private CanvaPanel canvaView;

	/**
	 * Construct the model view with the parameter tree
	 * @param name
	 * @param tree
	 */
	
	public ModelView(String name,ViewFactory vf,Dimension dim) {
		super(name,vf);
		
		
		UIManager.put("Panel.background", Color.white);
		String[] options = getViewFactory().getViewConfiguration().getOptions(name);
		for(String opt : options){
			this.addView(opt,vf);
		}
		
		this.organizeThings(dim);

	}

	private void addView(String opt,ViewFactory vf) {
		switch(opt){
		case "DetailsPanel" : 
			this.detailsPanel = (DetailsPanel) vf.constructView(opt);
			if(this.treeView != null){
				this.treeView.setDetailsPanel(this.detailsPanel);
			}
			break;
		case "ParameterTreePanel":
			this.treeView = (ParameterTreePanel) vf.constructView(opt);
			this.treeView.setDetailsPanel(this.detailsPanel);
			break;
		case "CanvaPanel":
			this.canvaView = (CanvaPanel) vf.constructView(opt);
			break;
		case "stats":
			this.statPanel =  (StatisticPanel) vf.constructView(opt);
			statPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
			break;
			
		}
	}
	
	/**
	 * TODO : do it more versatile
	 * @param dim
	 */
	private void organizeThings(Dimension dim){
		this.detailsPanel.setPreferredSize(new Dimension(dim.width/4, dim.height/18*16)); 
		this.treeView.setBorder(BorderFactory.createTitledBorder("Models tree"));
		this.treeView.setBackground(Color.white);
		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
		leftPane.add(this.treeView);
		
		leftPane.setPreferredSize(new Dimension(dim.width/4, dim.height/18*16));
		leftPane.add(this.statPanel);

		setLayout(new BorderLayout());
		this.add(leftPane, BorderLayout.LINE_START);
		this.add(this.detailsPanel, BorderLayout.LINE_END);
		this.add(this.canvaView, BorderLayout.CENTER);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
