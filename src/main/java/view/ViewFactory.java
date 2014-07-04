package main.java.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import main.java.controler.MapControler;
import main.java.controler.ParameterControler;
import main.java.controler.ParameterControlerTree;
import main.java.controler.SingleValueControler;
import main.java.controler.StatisticsControler;
import main.java.network.view.GraphControler;

/**
 * The view factory will construct the view thanks to a provided ParameterControler
 * and (optional) a ViewName string
 * The view construction consists of two steps:
 * 	1) ParameterViewAdapter construction 
 * 	2) View Construction
 * The {@ParameterViewAdapter} will handle the data transfer between {@ParameterControler} 
 * and {@ParameterView}
 * 
 * @author benoit
 * @version 09/06/2014
 *
 */
public class ViewFactory {
	/**
	 * View configuration for the view aspects
	 */
	private ViewConfiguration viewConfiguration;

	/**
	 * To access the parameterControlers optional
	 */
	private ParameterControlerTree pcTree; 

	/**
	 * Return the  view for the given {@ParameterControler}
	 * @param pc
	 * @return
	 */

	public ViewFactory(ViewConfiguration vc,ParameterControlerTree pcTree){
		this.viewConfiguration = vc;
		this.pcTree = pcTree;
	}

	/**
	 * Construct a view panel from the name
	 * @param name
	 * @return
	 */
	public ViewPanel constructViewPanel(String name){
		ViewPanel vp;
		Class<?> clazz = null;
		try {
			clazz = getClassName(name);
			Constructor<?> constructor = clazz.getConstructor(String.class, ViewFactory.class);
			vp = (ViewPanel) constructor.newInstance(name, this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("The construction of " + name + " failed. " + " class : " + clazz,e);
		}
		return vp;

	}
	//TODO make a method to get view wathever param or panel

	/**
	 * Return 
	 * @param parameterName
	 * @return
	 */
	private String defaultParamViewAdapter(ParameterControler pc){
		
		if(pc instanceof MapControler){
			return "MapView2DAdapter";
		}else if(pc instanceof SingleValueControler ){
			return "SingleValueParamCurve2DAdapter";
		}else if(pc instanceof StatisticsControler){
			return "StatisticsViewAdapter";
		}else if(pc instanceof GraphControler){
			return "GraphView2DAdapter";
		}else{
			return null;
		}
		
	}
	
	public ParameterView constructView(ParameterControler pc){
		System.err.println("constructing : " + pc.getName() + " pc type : " + pc.getClass());
		String pvaName = viewConfiguration.getViewAdapter(pc.getName());
		System.err.println("!!!!!!!!!!! pvA name = " + pvaName);
		if(pvaName == null){
			pvaName = this.defaultParamViewAdapter(pc);
			if(pvaName == null){
				//we assume that it is a panel
				return constructViewPanel(pc.getName());
			}else{
				return constructView(pc,pvaName);
			}
		}else{
			return constructView(pc,pvaName);
		}
	}

	public ParameterView constructView(String name){
		System.err.println("construct view of " + name);
		String pvaName = viewConfiguration.getViewAdapter(name);
		System.err.println("!!!!!!!!!!! pvA name = " + pvaName);
		if(pvaName == null){
			ParameterControler pc = this.getParameterControler(name); 
			System.out.println("parameter controler " + pc );
			pvaName = this.defaultParamViewAdapter(pc);
			if(pvaName == null){
				//we assume that it is a panel
				return constructViewPanel(name);
			}else{
				return constructView(pc,pvaName);
			}
		}else{
			return constructView(name,pvaName);
		}
	}

	private ParameterControler getParameterControler(String parameterName){
		return pcTree.getControler(parameterName);
	}
	
	private Class<?> getClassName(String name) throws ClassNotFoundException{
		Class<?> clazz;
		if(name.contains(".")){
			clazz = Class.forName(name);
		}else{
			clazz = Class.forName("main.java.view."+name);
		}
		return clazz;
	}

	private ParamViewAdapter constructParamViewAdapter(ParameterControler pc,String pvaName) {
		ParamViewAdapter ret;
		try {
			Class<?> clazz = getClassName(pvaName);
			Constructor<?> constructor = clazz.getConstructor(ParameterControler.class, ViewFactory.class);
			ret = (ParamViewAdapter) constructor.newInstance(pc, this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("The construction of paramViewAdapter " + pvaName + " failed.",e);
		}
		return ret;
	}
	
	

	public ParameterView constructView(String parameter,String viewAdapterName){
		ParameterControler pc = this.getParameterControler(parameter);
		if(pc == null){
			System.err.println("pc null for : " + parameter + " view adapter : " + viewAdapterName);
			System.exit(0);
		}
		ParamViewAdapter pva = constructParamViewAdapter(pc, viewAdapterName);
		//pva.constructView(getViewConfiguration(), this);
		return pva.getParamView();
	}
	
	public ParameterView constructView(ParameterControler pc,String viewAdapterName){
		ParamViewAdapter pva = constructParamViewAdapter(pc, viewAdapterName);
		//pva.constructView(getViewConfiguration(), this);
		return pva.getParamView();
	}

	/**
	 * @return the viewConfiguration
	 */
	protected ViewConfiguration getViewConfiguration() {
		return viewConfiguration;
	}
	
	public ParameterControlerTree getParameterControlerTree()
	{
		return this.pcTree;
	}

}
