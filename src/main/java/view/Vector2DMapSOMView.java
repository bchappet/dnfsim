package main.java.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import main.java.coordinates.Space;
import main.java.maps.Var;
import main.java.neigborhood.Neighborhood;
import main.java.space.Coord;
import main.java.space.Coord2D;

public class Vector2DMapSOMView extends DisplaySampleMap2D {


	/**Link each point in values (ref by index) to others points**/
	protected List<Coord<Integer>> link;
	protected List<Coord2D<Double>> values;

	protected List<int[]> neigh;

	protected Space mapSpace;

	//Graphics parameters

	protected Color cVal = Color.RED;
	protected Color cLinks = Color.GRAY;

	public Vector2DMapSOMView(String name, List<Coord2D<Double>> init,
			Coord<Var<Double>> spaceOri, Coord<Var<Double>> spaceLength,Neighborhood neigh,int volume) {
		super(name, init.get(0), spaceOri, spaceLength);
		this.neigh = new LinkedList<int[]>();
		link = new ArrayList<Coord<Integer>>(volume);
		values =init;
		for(int i = 0 ; i < volume; i++){
			this.neigh.add(neigh.getNeighborhood(i)); //TODO not dynamic
		}
	}


	@Override
	public void render(Graphics2D g) {
		Dimension dim = this.getSize();
		int dx = (int) (dim.width*margin);
		int dy = (int) (dim.height*margin);

		int offsetX = (int) ((dim.width - dx)/2d);
		int offsetY = (int) ((dim.height - dy)/2d);

		g.setColor(cVal);
		for(Coord<Double> old : values)
		{
			int[] xy = getCoord(old,dim);
			//System.out.println("Old : " + Arrays.toString(xy));
			g.drawOval(xy[X], xy[Y], radius*2, radius*2);
		}

		g.setColor(cLinks);
			
			for(int i = 0 ; i < neigh.size() ; i++ ){
				int[] array = neigh.get(i);
				//System.out.println("i " + i + "   " +Arrays.toString(array));
				int[] coordA = getCoord(values.get(i),dim);
				for(int j = 0 ; j < array.length ; j++){
					try{
						int[] coordB = getCoord(values.get(array[j]),dim);
					//	System.out.println("draw between : " + Arrays.toString(coordA) + " and " + Arrays.toString(coordB));
						g.drawLine(coordA[X], coordA[Y], coordB[X], coordB[Y]);
					}catch(ArrayIndexOutOfBoundsException e){
						//e.printStackTrace();
					}
				}
			//			break;
		}

		//black border
		//Image scaled = img.getScaledInstance(dx, dy, Image.SCALE_FAST);
		//System.out.println(dx+","+dy);
		//g.drawImage(scaled,offsetX,offsetY,dx,dy,null);
		g.setColor(Color.black);
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);

	}

	public void update(List<Coord2D<Double>> values){
		this.values = values;

	}
}
