package main.java.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
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

	protected int[] neigh;

	protected Space mapSpace;

	//Graphics parameters
	
	protected Color cVal = Color.RED;
	protected Color cLinks = Color.GRAY;

	public Vector2DMapSOMView(String name, List<Coord2D<Double>> init,
			Coord<Var<Double>> spaceOri, Coord<Var<Double>> spaceLength,Neighborhood neigh,int volume) {
		super(name, init.get(0), spaceOri, spaceLength);
		link = new ArrayList<Coord<Integer>>(volume);
		values =init;
		for(int i = 0 ; i < volume; i++){
			this.neigh = neigh.getNeighborhood(i); //TODO not dynamic
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
		for(int i = 0 ; i < values.size() ; i++){
			
			
			
			for(int j = 0 ; j < neigh.length ; j++ ){
				int[] coordA = getCoord(values.get(i),dim);
				int[] coordB = getCoord(values.get(neigh[j]),dim);
//				System.out.println("draw between : " + Arrays.toString(coordA) + " and " + Arrays.toString(coordB));
				g.drawLine(coordA[X], coordA[Y], coordB[X], coordB[Y]);
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
		
//		for(int i = 0 ; i < mapSpace.getSimulationSpace().getDiscreteVolume() ; i++){
//			double[] weights = ((SomUM)((UnitMap) displayed).
//					getUnit(FramedSpaceIterator.framedToFullIndex(i, mapSpace)).getUnitModel()).getWeights();
//			//System.out.println(Arrays.toString(weights));
//			//values.add(torusProjection(weights)); TODO?
//		}
		
//		link.clear();
//		//System.out.println("Links : ");
//		for(int i = 0 ; i < mapSpace.getSimulationSpace().getDiscreteVolume() ; i++){
//			//System.out.println("i = " + i + " index = " +FramedSpaceIterator.framedToFullIndex(i, mapSpace) );
//			int[] toAdd = neigh.getNeighborhood(FramedSpaceIterator.framedToFullIndex(i, mapSpace));
//			for(int j = 0 ; j < toAdd.length ;  j++){
//				toAdd[j] = FramedSpaceIterator.fullToFramedIndex(toAdd[j], mapSpace);
////				if(toAdd[j] >= values.size()){
////					System.err.println("error");
////					System.exit(-1);
////				}
//			}
//			link.add(toAdd);
	}

}
