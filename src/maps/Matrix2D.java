package maps;

import java.util.Arrays;

import utils.ArrayUtils;
import coordinates.Space;

/**
 * TODO use library JAMA
 * @author bchappet
 *
 */
public class Matrix2D extends Matrix implements Cloneable {

	public static final int X = 0;
	public final static int Y = 1;


	public Matrix2D(Parameter map) {
		super(map.getName()+"_asMatrix2D" , map.getDt() ,
				map.getSpace());
		for(int i = 0 ; i < space.getDiscreteVolume() ; i++){
			
			values[i] = map.get(i);
			//TODO will be static..
		}
	}
	
	public static Matrix2D like(Parameter map) {
		return new Matrix2D(map.getName()+"_asMatrix2D" , map.getDt() ,map.getSpace());
	}

	public Matrix2D(String name, Parameter dt, Space space, Parameter... params) {
		super(name, dt, space, params);

	}

	public Matrix2D(String name, Parameter dt, Space space, double[] values,
			Parameter... params) {
		super(name, dt, space, values,params);
	}




	public Matrix2D(String name, Parameter dt,double resolution, Space space) {
		this(name,dt,space.withResolution(resolution));
	}


	

	@Override
	public Matrix2D clone() 
	{
		Matrix2D mat = (Matrix2D) super.clone();
		return mat;
	}



	/**
	 * 
	 * @return a new Matrix2D with the same parameters but but with a 0 matrix
	 */
	public Matrix2D like() {
		return new Matrix2D(this.name +"_copy", this.dt,this.space,ArrayUtils.asArray(params));
	}


	/** Test if the matrix roughly equals the other one,
	 *  which means that a they match for all points on a regular grid
	 *  (this comparison only perform number*number tests compared to xs*ys)
	 *  @param number   number of point of the grid for both directions (<0 for a full test)
	 *  @return         true if the maps match for all selected points */
	public boolean equalsRough(Matrix2D map, int number) {
		int xs = getXs();
		int ys = getYs();
		boolean id = true; // are the matrices identical
		// Test the dimensions and location
		if (map.space.equals(this.space)) {
			// Determine the step for both directions
			if (number==0) {
				// Nothing to do as there is no test required
				// (id remains true whatever the matrix content)
			} else {
				int dx = xs/number;
				int dy = ys/number;
				// Special case of a full test
				if (number<0) {
					dx = 1;
					dy = 1;
				}
				// Iterate over the selected locations
				for (int ux=0; id && ux<xs; ux+=dx) {
					for (int uy=0; id && uy<ys; uy+=dy) {
						id &= map.getFast(ux,uy)==getFast(ux,uy);
					}
				}
			}
		} else {
			id = false; // different dimensions
		}
		return id;
	}

	public int getXs() {
		return this.getSpace().getDiscreteSize()[X];
	}

	public int getYs() {
		return this.getSpace().getDiscreteSize()[Y];
	}

	@Override
	public String toString() {
		int xs = getXs();
		int ys = getYs();
		
		String s = "";
		for (int uy=0; uy<ys-1; uy++) {
			for (int ux=0; ux<xs-1; ux++) {
				s += ((int)(values[ux + uy * xs]*10000))/10000. + ",";
			}
			s += ((int)(values[xs-1 + uy * xs]*10000))/10000. + "\n";
		}

		for (int ux=0; ux<xs-1; ux++) {
			s += ((int)(values[ux + (ys-1) * xs]*10000))/10000. + ",";
		}
		s += ((int)(values[xs-1 + (ys-1) * xs]*10000))/10000.;

		return s;
	}
	
	public static boolean multiply(double[] mat,int xs, int ys,
			double[] other,int other_xs,int other_ys,
			double[] out,int out_xs,int out_ys) {
		
		
		if (ys!=other_xs || out_ys!=other_ys || out_xs!=xs)
			return false;
		for (int i=0; i<out_xs; i++) {
			for (int j=0; j<out_ys; j++) {
				out[i + j * out_xs] = 0;
				for (int k=0; k<xs; k++)
					out[i + j * out_xs] += mat[i + k * xs]*other[k + j * other_xs];
			}
		}
		return true;
		
	}

	/** Multiply matrices */
	public boolean multiply(Matrix other, Matrix out) {
		int xs = getXs();
		int ys = getYs();
		
		int out_xs = out.getSpace().getDiscreteSize()[X];
		int out_ys = out.getSpace().getDiscreteSize()[Y];
		
		int other_xs = other.getSpace().getDiscreteSize()[X];
		int other_ys = other.getSpace().getDiscreteSize()[Y];
		
		if (ys!=other_xs || out_ys!=other_ys || out_xs!=xs)
			return false;
		for (int i=0; i<out_xs; i++) {
			for (int j=0; j<out_ys; j++) {
				out.getValues()[i + j * out_xs] = 0;
				for (int k=0; k<xs; k++)
					out.getValues()[i + j * out_xs] += values[i + k * xs]*other.getValues()[k + j * other_xs];
			}
		}
		return true;
	}

	/** Transpose the matrix (try to do it in place)
	 *  @return         the new matrix if it could not be done in place */
	public Matrix2D transpose() {
		return transpose(true);
	}

	/** Transpose the matrix
	 * (This is done by actually exchanging the elements
	 *  instead of considering a transposed view of the array,
	 *  useful to get a physical column access to the data)
	 *  @param inplace  if false or rectangular matrix, a new one is produced
	 *  @return         the new matrix if it could not be done in place */
	public Matrix2D transpose(boolean inplace) {
		int xs = getXs();
		int ys = getYs();
		// Is the matrix rectangular, or do we force the creation?
		if (!inplace || xs!=ys) {
			// Then we need a new matrix and full copy
			Matrix2D trans = new Matrix2D(
					name+"(transposed)",dt,space.transpose()
					);
			// Copy/transpose the elements
			for (int i=0; i<ys; i++) {
				for (int j=0; j<xs; j++) {
					trans.values[j + i * xs] = values[i + j * xs];
				}
			}
			return trans;
		} else {
			// Do it in place (swap around the diagonal)
			for (int i=0; i<ys; i++) {
				for (int j=0; j<i; j++) {
					double temp = values[i + j * xs];
					values[i + j * xs] = values[j + i * xs];
					values[j + i * xs] = temp;
				}
			}
			return this;
		}
	}    

	/** Test if the matrix roughly equals the other one,
	 *  which means that a they match for all points on a regular grid
	 *  (this comparison only perform number*number tests compared to xs*ys)
	 *  @param number   number of point of the grid for both directions (<0 for a full test)
	 *  @return         true if the maps match for all selected points */
	public boolean equalsRough(Parameter map, int number) {
		boolean id = true; // are the matrices identical
		// Test the dimensions and location
		if (map.getSpace().equals(this.space)) {
			// Determine the step for both directions
			if (number==0) {
				// Nothing to do as there is no test required
				// (id remains true whatever the matrix content)
			} else {
				int dx = space.getDiscreteVolume()/number;
				// Special case of a full test
				if (number<0) {
					dx = 1;
				}
				// Iterate over the selected locations
				for (int ux=0; id && ux<space.getDiscreteVolume(); ux+=dx) {
					id &= map.get(ux)==get(ux);
				}
			}
		} else {
			id = false; // different dimensions
		}
		return id;
	}





	


	public  void apply(Function f, Parameter ... maps) {
		// If all map parameters are dense matrices,
		// call the optimized version of the method
		// directly using the "values" variable

		if (testType(Matrix2D.class,maps)) {
			applyToMatrix2D(f,
					Arrays.copyOf(maps, maps.length, Matrix2D[].class)
					);
		}
		// Else, just update the elements with double coordinates
		else {
			applyToAny(f,maps);
		}


	}



	/** Apply a function on all the elements of the matrix
	 *  @param f        function to apply
	 *  @param maps     maps storing the parameters of the function */
	protected void applyToMatrix2D(Function f, Matrix2D... maps) {
		int xs = getXs();
		int ys = getYs();
		// Parameters for one element
		int nbp = maps.length;
		double[] params = new double[nbp];
		// Go through all the elements
		for (int ux=0; ux<xs; ux++) {
			for (int uy=0; uy<ys; uy++) {
				// Extract the parameters
				for (int p=0; p<nbp; p++) {
					params[p] = maps[p].getFast(ux,uy);
				}
				// Apply the function
				values[ux  + uy * xs] = f.apply(params);
			}
		}
	}

	/** Apply a function on all the elements of the matrix
	 *  @param f        function to apply
	 *  @param maps     matrices storing the parameters of the function */
	protected  void applyToAny(Function f, Parameter... maps) {
		int xs = getXs();
		int ys = getYs();
		// Parameters for one element
		int nbp = maps.length;
		double[] params = new double[nbp];
		// Go through all the elements
		for (int ux=0; ux<xs; ux++) {
			for (int uy=0; uy<ys; uy++) {
				// Double coordinates
				Double[] xy = space.continuousProj(new Double[]{(double) ux,(double) uy});
				// Extract the parameters
				for (int p=0; p<nbp; p++) {
					params[p] = maps[p].get(xy[0],xy[1]);
				}
				// Apply the function
				values[ux + uy * xs] = f.apply(params);
			}
		}
	}

	/** Class representing an aggregation function */
	public interface Aggregation extends Function {
		/** Constants to access the elements */
		public static final int R = 0; // result
		public static final int X = 1; // x coordinate
		public static final int Y = 2; // y coordinate
		public static final int V = 3; // value at (x,y)
	}

	/** Class representing a double function of several arguments */
	public interface Function {
		/** Apply the function to the given parameters
		 *  @param params    parameters of the function
		 *  @return          image of these parameters */
		public double apply(double... params);
	}

	/** Sum function */
	public class Sum implements Function {
		@Override
		public double apply(double... params) {
			double res = 0;
			for (double p : params)
				res += p;
			return res;
		}
	}



	/** Constant function */
	public class Cst implements Function {
		/** Constant value */
		double cst;

		/** Constructor */
		public Cst(double cst) {
			this.cst = cst;
		}

		@Override
		public double apply(double... params) {
			return cst;
		}
	}

	/** Test if the parameters are of the specified type
	 *  (ref is used since instanceof cannot be used with the variable type M)
	 *  @param ref  object of the checked type
	 *  @param maps map objects to check
	 *  @return     true if all objects are of the type of ref */
	protected  boolean testType(Class ref, Parameter...maps) {
		boolean same_type = true;
		for (Parameter m : maps) {
			same_type &= ref.isInstance(m);
		}
		return same_type;
	}

	

	

}