package main.resources.utils;

import main.java.coordinates.Coor;


@Deprecated
public class Point extends java.awt.Point {

	

	public static final int WEST = 0;
	public static final int EAST = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;

	public Point(int x, int y) {
		super(x, y);
	}
	
	
	public Point(Coor<Integer> coor) {
		this(coor.get(Coor.X),coor.get(Coor.Y));
	}

	


	public Point getNord()
	{
		return new Point(x,y+1);
	}
	
	public Point getSouth()
	{
		return new Point(x,y-1);
	}
	
	public Point getEast()
	{
		return new Point(x+1,y);
	}
	
	public Point getWest()
	{
		return new Point(x-1,y);
	}
	
	/**
	 * Return the distance between this point and pt
	 * @original pt
	 * @return
	 */
	public double distance(Point pt)
	{
		return Math.max(Math.abs(x-pt.x),Math.abs(y-pt.y));
		//return Math.sqrt((x-pt.x) * (x-pt.x)+ (y-pt.y)*(y-pt.y));
	}

	/**
	 * Return the direction from this point to the target
	 * @original target
	 * @return
	 */
	public int direction(Point target) {
		int direction = -1;
		if(target.y > y)
			direction = NORTH;
		else if(target.y < y)
			direction = SOUTH;
		else if(target.x > x)
			direction = EAST;
		else if(target.x < x)
			direction = WEST;
		
		return direction;
	}
	
	

}
