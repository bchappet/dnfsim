package main.java.view;

import java.awt.Color;

/**
 * Static color map
 *Colors are in RGBA to superpose map (exemple of highlighted trackable)
 * @author Benoit
 *
 */
public class ColorMap {

	/**End is outside the interval while start is included**/
	public static final int START = 0;
	public static final int MIDLE = 1;
	public static final int END = 2;
	/**End is outside the range**/

	protected Color[] fixedColors ; //3 color in rgba 
	protected double[] fixedPoints;
	

	public ColorMap(Color[] fixedColors)
	{
		this.fixedColors = fixedColors;
		this.fixedPoints = new double[3];
		this.fixedPoints[0] = -1;
		this.fixedPoints[1] = 0;
		this.fixedPoints[2] = 1;
		
	}
	
	
	
	/**
	 * Copy constructor
	 * @param col
	 */
	public ColorMap(ColorMap col)
	{
		this.fixedPoints = new double[3];
		this.fixedColors = new Color[3];
		System.arraycopy(col.fixedColors, 0, this.fixedColors, 0, col.fixedColors.length);
		System.arraycopy(col.fixedPoints, 0, this.fixedPoints, 0, col.fixedPoints.length);
	}

	public Color getColor(double point)  {
		//System.out.println(point);
		int[] compColor = new int[4];
		int[] closePoints = closestPoints(point);
		if(closePoints[0] == -1)
		{
			//Nothing return a black point
		}
		else
		{

			double ratio =( point- fixedPoints[closePoints[0]])/
					( fixedPoints[closePoints[1]]- fixedPoints[closePoints[0]]);
			for(int i = 0 ; i < 4 ; i++)
			{
				compColor[i] = (int) Math.round(getColorRGBA(fixedColors[closePoints[0]],i)+(ratio*(getColorRGBA(fixedColors[closePoints[1]],i) -
						getColorRGBA(fixedColors[closePoints[0]],i))));
			}
		}
		//System.out.println( new Color(compColor[0],compColor[1],compColor[2],compColor[3]));
		return new Color(compColor[0],compColor[1],compColor[2],compColor[3]);

	}

	public static int getColorRGBA(Color color,int rgb)
	{
		switch(rgb)
		{
		case 0 : return color.getRed();
		case 1 : return color.getGreen();
		case 2 : return color.getBlue();
		case 3 : return color.getAlpha();
		default : return -1;
		}
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
		if(i>= fixedPoints.length || point < fixedPoints[0])
		{
			System.err.println("The str " + point + " is outside the colorMap");
			ret[0] = -1;
			ret[1] = -1;
		}
		else
		{
			ret[0] = i-1;
			ret[1] = i;
		}
		return ret;
	}

	/**
	 * Return merge a on b
	 * @param a
	 * @param b
	 * @return
	 */
	public static Color merge(Color a, Color b) {
		
		int ra = a.getRed();
		int ga = a.getGreen();
		int ba = a.getBlue();
		int aa =a.getAlpha();
		
		int rb = b.getRed();
		int gb = b.getGreen();
		int bb = b.getBlue();
		int ab =b.getAlpha();
		
		
		int red = (ra * aa / 255) + (rb * ab * (255 - aa) / (255*255));
		int green = (ga * aa / 255) + (gb * ab * (255 - aa) / (255*255));
		int blue = (ba * aa / 255) + (bb * ab * (255 - aa) / (255*255));
		int alpha = aa + (ab * (255 - aa)/255);
		
		
		return new Color(red,green,blue,alpha);
		
		
	}
	
	public ColorMap clone()
	{
		return new ColorMap(this);
	}

	/**Reset the fixed point**/
	public void reset() {
		// nothing
		
	}

	



}
