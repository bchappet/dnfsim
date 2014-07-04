package main.java.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.EventObject;

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

	public View2D(String name,double[][] initialState,ColorMap colorMap) {
		super(name);
		this.buffer = initialState;
		this.colorMap = colorMap;
		
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
		for(int i = 0 ; i < buffer[0].length ; i++)
		{
			for( int j = 0 ; j <buffer.length ; j ++)
			{
//				System.out.println("i:"+i+" j:"+j);
//				System.out.println(buffer[j][i]);
				tmp.setColor(colorMap.getColor(getValue(i,j)));
				tmp.fillRect(i,j,1,1);
			}
		}
		Image scaled = img.getScaledInstance(dx, dy, Image.SCALE_FAST);
		//System.out.println(dx+","+dy);
		g.drawImage(scaled,offsetX,offsetY,dx,dy,null);
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
