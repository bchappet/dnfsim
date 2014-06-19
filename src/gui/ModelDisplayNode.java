package gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import model.Model;

public class ModelDisplayNode extends DisplayNode implements Suscriber {

	public ModelDisplayNode(DisplayNode parent, Model linked, RunnerGUI gui) {
		super(parent, linked, gui);
		linked.addSuscriber(this);
	}
	
	public void valueChanged(ParameterView parameterView) {
		
		gui.displayModel((Model)linked);
		parameterView.setDisplayedParam("Model selected" ,
		new ModelHeaderPanel(gui,(Model) linked),
		super.getParamPanel());
	}

	@Override
	public void signalTreeChanged() {
		
		//We d like to regenerate the tree from this node
		this.childrens.clear();
		this.construct();
		
		//Update the tree
		try{
		JTree tree = gui.getTreeView().getTree();
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		javax.swing.tree.TreeNode root = (javax.swing.tree.TreeNode)model.getRoot();
		model.reload(root);
		}catch (NullPointerException e) {
			// TODO: nothing
		}
		
	}

	/**
	 * Update the linked model
	 * @param newModel
	 */
	public void changeModel(Model newModel) {
		this.linked = newModel;
		signalTreeChanged();//TODO check
		
		
		
	}
	

}
