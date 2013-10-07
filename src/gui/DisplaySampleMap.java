package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import maps.AbstractMap;
import maps.Parameter;
import coordinates.NullCoordinateException;

/**
 * Will display a vector with 2 value x,y in a 2D continuous space (spaceInput)
 * The current value will be in red while the older are in green
 * @author bchappet
 *
 */
public class DisplaySampleMap extends DisplayMap {

	protected final static int LIMIT_HISTORY = 100000;


	/**List of older values**/
	protected LinkedList<double[]> history;

	/**Current value**/
	protected double[] value;
	



	//Graphics parameters
	/**Radius in pixel**/
	protected int radius = 1;
	protected Color cCurrent = Color.RED;
	protected Color cCurrent1 = Color.BLACK;
	protected Color cOld = Color.GREEN;
	/** **/

	public DisplaySampleMap(GUI gui, Parameter displayed) {
		super(gui, displayed);
		history = new LinkedList<double[]>();
		resolution = space.getSimulationSpace().getResolution();
		Double[] dim = space.getSize();
		sx = dim[X];
		sy = dim[Y];
		value = null;

	}



	@Override
	public void update() throws NullCoordinateException{
		
		
		
		if(value != null)
			history.add(value);
		if(history.size() > LIMIT_HISTORY-1){
			history.removeFirst();
		}
		 
		value = new double[2];
		for(int i = 0 ; i < 2 ; i++){
			value[i] = ((AbstractMap)displayed).getParam(i).get();
		}
		
		//System.out.println(Arrays.toString(value) +Arrays.toString( Thread.currentThread().getStackTrace()));
	}
	
	@Override
	public void reset(){
		super.reset();
		history.clear();
	}


	
	@Override
	public void render(Graphics2D g) {
		g.clearRect(0, 0, getWidth(), getHeight() );
		
		
		Dimension dim = this.getSize();
		dx = (int) (dim.width*margin);
		dy = (int) (dim.height*margin);

		offsetX = (int) ((dim.width - dx)/2d);
		offsetY = (int) ((dim.height - dy)/2d);
		

		
		
		

		g.setColor(cOld);
		for(double[] old : history)
		{
			int[] xy = getCoord(old);
			//System.out.println("Old : " + Arrays.toString(xy));
			g.drawOval(xy[X], xy[Y], radius*2, radius*2);
		}
		
		g.setColor(cCurrent1);
		if(!history.isEmpty()){
			int[] xy = getCoord(history.get(history.size()-1));
			g.drawOval(xy[X], xy[Y], radius*2, radius*2);
		}
		
		g.setColor(cCurrent);
		int[] xy = getCoord(value);
		//System.out.println("New : " + Arrays.toString(xy));
		g.drawOval(xy[X], xy[Y], radius*2, radius*2);

		//black border
		g.setColor(Color.black);
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);

	}
	/**
	 * Calculate pixel coordinate
	 * @param coord
	 */
	protected int[] getCoord(double[] coord) {
		//System.out.println("here" + space);
		
		double oriX = space.getOrigin()[X];
		double oriY = space.getOrigin()[Y];
		double factX = this.sx/dx;
		int x = (int) Math.round(((coord[X] - oriX) / factX));
		
		x += offsetX;
		
		double factY = this.sy/dy;
		int y = (int) Math.round(((coord[Y] - oriY) / factY));
		y += offsetY;
		//System.out.println("sy : " + sy + " dy : " + dy);
		
		
		
		
		return new int[]{x,y};
	}
	
	






}
