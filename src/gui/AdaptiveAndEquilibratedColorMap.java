package gui;

import java.awt.Color;


public class AdaptiveAndEquilibratedColorMap extends AdaptiveColorMap {
	
	protected double beta = 0.01; //Basis of apprentissage
	protected int  repError = 0; //Repetition of error in the recursive call of closestPoint

	public AdaptiveAndEquilibratedColorMap(Color[] fixedColors) {
		super(fixedColors);
		this.alpha = 1;
		this.fixedPoints = new double[3];
		this.fixedPoints[0] = -0.0001;
		this.fixedPoints[1] = 0;
		this.fixedPoints[2] =  - this.fixedPoints[0];
	}
	
	public AdaptiveAndEquilibratedColorMap(AdaptiveAndEquilibratedColorMap map)
	{
		super(map);
		this.beta = map.beta;
		this.repError = map.repError;
	}


	public int[] closestPoints(double point)
	{
		
		//We try to diminish the threshold
//		fixedPoints[fixedPoints.length-1]-= beta;
//		fixedPoints[0] += beta;
//		if(point >1)
//		System.out.println("Point : " + point);
		int[] ret = new int[2];
		int i = 0;
		
	

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
				fixedPoints[fixedPoints.length-1] = point+(beta*alpha);
			}
			else
			{
				fixedPoints[0] = point-(beta*alpha);
			}
			repError ++;
			alpha = (repError % 10) * alpha+1;
			//System.out.println("repError : " + repError + " alpha : " + alpha);
			ret = closestPoints(point);

		}
		else
		{
			
			ret[0] = i-1;
			ret[1] = i;
			
		}
		//Equilibrate the two fixed points : they need to be symetric 
		double max = Math.max(Math.abs(fixedPoints[0]),Math.abs(fixedPoints[2]));
		fixedPoints[0] = - max;
		fixedPoints[2] = max;
		repError = 0;
		return ret;
	}
	
	public AdaptiveAndEquilibratedColorMap clone()
	{
		return new AdaptiveAndEquilibratedColorMap(this);
	}
}
