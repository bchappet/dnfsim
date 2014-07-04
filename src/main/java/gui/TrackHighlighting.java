package main.java.gui;

import java.awt.Color;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.AbstractMap;
import main.java.maps.Parameter;

public class TrackHighlighting implements Updated {
	
	/**Reference to the curretly hilighted Track**/
	protected AbstractMap highlighted;
	protected double[][] highlightedBuffer;
	protected ColorMap highlightColorMap;
	protected boolean displayed;
	
	protected int sx,sy;
	
	public static int num = 0;
	
	public int nb;
	
	
	public TrackHighlighting(){
		Color highColor[] = {new Color(0,0,0,0),new Color(0,0,0,0), Color.YELLOW};
		highlightColorMap = new AdaptiveAndEquilibratedColorMap(highColor);
		displayed = false;
		nb = num;
		num++;
		
		
	}
	
	public  TrackHighlighting(ColorMap highlightColorMap){
		this.highlightColorMap = highlightColorMap;
		displayed = false;
		nb = num;
		num++;
		
		
	}
	
	public void setDim(int _sx,int _sy){
		
		if(sx != _sx && sy != _sy){
			 highlightedBuffer = new double[_sx][_sy];
		}
		
		sx = _sx;
		sy = _sy;
		
	}

	public boolean isDisplayed() {
		return displayed;
	}

	public void setDisplayed(boolean displayed) {
		this.displayed = displayed;
	}

	

	public void setHilightedTrack(AbstractMap closest) {
		this.highlighted = closest;
		
	}

	public boolean isTheTrack(AbstractMap closest) {
		return highlighted.equals(closest);
	}

	public Color getColor(int i, int j) {
		//System.out.println(highlightedBuffer+ "@  " +highlightedBuffer[i][j]);
		return highlightColorMap.getColor(highlightedBuffer[i][j]);
	}

	@Override
	public void update() throws NullCoordinateException {
		if(isDisplayed()){
			for(int i = 0 ; i < sx ; i++)
			{
				for(int j = 0 ; j < sy ; j++)
				{
					//Double[] coord = displayed.getSpace().continuousProj(new Double[]{(double)i,(double)j});
					
					highlightedBuffer[i][j] = highlighted.getFast(i,j);
					//System.out.println(highlightedBuffer+ "  " +highlightedBuffer[i][j]);
				}
			}
		}
		
	}

	public void reset() {
		highlighted = null;
		displayed = false;
		highlightColorMap.reset();
		sx = 0;
		sy = 0;
		highlightedBuffer = null;
	}
	
	

}
