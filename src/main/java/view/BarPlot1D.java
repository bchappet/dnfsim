package main.java.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import main.java.maps.Var;
import main.java.space.Coord2D;

/**
 * Bar plot. Well adapted to static 1D data. Otherwise use View1D.
 * @author bchappet
 *
 */
public class BarPlot1D extends ParameterViewDB {
	/**Margin ratio**/
	private double margin = 0.99;
	/**Black border in pixel**/
	private int blackBorder = 1; 
	
	/**Color map**/
	private ColorMap colorMap;
	/**Min max for the graph axis**/
	private Coord2D<Double> minMax;

	protected double[] values;
	
	protected double widthBar; //
	protected double interBar;
	
	public BarPlot1D(String name,double[] initialState,ColorMap colorMap) {
		super(name);
		this.values = initialState;
		this.colorMap = colorMap;
		this.minMax = new Coord2D<Double>(0., 0.);
		this.computeMinMax(values);
		
	}
	
	public void update(double[] values)
	{
		this.values = values;
		this.computeMinMax(values);
	}
	
	@Override
	public void render(Graphics2D g) {
		Dimension dim = this.getSize();
		int dx = (int) (dim.width*margin);
		int dy = (int) (dim.height*margin);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, dim.width, dim.height);
		int offsetX = (int) ((dim.width - dx)/2d);
		int offsetY = (int) ((dim.height - dy)/2d);
		
		double sizeX = values.length;
		int width = (int)((dx/sizeX)/1.5);
		int offSet = (int)(width/2.);
		int origin = transformValue(0, dy);
		origin = dy -origin;
		for(int i = 0 ; i < values.length ; i++){
			g.setColor(colorMap.getColor(values[i]));
			//System.out.println("sizeX : " + sizeX + " sizeY : "+ sizeY);
			
			
			int x1 = (int) (i/sizeX* dx);
			int y1 = transformValue(values[i],dy);
			
			y1 = dy - y1; 
			
			
			
			if(values[i] > 0){
				g.fillRect(offSet+x1, y1, width,origin-y1 );
			}else{
				g.fillRect(offSet+x1,origin,width,y1-origin);
			}
			
			
		}
		
		g.setColor(Color.BLACK);
		//draw information
		g.drawString(String.format("%.2f", minMax.y), offsetX, 15);
		g.drawString(String.format("%.2f", minMax.x), offsetX, dy);
		
		//draw line for origin
		g.drawLine(offsetX, origin, dx, origin);
		
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);

	}
	
	/**
	 * Transform this value into an int to plot in a graph
	 * @param val
	 * @param plotSize
	 * @return
	 */
	public int transformValue(double val,int plotSize){
		double size = minMax.y - minMax.x;
		return  (int) ((val-minMax.x)/size * plotSize);
	}
	
	/** * Compute min and max from values
	 * @param coords2
	 * @param min2
	 * @param max2
	 */
	private  void computeMinMax(double[] values){
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		for(double coord : values){
			if(coord > maxX)
				maxX = coord;
			if(coord < minX)
				minX = coord;
		}
		minMax.x =  minX;
		minMax.y = maxX;
	}


}
