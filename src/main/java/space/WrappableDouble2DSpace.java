package main.java.space;

import main.java.maps.Var;
import main.java.neigborhood.WrappedSpace;

/**
 * Standard space for DNF
 * Continuous space defined with double coord origin and length of each axis
 * And a resolution to discretize it
 * @author benoit
 * @version 12/05/2014
 *
 */
public class WrappableDouble2DSpace extends DoubleSpace2D implements WrappedSpace<Double>{

	public WrappableDouble2DSpace(Var<Double> originX, Var<Double> originY,
			Var<Double> lengthX, Var<Double> lengthY, Var<Integer> resolution) {
		super(originX, originY, lengthX, lengthY, resolution);
	}











}
