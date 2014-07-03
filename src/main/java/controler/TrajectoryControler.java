package main.java.controler;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.space.Space;

public class TrajectoryControler extends MapControler implements SingleValueControler {

	public TrajectoryControler(Parameter param) {
		super((Map) param);
//		System.err.println("construct trajControler for param : " + param.getName());
	}

	public Object get() {
		return this.getParam().getIndex(0);
	}
	
	public Space getValueSpace(){
		return (Space) ((Map) this.getParam()).getParam(0); //TODO return to prrotected
	}
	
	

}
