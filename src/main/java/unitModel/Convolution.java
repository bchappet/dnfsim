package main.java.unitModel;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;

/**
 * Convolution Input * Kernel
 * @author bchappet
 *
 */
public class Convolution extends NeighborhoodUnitModel {

	public final static int KERNEL = 0; 
	public final static int INPUT = 1;




	public Convolution(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

	@Override
	public double compute() throws NullCoordinateException {
		double result = 0;
		int length = neighborhoods.get(INPUT).length;
		//System.out.println("main.java.input : " + length + " kernel : " + neighborhoods.get(KERNEL).length);
		for(int i = 0 ; i < length ; i++){
			try{
				//TODO why do we have to reverse the kernel? (matlab convolution...?)
				//System.out.println(neighborhoods.get(INPUT)[i].get() + "*"+ neighborhoods.get(KERNEL)[length-i-1].get());
				result += neighborhoods.get(INPUT)[i].get() * neighborhoods.get(KERNEL)[length-i-1].get();
			}catch (NullPointerException e) {
				// out of bounds
				//Do nothing
			}
		}
		//System.exit(-1);
		return result;
	}

}
