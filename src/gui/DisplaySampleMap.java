package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.Parameter;
import coordinates.NullCoordinateException;
import coordinates.RoundedSpace;
import coordinates.Space;

/**
 * Will display a vector with 2 value x,y in a 2D continuous space (spaceInput)
 * The current value will be in red while the older are in green
 * @author bchappet
 *
 */
public class DisplaySampleMap extends DisplayMap {




	/**List of older values**/
	protected List<double[]> history;

	/**Current value**/
	protected double[] value;
	
	protected Space space;



	//Graphics parameters
	/**Radius in pixel**/
	protected int radius = 2;
	protected Color cCurrent = Color.RED;
	protected Color cOld = Color.GREEN;
	/** **/

	public DisplaySampleMap(GUI gui, Parameter displayed) {
		super(gui, displayed);
		this.space = displayed.getSpace();
		history = new ArrayList<double[]>();
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
		 
		value = new double[2];
		for(int i = 0 ; i < 2 ; i++){
			value[i] = ((AbstractMap)displayed).getParam(i).get();
		}
		
		//System.out.println(Arrays.toString(value) +Arrays.toString( Thread.currentThread().getStackTrace()));
	}


	
	@Override
	public void render(Graphics2D g) {
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
		
		double oriX = space.getOrigin()[X];
		double oriY = space.getOrigin()[Y];
		double factX = this.sx/dx;
		int x = (int) Math.round(((coord[X] - oriX) / factX));
		x += offsetX;
		
		double factY = this.sy/dy;
		int y = (int) Math.round(((coord[Y] - oriY) / factY));
		y += offsetY;
		
		
		
		
		return new int[]{x,y};
	}
	
	






}
