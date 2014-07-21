package main.java.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.EventObject;
import java.util.LinkedList;

import main.java.maps.Var;
import main.java.space.Coord;
import main.java.space.Coord2D;
import main.java.space.Space2D;

/**
 * Will display a vector with 2 value x,y in a 2D continuous main.java.space (spaceInput)
 * The current value will be in red while the older are in green
 * @author bchappet
 *
 */
public class DisplaySampleMap2D extends ParameterViewDB {
	
	protected static final int X = 0;
	protected static final int Y = 1;

	protected final static int LIMIT_HISTORY = 100000;


	/**List of older values**/
	private LinkedList<Coord<Double>> history;

	/**Current value**/
	private Coord<Double> value;
	

	/**Margin ratio**/
	protected double margin = 0.99;
	/**Black border in pixel**/
	protected int blackBorder = 1; 

	//Graphics parameters
	/**Radius in pixel**/
	protected int radius = 1;
	protected Color cCurrent = Color.RED;
	protected Color cCurrent1 = Color.BLACK;
	protected Color cOld = Color.GREEN;
	/** **/
	
	private Coord<Var<Double>> spaceOrigin;
	private Coord<Var<Double>> spaceLength;

	public DisplaySampleMap2D(String name,Coord2D<Double> init,Coord<Var<Double>> spaceOri,Coord<Var<Double>> spaceLength) {
		super(name);
		this.spaceLength = (Coord<Var<Double>>) spaceLength;
		this.spaceOrigin = (Coord<Var<Double>>) spaceOri;
		history = new LinkedList<Coord<Double>>();
		value = init;
	}
	



	/**
	 * 
	 * @param coord
	 */
	public void update(Coord2D<Double> coord) {
		if(this.value != null)
			history.add(value);
		if(history.size() > LIMIT_HISTORY-1){
			history.removeFirst();
		}
		 
		this.value = coord.clone();
		
		//System.out.println(Arrays.toString(value) +Arrays.toString( Thread.currentThread().getStackTrace()));
	}
	
//	@Override
//	public void reset(){
//		super.reset();
//		history.clear();
//	}


	
	@Override
	public void render(Graphics2D g) {
		Dimension dim = this.getSize();
		int dx = (int) (dim.width*margin);
		int dy = (int) (dim.height*margin);

		int offsetX = (int) ((dim.width - dx)/2d);
		int offsetY = (int) ((dim.height - dy)/2d);

		
		g.setColor(cOld);
		for(Coord<Double> old : history)
		{
			int[] xy = getCoord(old,dim);
			//System.out.println("Old : " + Arrays.toString(xy));
			g.drawOval(xy[X], xy[Y], radius*2, radius*2);
		}
		
		g.setColor(cCurrent1);
		if(!history.isEmpty()){
			int[] xy = getCoord(history.get(history.size()-1),dim);
			g.drawOval(xy[X], xy[Y], radius*2, radius*2);
		}
		
		g.setColor(cCurrent);
		int[] xy = getCoord(value,dim);
		//System.out.println("New : " + Arrays.toString(xy));
		g.drawOval(xy[X], xy[Y], radius*2, radius*2);

		//black border
		//Image scaled = img.getScaledInstance(dx, dy, Image.SCALE_FAST);
		//System.out.println(dx+","+dy);
		//g.drawImage(scaled,offsetX,offsetY,dx,dy,null);
		g.setColor(Color.black);
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);

	}
	/**
	 * Calculate pixel coordinate
	 * @param coord
	 * @param dim 
	 */
	protected int[] getCoord(Coord<Double> coord, Dimension dim) {
		//System.out.println("here" + main.java.space);
		//System.out.println("coordX = " + coord.getIndex(0));
		double oriX = this.spaceOrigin.getIndex(Space2D.X).get();
		double oriY = this.spaceOrigin.getIndex(Space2D.Y).get();
		
		int dx = (int) (dim.width*margin);
		int dy = (int) (dim.height*margin);
		
		
		
		double factX = dx/this.spaceLength.getIndex(X).get();
		int x = (int) Math.round(((coord.getIndex(Space2D.X) - oriX) * factX));
		//x += offsetX;
		
		double factY = dy/this.spaceLength.getIndex(Y).get();
		int y = (int) Math.round(((coord.getIndex(Space2D.Y) - oriY) * factY));
	//	y += offsetY;
		//System.out.println("sy : " + sy + " dy : " + dy);
		
		
		
		
		return new int[]{x,y};
	}



	@Override
	public void interact(EventObject event) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void interactRelease(EventObject event) {
		// TODO Auto-generated method stub
		
	}
	
	






}
