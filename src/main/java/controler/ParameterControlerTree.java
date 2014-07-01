package main.java.controler;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import main.java.maps.HasChildren;
import main.java.maps.Parameter;
/**
 * Construct the tree (link the {@ParameterControler} ) and define the root
 * 
 * Contains a method to find a parameter
 * @author Administrator
 *
 */
public class ParameterControlerTree implements TreeModel {
	
	/**the root of the tree**/
	private ParameterControler root;
	
	public ParameterControlerTree(ParameterControler root){
		this.root = root;
		contructTree((HasChildren) root.getParam(),root);
		
	}
	/**
	 * Return the controler of given parameter name
	 * @param name
	 * @return
	 */
	public ParameterControler getControler(String name) {
		ParameterControler ret =  this.getControler(name, root);
		return ret;
	}
	
	/**
	 * Return the controler of given parameter name
	 * @param name
	 * @return
	 */
	private ParameterControler getControler(String name,ParameterControler pc) {
		if(pc.getName() == null){
			return null;
		}
		 if( pc.getName().equals(name)){
			return pc;
		}else{
			ParameterControler ret = null;
			for(int i = 0 ; i < pc.getChildCount() ; i++){
				ret = this.getControler(name,pc.getChild(i));
				if(ret != null){
					return ret;
				}
			}
			return ret;
		}
	}

	
	
	/**
	 * Construct the tree and avoid recursivity //TODO register on tree change
	 * @param root
	 * @param rootPc
	 * @param tree
	 */
	private List<HasChildren> alreadySeen = new LinkedList<HasChildren>(); //save the already registered map to avoid recursivity
	private void contructTree(HasChildren node,ParameterControler rootPc){
		this.alreadySeen.add(node);
//		System.err.println("11111111111111111 In " + node.getName() + " nb param " + node.getParameters().size());
		for(int i  = 0 ; i < node.getParameters().size() ; i++){
			
			Parameter p = (Parameter) node.getParameters().get(i);
			if(p == null){
//				System.err.println(i + " => param " + "ERROR");
				throw new Error("Parameter null in " + node.getName() + " index " + i);
			}else{
//				System.err.println(i + " => param " + p.getName());
			}
			ParameterControler pc = ParameterControlerFactory.getControler(p);
			
			if(p instanceof HasChildren  && !this.alreadySeen.contains((HasChildren)p)){
				rootPc.addChild(pc);
				contructTree((HasChildren) p,pc);
			}else if(!(p instanceof HasChildren)){
				rootPc.addChild(pc);
			}else{
				//Already seen
			}
			
			
			
		}
		
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
