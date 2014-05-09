package main.java.maps;

import java.util.List;

import main.java.maps.Parameter;

public interface HasChildren<T> extends Parameter<T> {
	
	
	public List<Parameter> getParameters();

}
