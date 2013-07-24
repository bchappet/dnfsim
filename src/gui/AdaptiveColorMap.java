package gui;

import java.awt.Color;

public class AdaptiveColorMap extends ColorMap {
	
	protected double alpha = 0.01; //adaptation ratio
	
	
	public AdaptiveColorMap(Color[] fixedColors)
	{
		super(fixedColors);
		this.fixedPoints = new double[3];
		this.fixedPoints[0] = -0.0001;
		this.fixedPoints[1] = 0;
		this.fixedPoints[2] = 0.0001;
	}
	
	public AdaptiveColorMap(AdaptiveColorMap map)
	{
		super(map);
		this.alpha = map.alpha;
	}
	
	
	public int[] closestPoints(double point)
	{
		int i = 0;
		int[] ret = new int[2];
		while(i < fixedPoints.length && point >= fixedPoints[i])
		{
			i++;
		}
		//i >= fixedPoint.length or point <= fixedPoint[i]
		
		//The str is outside the color Map => we rise the thresholds
		if(i>= fixedPoints.length || point < fixedPoints[0])
		{
			//System.err.println("The str " + point + " is outside the colorMap");
			if(i>= fixedPoints.length)
			{
				fixedPoints[fixedPoints.length-1] = point+alpha;
			}
			else
			{
				fixedPoints[0] = point-alpha;
			}
			ret = closestPoints(point);
		}
		else
		{
			ret[0] = i-1;
			ret[1] = i;
		}
		return ret;
	}
	
	public AdaptiveColorMap clone()
	{
		return new AdaptiveColorMap(this);
	}
	
	/**Reset the fixed point**/
	public void reset() {
		this.fixedPoints = new double[3];
		this.fixedPoints[0] = -0.0001;
		this.fixedPoints[1] = 0;
		this.fixedPoints[2] = 0.0001;
		
	}

}
