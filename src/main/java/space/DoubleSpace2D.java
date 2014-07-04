package main.java.space;

import java.util.Random;

import main.java.maps.Var;


/**
 * The refSpace currently used : 
 * -0.5 -0.3 -0.1 0.1 0.3 0.5  : continous coord                                 
 *   [---------------------[
 *     0  |  1 | 2 | 3 | 4     : discretes coord
 * @author bchappet
 *
 */
public class DoubleSpace2D extends DoubleSpace implements ISpace2D{
	
	
	public DoubleSpace2D(Coord<Var<Double>> origin,
			Coord<Var<Double>> length,Var<Integer> resolution) {
		super(origin,length,resolution);
	}
	
	public DoubleSpace2D(Var<Double> originX,Var<Double> originY,
			Var<Double> lengthX,Var<Double> lengthY,Var<Integer> resolution) {
		this(new Coord<Var<Double>>(originX,originY),new Coord<Var<Double>>(lengthX,lengthY),resolution);
	}
	
	
	@Override
	public Coord2D<Double> indexToCoord(int index) {
		return (Coord2D<Double>)(super.indexToCoord(index));
	}
	
	
	@Override
	public Coord<Double> toTypeCoord(Coord<Integer> intCoord) {
		return new Coord2D<Double>(super.toTypeCoord(intCoord),this);
	}
	
	
	
	private static Var<Double>[] getArray(Var<Double> ...vars )
	{
		return vars;
	}
	private double getLengthX(){
		return getLenght(Space2D.X);
	}
	
	private double getLengthY(){
		return getLenght(Space2D.Y);
	}
	
	private double getOriX(){
		return getOrigin(Space2D.X);
	}
	
	private double getOriY(){
		return getOrigin(Space2D.Y);
	}
	
	/**
	 * Return the X dimension (row length)
	 * @return
	 */
	public int getDimX(){
		return this.getDimensions().getIndex(Space2D.X).get().intValue();
	}
	
	/**
	 * Return the Y dimension (column length)
	 * @return
	 */
	public int getDimY(){
		return this.getDimensions().getIndex(Space2D.Y).get().intValue();
	}
	
	@Override
	public Coord2D<Integer> indexToCoordInt(int index){
		int dimX = this.getDimX();
		return new Coord2D<Integer>(index % dimX,index/dimX);
		
	}

	

	@Override
	public int coordToIndex(Coord<Double> coord) {
		Coord<Integer> coordInt = toIntCoord(coord);
		return this.coordIntToIndex(coordInt);
	}
	
	@Override
	public int coordIntToIndex(Coord<Integer> coord){
		int dimX = this.getDimX();
		return coord.getIndex(Space2D.X)+ coord.getIndex(Space2D.Y)*dimX;
	}

	public Coord2D<Double> getGaussianSample(){
		Random rand = new Random();
		double widthX = this.getLengthX();
		double widthY = this.getLengthY();
		double oriX = this.getOriX();
		double oriY = this.getOriY();
		double cx = widthX/2d - oriX;
		double cy = widthY/2d - oriY;
		System.out.println(rand.nextGaussian());
		double x = (rand.nextGaussian()*widthX/6d)+cx;  //TODO
		double y = (rand.nextGaussian()*widthY/6d)+cy; //TODO test
	//	System.out.println("("+x+","+y+")");
		
		return new Coord2D<Double>(x,y);
	}
	
	@Override
	public int getVolume() {
		return this.getDimX() * this.getDimY();
	}

	@Override
	public Coord<Double> wrapCoord(Coord<Double> coord) {
		
		return null;
	}

	@Override
	public Coord<Integer> wrapCoordInt(Coord<Integer> coord) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
