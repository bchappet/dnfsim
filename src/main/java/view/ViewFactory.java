package main.java.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import main.java.controler.ParameterControler;
import main.java.controler.ParameterControlerTree;
import main.java.controler.*;

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
	 * To access the parameterControlers
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
	 * Contruct a view panel from the name
	 * @param name
	 * @return
	 */
	public ViewPanel constructViewPanel(String name){
		ViewPanel vp;
		try {
			Class<?> clazz = Class.forName("main.java.view."+name);
			Constructor<?> constructor = clazz.getConstructor(String.class, ViewFactory.class);
			vp = (ViewPanel) constructor.newInstance(name, this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("The construction of " + name + " failed.",e);
		}
		return vp;

	}
	//TODO make a method to get view wathever param or panel

	/**
	 * Return 
	 * @param parameterName
	 * @return
	 */
	private String defaultParamViewAdapter(String parameterName){
		ParameterControler pc = this.getParameterControler(parameterName); //will be two calls to this function...
		if(pc instanceof MapControler){
			return "MapView2DAdapter";
		}else{
			return null;
		}
		
	}

	public ParameterView constructView(String name){
		String pvaName = viewConfiguration.getViewAdapter(name);
		if(pvaName == null){
			pvaName = this.defaultParamViewAdapter(name);
			if(pvaName == null){
				//we assume that it is a panel
				return constructViewPanel(name);
			}else{
				return constructView(name,pvaName);
			}
		}else{
			return constructView(name,pvaName);
		}
	}

	private ParameterControler getParameterControler(String parameterName){
		return pcTree.getControler(parameterName);
	}

	private ParamViewAdapter constructParamViewAdapter(String parameterName,String pvaName) {
		ParamViewAdapter ret;
		try {
			Class<?> clazz = Class.forName("main.java.view."+pvaName);
			Constructor<?> constructor = clazz.getConstructor(ParameterControler.class, ViewConfiguration.class);
			ret = (ParamViewAdapter) constructor.newInstance(this.getParameterControler(parameterName), this.viewConfiguration);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("The construction of paramViewAdapter " + pvaName + " failed.",e);
		}
		return ret;
	}

	public ParameterView constructView(String parameter,String viewAdapterName){
		return constructParamViewAdapter(parameter, viewAdapterName).getParamView();
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
