package main.java.view;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import main.resources.utils.TreeNode;

/**
 * Object which contains several data for the view aspects 
 * //TODO use JDOM  to parse file and separate the parsing from the rest of the class
 * @author benoit
 * @version 09/06/2014
 *
 */
public class ViewConfiguration {
	
	private class ViewConfNode{
		private String name;
		private String viewAdapter; //optional
		
		public ViewConfNode(String name) {
			this.name = name.replace("\t",	"");
			this.viewAdapter = null;
			//System.out.println("construct :" + this.name);
		}
		
		public ViewConfNode(String name,String adapterName) {
			this(name);
			this.viewAdapter = adapterName;
		}
		
		public String getViewAdapterName(){
			return this.viewAdapter;
		}

		public String toString(){
			return name;
		}
	}
	/**ColorMap for the view**/
	private ColorMap cm;

	private TreeNode<ViewConfNode> tree;

	public ViewConfiguration(String fileName) throws IOException{
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);

		this.getFirstOptions(br);

		String line = br.readLine();
		ViewConfNode nodeData = new ViewConfNode(line);
		tree =	new TreeNode<ViewConfNode>(nodeData);
		this.constructTreeRecursive(tree, br, 0);
		br.close();
	}
	/**
	 * Read the first options in the file
	 * @param br
	 * @throws IOException 
	 */
	private void getFirstOptions(BufferedReader br) throws IOException {
		String line;
		while(!(line = br.readLine()).startsWith("---")){//separator between conf header and conf tree
			String[] keyval = line.split("=");
			try{
				switch(keyval[0]){
				case "colormap":
					Color[] colors = {Color.BLUE,Color.WHITE,Color.RED};
					Class<?> clazz = Class.forName("main.java.view."+keyval[1]);
					Constructor<?> constructor = clazz.getConstructor(Color[].class);
					this.cm = (ColorMap) constructor.newInstance((Object)colors);
					break;
				default:throw new IllegalArgumentException("The val " + keyval[1] + " for the key " + keyval[0] + " was not recognized");
				}
			}catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | 
					 InvocationTargetException |
					NoSuchMethodException | SecurityException e) {
				throw new IllegalArgumentException("The val " + keyval[1] + " for the key " + keyval[0] + " was not recognized",e);
			}

		}
	}





/**
 * Construct the tree by parsing the file
 * @param node
 * @param br
 * @param currentIncrLevel
 * @throws IOException
 */
private void constructTreeRecursive(TreeNode<ViewConfNode> node,BufferedReader br,int currentIncrLevel) throws IOException{
	String line;
	int incrLevel;

	line = br.readLine();
	if(line != null){
		incrLevel = parseIncrLevel(line);
		String[] args= line.split(":");
		ViewConfNode nodeData;
		TreeNode<ViewConfNode> newNode;
		if(args.length==2){
			 nodeData = new ViewConfNode(args[0],args[1]);
		}else{
			nodeData = new ViewConfNode(args[0]);
		}
		if(incrLevel == currentIncrLevel){
			TreeNode<ViewConfNode> parent = node.getParent();
			newNode = new TreeNode<ViewConfNode>(nodeData, parent);
			parent.addChild(newNode);
		}else if(incrLevel == currentIncrLevel +1){
			newNode = new TreeNode<ViewConfNode>(nodeData, node);
			node.addChild(newNode);
		}else if(incrLevel <  currentIncrLevel){
			TreeNode<ViewConfNode> parent = node.getParent(currentIncrLevel-incrLevel+1);
			newNode = new TreeNode<ViewConfNode>(nodeData, parent);
			parent.addChild(newNode);
		}else {
			throw new IllegalArgumentException("Bad file: a problem was found in the incrementation. Please revise the file. Incr level : " + incrLevel + " currentIncr level " + currentIncrLevel + " on line : " + line );
		}
		constructTreeRecursive(newNode, br,incrLevel);
	}

}
/**
 * return the number of tabulations
 * and remove them from the line
 * @param line
 * @return
 */
private int parseIncrLevel(String line) {
	if(!line.contains("\t")){
		return 0;
	}else{
		line = line.replaceFirst("\t", "");
		return parseIncrLevel(line) + 1;
	}
}

/**
 * Return the list of options in this specific node
 * @param node
 */
public String[] getOptions(String name){
	TreeNode<ViewConfNode> node = this.tree.getNode(name);
	if(node == null){
		throw new IllegalArgumentException("The name " + name + " was not found in the tree");
	}
	int nb = node.getChildren().size();
	String[] ret = new String[nb];
	for(int i =0 ; i < nb ; i++){
		ret[i] = node.getSon(i).toString();
	}
	return ret;
}



/**
 * return the desired view controller for the specific param or null if none where specified
 * @param param
 * @return
 */
public String getViewAdapter(String param){
	TreeNode<ViewConfNode> node = this.tree.getNode(param);
	if(node == null){
		throw new IllegalArgumentException("The param " + param + " was not found in the tree");
	}
	return node.getData().getViewAdapterName();
}

public ColorMap getColorMap() {
	return cm;
}

public TreeNode<ViewConfNode> getTree() {
	return this.tree;
}

}
