package coordinates;

import gui.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import maps.Unit;
import maps.Var;
import utils.Cloneable;

/**
 * TODO : crate two classes of refSpace :
 * main refSpace : one instance
 * other refSpace which are aware of their differences
 * @author bchappet
 *
 */
public abstract class Space implements Cloneable,Node{




	/**This is the refSpace of reference for the simulation
	 * there should be only one refSpace of reference for the entire simulation
	 * any other refSpace should reference it here**/
	protected Space simulationSpace;
	
	/**
	 * Space where unit are computed
	 */
	protected Space frameSpace;

	/**
	 * As we have a "main" refSpace (simulationSpace) it will receive the signal of modification
	 * it will have to update its childrens
	 */
	protected Set<Space> childrenSpaces;

	/**Index of coordinates**/
	public static int X = 0;
	public static int Y = 1;
	public static int Z = 2;

	/**Origin of discrete refSpace**/
	protected Double[] origin;
	/**Size of each dimension**/
	protected Double[] size;
	/**Resolution for discretisation**/
	protected Var resolution;

	//Copy of resolution value
	protected double res;

	/**Number of dimension**/
	protected int dim;
	/**Size of each discrete dimension**/
	protected Integer[] discreteSize;
	/**True if tore projection**/
	protected boolean wrap;


	/**1 if the dimension i is defined*/
	protected int[] dimension;

	/**List of sucribers to the event refSpace modified**/
	protected List<SpaceReceiver> suscribers; 
	
	

	public Space(Double[] origin,Double[] size,Var resolution,boolean wrap)
	{
		this.childrenSpaces = new HashSet<Space>();

		this.origin = origin;
		this.size = size;
		this.resolution = resolution;
		this.res = resolution.get();

		this.dim = origin.length;
		initDiscreteSpace(resolution.get());
		this.wrap = wrap;

		this.suscribers = new LinkedList<SpaceReceiver>();

		this.dimension = new int[dim];
		for(int i = 0 ; i < dim ; i++)
			dimension[i] = 1;
		
		this.frameSpace = this;
	}




	public Space clone()
	{
		Space space  = null;
		try{
			space = (Space) super.clone();
			space.origin = this.origin;
			space.size = this.size;
			space.resolution = this.resolution;
			space.res = this.resolution.get();

			space.dim = origin.length;
			space.initDiscreteSpace(resolution.get());
			space.wrap = this.wrap;
			space.suscribers = new LinkedList<SpaceReceiver>();
			space.dimension = this.dimension.clone();
			space.frameSpace = space;
		}catch (CloneNotSupportedException e) {
			//Cannot append as we are cloanable
			e.printStackTrace();
		}
		return space;
	}






	/**
	 * To sucribe to the events
	 * @original receiver
	 */
	public void sucribe(SpaceReceiver receiver)
	{
		suscribers.add(receiver);
	}

	public void signalWrapChanged(boolean newWrap)
	{
		for(SpaceReceiver s : suscribers)
			s.onWrapChanged(newWrap);
	}

	public void signalResolutionChanged(double oldRes,double newRes)
	{
		for(SpaceReceiver s : suscribers)
			s.onResolutionChanged(oldRes,newRes);
	}

	/**
	 * Initialize discrete sopace values : discreteSize and discreteVolume
	 * @param resolution
	 */
	public void initDiscreteSpace(double resolution)
	{
		discreteSize = new Integer[dim];
		for(int i = 0 ; i < dim ; i++)
		{
			discreteSize[i] = (int)(resolution);
		}


	}








	/**
	 * Return the integer (descretized) coordinates corresponding
	 * to this refSpace
	 * @original coor
	 * @return
	 */
	public abstract Double[] discreteProj(Double[] coor) throws NullCoordinateException;
	//	{
	//		//No checking to save time
	//		//ret = (c-orig)*res
	//		Double[] ret = new Double[dim];
	//		for(int i = 0 ; i < dim ; i++)
	//		{
	//			ret[i] =(double) (int)((origin[i] - coor[i])*resolution.get());
	//		}
	//		return ret;
	//	}

	/**
	 * Return the double (in the continuous refSpace) coordinates corresponding
	 * to the discretized refSpace
	 * @original coor
	 * @return
	 * @throws NullCoordinateException 
	 */
	public abstract Double[] continuousProj(Double[] coor) throws NullCoordinateException; 
	//	{
	//		//No checking to save time 
	//		// ret = (c)/res + orig
	//		Double[] ret = new Double[dim];
	//		for(int i = 0 ; i < dim ; i++)
	//			ret[i] = coor[i]/resolution.get() + origin[i];
	//
	//		return ret;
	//	}

	/**
	 * Only for one axis
	 * @original val
	 * @original axis
	 * @return
	 */
	public abstract  double continuousProj(double val, int axis);
	//	{
	//		return val/resolution.get() + origin[axis];
	//	}

	/**
	 * Axis projection : res = val/resolution
	 * @param val
	 * @param axis
	 * @return
	 */
	public double distContinuousProj(int val, int axis) {
		return val/resolution.get();
	}

	//	@Override
	//	public  Iterator<Coor<Double>> iterator() {
	//		return new SpaceIterator(this);
	//	}
	//	
	//	public Iterator<Coor<Integer>> getIteratorInt() {
	//		return new SpaceIteratorInt(this);
	//	}

	public void onParameterChange()
	{
		initDiscreteSpace(resolution.get());
		//(OldRes,new Res)
		signalResolutionChanged(res,resolution.get());
		//Update res
		res = resolution.get();

		//		//Signal the parents TODO
		//		for(TreeNode t : parentNodes)
		//			if(t instanceof Parameter)
		//				((Parameter) t).onParameterChange();

		//		for(Lock l : locks)
		//			l.unlock();
	}

	/**
	 * We choose two order to update the components before the changing of resolution
	 * in order to avoid outOfBounds exception  should be atomic
	 * @original resolution
	 */
	//	public  void setResolution(double resolution)
	//	{
	//		
	//		double oldRes = this.resolution;
	//		this.resolution = resolution;
	//		this.discreteSize = computeDimension(resolution);
	//		signalResolutionChanged(oldRes,resolution);
	//		
	//		for(Lock l : locks)
	//			l.unlock();
	//		
	////		if(resolution >= this.resolution)
	////		{
	////			signalResolutionChanged(oldRes,resolution);
	////			this.resolution = resolution;
	////			this.discreteSize = computeDimension(resolution);
	////		}
	////		else
	////		{
	////			this.resolution = resolution;
	////			this.discreteSize = computeDimension(resolution);
	////			signalResolutionChanged(oldRes,resolution);
	////		}
	//	}

	/**
	 * 
	 * @original p : in given point
	 * @original ret : out wrapped result (not null)
	 * @return true if p was out of bounds
	 */
	public boolean wrapDiscrete(Double[] coord,Double[] res) {
		int dimX = discreteSize[X];
		int dimY = discreteSize[Y];
		Double x = coord[X];
		Double y = coord[Y];

		Double retX = null;
		Double retY = null;
		boolean outOfBounds;

		if(x < 0 || x >= dimX || y < 0 || y >=dimY)
		{
			outOfBounds = true;
			if(this.wrap)
			{
				retX = (x % (int)dimX);
				retY =  (y % (int)dimY);
				if(retX < 0) retX += dimX;
				if(retY < 0) retY += dimY;
			}else{

				retX = x;
				retY = y;

			}
		}
		else
		{
			outOfBounds = false;
			retX = x;
			retY = y;
		}

		res[X] = retX;
		res[Y] = retY;
		return outOfBounds;

	}

	/**
	 * Continuous coordinates!!
	 * Return a wrapped version of the coor if wrap = true
	 * Otherwise the outside coord unit is set to null (outsie the frame space)
	 * 
	 * ==> if wrap  == false alway verify that a Double is not null
	 * @param coor (Continous)
	 * @return a wrapped version of the coor if wrap = true
	 * Otherwise the outside coord unit is set to null
	 */
	public Double[] wrap(Double[] coord) {
		Double[] res = new Double[dim];

		for(int i = 0 ; i < dim ; i++)
		{
			Double x = coord[i];
			double oriX = frameSpace.origin[i];
			double sizeX =  frameSpace.size[i];
			double endX = oriX + sizeX;

			//System.out.println(" avant " + Arrays.toString(coord));

			if( x < oriX || x > endX )
			{
				
				//We are outside the frame bounds
				if(wrap)
				{
					x = x - oriX;
					x = x % sizeX;
					if(x < 0){
						x += sizeX;
					}
					x = x + oriX;
				}
				else
				{
					x = null;
				}
			}
			res[i] = x;
		}

		//System.out.println(" apres " + Arrays.toString(res));

		return res;
	}



	/**
	 * Return the coordinate resulting from the projection of coor (from this refSpace) on
	 * the given refSpace
	 * @original coor Coordinate from this refSpace
	 * @original refSpace Space of the resulting coordinate
	 * @return 
	 */
	public Double[] project(Double[] coor, Space space) {
		Double[] res = new Double[dim];

		for(int axis = 0 ; axis < dim ; axis ++)
		{

			double diffOri =  space.origin[axis] - this.origin[axis];
			double scaleFactor = space.size[axis] / this.size[axis];
			double thisCoor = coor[axis];

			double retCoor = thisCoor + diffOri; //Put the two refSpace at the same origin
			double diffFrom0 = 0 - space.origin[axis];

			retCoor  = retCoor + diffFrom0; //Put the two refSpace origin at 0
			retCoor = retCoor * scaleFactor; //Scale the coor according to refSpace size
			retCoor = retCoor - diffFrom0; //Translate to the true origin

			res[axis] =  retCoor;
		}

		return res;
	}

	/**
	 * Same as project but in discrete version
	 * Return the coordinate resulting from the projection of coor (from this refSpace) on
	 * the given refSpace.
	 * Same as project but in discrete version
	 * @original coor Coordinate from this refSpace
	 * @original refSpace Space of the resulting coordinate
	 * @return 
	 * @throws NullCoordinateException 
	 */
	public Double[] projectInt(Double[] coor, Space space) throws NullCoordinateException {
		return space.discreteProj(project(this.continuousProj(coor),space));
	}




	public Double[] getOrigin() {
		return origin;
	}

	public Var getResolutionVar() {
		return resolution;
	}

	public double getResolution() {
		return resolution.get();
	}


	public int getDim() {
		return dim;
	}

	public Integer[] getDiscreteSize() {
		return discreteSize;
	}

	public boolean isWrap() {
		return wrap;
	}

	public void setWrap(boolean wrap) {
		signalWrapChanged(wrap);
		this.wrap = wrap;
	}




	//	/**
	//	 * Return the product of each dimension size
	//	 * @return
	//	 */
	//	public int getDiscSize() {
	//		
	//		return 0;
	//	}

	//	/**
	//	 * Return an iterator for the discrete index order of map,
	//	 * which will be used in display : printing, painting etc...
	//	 * The value is -x when we change the dimension x (line break, map beak, cube break etc...)
	//	 * @return
	//	 */
	//	public Iterator<Integer> getDisplayIterator() {
	//		
	//		return null;
	//	}
	/**
	 * Transform a continous coordinate into an index on a one dimension array
	 * index = coord[0] + coord[1]*discreteSize[0] + coord[2]*discreteSize[0]*discreteSize[1] ...
	 * If the dimension x is not defined (dimension[x] == 0), we ignore it.
	 * If the coordinate x is null and the dimension x is defined, throw an exception
	 * @param coor : coordinates to transform into an index
	 * @param dimension : defined dimension of the map
	 * @return
	 * @throws NullCoordinateException if the coordinate x is null and the dimension x is defined
	 */
	public int coordToIndex(Double... coord) throws NullCoordinateException {
		//System.out.println("Début : " + Arrays.toString(coord));
		int i = 0;
		Double[] newCoord = this.discreteProj(coord);
		//System.out.println("NewCoord : " + Arrays.toString(newCoord));
		try{
			int index = 0; //returned index
			for(i = 0 ; i < dim ; i++)
			{
				if(dimension[i] != 0)
				{
					int product = newCoord[i].intValue();
					for(int j = 0 ; j < i ; j++)
					{
						if(dimension[j] != 0)
							product *= discreteSize[j];
					}
					index += product;
				}
			}
			//			System.out.println("fin : index : " + index);
			return index;
		}catch(NullPointerException e){
			throw new NullCoordinateException("The coordinate " + i + " was null." +" Coord were "+Arrays.toString(coord) +" . Dimension was : "+ Arrays.toString(dimension));
		}
	}

	/**
	 * Fast version with discrete coord
	 * @param coord
	 * @return
	 */
	public int coordToIndex(int... coord) {
		int index = 0; //returned index
		for(int i = 0 ; i < dim ; i++)
		{
			if(dimension[i] != 0)
			{
				int product = coord[i];
				for(int j = 0 ; j < i ; j++)
				{
					if(dimension[j] != 0)
						product *= discreteSize[j];
				}
				index += product;
			}
		}
		return index;
	}

	/**
	 * Transform an index value in continous coordinate vector
	 * @return
	 * @throws NullCoordinateException  if a coordinate was excpected and Null was found
	 */
	public Double[] indexToCoord(int index) {


		int mapDim = sum(dimension); //Real dimension of the map index

		if(mapDim == 0)
		{
			Double[] ret = new Double[dim];
			for(int i = 0 ; i < dim ; i++)
				ret[i] = null;
			return ret;
		}
		else
		{
			Double[]  coord = new Double[mapDim];
			int[] mapDiscreteSize = project(discreteSize,dimension);

			//////////////////////////////////////////////////
			//Compute the coordinate considering that the refSpace is not sparse
			//////////////////////////////////////////////////

			int prod = index;

			/*Compute last coord*/
			int prodI = 1; //prodi = prod[0--dim-2]size_j
			for(int j = 0 ; j < mapDim-1 ; j++ )
				prodI *= mapDiscreteSize[j];
			coord[mapDim-1] = (double) (index / prodI);

			/*Compute the others except the first*/
			for(int i = mapDim-2 ; i > 0 ; i--)
			{
				int prodI_1 = 1; //prod 0=>i-1 Si
				for(int j = 0 ; j < i ; j++ )
					prodI_1 *= mapDiscreteSize[j];
				//p = i - coor[i+1]*si* si-1*si-1...
				prod = (int) (prod - coord[i+1]*mapDiscreteSize[i]*prodI_1);
				//coor[i] = prod/si-1*si-1...
				coord[i] = (double) (prod / prodI_1);
			}

			/*Compute the first*/
			coord[0] = (double) (index % mapDiscreteSize[0]);
			//System.out.println("Index = " + index + " ;mapDiscreteSize[0] : " + mapDiscreteSize[0]);
			//System.out.println("coor[0] : " + coord[0]);



			///////////////////////////////////////////////////
			//Then we affect each coordinates of the the non null dimension
			///////////////////////////////////////////////////
			Double[] fullCoord = new Double[dim];
			int j = 0; //acces to the coord
			for(int i = 0 ; i < dimension.length ; i++)
			{
				if(dimension[i] != 0)
				{
					//The dimension is defined
					fullCoord[i] = coord[j];
					j++;
				}
			}
			return 	continuousProj(fullCoord);
		}
	}

	private  static int[]project(Integer[] tab, int[] dimension) {
		int newDim = sum(dimension);
		int[] proj = new int[newDim];
		int i = 0;
		int j = 0;
		while(i < newDim && j < tab.length)
		{
			if(dimension[j] != 0)
			{
				proj[i] = tab[j];
				i++;
			}
			j++;
		}
		return proj;
	}

	private static int sum(int[] tab) {
		int ret = 0;
		for(int i = 0 ; i < tab.length ; i++)
			ret += tab[i];

		return ret;
	}

	/**
	 * Computation iterator (synchronous assynchronous random etc...
	 * @return
	 */
	public Iterator<Unit> getComputationIterator(List<Unit> l) {
		return l.iterator();
	}







	//	@Override
	//	public String toString() {
	//		return "Space [origin=" + origin + ", size=" + size + ", resolution="
	//				+ resolution + ", dim=" + dim + ", discreteSize="
	//				+ discreteSize + ", wrap=" + wrap + "]";
	//	}



	/**
	 * Return a vector of identic double of simension dim
	 * @param dim
	 * @param value
	 * @return
	 */
	public static Double[] getUniformDouble(int dim,double value) {
		Double[] ret = new Double[dim];
		for(int i = 0 ; i < dim ; i++)
			ret[i] = value;
		return ret;
	}

	public int getDiscreteVolume() {
		int vol = 1;
		for(int i = 0 ; i  < dim ; i ++)
		{
			if(dimension[i] != 0)
				vol *= discreteSize[i];
		}
		return vol;
	}

	/**
	 * Take a neigbhoors coord array and 
	 * 	if the refSpace is wrapped, wrap the coords outside the refSpace
	 * 	otherwise replace the outside coords by a null Object
	 * 
	 * ==> if !wrap alway verify that a Double[] object is not null
	 * @param coord : discretes!!
	 * @param neighborhoods : discretes!!
	 * @return
	 */
	public Double[][] wrapNeighborhood(Double[][] neighborhood) {
		Double[][] res = new Double[neighborhood.length][neighborhood[0].length];
		System.arraycopy(neighborhood, 0, res, 0, neighborhood.length);

		for(int i = 0 ; i < res.length ; i++)
		{
			Double[] wrapped = this.wrap(res[i]);

			if(wrap)
			{
				//We may have null values
				boolean inside = true; //false if there is a null value
				for(int j = 0 ; j < wrapped.length ; j++){
					inside &= (wrapped[j] != null);
				}
				if(!inside){
					res[i] = null;
				}
			}
		}
		return res;
	}

	public void setDimension(int[] dimension) {
		this.dimension = dimension;
	}



	/**
	 * Project a discrete value : val on the constinuous axis axis
	 * @param val
	 * @param axis
	 * @return
	 * @throws NullCoordinateException 
	 */
	public abstract Double continusousProj(double val, int axis) throws NullCoordinateException;

	@Override
	public String getName()
	{
		return "Space";
	}
	//	/**
	//	 * Construction in progress TODO
	//	 * @param a
	//	 * @return
	//	 */
	//	public Double[] decompose(int a)
	//	{
	//		
	//		List<Double> list = new LinkedList<Double>();
	//		int i = 0;
	//
	//		while(a > 0)
	//		{
	//		
	//			list.add(new Double(a%discreteSize[i]));
	//			a = a/discreteSize[i];
	//			i++;
	//		}
	//		while(i < this.getDim())
	//		{
	//			list.add(0d);
	//			i++;
	//		}
	//		Double[] ret = new Double[list.size()];
	//		ret = list.toArray(ret);
	//
	//		return ret;
	//	}
	//
	//
	//
	//	



	/**
	 * Extend the refSpace size by multipling it by the given factor 
	 * The origin will be shifted with factor/2
	 * @post dim = dim * factor
	 * @post orig = orig - factor/2d
	 * @param factor
	 * @param framed 
	 * @return the new extended Space
	 */
	public abstract Space extend(double factor, boolean framed);


	/**
	 * Return a new Space with the specific resolution
	 * @param newRes
	 * @return
	 */
	public Space withResolution(double newRes){
		Space ret = this.clone();
		ret.setResolution(newRes);
		return ret;

	}


	public void setResolution(double newRes) {
		this.res = newRes;
		this.resolution.set(newRes);
		this.initDiscreteSpace(newRes);

	}



	/**
	 * Get the refSpace of référence for the simulation
	 * if the simulationSpace is null here, we assume that
	 * the simulation refSpace is this refSpace
	 * @return the simulationSpace
	 */
	public Space getSimulationSpace() {
		return simulationSpace != null ? simulationSpace : this;
	}




	public void setSimulationSpace(Space simulationSpace) {
		this.simulationSpace = simulationSpace;
		simulationSpace.childrenSpaces.add(this);
	}



	/**
	 * Return the center (continuous) coordinates of the refSpace :
	 * center = origin + size/2
	 * @return
	 */
	public Double[] getCenter() {
		Double[] center = new Double[this.getDim()];
		for(int i = 0 ; i < this.getDim() ;i ++)
		{
			center[i] = origin[i] + size[i]/2d;
		}
		return center;
	}




	public Double[] getSize() {
		return size;
	}

	public static double distance(Double[] a, Double[] b) {
		double dist = 0;
		for(int i = 0 ; i < a.length ; i++)
		{
			//System.out.println(dist + " =W>" + a.get(key) + " - " + b.get(key) + " " + key);
			dist += Math.pow(a[i]-b[i], 2);
		}
		dist = Math.sqrt(dist);
		return dist;
	}




	@Override
	public String toString() {
		return "Space ["
				+ ", origin="
				+ Arrays.toString(origin) + ", size=" + Arrays.toString(size)
				+ ", resolution=" + resolution + ", res=" + res + ", dim="
				+ dim + ", discreteSize=" + Arrays.toString(discreteSize)
				+ ", wrap=" + wrap + ", dimension="
				+ Arrays.toString(dimension) 
				+ "]";
	}




	public int[] getDimension() {
		return dimension;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dim;
		result = prime * result + Arrays.hashCode(origin);
		result = prime * result
				+ ((resolution == null) ? 0 : resolution.hashCode());
		result = prime * result + (wrap ? 1231 : 1237);
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Space other = (Space) obj;
		if (dim != other.dim)
			return false;
		if (!Arrays.equals(origin, other.origin))
			return false;
		if (resolution == null) {
			if (other.resolution != null)
				return false;
		} else if (!resolution.equals(other.resolution))
			return false;
		if (wrap != other.wrap)
			return false;
		return true;
	}


	/**
	 * Transpose space
	 * @return
	 */
	public abstract Space transpose();



	/**
	 * Get a uniform discrete sample on this axis
	 * @param axis
	 * @return
	 */
	public double getUniformSample(int axis) {
		double min = origin[axis];
		double siz =  size[axis];
		double res = min + (Math.random() * ((siz) ));
		return  res;
	}
	
	/**
	 * Get a gaussian discrete sample on this axis
	 * @param axis
	 * @return
	 */
	public double getGaussianSample(int axis,double width) {
		double min = origin[axis];
		double siz =  size[axis];
		double middle = (min + siz)/2d;
		
		Random rand = new Random();
		double res = (rand.nextGaussian()*width)/(siz);
		return  res;
	}




	/**
	 * Return the norme of the space
	 * for size 1 * 1 => sqrt(2)
	 * @return
	 */
	public double getNorme() {
		return Math.sqrt(size[Space.X]*size[Space.X] + size[Space.Y]*size[Space.Y]);
	}




	public Space getFramedSpace() {
		return frameSpace;
	}




	

	

















}
