package main.java.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main.java.space.Coord2D;

public class Curve2D extends ParameterView{
	
	/**Margin ratio**/
	private double margin = 0.99;
	/**Black border in pixel**/
	private int blackBorder = 1; 


	/**Max number of dot to display**/
	private int nbDotMax=100;


	/**X Y coordinate**/
	private List<Coord2D<Double>> coords;

	/**Min max for the graph axis**/
	private Coord2D<Double> min;
	private Coord2D<Double> max;
	
	
	
	
	public Curve2D() {
		min = new Coord2D<Double>(0d, 0d);
		max = new Coord2D<Double>(0d, 0d);
		coords = new ArrayList<Coord2D<Double>>();
	}
	
	public void update(Coord2D<Double> coor)
	{
		coords.add(coor);
		computeMinMax(coords,min,max);
//		System.out.println("Min : " + min);
//		System.out.println("Max : " + max);
	}

	/**
	 * Compute min and max from coords
	 * @param coords2
	 * @param min2
	 * @param max2
	 */
	private static void computeMinMax(List<Coord2D<Double>> coords,
			Coord2D<Double> min, Coord2D<Double> max) {
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;
		
		for(Coord2D<Double> coord : coords){
			if(coord.x > maxX)
				maxX = coord.x;
			if(coord.x < minX)
				minX = coord.x;
			
			if(coord.y > maxY)
				maxY = coord.y;
			if(coord.y < minY)
				minY = coord.y;
		}
		
		min.x = minX;
		min.y = minY;
		max.x = maxX;
		max.y = maxY;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		Dimension dim = this.getSize();
		int dx = (int) (dim.width*margin);
		int dy = (int) (dim.height*margin);

		int offsetX = (int) ((dim.width - dx)/2d);
		int offsetY = (int) ((dim.height - dy)/2d);
		
		
		
		for(int i = 0 ; i < coords.size()-1 ; i++){
			Coord2D<Double> coord1 = coords.get(i);
			Coord2D<Double> coord2 = coords.get(i+1);
			double sizeX = max.x - min.x;
			double sizeY = max.y - min.y;
			//System.out.println("sizeX : " + sizeX + " sizeY : "+ sizeY);
			
			int x1 = (int) ((coord1.x-min.x)/sizeX * dx);
			int x2 = (int) ((coord2.x-min.x)/sizeX * dx);
			int y1 = (int) ((coord1.y-min.y)/sizeY * dy);
			int y2 = (int) ((coord2.y-min.y)/sizeY * dy);
			//System.out.println(x1+","+y1+","+x2+","+y2);
			
			g2.drawLine(x1, dy-y1, x2, dy-y2);
		}
		
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);
	}

}
