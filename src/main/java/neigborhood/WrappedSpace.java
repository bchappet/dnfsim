package main.java.neigborhood;

import main.java.space.Coord;


public interface WrappedSpace<C> {
	
	public Coord<C> wrap(Coord<C> coord);

	

}
