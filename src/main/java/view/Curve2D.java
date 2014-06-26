package main.java.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.UIManager;

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
	private int nbDotMax=100;
	/** Y coordinate**/
	private List<Double> values;
	/**Min max for the graph axis**/
	private Coord2D<Double> minMax;
	
	/**
	 * 
	 * @param name
	 */
	public Curve2D(String name) {
		super(name);
		minMax = new Coord2D<Double>(0d,0d);
		values = new ArrayList<Double>();
	}
	
	public void update(Double coor)
	{
		values.add(coor);
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
	private void computeMinMax(List<Double> coords){
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


	@Override
	public void interact(EventObject event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactRelease(EventObject event) {
		// TODO Auto-generated method stub
		
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
		g.fillRect(0, 0, dx, dy);
		g.setColor(Color.BLACK);
		int offsetX = (int) ((dim.width - dx)/2d);
		int offsetY = (int) ((dim.height - dy)/2d);
		
		
		for(int i = 0 ; i < values.size()-1 ; i++){
			Double coord1 = values.get(i);
			Double coord2 = values.get(i+1);
			double sizeX = values.size()-1;
			//System.out.println("sizeX : " + sizeX + " sizeY : "+ sizeY);
			
			int x1 = (int) (i/sizeX* dx);
			int x2 = (int) ((i+1)/sizeX * dx);
			int y1 = transformValue(coord1,dy);
			int y2 =  transformValue(coord2,dy);
//			System.out.println("i: " + i + " " + x1+","+y1+","+x2+","+y2);
			g.drawLine(x1, dy-y1, x2, dy-y2);
		}
		
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);

		
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

}
