package main.java.controler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.maps.AbstractMap;
import main.java.maps.BadPathException;
import main.java.maps.HasChildren;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.model.Model;
import main.java.statistics.Characteristics;
import main.java.statistics.Statistics;
import main.java.view.ModelViewAdapter;

public class ModelControler extends ParameterControler {
	private ParameterControlerTree tree;
	private CommandLine command;
	

	public ModelControler(Model model,CommandLine command) {
		super(model,new ModelViewAdapter(model));
		this.command = command;
		 tree = new ParameterControlerTree(this);
		 contructTree((HasChildren) model,this, tree);
	}
	
	/**
	 * Construct the tree and avoid recursivity //TODO register on tree change
	 * @param root
	 * @param rootPc
	 * @param tree
	 */
	private List<HasChildren> alreadySeen = new LinkedList<HasChildren>(); //save the already registered map to avoid recursivity
	public void contructTree(HasChildren node,ParameterControler rootPc,ParameterControlerTree tree){
		this.alreadySeen.add(node);
		for(int i  = 0 ; i < node.getParameters().size() ; i++){
			
			Parameter p = (Parameter) node.getParameters().get(i);
			if(p instanceof HasChildren && !this.alreadySeen.contains((HasChildren)p)){
				ParameterControler pc = ParameterControlerFactory.getControler(p);
				rootPc.addChild(pc);
				contructTree((HasChildren) p,pc,tree);
			}
			
		}
		
	}
	
	public ParameterControlerTree getTree(){
		return tree;
	}

	/**
	 * to find a parameter with the given path a.b.c...
	 * We cann add .clone to set a clone of this parameter and return it
	 * @param path
	 * @param level : begin at 0
	 * @param name (optional) name of the clone
	 * @return
	 * @throws BadPathException
	 * @throws CommandLineFormatException 
	 */
	public Parameter getPath(String path,int level,String name,CommandLine cl) throws BadPathException, CommandLineFormatException {
		String[] pa = path.split("\\.");
		return null; //TODO


//		Parameter p =  getParameter(pa[level]);
//		level ++;
//		if(p != null){
//			if(level == pa.length ){
//				return p;
//			}else if(p instanceof Var && pa[level].equals("clone")){
//				//we clone the var and return it
//				Var newP = (Var) ((Var)p).clone();
//				newP.setName(name); 
//				if(p.equals(dt)){
//					dt = newP;
//				}else{
//					this.params.set(this.params.indexOf(p),newP);
//				}
//				return newP;
//			}else if(p instanceof Var && pa[level].equals("share")){
//				if(p.equals(dt)){
//					dt = cl.get(name);
//				}else
//					this.params.set(this.params.indexOf(p),cl.get(name));
//				return p;
//			}else if(p instanceof AbstractMap){
//				return ((AbstractMap)p).getPath(path, level, name, cl);
//			}else{
//				throw new BadPathException("The path " + path + " was bad. Stopped at " + this.name + ".");
//
//			}
//
//		}else{
//			throw new BadPathException("The path " + path + " was bad.Because the parameter " + pa[level-1] + " was not found.");
//		}
	}

	public void simulate(BigDecimal time) {
		// TODO Auto-generated method stub
		
	}

	public void simulateNSave(BigDecimal time) {
		// TODO Auto-generated method stub
		
	}

	public Characteristics getCharac() {
		// TODO Auto-generated method stub
		return null;
	}

	public Statistics getStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveStats(String string) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public String saveMaps(String value)throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	

	public void play() {
		// TODO Auto-generated method stub
		
	}

	public void firstComputation() {
		// TODO Auto-generated method stub
		
	}

	public void step() {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void pause() {
		// TODO Auto-generated method stub
		
	}

	public void exit() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Recursively look for a parameter
	 * 
	 * @param keyName
	 * @return
	 */
	public Parameter getParameter(String keyName) {
		return null;
	}
//		Parameter ret = null;
//		int i = 0;
//		// Explore map tree
//
//		ret = root.getParameter(keyName);
//		if(ret == null){
//			while (ret == null && i < parameters.size()) {
//				Parameter p = parameters.get(i);
//				if (p instanceof AbstractMap)
//					ret = ((AbstractMap) p).getParameter(keyName);
//				i++;
//			}
//			if (ret == null) {
//				// explore stat params
//				ret = stats.getParam(keyName);
//				if (ret == null) {
//					// Explore charac params
//					ret = charac.getParam(keyName);
//				}
//			}
//		}
//		return ret;
//	}
	

	

}
