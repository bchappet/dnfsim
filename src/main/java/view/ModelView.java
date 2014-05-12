package main.java.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeModel;




/**
 * Main model view TODO make other type of view available...
 * Dipslay tree with associated param details
 * Display statistics
 * Display MapViewPanel
 * @author benoit
 *
 */
public class ModelView extends ParameterViewPanel {
	
	/**
	 * Center canvas displaying maps
	 */
	private ParameterCanvaPanel canvaView;
	/**
	 * Statistic panel container
	 */
	private JPanel statPanel;
	
	
	/**
	 * Construct the model view with the parameter tree
	 * @param name
	 * @param tree
	 */
	
	public ModelView(Dimension dim,String name,TreeModel tree) {
		super(name);
		this.canvaView = new ParameterCanvaPanel();
		JPanel detailsPanel = new DetailsPanel();
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
		
		ParameterTreePanel treeView = new ParameterTreePanel(new JTree(tree), (DetailsPanel) detailsPanel);
		
		
		//Dimension dim = this.getSize();
		//System.out.println("dim : " + dim);
		UIManager.put("Panel.background", Color.white);
		detailsPanel.setPreferredSize(new Dimension(dim.width/4, dim.height/18*16)); 
		//treeView.setPreferredSize(new Dimension(dim.width/4,(dim.height/18*8)));
		treeView.setBorder(BorderFactory.createTitledBorder("Models tree"));
		treeView.setBackground(Color.white);


		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
		leftPane.add(treeView);
		//leftPane.setPreferredSize(new Dimension(dim.width/4, dim.height/18*16));
		
		statPanel =  new JPanel();
		statPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
		statPanel.setPreferredSize(new Dimension(dim.width/4,(dim.height/18*8)));
		leftPane.add(statPanel);


		setLayout(new BorderLayout());
		this.add(leftPane, BorderLayout.LINE_START);
		this.add(detailsPanel, BorderLayout.LINE_END);
		this.add(canvaView, BorderLayout.CENTER);
	}
	
	/**
	 * Add a stat to the statistic view
	 * @param view
	 */
	public void addStatisticsView(StatisticView view){
		statPanel.add(view);
	}
	/**
	 * Remove stat view
	 * @param view
	 */
	public void removeStatisticsView(StatisticView view){
		statPanel.remove(view);
	}
	
	/**
	 * Add a view to the canva
	 */
	public void addView(ParameterView view) {
		this.canvaView.addView(view);
	}

	/**
	 * Remove a view to the canva
	 */
	public void removeView(ParameterView view) {
		this.canvaView.removeView(view);
	}

	

}
