package main.java.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import main.java.space.Coord2D;

/**
 * Display the evolution of one parameter value.
 * The plot will only display the y values with a undefined X axis: 1 unit per value
 * @author benoit
 *
 */
public class Curve2D extends ParameterViewDB{
	
	/**Margin ratio**/
	private double margin = 0.99;
	/**Black border in pixel**/
	private int blackBorder = 1; 

	/**Max number of dot to display**/
	private int nbDotMax=500;
	/**True when we reached the nbDotMax**/
	private boolean reachNbDotMax = false;
	
	/** Y coordinate**/
	private LinkedList<Double> values;
	/**Min max for the graph axis**/
	private Coord2D<Double> minMax;
	
	/**
	 * Nb total of computation
	 */
	private int nbComputation = 0;
	
	
	/**
	 * 
	 * @param name
	 */
	public Curve2D(String name) {
		super(name);
		minMax = new Coord2D<Double>(0d,0d);
		values = new LinkedList<Double>();
	}
	
	public void reset(){
		values.clear();computeMinMax(values);
		reachNbDotMax = false;
		nbComputation = 0;
	}
	
	public synchronized void update(Double coor)
	{
		nbComputation ++;
		values.addFirst(coor);
		
		if(values.size() > nbDotMax){
			reachNbDotMax  = true;
		}
		
		if(reachNbDotMax){
			values.removeLast();
		}
		computeMinMax(values);
//		System.out.println("Min : " + min);
//		System.out.println("Max : " + max);
	}

	/**
	 * Compute min and max from values
	 * @param coords2
	 * @param min2
	 * @param max2
	 */
	private  void computeMinMax(List<Double> coords){
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		for(Double coord : coords){
			if(coord > maxX)
				maxX = coord;
			if(coord < minX)
				minX = coord;
		}
		minMax.x =  minX;
		minMax.y = maxX;
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

	@Override
	public void render(Graphics2D g) {
		Dimension dim = this.getSize();
		int dx = (int) (dim.width*margin);
		int dy = (int) (dim.height*margin);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, dim.width, dim.height);
		g.setColor(Color.BLACK);
		int offsetX = (int) ((dim.width - dx)/2d);
		int offsetY = (int) ((dim.height - dy)/2d);
		int origin = transformValue(0, dy);
		origin = dy -origin;
		double sizeX = values.size()-1;
		
		/**element t-1**/
		Double coord1 = values.getFirst();
		int i = 0;
		for(Double coord2 : values){//element t
			
			//System.out.println("sizeX : " + sizeX + " sizeY : "+ sizeY);
			
			int x1 = (int) (i/sizeX* dx);
			int x2 = (int) ((i+1)/sizeX * dx);
			int y1 = transformValue(coord1,dy);
			int y2 =  transformValue(coord2,dy);
//			System.out.println("i: " + i + " " + x1+","+y1+","+x2+","+y2);
			g.drawLine(x1, dy-y1, x2, dy-y2);
			coord1 = coord2;
			i++;
		}
		
		for(int j = 0 ; j < blackBorder ; j++)
			g.drawRect(offsetX-j, offsetY-j, dx+2*j, dy+2*j);
		
		//draw information
		g.drawString(String.format("%.2f", minMax.y), offsetX, 15);
		g.drawString(String.format("%.2f", minMax.x), offsetX, dy);
		
		g.drawString("0", offsetX, origin-5);
		
		//Offset to see all the number of time
		int xoffset = ((int) Math.log10(nbComputation)+1)*8;
		g.drawString(String.format("%d", nbComputation), dx-xoffset, 15);
		
		//draw line for origin
		g.drawLine(offsetX, origin, dx, origin);
	

		
	}
	
	public Curve2D clone(){
		Curve2D  clone = (Curve2D)super.clone();
		return clone;
		
	}

	/**
	 * @return the values
	 */
	protected List<Double> getValues() {
		return values;
	}


	public double getMin() {
		return minMax.x;
	}

	public double getMax() {
		return minMax.y;
	}

	public int getNbComputation() {
		return nbComputation;
	}

	

}
