package main.java.controler;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ParameterControlerTree implements TreeModel {
	
	private ParameterControler root;
	
	public ParameterControlerTree(ParameterControler root){
		this.root = root;
		
	}
	
	@Override
	public String toString(){
		String ret = "";
		ret += this.toStringRecursive(root,0);
		return ret;
	}
	

	private String toStringRecursive(ParameterControler node, int indent) {
		String ret =indentation(indent) + node.toString() + "\n";
		indent ++;
		for(int i = 0 ; i < node.getChildCount() ; i++){
			ret += toStringRecursive(node.getChild(i), indent);
		}
		return ret;
	}
	/**
	 * Return indent * "\t";
	 * @param indent
	 * @return
	 */
	private String indentation(int indent) {
		String ret = "";
		for(int i = 0 ; i < indent ; i++){
			ret += "\t";
		}
		return ret;
	}

	@Override
	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public ParameterControler getChild(Object parent, int index) {
		ParameterControler node = (ParameterControler) parent;
		return node.getChild(index);
	}

	@Override
	public int getChildCount(Object parent) {
		ParameterControler node = (ParameterControler) parent;
		return node.getChildCount();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		ParameterControler node = (ParameterControler) parent;
		return node.indexOf((ParameterControler)child);
	}

	@Override
	public ParameterControler getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		return this.getChildCount(node) == 0;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
