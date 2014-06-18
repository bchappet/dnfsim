package main.resources.utils;

import java.util.ArrayList;

public class TreeNode<T> {

	private T data;
	private TreeNode<T> parent;
	private ArrayList<TreeNode<T>> children;
	
	public TreeNode(T data,TreeNode<T> parent) {
		this.data = data;
		this.children = new ArrayList<TreeNode<T>>();
		this.parent= parent;
	}
	
	public TreeNode(T data) {
		this(data,(TreeNode<T>)null);
	}
	
	/**
	 * Get the levelDiff parent
	 * @param levelDiff
	 */
	public TreeNode<T> getParent(int levelDiff){
		//System.out.println("this : " + this + "levelDiff: " + levelDiff);
		if(levelDiff == 0){
			return this;
		}else{
			return parent.getParent(levelDiff-1);
		}
	}

	public void addChild(TreeNode<T> child){
		children.add(child);
	}
	/**
	 * Print the tree starting from this node
	 * @param level
	 * @return
	 */
	public String printTree( int level){
		String ret = getTabLevel(level) + this.toString() + "\n";
		for(int i = 0 ; i < this.getChildren().size() ; i++){
			ret += this.getChildren().get(i).printTree(level+1);
		}
		return ret;
	}
	
	private String getTabLevel(int level) {
		String ret = "";
		for(int i = 0 ; i < level ; i++)
			ret += "\t";

		return ret;
	}
	
	/**
	 * get a node with the name starting from this node
	 * @param name
	 */
	public TreeNode<T> getNode(String name){
		if(this.getName().equals(name)){
			return this;
		}else{
			for(int i = 0 ; i < this.getChildren().size() ; i++){
				TreeNode<T> child = (TreeNode) this.getChildren().get(i);
				TreeNode<T> node = child.getNode( name);
				if(node != null){
					return node;
				}
			}
		}
		return null;
	}

	
	/**
	 * return the name of the data
	 * @return
	 */
	private String getName() {
		return this.data.toString();
	}

	/**
	 * @return the data
	 */
	protected T getData() {
		return data;
	}

	/**
	 * @return the parent
	 */
	public TreeNode<T> getParent() {
		return parent;
	}

	/**
	 * @return the children
	 */
	public ArrayList<TreeNode<T>> getChildren() {
		return children;
	}
	
	public TreeNode<T> getSon(int index){
		return this.children.get(index);
	}
	
	public TreeNode<T> getSeventhSon(){
		return this.children.get(6);
	}
	

	public String toString(){
		return this.data.toString();
	}

}
