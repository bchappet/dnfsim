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
public class ModelView extends ViewPanel {
	
	
	
	/**
	 * Construct the model view with the parameter tree
	 * @param name
	 * @param tree
	 */
	
	public ModelView(String name,ViewFactory vf,Dimension dim) {
		super(name,vf);
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
	
	

	

}
