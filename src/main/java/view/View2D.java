package main.java.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.EventObject;

import main.java.maps.Var;

/**
 * Respect the minimum knowledge paradigm
 * Can display wathever double matrix
 * Using a color map
 * @author benoit
 *
 */
public class View2D extends ParameterViewDB{

	/**Values to display**/
	protected double[][] buffer;
	/**Margin ratio**/
	private double margin = 0.99;
	/**Black border in pixel**/
	private int blackBorder = 1; 
	
	/**Color map**/
	private ColorMap colorMap;
	
	private Var<Boolean> grid;

	public View2D(String name,double[][] initialState,
			ColorMap colorMap,Var<Boolean> grid) {
		super(name);
		this.buffer = initialState;
		this.colorMap = colorMap;
		this.grid = grid;
		
	}
	
	public void reset(){
		colorMap.reset();
	}
	
	

	public void update(double[][] values)
	{
		this.buffer = values;
	}

	@Override
	public void render(Graphics2D g) {
		Dimension dim = this.getSize();
		int dx = (int) (dim.width*margin);
		int dy = (int) (dim.height*margin);

		int offsetX = (int) ((dim.width - dx)/2d);
		int offsetY = (int) ((dim.height - dy)/2d);

		Image img = createImage(buffer[0].length,buffer.length);
		Graphics tmp = img.getGraphics();
		for(int i = 0 ; i < buffer[0].length ; i++){
			for( int j = 0 ; j <buffer.length ; j ++){
				tmp.setColor(colorMap.getColor(getValue(i,j)));
				tmp.fillRect(i,j,1,1);
			}
		}
		Image scaled = img.getScaledInstance(dx, dy, Image.SCALE_FAST);
		//System.out.println(dx+","+dy);
		g.drawImage(scaled,offsetX,offsetY,dx,dy,null);
		if(grid.get()){
			//draw the grid
			for (int i = 0; i < buffer[0].length; i++) { //X
				int x = i * (dx/buffer[0].length);
				x += offsetX;
				g.drawLine(x, offsetY, x, dy+offsetY);
			}
			
			for (int j = 0; j < buffer.length; j++) { //Y
				int y = j * (dy/buffer.length);
				y += offsetY;
				g.drawLine(0, y, dx, y);
			}
			
		}
		
		g.setColor(Color.BLACK);
		//draw information
		g.drawString(String.format("%.2f", colorMap.getMax()), offsetX, 15);
		g.drawString(String.format("%.2f", colorMap.getMin()), offsetX, dy);
		
		
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);

	}
	/**
	 * Value accessed to have the color at the coordinate i,j
	 * @param i
	 * @param j
	 * @return
	 */
	protected double getValue(int i, int j) {	
		return buffer[j][i];
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
