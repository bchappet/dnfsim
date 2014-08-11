package main.java.neigborhood;

import main.java.maps.Unit;

public class NullUnit<T> extends Unit<T> {
	
	private T val;
	public NullUnit(Unit<T> u) {
		super(null);
		this.val = u.get();
	}
	
	public T get() {
		
		return val;
	}
	
	

}
