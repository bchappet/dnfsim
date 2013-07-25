package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import maps.Map;
import maps.Parameter;
import model.Model;
import coordinates.NullCoordinateException;

/**
 * In this implementation there is a thread for each map view
 * @author Benoit
 *
 */
public class DynamicParamsView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718261595171377066L;

	protected List<QuickViewPanel> maps;
	protected Model model;
	protected GUI gui;
	
	//Setting of the GridLayout
	protected int nbRow;
	protected int nbCol;
	
	/**To highlight the selected track**/
	protected TrackHighlighting trackHigh;


	public DynamicParamsView(GUI gui)
	{
		super(new GridLayout(1,1));
		trackHigh = new TrackHighlighting();
		gui.addUpdated(trackHigh);
		nbCol = 1;
		nbRow = 1;
		this.gui = gui;
		this.maps = new LinkedList<QuickViewPanel>();
	}
	
//	public synchronized void update()
//	{
//		System.out.println("update");
//		for(QuickViewPanel m : maps)
//		{
//			try {
//				m.update();
//			} catch (NullCoordinateException e) {
//				e.printStackTrace();
//				System.exit(-1);
//			}
//		}
//	}
	
	/**
	 * Return a list with every displayed parameter
	 * @return
	 */
	public List<Parameter> getDisplayedMaps(){
		List<Parameter> disp = new LinkedList<Parameter>();
		for(QuickViewPanel qvp : maps){
			disp.add( qvp.getDisplayed());
		}
		return disp;
	}

	


	/**
	 * Add a map in the display vue
	 * @param panel
	 */
	public synchronized void addView(QuickViewPanel panel)
	{
		((DisplayMap)panel).setTrackHigh(trackHigh);
		panel.setDisplayed(true);
		panel.setPreferredSize(new Dimension(300,300));
		maps.add(panel);
		calculateGridLayout(maps.size());
		this.add(panel.getBorderPane());
		this.validate();
	}
	
	/**
	 * Recalculate the nbCol and nbRow and update the layout if necessary
	 * @param size : the new size
	 */
	protected void calculateGridLayout(int size) {
		if(size == nbRow*nbCol)
		{
			//NoProblem
		}
		else if(size > nbRow*nbCol)
		{
			//We need to increase the capacity
			if(nbRow < nbCol)
			{
				nbRow ++;
			}
			else if(nbRow == nbCol)
			{
				nbCol ++;
			}
			else
			{
				throw new IllegalArgumentException("Impossible");
			}
			
			this.setLayout(new GridLayout(nbRow,nbCol));
		}
		else
		{
			
			//Do we need to decrease
			if(nbRow == nbCol)
			{//We test to decrease the number of row
				if(size == ((nbRow -1) * nbCol))
				{
					nbRow --;
					this.setLayout(new GridLayout(nbRow,nbCol));
				}
				else
				{
					//No problem;
				}
			}
			else if(nbRow < nbCol)
			{
				//We test to decrease the number of col
				if(size == ((nbCol -1) * nbRow))
				{
					nbCol --;
					this.setLayout(new GridLayout(nbRow,nbCol));
				}
				else
				{
					//No problem;
				}
			}
			else
			{
				throw new IllegalArgumentException("Impossible");
			}
		}
		
		
		
	}

	public void removeView(QuickViewPanel pane) {
		pane.setDisplayed(false);
		maps.remove(pane);
		calculateGridLayout(maps.size());
		this.remove(pane.getBorderPane());
		this.validate();
	}
	
	
//	/**
//	 * Remove a map from the display vue
//	 * @param param
//	 */
//	public synchronized void remove(Updatable param)
//	{
//		QuickViewPanel comp = findCompOf(param);
//		params.remove(comp);
//		this.remove(comp);
//	}
	/**
	 * Remove every display panel
	 */
	public synchronized void clear()
	{
		maps.clear();
		trackHigh.reset();
		gui.addUpdated(trackHigh);
		nbRow = 1;
		nbCol = 1;
		this.setLayout(new GridLayout(nbRow,nbCol));
		this.removeAll();
	}

	/**
	 *Return the MapRenderer associated with the param 
	 */
	protected QuickViewPanel findCompOf(Parameter param) {
		QuickViewPanel ret = null;
		for(QuickViewPanel m : maps)
		{
			if(m.getDisplayed().hashCode() == param.hashCode())
				ret = m;
		}
		
		return ret;
	}

	

	

	
	




	




	
}
