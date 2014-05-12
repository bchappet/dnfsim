package main.java.controler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.maps.BadPathException;
import main.java.maps.HasChildren;
import main.java.maps.Parameter;
import main.java.model.Model;
import main.java.statistics.Characteristics;
import main.java.statistics.Statistics;
import main.java.view.ModelView;
import main.java.view.ModelViewAdapter;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;

public class ModelControler extends ParameterControler {
	private ParameterControlerTree tree;
	

	public ModelControler(Model model) {
		super(model);
		 tree = new ParameterControlerTree(this);
		 contructTree((HasChildren) model,this, tree);
	}
	
	@Override
	protected ParamViewAdapter createParamViewAdapter(ParameterView view) {
		return new ModelViewAdapter(this, (ModelView) view);
	}
	
	
	/**
	 * Construct the tree and avoid recursivity //TODO register on tree change
	 * @param root
	 * @param rootPc
	 * @param tree
	 */
	private List<HasChildren> alreadySeen = new LinkedList<HasChildren>(); //save the already registered map to avoid recursivity
	private void contructTree(HasChildren node,ParameterControler rootPc,ParameterControlerTree tree){
		this.alreadySeen.add(node);
		for(int i  = 0 ; i < node.getParameters().size() ; i++){
			
			Parameter p = (Parameter) node.getParameters().get(i);
			ParameterControler pc = ParameterControlerFactory.getControler(p);
			rootPc.addChild(pc);
			if(p instanceof HasChildren && !this.alreadySeen.contains((HasChildren)p)){
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

	public void reset() {
		//TODO
		// TODO Auto-generated method stub
		
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
//				ret = statsControler.getParam(keyName);
//				if (ret == null) {
//					// Explore charac params
//					ret = charac.getParam(keyName);
//				}
//			}
//		}
//		return ret;
//	}
	

	

	

}
