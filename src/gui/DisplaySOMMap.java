package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maps.AbstractUnitMap;
import maps.NeighborhoodMap;
import maps.Parameter;
import neigborhood.Neighborhood;
import unitModel.SomUM;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Will display map weight in the 2D (spaceInput)
 * Link are displayed to
 * @author bchappet
 *
 */
public class DisplaySOMMap extends DisplaySampleMap {


	protected List<double[]> values;
	/**Link each point in values (ref by index) to others points**/
	protected List<int[]> link;

	protected Neighborhood neigh;


	//Graphics parameters
	
	protected Color cVal = Color.RED;
	protected Color cLinks = Color.GREEN;
	/** **/

	public DisplaySOMMap(GUI gui, Parameter displayed,Space inputSpace) {
		super(gui, displayed);
		this.neigh = ((NeighborhoodMap)displayed).getNeighborhood().get(0);

		this.space = ((SomUM)((AbstractUnitMap)displayed).getUnitModel()).getInputSpace();
		values = new ArrayList<double[]>();
		link = new ArrayList<int[]>();

	}




	@Override
	public void update() throws NullCoordinateException{
		//System.out.println("Weights : ");
		for(int i = 0 ; i < displayed.getSpace().getDiscreteVolume() ; i++){
			double[] weights = ((SomUM)((AbstractUnitMap) displayed).getUnit(i).getUnitModel()).getWeights();
			//System.out.println(Arrays.toString(weights));
			values.add(weights);
		}
		//System.out.println("Links : ");
		for(int i = 0 ; i < displayed.getSpace().getDiscreteVolume() ; i++){
			link.add(neigh.getNeighborhood(i));
			//System.out.println(Arrays.toString(link.get(link.size()-1)));
			
		}
		


	}


	@Override
	public void render(Graphics2D g) {

		
		Dimension dim = this.getSize();
		dx = (int) (dim.width*margin);
		dy = (int) (dim.height*margin);

		offsetX = (int) ((dim.width - dx)/2d);
		offsetY = (int) ((dim.height - dy)/2d);

		//System.out.println(space);
		g.setColor(cVal);
		for(double[] val : values)
		{
			//System.out.println(Arrays.toString(val));
			int[] xy = getCoord(val);
			//System.out.println(Arrays.toString(xy));
			g.drawOval(xy[X], xy[Y], radius*2, radius*2);
		}
		g.setColor(cLinks);
		for(int i = 0 ; i < displayed.getSpace().getDiscreteVolume() ; i++){
			for(Integer v : link.get(i)){
				int[] coordA = getCoord(values.get(i));
				int[] coordB = getCoord(values.get(v));
				//System.out.println("draw between : " + Arrays.toString(coordA) + " and " + Arrays.toString(coordB));
				g.drawLine(coordA[X], coordA[Y], coordB[X], coordB[Y]);
			}
			break;
		}


		//black border
		g.setColor(Color.black);
		for(int i = 0 ; i < blackBorder ; i++)
			g.drawRect(offsetX-i, offsetY-i, dx+2*i, dy+2*i);

	}
	
	






}
