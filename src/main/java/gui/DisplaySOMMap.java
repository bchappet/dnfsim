package main.java.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.FramedSpaceIterator;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.UnitMap;
import main.java.neigborhood.Neighborhood;
import main.java.unitModel.SomUM;

/**
 * Will display map weight in the 2D (spaceInput)
 * Link are displayed too
 * If the main.java.space is wrapped, we project the tore on a 2d screen
 * @author bchappet
 *
 */
public class DisplaySOMMap extends DisplaySampleMap {


	protected List<double[]> values;
	/**Link each point in values (ref by index) to others points**/
	protected List<int[]> link;

	protected Neighborhood neigh;

	protected Space mapSpace;

	//Graphics parameters
	
	protected Color cVal = Color.RED;
	protected Color cLinks = Color.GRAY;
	/** **/

	public DisplaySOMMap(RunnerGUI gui, Parameter displayed,Space inputSpace) {
		super(gui, displayed);
		this.neigh = ((NeighborhoodMap)displayed).getNeighborhoods().get(0);
		this.mapSpace = displayed.getSpace();
		this.space = ((SomUM)((UnitMap)displayed).getUnitModel()).getInputSpace();
		Double[] dim = space.getSize();
		sx = dim[X];
		sy = dim[Y];
		values = new ArrayList<double[]>();
		link = new ArrayList<int[]>();

	}




	@Override
	public void update() throws NullCoordinateException{
		values.clear();
		//System.out.println("Space : " + main.java.space);
	//	System.out.println("Weights : ");
		for(int i = 0 ; i < mapSpace.getSimulationSpace().getDiscreteVolume() ; i++){
			double[] weights = ((SomUM)((UnitMap) displayed).
					getUnit(FramedSpaceIterator.framedToFullIndex(i, mapSpace)).getUnitModel()).getWeights();
			//System.out.println(Arrays.toString(weights));
			values.add(torusProjection(weights));
		}
		
		link.clear();
		//System.out.println("Links : ");
		for(int i = 0 ; i < mapSpace.getSimulationSpace().getDiscreteVolume() ; i++){
			//System.out.println("i = " + i + " index = " +FramedSpaceIterator.framedToFullIndex(i, mapSpace) );
			int[] toAdd = neigh.getNeighborhood(FramedSpaceIterator.framedToFullIndex(i, mapSpace));
			for(int j = 0 ; j < toAdd.length ;  j++){
				toAdd[j] = FramedSpaceIterator.fullToFramedIndex(toAdd[j], mapSpace);
//				if(toAdd[j] >= values.size()){
//					System.err.println("error");
//					System.exit(-1);
//				}
			}
			link.add(toAdd);
			//System.out.println(Arrays.toString(link.get(link.size()-1)));
		}
		


	}

	/**
	 * Project on a tore if we are on one
	 * @param weights
	 * @return
	 */
	private double[] torusProjection(double[] weights) {
		if(space.isWrap()){
			
			
			double a = 1;//mapSpace.getResolution()/(2*Math.PI)*2; //radius of tube
			double c = 0;//a/2; //center to the hole to the center of the torus
			
			
			double u = weights[Space.X]/1.3 ;
			double v = weights[Space.Y]/1.3 ;
			
//			double v = Math.asin(z/a);
//			double u1 = Math.acos(x/(c + a*Math.cos(v)));
//			double u2 = Math.asin(x/(c + a*Math.cos(v)));
			
			double x = (c + a * Math.cos(v))*Math.cos(u);
			double y = (c + a * Math.cos(v))*Math.sin(u);
			double z = a * Math.sin(v);
			
//			System.out.println("u : " + u + " v : " + v );
//			System.out.println(" x : " + x + " y : " + y + " z : " + z);
			
//			return weights.clone();
			return new double[]{z,y};
			
			
		}else{
			return weights.clone();
		}
	}




	@Override
	public void render(Graphics2D g) {

		g.clearRect(0, 0, getWidth(), getHeight() );
		
		Dimension dim = this.getSize();
		dx = (int) (dim.width*margin);
		dy = (int) (dim.height*margin);
		
		
		
		offsetX = (int) ((dim.width - dx)/2d);
		offsetY = (int) ((dim.height - dy)/2d);

		
		g.setColor(cLinks);
		for(int i = 0 ; i < mapSpace.getSimulationSpace().getDiscreteVolume() ; i++){
			int[] neigh = link.get(i);
			for(int j = 0 ; j < neigh.length ; j++ ){
				int n = neigh[j];
				int[] coordA = getCoord(values.get(i));
				int[] coordB = getCoord(values.get(neigh[j]));
//				System.out.println("draw between : " + Arrays.toString(coordA) + " and " + Arrays.toString(coordB));
				g.drawLine(coordA[X], coordA[Y], coordB[X], coordB[Y]);
			}
//			break;
		}
		
		g.setColor(cVal);
		for(double[] val : values)
		{
			int[] xy = getCoord(val);
//			System.out.println(Arrays.toString(xy));
			g.drawOval(xy[X], xy[Y], radius*2, radius*2);
		}
		


		//black border
		g.setColor(Color.black);
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);

	}
	
	
	
	
	






}
