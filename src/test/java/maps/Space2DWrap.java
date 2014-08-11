package test.java.maps;

import main.java.neigborhood.WrappedSpace;
import main.java.space.Coord;
import main.java.space.Space2D;

public class Space2DWrap extends Space2D implements WrappedSpace<Integer> {

	public Space2DWrap(int x, int y) {
		super(x, y);
	}

	@Override
	public Coord<Integer> wrap(Coord<Integer> coord){
		throw new Error("notImplented");
	}
	
	

}
