package space;

import space.Space;
/**
 * By default space is discrete
 * 2D implementation to optimize things
 * @author bchappet
 *
 */
public class Space2D extends Space<Integer> {
	
	public final static int X = 0; //row length
	public final static int Y = 1; //column length

	/**
	 * Construct a 2D space
	 * @param dimensions
	 */
	public Space2D(int x, int y) {
		super(x,y);
	}
	
	/**
	 * Return the X dimension (row length)
	 * @return
	 */
	public int getDimX(){
		return this.getDimensions()[X];
	}
	
	/**
	 * Return the Y dimension (column length)
	 * @return
	 */
	public int getDimY(){
		return this.getDimensions()[Y];
	}
	
	@Override
	public Coord2D<Integer> indexToCoord(int index){
		int dimX = this.getDimX();
		return new Coord2D<Integer>(index % dimX,index/dimX);
		
	}
	@Override
	public int coordToIndex(Coord<Integer> coord){
		int dimX = this.getDimX();
		return coord.getIndex(X)+ coord.getIndex(Y)*dimX;
	}
	
	@Override
	public int getVolume(){
		return getDimX() * getDimY();
		
	}
	@Override
	public Coord2D<Integer> wrapCoord(Coord<Integer> coord){
		return new Coord2D<Integer>(coord.getIndex(X)%this.getDimX(),coord.getIndex(Y)%this.getDimY());
		
	}
	
	
	
	@Override
	public Space2D clone(){
		Space2D clone = (Space2D) super.clone();
		return clone;
	}

}
